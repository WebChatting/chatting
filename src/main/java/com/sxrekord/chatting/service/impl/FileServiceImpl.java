package com.sxrekord.chatting.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sxrekord.chatting.dao.*;
import com.sxrekord.chatting.model.po.FileContent;
import com.sxrekord.chatting.model.po.Group;
import com.sxrekord.chatting.model.po.ImageContent;
import com.sxrekord.chatting.model.po.User;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.FileService;
import com.sxrekord.chatting.util.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rekord
 * @date 2023/4/11 10:48
 */
@Service
public class FileServiceImpl implements FileService {

    private final static String SERVER_URL_PREFIX = "upload" + FileUtils.SLASH;
    @Value("${file.upload.location}")
    private String FILE_STORE_PATH;

    @Autowired
    FileDao fileDao;
    
    @Override
    public ResponseJson upload(MultipartFile file) {
        // 重命名文件，防止重名
        String filename = FileUtils.calculateFileHash(file);
        String originalFilename = file.getOriginalFilename();
        String fileSize = FileUtils.getFormatSize(file.getSize());
        String suffix = "";
        // 截取文件的后缀名
        if (originalFilename.contains(FileUtils.DOT)) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf(FileUtils.DOT));
        }
        filename = filename + suffix;

        System.out.println("存储路径为:" + FILE_STORE_PATH + filename);
        new File(FILE_STORE_PATH).mkdir();
        Path filePath = Paths.get(FILE_STORE_PATH, filename);
        try {
            Files.copy(file.getInputStream(), filePath);
            // 将文件保存到数据库
            fileDao.insert(new com.sxrekord.chatting.model.po.File(fileSize, SERVER_URL_PREFIX + filename));
        } catch (FileAlreadyExistsException e) {
            System.out.println("复用文件");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return new ResponseJson().error("文件上传发生错误！");
        }

        return new ResponseJson().success()
                .setData("originalFilename", originalFilename)
                .setData("fileSize", fileSize)
                .setData("fileUrl", SERVER_URL_PREFIX + filename);
    }

    @Autowired
    UserDao userDao;
    @Autowired
    GroupDao groupDao;
    @Autowired
    FileContentDao fileContentDao;
    @Autowired
    ImageContentDao imageContentDao;

    @Override
    public void tidyUp() {
        List<com.sxrekord.chatting.model.po.File> files = fileDao.selectList(null);

        // 处理永不过期的文件
        List<IdAndPathAndFileId> userAvatarPath = userDao
                .selectList(Wrappers.<User>lambdaQuery()
                .select(User::getId, User::getAvatarPath, User::getFileId))
                .stream()
                .map(user -> new IdAndPathAndFileId(user.getId(), user.getAvatarPath(), user.getFileId()))
                .collect(Collectors.toList());
        handleFileAssociation(userAvatarPath, files, 1, 0);
        List<IdAndPathAndFileId> groupAvatarPath = groupDao
                .selectList(Wrappers.<Group>lambdaQuery()
                .select(Group::getId, Group::getAvatarPath, Group::getFileId))
                .stream()
                .map(group -> new IdAndPathAndFileId(group.getId(), group.getAvatarPath(), group.getFileId()))
                .collect(Collectors.toList());
        handleFileAssociation(groupAvatarPath, files, 1, 1);
        List<IdAndPathAndFileId> imageContentPath = imageContentDao
                .selectList(Wrappers.<ImageContent>lambdaQuery()
                .select(ImageContent::getId, ImageContent::getPath, ImageContent::getFileId))
                .stream()
                .map(imageContent -> new IdAndPathAndFileId(imageContent.getId(), imageContent.getPath(), imageContent.getFileId()))
                .collect(Collectors.toList());
        handleFileAssociation(imageContentPath, files, 1, 2);

        // 处理可能会过期的文件
        List<IdAndPathAndFileId> fileContentPath = fileContentDao
                .selectList(Wrappers.<FileContent>lambdaQuery()
                .select(FileContent::getId, FileContent::getPath, FileContent::getFileId))
                .stream()
                .map(fileContent -> new IdAndPathAndFileId(fileContent.getId(), fileContent.getPath(), fileContent.getFileId()))
                .collect(Collectors.toList());
        handleFileAssociation(fileContentPath, files, 0, 3);
    }

    @Override
    public void clean() {
        List<com.sxrekord.chatting.model.po.File> files = fileDao.selectList(null);
        for (com.sxrekord.chatting.model.po.File file : files) {
            if (isRemoveFile(file)) {
                // 删除数据库记录
                fileDao.deleteById(file.getId());
                // 删除本地文件
                FileUtils.removeFile(Paths.get(FILE_STORE_PATH,
                        file.getPath().substring(SERVER_URL_PREFIX.length() + 1)).toString());
            }
        }
    }

    private boolean isRemoveFile(com.sxrekord.chatting.model.po.File file) {
        return file.getExpirePolicy() == 0
                || file.getExpirePolicy() == 1 && file.getExpireTime().before(new Date());
    }

    /**
     * 处理不过期文件
     */
    private void handleFileAssociation(List<IdAndPathAndFileId> nonExpireFiles,
                                       List<com.sxrekord.chatting.model.po.File> allFiles,
                                       Integer expirePolicy, Integer type) {
        for (IdAndPathAndFileId idAndPathAndFileId : nonExpireFiles) {
            if (idAndPathAndFileId.getFileId() != null) {
                continue;
            }
            for (com.sxrekord.chatting.model.po.File file : allFiles) {
                if (file.getPath().equals(idAndPathAndFileId.getPath())) {
                    // 关联文件表并更新过期政策
                    Long id = idAndPathAndFileId.getId();
                    Long fileId = file.getId();
                    switch (type) {
                        case 0:
                            userDao.update(new User(id, fileId), Wrappers.<User>lambdaUpdate()
                                    .eq(User::getId, id)
                                    .set(User::getFileId, fileId));
                            break;
                        case 1:
                            groupDao.update(new Group(id, fileId), Wrappers.<Group>lambdaUpdate()
                                    .eq(Group::getId, id)
                                    .set(Group::getFileId, fileId));
                            break;
                        case 2:
                            imageContentDao.update(new ImageContent(id, fileId), Wrappers.<ImageContent>lambdaUpdate()
                                    .eq(ImageContent::getId, id)
                                    .set(ImageContent::getFileId, fileId));
                            break;
                        case 3:
                            fileContentDao.update(new FileContent(id, fileId), Wrappers.<FileContent>lambdaUpdate()
                                    .eq(FileContent::getId, id)
                                    .set(FileContent::getFileId, fileId));
                            break;
                        default:
                            System.out.println("未知类型错误");
                    }
                    file.setExpirePolicy(Math.max(1, file.getExpirePolicy()) + expirePolicy);
                    fileDao.updateExpirePolicy(file);
                    break;
                }
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class IdAndPathAndFileId {
        private Long id;
        private String path;
        private Long fileId;
    }
}

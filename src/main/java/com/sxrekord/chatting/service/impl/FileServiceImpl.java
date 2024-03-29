package com.sxrekord.chatting.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sxrekord.chatting.common.ExpirePolicy;
import com.sxrekord.chatting.common.FileAssociationType;
import com.sxrekord.chatting.dao.*;
import com.sxrekord.chatting.model.po.FileContent;
import com.sxrekord.chatting.model.po.Group;
import com.sxrekord.chatting.model.po.ImageContent;
import com.sxrekord.chatting.model.po.User;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.FileService;
import com.sxrekord.chatting.util.FileUtils;
import com.sxrekord.chatting.util.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rekord
 * @date 2023/4/11 10:48
 */
@Service
public class FileServiceImpl implements FileService {
    private final static String SERVER_URL_PREFIX = "/chatting/upload" + FileUtils.SLASH;
    @Value("#{ T(java.lang.System).getProperty('os.name').contains('Windows') ? '${file.upload.location.windows}' : '${file.upload.location.linux}' }")
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
        new File(FILE_STORE_PATH).mkdirs();
        Path filePath = Paths.get(FILE_STORE_PATH, filename);
        try {
            Files.copy(file.getInputStream(), filePath);
            // 将文件保存到数据库
            fileDao.insert(new com.sxrekord.chatting.model.po.File(fileSize, SERVER_URL_PREFIX + filename));
        } catch (FileAlreadyExistsException e) {
            System.out.println("复用文件");
        } catch (NoSuchFileException | AccessDeniedException nfe) {
            return new ResponseJson(530).setMsg("文件存取路径无效(路径有误或没有写入权限)");
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
        List<FileAssociation> userAvatarPath = userDao
                .selectList(Wrappers.<User>lambdaQuery()
                .isNull(User::getFileId)
                .select(User::getId, User::getAvatarPath, User::getFileId, User::getUpdateTime))
                .stream()
                .map(user -> new FileAssociation(user.getId(), user.getAvatarPath(),
                        user.getFileId(), user.getUpdateTime()))
                .collect(Collectors.toList());
        handleFileAssociation(userAvatarPath, files, ExpirePolicy.PERMANENT_ASSOCIATION, FileAssociationType.UserAvatar);
        List<FileAssociation> groupAvatarPath = groupDao
                .selectList(Wrappers.<Group>lambdaQuery()
                .isNull(Group::getFileId)
                .select(Group::getId, Group::getAvatarPath, Group::getFileId, Group::getUpdateTime))
                .stream()
                .map(group -> new FileAssociation(group.getId(), group.getAvatarPath(),
                        group.getFileId(), group.getUpdateTime()))
                .collect(Collectors.toList());
        handleFileAssociation(groupAvatarPath, files, ExpirePolicy.PERMANENT_ASSOCIATION, FileAssociationType.GroupAvatar);
        List<FileAssociation> imageContentPath = imageContentDao
                .selectList(Wrappers.<ImageContent>lambdaQuery()
                .isNull(ImageContent::getFileId)
                .select(ImageContent::getId, ImageContent::getPath, ImageContent::getFileId, ImageContent::getUpdateTime))
                .stream()
                .map(imageContent -> new FileAssociation(imageContent.getId(), imageContent.getPath(),
                        imageContent.getFileId(), imageContent.getUpdateTime()))
                .collect(Collectors.toList());
        handleFileAssociation(imageContentPath, files, ExpirePolicy.PERMANENT_ASSOCIATION, FileAssociationType.ImageContent);

        // 处理可能会过期的文件
        List<FileAssociation> fileContentPath = fileContentDao
                .selectList(Wrappers.<FileContent>lambdaQuery()
                .isNull(FileContent::getFileId)
                .select(FileContent::getId, FileContent::getPath, FileContent::getFileId, FileContent::getUpdateTime))
                .stream()
                .map(fileContent -> new FileAssociation(fileContent.getId(), fileContent.getPath(),
                        fileContent.getFileId(), fileContent.getUpdateTime()))
                .collect(Collectors.toList());
        handleFileAssociation(fileContentPath, files, ExpirePolicy.TIMING_ASSOCIATION, FileAssociationType.FileContent);
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
                        file.getPath().substring(SERVER_URL_PREFIX.length())).toString());
            }
        }
    }

    @Override
    public void updateFileAssociation(Long fileId) {
        com.sxrekord.chatting.model.po.File file = fileDao.selectById(fileId);
        file.setExpirePolicy(file.getExpirePolicy() - ExpirePolicy.PERMANENT_ASSOCIATION);
        file.setExpireTime(TimeUtils.subtractDaysFromDate(file.getExpireTime(), ExpirePolicy.EXPIRE_DAY_INTERVAL));
        fileDao.updateExpirePolicy(file);
    }

    private boolean isRemoveFile(com.sxrekord.chatting.model.po.File file) {
        return file.getExpirePolicy().equals(ExpirePolicy.DIRECT_EXPIRATION)
                || file.getExpirePolicy().equals(ExpirePolicy.TIMED_EXPIRATION) && file.getExpireTime().before(new Date());
    }

    /**
     * 处理不过期文件
     */
    private void handleFileAssociation(List<FileAssociation> nonExpireFiles,
                                       List<com.sxrekord.chatting.model.po.File> allFiles,
                                       Integer expirePolicy, FileAssociationType type) {
        for (FileAssociation fileAssociation : nonExpireFiles) {
            for (com.sxrekord.chatting.model.po.File file : allFiles) {
                if (file.getPath().equals(fileAssociation.getPath())) {
                    // 关联文件表并更新过期政策
                    Long id = fileAssociation.getId();
                    Long fileId = file.getId();
                    switch (type) {
                        case UserAvatar:
                            userDao.update(new User(id, fileId), Wrappers.<User>lambdaUpdate()
                                    .eq(User::getId, id)
                                    .set(User::getFileId, fileId));
                            break;
                        case GroupAvatar:
                            groupDao.update(new Group(id, fileId), Wrappers.<Group>lambdaUpdate()
                                    .eq(Group::getId, id)
                                    .set(Group::getFileId, fileId));
                            break;
                        case ImageContent:
                            imageContentDao.update(new ImageContent(id, fileId), Wrappers.<ImageContent>lambdaUpdate()
                                    .eq(ImageContent::getId, id)
                                    .set(ImageContent::getFileId, fileId));
                            break;
                        case FileContent:
                            fileContentDao.update(new FileContent(id, fileId), Wrappers.<FileContent>lambdaUpdate()
                                    .eq(FileContent::getId, id)
                                    .set(FileContent::getFileId, fileId));
                            break;
                        default:
                            System.out.println("未知类型错误");
                    }
                    file.setExpirePolicy(Math.max(ExpirePolicy.TIMED_EXPIRATION, file.getExpirePolicy()) + expirePolicy);
                    file.setExpireTime(fileAssociation.getExpireTime());
                    fileDao.updateExpirePolicy(file);
                    break;
                }
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class FileAssociation {
        private Long id;
        private String path;
        private Long fileId;
        private Date expireTime;
    }
}

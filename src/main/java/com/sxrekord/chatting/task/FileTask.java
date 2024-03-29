package com.sxrekord.chatting.task;

import com.sxrekord.chatting.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * @author Rekord
 * @date 2023/4/6 15:11
 */
@Component
@Slf4j
@EnableScheduling
public class FileTask implements SchedulingConfigurer {

    @Autowired
    private FileService fileService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(() -> cleanExpiredFile(),
                triggerContext -> {
                    // 可动态指定，默认为每周六4点执行一次
                    String cron = "0 0 4 ? * SAT";
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                });
    }

    public void cleanExpiredFile() {
        log.info("文件开始整理...");
        // 整理文件
        fileService.tidyUp();
        // 清理文件
        fileService.clean();
        log.info("文件整理完成。");
    }
}

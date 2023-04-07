package com.sxrekord.chatting.task;

import com.sxrekord.chatting.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
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
@EnableScheduling
public class FileTask implements SchedulingConfigurer {

    @Value("${file.upload.location}")
    private String FILE_STORE_PATH;

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
//        FileUtils.removeFile(FILE_STORE_PATH);
        System.out.println("hello world schedule!");
    }
}

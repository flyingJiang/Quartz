package com.cn.flying.job.factory;

import java.util.TimeZone;

import com.cn.flying.job.SimpleJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class SimpleJobConfig {

    @Bean("jobDetail")
    public JobDetailFactoryBean jobDetail(@Value("${simple.job.group.name}") String groupName,
                                                    @Value("${simple.job.name}") String jobName) {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(SimpleJob.class);
        jobDetailFactory.setDurability(true);
        jobDetailFactory.setName(jobName);
        jobDetailFactory.setGroup(groupName);
        return jobDetailFactory;
    }

    @Bean("jobTrigger")
    public CronTriggerFactoryBean jobCCBTrigger(
            JobDetail jobDetail,
            @Value("${simple.job.group.name}") String groupName,
            @Value("${simple.jobTrigger.name}") String triggerName,
            @Value("${simple.jobTask.cron}") String cron) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(jobDetail);
        trigger.setCronExpression(cron);
        trigger.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        trigger.setName(triggerName);
        trigger.setGroup(groupName);
        return trigger;
    }
}

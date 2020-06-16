package com.cn.flying.job.factory;

import java.util.Arrays;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfiguration {

    @Autowired
    private ConfigurableEnvironment env;

    @Bean(name = "app-quartz-x")
    public SchedulerFactoryBean schedulerFactoryBean(SpringJobFactory springJobFactory, JobDetail[] jobDetails, Trigger[] triggers) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setQuartzProperties(loadProperties());
        schedulerFactoryBean.setJobFactory(springJobFactory);
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setTriggers(triggers);
        schedulerFactoryBean.setJobDetails(jobDetails);
        return schedulerFactoryBean;
    }

    private Properties loadProperties() {
        return StreamSupport.stream(env.getPropertySources().spliterator(), false)
                .filter(EnumerablePropertySource.class::isInstance)
                .map(e -> ((EnumerablePropertySource<?>) e).getPropertyNames())
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toMap(Function.identity(), env::getProperty, (oldValue, newValue) -> oldValue, Properties::new));
    }
}

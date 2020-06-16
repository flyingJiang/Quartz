package com.cn.flying;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@PropertySource(value={
        "classpath:job-quartz-cfg.properties"
})
public class FlyingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlyingApplication.class, args);
    }

}

package com.li.missyou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MissyouApplication {
    public static void main(String[] args) {
        SpringApplication.run(MissyouApplication.class, args);
    }

}

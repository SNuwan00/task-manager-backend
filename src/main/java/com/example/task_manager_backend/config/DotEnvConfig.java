package com.example.task_manager_backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DotEnvConfig {

    @Primary
    @Bean
    public DataSource dataSource() {
        Dotenv dotenv = Dotenv.configure().load();
        return DataSourceBuilder
                .create()
                .url(dotenv.get("DB_URL"))
                .username(dotenv.get("DB_USERNAME"))
                .password(dotenv.get("DB_PASSWORD"))
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
//    @Bean
//    public Dotenv dotenv() {
//        return Dotenv.configure().load();
//    }
}
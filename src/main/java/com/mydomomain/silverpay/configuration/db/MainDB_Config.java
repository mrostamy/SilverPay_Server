package com.mydomomain.silverpay.configuration.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
public class MainDB_Config {

    @Bean
    public DataSource mainDB() {

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setPassword("mohammad1234");
        ds.setUrl("jdbc:mysql://localhost:3306/silverpay_main");
        ds.setUsername("root");

        return ds;

    }
}

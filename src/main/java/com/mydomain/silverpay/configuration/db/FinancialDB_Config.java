package com.mydomain.silverpay.configuration.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.mydomain.silverpay.repository.FinancialDB",
        entityManagerFactoryRef = "financialEntityManager",
        transactionManagerRef = "financialTransactionManager"
)
public class FinancialDB_Config {

    @Bean
    public DataSource financialDataSource() {

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setPassword("mohammad1234");
        ds.setUrl("jdbc:mysql://localhost:3306/silverpay_financial");
        ds.setUsername("root");

        return ds;

    }

    @Bean
    public LocalContainerEntityManagerFactoryBean financialEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(financialDataSource());
        em.setPackagesToScan("com.mydomain.silverpay.data.model.financialDB");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        Map<String,String> properties=new HashMap<>();

        properties.put("hibernate.hbm2ddl.auto","update");
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL8Dialect");

        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(properties);

        return em;
    }


    @Bean
    public PlatformTransactionManager financialTransactionManager() {

        JpaTransactionManager tx = new JpaTransactionManager();
        tx.setEntityManagerFactory(financialEntityManager().getObject());
        return tx;

    }



}

package com.example.springshadow.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = {
        "com.example.springshadow.database.fdms.repository" }, entityManagerFactoryRef = "fdmsEntityManager", transactionManagerRef = "fdmsTransactionManager")
@PropertySource("classpath:application-${spring.profiles.active}.yml")
public class FdmsDataSourceConfig {
    @Value("${spring.datasourceFdms.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasourceFdms.url}")
    private String url;

    @Value("${spring.datasourceFdms.username}")
    private String username;

    @Value("${spring.datasourceFdms.password}")
    private String password;

    @Value("${spring.jpa.database-platform}")
    private String dialect;

    @Value("$(spring.jpa.hibernate.ddl-auto)")
    private String ddlAuto;

    @Value("$(spring.jpa.show-sql)")
    private String showSql;

    @Bean(name = "datasourceFdms")
    public DataSource datasourceFdms() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean("fdmsEntityManager")
    public LocalContainerEntityManagerFactoryBean fdmsEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(datasourceFdms());
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan(new String[] { "com.example.springshadow.model.database.fdms" });
        // em.setPersistenceXmlLocation("classpath:persistence.xml");
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.ddl-auto", ddlAuto);
        properties.put("hibernate.show-sql", showSql);
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean("fdmsTransactionManager")
    public PlatformTransactionManager fdmsTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(fdmsEntityManager().getObject());
        return transactionManager;
    }
}
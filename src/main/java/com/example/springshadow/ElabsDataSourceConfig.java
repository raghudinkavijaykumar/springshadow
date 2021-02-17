package com.example.springshadow;

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
        "com.example.springshadow.database.elabs.repository" }, entityManagerFactoryRef = "elabsEntityManager", transactionManagerRef = "elabsTransactionManager")
@PropertySource("classpath:application-${spring.profiles.active}.properties")
public class ElabsDataSourceConfig {
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.jpa.database-platform}")
    private String dialect;

    @Value("$(spring.jpa.hibernate.ddl-auto)")
    private String ddlAuto;

    @Value("$(spring.jpa.show-sql)")
    private String showSql;

    @Bean(name = "datasource-elabs")
    public DataSource datasourceElabs() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean("elabsEntityManager")
    public LocalContainerEntityManagerFactoryBean elabsEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(datasourceElabs());
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan(new String[] { "com.example.springshadow.model.database.elabs" });
        // em.setPersistenceXmlLocation("classpath:persistence.xml");
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.ddl-auto", ddlAuto);
        properties.put("hibernate.show-sql", showSql);
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean("elabsTransactionManager")
    public PlatformTransactionManager elabsTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(elabsEntityManager().getObject());
        return transactionManager;
    }
}
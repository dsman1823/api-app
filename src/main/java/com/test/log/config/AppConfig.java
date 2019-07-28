package com.test.log.config;


import com.test.log.model.Log;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.username}")
    private String dbName;
    @Value("${db.password}")
    private String dbPassword;

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        Properties settings = new Properties();
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        settings.put(Environment.DATASOURCE, dataSource);
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "org.hibernate.context.internal.ThreadLocalSessionContext");

        configuration.setProperties(settings);
        configuration.addAnnotatedClass(Log.class);

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(registry);
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUsername(dbName);
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setPassword(dbPassword);
        return basicDataSource;
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase/changelog.xml");
        liquibase.setDataSource(dataSource());
        return liquibase;
    }
}
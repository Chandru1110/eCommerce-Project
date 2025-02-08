package com.springh2.config;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.springh2.repository.sys", // repositories that use the sys database
        entityManagerFactoryRef = "sysEntityManagerFactory",
        transactionManagerRef = "sysTransactionManager"
)
public class SysDbConfig {

    @Bean(name = "sysDataSource")
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource sysDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sysEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sysEntityManagerFactory(
            @Qualifier("sysDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataSource);
        // Scan for entities; adjust the package if your Users entity is in a different package.
        em.setPackagesToScan("com.springh2.model");

        // Set the JPA vendor adapter (Hibernate in this example)
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        // Manually set the JPA properties for the sys database.
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        jpaProperties.put("hibernate.show_sql", true);
        em.setJpaPropertyMap(jpaProperties);

        return em;
    }

    @Bean(name = "sysTransactionManager")
    public PlatformTransactionManager sysTransactionManager(
            @Qualifier("sysEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

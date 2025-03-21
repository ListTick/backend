package com.pro.list_tick.account;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.pro.list_tick.account",
        entityManagerFactoryRef = "accountEntityManagerFactory",
        transactionManagerRef = "accountTransactionManager"
)
@RequiredArgsConstructor
public class AccountDataSourceConfig {

    @Value("${datasource.database.account}")
    private String database;

    @Value("${datasource.user}")
    private String user;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.driver}")
    private String driver;

    private final Environment environment;

    @Bean(name = "accountDataSource")
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(database);
        dataSourceBuilder.username(user);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    @Bean(name = "accountFlyway")
    public Flyway flyway(@Qualifier("accountDataSource") DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/account/migration")
                .load();
        flyway.migrate();
        return flyway;
    }

    @Primary
    @Bean(name = "accountEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("accountDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.pro.list_tick.account", "org.springframework.modulith.events.jpa");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        entityManager.setJpaPropertyMap(properties);

        return entityManager;
    }

    @Primary
    @Bean(name = "accountTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("accountEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}

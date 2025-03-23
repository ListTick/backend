package com.pro.list_tick.note;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        basePackages = "com.pro.list_tick.note",
        entityManagerFactoryRef = "noteEntityManagerFactory",
        transactionManagerRef = "noteTransactionManager"
)
@RequiredArgsConstructor
public class NoteDataSourceConfig {

    @Value("${datasource.database.note}")
    private String database;

    @Value("${datasource.user}")
    private String user;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.driver}")
    private String driver;

    @Value("${datasource.dialect}")
    private String dialect;

    private final Environment environment;

    @Bean(name = "noteDataSource")
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(database);
        dataSourceBuilder.username(user);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    @Bean(name = "noteFlyway")
    public Flyway flyway(@Qualifier("noteDataSource") DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/note/migration")
                .load();
        flyway.migrate();
        return flyway;
    }

    @Bean(name = "noteEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("noteDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.pro.list_tick.note", "org.springframework.modulith.events.jpa");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", dialect);
        entityManager.setJpaPropertyMap(properties);

        return entityManager;
    }

    @Bean(name = "noteTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("noteEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}

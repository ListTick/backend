package com.pro.list_tick.shopping_list;

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
        basePackages = "com.pro.list_tick.shopping_list",
        entityManagerFactoryRef = "shoppingListEntityManagerFactory",
        transactionManagerRef = "shoppingListTransactionManager"
)
@RequiredArgsConstructor
public class ShoppingListDataSourceConfig {

    @Value("${datasource.database.shopping_list}")
    private String database;

    @Value("${datasource.user}")
    private String user;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.driver}")
    private String driver;

    private final Environment environment;

    @Bean(name = "shoppingListDataSource")
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(database);
        dataSourceBuilder.username(user);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    @Bean(name = "shoppingListFlyway")
    public Flyway flyway(@Qualifier("shoppingListDataSource") DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/shopping_list/migration")
                .load();
    }

    @Bean(name = "shoppingListEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("shoppingListDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("com.pro.list_tick.shopping_list", "org.springframework.modulith.events.jpa");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        entityManager.setJpaPropertyMap(properties);

        return entityManager;
    }

    @Bean(name = "shoppingListTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("shoppingListEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}

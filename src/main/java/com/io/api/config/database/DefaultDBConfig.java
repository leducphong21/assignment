package com.io.api.config.database;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ConditionalOnProperty(value = "app.datasource.default.enable", havingValue = "true", matchIfMissing = true)
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "mainEntityManagerFactory",
    transactionManagerRef = "mainTransactionManager", basePackages = {
    "com.io.api.repository.main"})
public class DefaultDBConfig {

  @Bean
  @Primary
  @ConfigurationProperties("app.datasource.default")
  public DataSourceProperties defaultDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Primary
  @Bean(name = "mainDataSource")
  @ConfigurationProperties(prefix = "app.datasource.default.configuration")
  public DataSource dataSource() {
    return defaultDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class)
        .build();
  }

  @Primary
  @Bean(name = "mainEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("mainDataSource") DataSource dataSource) {
    return builder.dataSource(dataSource).packages("com.io.api.model")
        .persistenceUnit("main")
        .build();
  }

  @Primary
  @Bean(name = "mainTransactionManager")
  public PlatformTransactionManager mainTransactionManager(
      @Qualifier("mainEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
  
}

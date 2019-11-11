package com.miaguila.trips.config

import org.apache.tomcat.jdbc.pool.DataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager

/**
 * Provides configuration beans for the trips database
 */
@Configuration
class DatabaseConfig {

    @Bean(name = ["tripsDataSource"])
    @Primary
    @ConfigurationProperties(prefix = "miaguila.trips.datasource")
    fun dataSource(): DataSource = DataSource()

    @Bean(name = ["tripsJdbcNamedTemplate"])
    fun jdbcNamedTemplate(ds: DataSource) = NamedParameterJdbcTemplate(ds)

    @Bean(name = ["tripsTransactionManager"])
    fun transactionManager(ds: DataSource): PlatformTransactionManager {
        return DataSourceTransactionManager(ds)
    }
}

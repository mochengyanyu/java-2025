package com.wk.dynamic.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

/**
 * 事务管理器(只能是单库的事务管理s)
 */
@Configuration
public class TransactionManagerConfig {
    @Resource(name = "db1")
    private DataSource db1;

    @Resource(name = "db2")
    private DataSource db2;

    @Bean(name = "db1TransactionManager")
    public TransactionManager buildDb1TransactionManager(){
        return new DataSourceTransactionManager(db1);
    }

    @Bean(name = "db2TransactionManager")
    public TransactionManager buildDb2TransactionManager(){
        return new DataSourceTransactionManager(db2);
    }

}

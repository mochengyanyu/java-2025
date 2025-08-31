package com.wk.config;


import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

@Configuration
@EnableTransactionManagement
public class TransactionXaManagerConfig {

    @Bean(name = "userTransaction")
    public UserTransaction userTransaction() throws Exception {
        UserTransactionImp userTransaction = new UserTransactionImp();
        userTransaction.setTransactionTimeout(1000);
        return userTransaction;
    }

    @Bean(name = "atomikosTransactionManager",initMethod = "init", destroyMethod = "close")
    public UserTransactionManager atomikosTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    @Bean(name = "transactionManager")
    @DependsOn({"userTransaction","atomikosTransactionManager"})
    public JtaTransactionManager transactionManager() throws Exception {
        return new JtaTransactionManager(userTransaction(), atomikosTransactionManager());
    }
}

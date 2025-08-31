package com.wk.dynamic.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceMBean;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.wk.dynamic.enums.DBEnums;
import com.wk.dynamic.multiple.DynamicDatasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean(name = "db1")
    @ConfigurationProperties(prefix = "spring.datasource.druid.db1")
    public DataSource createPrimaryDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "db2")
    @ConfigurationProperties(prefix = "spring.datasource.druid.db2")
    public DataSource createSecondDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDatasource")
    @DependsOn({"db1","db2"})
    public DynamicDatasource buildDynamicDatasource(@Qualifier("db1")DataSource db1,
                                                    @Qualifier("db2")DataSource db2){
        DynamicDatasource dynamicDatasource = new DynamicDatasource();
        Map<Object,Object> dynamicDatasourceMap = new HashMap<Object,Object>();
        dynamicDatasourceMap.put(DBEnums.DB1.getValue(),db1);
        dynamicDatasourceMap.put(DBEnums.DB2.getValue(),db2);
        dynamicDatasource.setTargetDataSources(dynamicDatasourceMap);
        return dynamicDatasource;
    }

}

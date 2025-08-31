package com.wk.config;


import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@MapperScan(value = {"com.wk.mapper.primary"},sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryDatasourceConfig {

    @Resource
    private MybatisPlusInterceptor mybatisPlusInterceptor;


    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory budilSqlSessionFactory(@Qualifier("primaryDatasource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setLogImpl(StdOutImpl.class);
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{
                //分页插件
                mybatisPlusInterceptor
        });
        return sqlSessionFactoryBean.getObject();
    }

    //事务管理器
//    @Bean(name = "primaryTransactionManager")
//    @Primary
//    public DataSourceTransactionManager buildTransactionManager(@Qualifier("primaryDatasource") DataSource dataSource){
//        return new DataSourceTransactionManager(dataSource);
//    }

    @Bean(name = "primarySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate buildSqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

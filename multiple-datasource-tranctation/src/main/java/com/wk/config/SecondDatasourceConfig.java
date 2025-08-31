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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@MapperScan(value = {"com.wk.mapper.second"},sqlSessionTemplateRef = "secondSqlSessionTemplate")
public class SecondDatasourceConfig {


    @Resource
    private MybatisPlusInterceptor mybatisPlusInterceptor;

    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory budilSqlSessionFactory(@Qualifier("secondDatasource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory =
                new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setLogImpl(StdOutImpl.class);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setPlugins(new Interceptor[]{
                //分页插件
                mybatisPlusInterceptor
        });
        return sqlSessionFactory.getObject();
    }

    //事务管理器
//    @Bean(name = "secondTransactionManager")
//    public DataSourceTransactionManager buildTransactionManager(@Qualifier("secondDatasource") DataSource dataSource){
//        return new DataSourceTransactionManager(dataSource);
//    }

    @Bean(name = "secondSqlSessionTemplate")
    public SqlSessionTemplate buildSqlSessionTemplate(@Qualifier("secondSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

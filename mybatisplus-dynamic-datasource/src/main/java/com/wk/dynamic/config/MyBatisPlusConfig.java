package com.wk.dynamic.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.wk.dynamic.multiple.DynamicDatasource;
import jakarta.annotation.Resource;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.wk.dynamic.mapper"})
public class MyBatisPlusConfig {

    @Resource(name = "dynamicDatasource")
    private DynamicDatasource dynamicDatasource;

    @Resource(name = "mybatisPlusInterceptor")
    private MybatisPlusInterceptor mybatisPlusInterceptor;

    @Bean
    public SqlSessionFactory buildSqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dynamicDatasource);
        //设置config
        MybatisConfiguration configuration = new MybatisConfiguration ();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        //控制台打印数据
        configuration.setLogImpl(StdOutImpl.class);
        // 还可以设置全局属性
        sqlSessionFactory.setPlugins(new Interceptor[]{
                mybatisPlusInterceptor
        });
        sqlSessionFactory.setConfiguration(configuration);
        return sqlSessionFactory.getObject();
    }

}

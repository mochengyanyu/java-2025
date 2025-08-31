package com.wk.config;


//import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Value("${spring.datasource.type}")
    private String dataSourceType;

    //使用druid作为数据源的连接池
    @Bean(name = "primaryDatasource")
    public DataSource cratePrimaryDatasource(){
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        String prefix = "spring.datasource.druid.db1.";
        return getDataSource(ds, prefix);
    }

    //使用druid作为数据源的连接池
    @Bean(name = "secondDatasource")
    public DataSource crateSecondDatasource(){
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        String prefix = "spring.datasource.druid.db2.";
        return getDataSource(ds, prefix);
    }

    private DataSource getDataSource(AtomikosDataSourceBean ds, String prefix) {
        Properties properties = build(env,prefix);
        String uniqueResourceName = env.getProperty(prefix+"uniqueResourceName");
        ds.setXaDataSourceClassName(dataSourceType);
        ds.setXaProperties(properties);
        ds.setUniqueResourceName(uniqueResourceName);
        ds.setPoolSize(5);
        return ds;
    }

    private Properties build(Environment env, String prefix) {
        Properties prop = new Properties();
        prop.put("name", env.getProperty(prefix + "name"));
        prop.put("url", env.getProperty(prefix + "url"));
        prop.put("username", env.getProperty(prefix + "username"));
        prop.put("password", env.getProperty(prefix + "password"));
        prop.put("driverClassName", env.getProperty(prefix + "driverClassName", ""));
        prop.put("filters", env.getProperty(prefix + "filters"));
        prop.put("maxActive", env.getProperty(prefix + "maxActive", Integer.class));
        prop.put("initialSize", env.getProperty(prefix + "initialSize", Integer.class));
        prop.put("maxWait", env.getProperty(prefix + "maxWait", Integer.class));
        prop.put("minIdle", env.getProperty(prefix + "minIdle", Integer.class));
        prop.put("timeBetweenEvictionRunsMillis",
                env.getProperty(prefix + "timeBetweenEvictionRunsMillis", Integer.class));
        prop.put("minEvictableIdleTimeMillis", env.getProperty(prefix + "minEvictableIdleTimeMillis", Integer.class));
        prop.put("validationQuery", env.getProperty(prefix + "validationQuery"));
        prop.put("testWhileIdle", env.getProperty(prefix + "testWhileIdle", Boolean.class));
        prop.put("testOnBorrow", env.getProperty(prefix + "testOnBorrow", Boolean.class));
        prop.put("testOnReturn", env.getProperty(prefix + "testOnReturn", Boolean.class));
        prop.put("poolPreparedStatements", env.getProperty(prefix + "poolPreparedStatements", Boolean.class));
        prop.put("maxOpenPreparedStatements", env.getProperty(prefix + "maxOpenPreparedStatements", Integer.class));
        return prop;
    }
}

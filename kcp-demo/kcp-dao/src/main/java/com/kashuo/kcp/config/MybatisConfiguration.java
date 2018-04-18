package com.kashuo.kcp.config;

import com.kashuo.common.mybatis.helper.PageInterceptor;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PreDestroy;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties({DataBaseProperties.class})
@MapperScan(basePackages = {"com.kashuo.kcp.dao"})
public class MybatisConfiguration {
    @Autowired
    private DataBaseProperties dataSourceProperties;

    private DataSource dataSource;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataBaseProperties config = this.dataSourceProperties;

        this.dataSource = new DataSource();

        this.dataSource.setDriverClassName(config.getDriverClassName());
        this.dataSource.setUrl(config.getUrl());
        if (config.getUsername() != null) {
            this.dataSource.setUsername(config.getUsername());
        }
        if (config.getPassword() != null) {
            this.dataSource.setPassword(config.getPassword());
        }
        this.dataSource.setInitialSize(config.getInitialSize());
        this.dataSource.setMaxActive(config.getMaxActive());
        this.dataSource.setMaxIdle(config.getMaxIdle());
        this.dataSource.setMinIdle(config.getMinIdle());
        this.dataSource.setTestOnBorrow(config.isTestOnBorrow());
        this.dataSource.setTestOnReturn(config.isTestOnReturn());
        this.dataSource.setValidationQuery(config.getValidationQuery());
        this.dataSource.setTestWhileIdle(config.isTestWhileIdle());
        return this.dataSource;
    }

    @PreDestroy
    public void close() {
        if (this.dataSource != null)
            this.dataSource.close();
    }

    @Bean
    @Lazy
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        //mybatis配置文件
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(true);
        configuration.setLazyLoadingEnabled(false);
        configuration.setLogImpl(StdOutImpl.class);
        sqlSessionFactoryBean.setConfiguration(configuration);
        //分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("supportMethodsArguments", "false");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("pageSizeZero", "true");
        properties.setProperty("reasonable", "true");
        //设置param的值，避免在condition中含有pageSise.pageNum时，
        // 自动进行分页，其中键-值中的值为任意值，所以condition中不可含有例如pageNumabc等.
        properties.setProperty("params", "count=countSqlabc;pageNum=pageNumabc;" +
                "pageSize=pageSizeabc;reasonable=reasonableabc;pageSizeZero=pageSizeZeroabc");
        pageInterceptor.setProperties(properties);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor});
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/*Mapper.xml"));
        //sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @Lazy
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}

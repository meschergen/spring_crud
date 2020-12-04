package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * 04.12.2020
 *
 * @author MescheRGen
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan(value = "web")
public class DatabaseConfig {

    @Autowired
    Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean lemf = new LocalContainerEntityManagerFactoryBean();
        lemf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        lemf.setDataSource(getDataSource());
        lemf.setPersistenceUnitName("someJpaPersistenceUnit");
        lemf.setPackagesToScan("java");
        lemf.setJpaProperties(getHibernateProperties());
        return lemf;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager getTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(getDataSource());
        transactionManager.setEntityManagerFactory(getEntityManagerFactoryBean().getObject());
        return transactionManager;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        Map<String, Object> pr = Map.ofEntries(
                Map.entry("hibernate.dialect", env.getRequiredProperty("hibernate.dialect")),
                Map.entry("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto")),
                Map.entry("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql")),
                Map.entry("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"))
        );
        properties.putAll(pr);
        return properties;
    }

}

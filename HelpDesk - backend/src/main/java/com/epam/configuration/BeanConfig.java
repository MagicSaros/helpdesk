package com.epam.configuration;

import com.epam.security.AuthenticationEntryPointImpl;
import com.epam.security.AuthenticationTokenProvider;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class BeanConfig {

    private static final String USER_INIT_SCRIPT = "classpath:sql/userInitScript.sql";
    private static final String TICKET_INIT_SCRIPT = "classpath:sql/ticketInitScript.sql";
    private static final String CATEGORY_INIT_SCRIPT = "classpath:sql/categoryInitScript.sql";
    private static final String TEST_DATA_SCRIPT = "classpath:sql/testData.sql";
    private static final String PACKAGE_TO_SCAN = "com.epam.entity";

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript(USER_INIT_SCRIPT)
            .addScript(CATEGORY_INIT_SCRIPT)
            .addScript(TICKET_INIT_SCRIPT)
            .addScript(TEST_DATA_SCRIPT);
        return builder.build();
    }

    @Bean
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(PACKAGE_TO_SCAN);
        return sessionFactory;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    @Autowired
    public UserDetailsService userDetailsService(AuthenticationManagerBuilder authentication) {
        return authentication.getDefaultUserDetailsService();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    @Bean
    @Autowired
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        return new AuthenticationTokenProvider(userDetailsService);
    }
}

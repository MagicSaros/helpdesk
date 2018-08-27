package com.epam.configuration;

import com.epam.component.StateTransitionManager;
import com.epam.enums.State;
import com.epam.enums.TicketAction;
import com.epam.enums.UserRole;
import com.epam.security.AuthenticationEntryPointImpl;
import com.epam.security.AuthenticationTokenProvider;
import com.epam.component.implementation.StateTransitionManagerImpl;
import javax.sql.DataSource;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@EnableTransactionManagement
public class BeanConfig {

    private static final String USER_INIT_SCRIPT = "classpath:sql/userInitScript.sql";
    private static final String CATEGORY_INIT_SCRIPT = "classpath:sql/categoryInitScript.sql";
    private static final String TICKET_INIT_SCRIPT = "classpath:sql/ticketInitScript.sql";
    private static final String COMMENT_INIT_SCRIPT = "classpath:sql/commentInitScript.sql";
    private static final String ATTACHMENT_INIT_SCRIPT = "classpath:sql/attachmentInitScript.sql";
    private static final String HISTORY_INIT_SCRIPT = "classpath:sql/historyInitScript.sql";
    private static final String TEST_DATA_SCRIPT = "classpath:sql/testData.sql";
    private static final String PACKAGE_TO_SCAN = "com.epam.entity";
    private static final long FILE_MAX_SIZE = 5242880L;

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript(USER_INIT_SCRIPT)
            .addScript(CATEGORY_INIT_SCRIPT)
            .addScript(TICKET_INIT_SCRIPT)
            .addScript(COMMENT_INIT_SCRIPT)
            .addScript(ATTACHMENT_INIT_SCRIPT)
            .addScript(HISTORY_INIT_SCRIPT)
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

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(FILE_MAX_SIZE);
        return multipartResolver;
    }

    @Bean
    public StateTransitionManager stateTransitionManager() {
        return new StateTransitionManagerImpl.Builder()
            .addStateTransition(State.DRAFT, TicketAction.CREATE, State.DRAFT)
            .addPermission(UserRole.EMPLOYEE, UserRole.MANAGER)
            .addStateTransition(State.DRAFT, TicketAction.SUBMIT, State.NEW)
            .addPermission(UserRole.EMPLOYEE, UserRole.MANAGER)
            .addStateTransition(State.DRAFT, TicketAction.CANCEL, State.CANCELLED)
            .addPermission(UserRole.EMPLOYEE, UserRole.MANAGER)
            .addStateTransition(State.NEW, TicketAction.APPROVE, State.APPROVED)
            .addPermission(UserRole.MANAGER)
            .addStateTransition(State.NEW, TicketAction.DECLINE, State.DECLINED)
            .addPermission(UserRole.MANAGER)
            .addStateTransition(State.NEW, TicketAction.CANCEL, State.CANCELLED)
            .addPermission(UserRole.MANAGER)
            .addStateTransition(State.APPROVED, TicketAction.ASSIGN, State.IN_PROGRESS)
            .addPermission(UserRole.ENGINEER)
            .addStateTransition(State.APPROVED, TicketAction.CANCEL,
                State.CANCELLED)
            .addPermission(UserRole.ENGINEER)
            .addStateTransition(State.DECLINED, TicketAction.SUBMIT, State.NEW)
            .addPermission(UserRole.EMPLOYEE, UserRole.MANAGER)
            .addStateTransition(State.DECLINED, TicketAction.CANCEL, State.CANCELLED)
            .addPermission(UserRole.EMPLOYEE, UserRole.MANAGER)
            .addStateTransition(State.IN_PROGRESS, TicketAction.DONE, State.DONE)
            .addPermission(UserRole.ENGINEER)
            .build();
    }
}

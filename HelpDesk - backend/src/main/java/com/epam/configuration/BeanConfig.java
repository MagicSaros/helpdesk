package com.epam.configuration;

import com.epam.component.StateTransitionManager;
import com.epam.component.implementation.StateTransitionManagerImpl;
import com.epam.enums.State;
import com.epam.enums.TicketAction;
import com.epam.enums.UserRole;
import com.epam.security.AuthenticationEntryPointImpl;
import com.epam.service.implementation.UserDetailsServiceImpl;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.models.dto.builder.ApiInfoBuilder;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@EnableTransactionManagement
@EnableSwagger
public class BeanConfig {

    private static final String USER_INIT_SCRIPT = "classpath:sql/userInitScript.sql";
    private static final String AUTHORITY_INIT_SCRIPT = "classpath:sql/authorityInitScript.sql";
    private static final String CATEGORY_INIT_SCRIPT = "classpath:sql/categoryInitScript.sql";
    private static final String TICKET_INIT_SCRIPT = "classpath:sql/ticketInitScript.sql";
    private static final String COMMENT_INIT_SCRIPT = "classpath:sql/commentInitScript.sql";
    private static final String ATTACHMENT_INIT_SCRIPT = "classpath:sql/attachmentInitScript.sql";
    private static final String HISTORY_INIT_SCRIPT = "classpath:sql/historyInitScript.sql";
    private static final String FEEDBACK_INIT_SCRIPT = "classpath:sql/feedbackInitScript.sql";
    private static final String TOKEN_INIT_SCRIPT = "classpath:sql/tokenInitScript.sql";
    private static final String TEST_DATA_SCRIPT = "classpath:sql/testData.sql";
    private static final String PACKAGE_TO_SCAN = "com.epam.entity";
    private static final long FILE_MAX_SIZE = 5242880L;

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript(USER_INIT_SCRIPT)
            .addScript(AUTHORITY_INIT_SCRIPT)
            .addScript(CATEGORY_INIT_SCRIPT)
            .addScript(TICKET_INIT_SCRIPT)
            .addScript(COMMENT_INIT_SCRIPT)
            .addScript(ATTACHMENT_INIT_SCRIPT)
            .addScript(HISTORY_INIT_SCRIPT)
            .addScript(FEEDBACK_INIT_SCRIPT)
            .addScript(TOKEN_INIT_SCRIPT)
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
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
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

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("magicsaros@gmail.com");
        mailSender.setPassword("esnpiiyqbvftgbgp");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    @Autowired
    public SwaggerSpringMvcPlugin configureSwagger(SpringSwaggerConfig springSwaggerConfig) {
        SwaggerSpringMvcPlugin swaggerSpringMvcPlugin
            = new SwaggerSpringMvcPlugin(springSwaggerConfig);
        ApiInfo apiInfo = new ApiInfoBuilder()
            .title("Helpdesk REST API")
            .description("Helpdesk Api for creating and managing help tickets")
            .contact("magicsaros@gmail.com")
            .build();
        swaggerSpringMvcPlugin.apiInfo(apiInfo)
            .apiVersion("1.0");
        return swaggerSpringMvcPlugin;
    }
}
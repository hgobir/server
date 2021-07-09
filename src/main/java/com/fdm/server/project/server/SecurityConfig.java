package com.fdm.server.project.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import com.fdm.server.project.server.service.UserDetailServiceImpl;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.activation.DataHandler;
import javax.sql.DataSource;
import javax.persistence.EntityManagerFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

@Configuration
@EnableWebSecurity
@EnableAsync
@EnableWebMvc
@EnableJpaRepositories("com.fdm.server.project.server.repository")
@ComponentScan(basePackages = {"com.fdm.server.project.server.repository", "com.fdm.server.project.server"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//
//    private UserDetailServiceImpl userDetailServiceImpl;
//
//    @Autowired
//    public SecurityConfig(UserDetailServiceImpl userDetailServiceImpl) {
//        this.userDetailServiceImpl = userDetailServiceImpl;
//    }

    public static final String BROKER_EXCHANGE = "broker.exchange";
    public static final String BROKER_QUEUE = "broker.request.queue";
    public static final String ROUTING_KEY = "broker.routing.key";



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().cors().and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/server/registration").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/server/registration/confirm/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/server/applicationUser/accounts/forgotPassword/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/server/applicationUser/accounts/resetPassword/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/server/stockModulation/currentStockPrices").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/v1/server/stockModulation/getCompany").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/v1/server/stockModulation/getCompany").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/server/stockModulation/getCompanies").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/stockModulation/admin/companies/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/stockModulation/admin/companies/specific").permitAll()
                .antMatchers(HttpMethod.POST, "/trade").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/server/reports/**").permitAll()
                .anyRequest().authenticated()
                .and()
// Filter for the api/login requests
                .addFilterBefore(new LoginFilter("/login",
                                authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
// Filter for other requests to check JWT in header
                .addFilterBefore(new AuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);


    }


    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

//    @Bean
//    public DataSource dataSource() {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("org.postgresql.Driver");
//        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/server_db");
//        dataSourceBuilder.username("postgres");
//        dataSourceBuilder.password("Nub70raz");
//        return dataSourceBuilder.build();
//    }
//
//
//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("com.fdm.server.project.server", "com.fdm.server.project.server.entity", "com.fdm.server.project.server.repository" );
//        factory.setDataSource(dataSource());
//        factory.afterPropertiesSet();
//
//        return factory.getObject();
//    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);
        config.applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.userDetailsService(userDetailServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
//    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(BROKER_EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(BROKER_QUEUE);
    }

    @Bean
    public Binding binding(DirectExchange directExchange,
                           Queue queue) {
        return BindingBuilder.bind(queue)
                .to(directExchange)
                .with(ROUTING_KEY);
    }
//
//@Bean
//    public UserDetailServiceImpl userDetailServiceImpl() {
//        return new UserDetailServiceImpl(applicationUserRepository);
//    }//

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("alphatrader.solutions@gmail.com");
        mailSender.setPassword("uwbjosczgsfcdgwt");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }






}

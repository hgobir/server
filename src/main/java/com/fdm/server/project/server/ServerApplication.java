package com.fdm.server.project.server;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.EncryptionKey;
import com.fdm.server.project.server.repository.EncryptionKeyRepository;
import com.fdm.server.project.server.user.Role;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@SpringBootApplication
@EnableAsync
@EnableRabbit
@EnableTransactionManagement
//@EnableWebMvc
//@EnableJpaRepositories("com.fdm.server.project.server.repository")
@EntityScan("com.fdm.server.project.server.entity")
@EnableCaching(proxyTargetClass = true)
public class ServerApplication {
    /*
        todo: steps to run rabbitmq
            1. navigate to C:\Program Files\RabbitMQ Server\rabbitmq_server-3.8.11\sbin
            2. run rabbitmq-plugins enable rabbitmq_management in sbin directory on admin cmd
            3. navigate to http://localhost:15672/ on browser
            4. Login page default username and password is guest
            5. After successfully login you should see RabbitMQ Home page
     */

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private EncryptionKeyRepository encryptionKeyRepository;



    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
// username: admin password: admin
            applicationUserRepository.save(new ApplicationUser("admin",
                    "$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG", "alphatrader@mail.com",
                    Role.ADMIN));

            encryptionKeyRepository.save(new EncryptionKey(1L, 9));

        };
    }




}

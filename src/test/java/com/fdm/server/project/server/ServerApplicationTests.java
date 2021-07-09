package com.fdm.server.project.server;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootTest
//@EnableTransactionManagement
//@EnableJpaRepositories("com.fdm.server.project.server.repository")
//@EntityScan("com.fdm.server.project.server.entity")
class ServerApplicationTests {

    @Bean
    @Primary
    public CachingConnectionFactory rabbitAdmin() {
        return Mockito.mock(CachingConnectionFactory.class);
    }

    @Test
    void contextLoads() {
    }

}

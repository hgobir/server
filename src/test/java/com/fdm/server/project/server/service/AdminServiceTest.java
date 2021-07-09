package com.fdm.server.project.server.service;

import com.fdm.server.project.server.email.EmailSender;
import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.model.ExceptionModel;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import com.fdm.server.project.server.service.AdminService;
import com.fdm.server.project.server.service.ApplicationUserService;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    private AdminService underTest;

    @Mock
    private ApplicationUserService applicationUserService;


    @Mock
    private EmailSender emailSender;


    @BeforeEach
    void setUp() {
        underTest = new AdminService(emailSender, applicationUserService);
    }



    @Test
    void testSendExceptionEmail() {

        Optional<ApplicationUser> a = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));
        Mockito.when(applicationUserService.getOneUser(anyLong())).thenReturn(a.get());

        ExceptionModel exceptionModel = new ExceptionModel("", HttpStatus.MULTI_STATUS, HttpStatus.MULTI_STATUS.value(), new Throwable(), ZonedDateTime.now(), 1L);

        underTest.sendExceptionEmail(exceptionModel);

        Mockito.verify(emailSender).send(anyString(), anyString(), anyString());

    }
}
package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.ConfirmationToken;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import com.fdm.server.project.server.repository.ConfirmationTokenRepository;
import com.fdm.server.project.server.service.ConfirmationTokenService;
import com.fdm.server.project.server.service.ConfirmationTokenServiceImpl;
import com.fdm.server.project.server.user.Role;
import com.fdm.server.project.server.user.TokenType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceTest {


    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    private ConfirmationTokenService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ConfirmationTokenServiceImpl(confirmationTokenRepository);
    }

//    @AfterEach
//    void tearDown() {
//    }

    @Test
    void testSaveConfirmationToken() {

        ApplicationUser a =  new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER);

        ConfirmationToken ct1 = new ConfirmationToken("test-token", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), a,"Registration" );

        Mockito.when(confirmationTokenRepository.save(any())).thenReturn(ct1);

        underTest.saveConfirmationToken(ct1);


        Mockito.verify(confirmationTokenRepository).save(any());

    }

    @Test
    void testGetToken() {

        ApplicationUser a =  new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER);

        Optional<ConfirmationToken> ct1 = Optional.of(new ConfirmationToken("test-token", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), a,"Registration" ));

        Mockito.when(confirmationTokenRepository.findByToken(anyString())).thenReturn(ct1);

        underTest.getToken("test-token");


        Mockito.verify(confirmationTokenRepository).findByToken(any());



    }

    @Test
    void testGetApplicationUserFromToken() {

        ApplicationUser a =  new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER);

        Optional<ConfirmationToken> ct1 = Optional.of(new ConfirmationToken("test-token", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), a,"Registration" ));

        Mockito.when(confirmationTokenRepository.findByToken(anyString())).thenReturn(ct1);

        underTest.getToken("test-token");


        Mockito.verify(confirmationTokenRepository).findByToken(any());
    }

    @Test
    void testConfirmToken() {

        ApplicationUser a =  new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER);

        Optional<ConfirmationToken> ct1 = Optional.of(new ConfirmationToken("test-token", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), a,"Registration" ));

        Mockito.when(confirmationTokenRepository.findByToken(anyString())).thenReturn(ct1);

        underTest.confirmToken("test-token", TokenType.REGISTRATION);

        Mockito.verify(confirmationTokenRepository).updateConfirmedAt(any(), any());


    }
}
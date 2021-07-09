package com.fdm.server.project.server.service;

import com.fdm.server.project.server.email.EmailSender;
import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.ConfirmationToken;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import com.fdm.server.project.server.service.ApplicationUserService;
import com.fdm.server.project.server.service.ConfirmationTokenService;
import com.fdm.server.project.server.user.Role;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;


@ExtendWith(MockitoExtension.class)
class ApplicationUserServiceTest {

    @Mock
    private ApplicationUserRepository applicationUserRepository;

    private ApplicationUserService underTest;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private EmailSender emailSender;


    @BeforeEach
    void setUp() {
        underTest = new ApplicationUserService(applicationUserRepository, bCryptPasswordEncoder, confirmationTokenService, emailSender);
    }

    @Test
    void testGetUserByUsernameAfterAuthorization() {

        Optional<ApplicationUser> a = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));
        Mockito.when(applicationUserRepository.findByUsername(anyString())).thenReturn(a);

        underTest.getUserByUsernameAfterAuthorization(anyString());

        Mockito.verify(applicationUserRepository).findByUsername(anyString());
    }

    @Test
    void testLoadUserByApplicationId() {
        Optional<ApplicationUser> a = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));
        Mockito.when(applicationUserRepository.findByEmail(anyString())).thenReturn(a);

        underTest.loadUserByApplicationId(anyString());

        Mockito.verify(applicationUserRepository).findByEmail(anyString());
    }

    @Test
    void testLoadUserByUsername() {

        Optional<ApplicationUser> a = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));
        Mockito.when(applicationUserRepository.findByUsername(anyString())).thenReturn(a);

        underTest.loadUserByUsername(anyString());

        Mockito.verify(applicationUserRepository).findByUsername(anyString());


    }

    @Test
    void testSignUpUser() {

        Optional<ApplicationUser> a = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));

        underTest.signUpUser(a.get());

        Mockito.verify(applicationUserRepository).findByEmail(anyString());
        Mockito.verify(applicationUserRepository).findByUsername(anyString());
        Mockito.verify(bCryptPasswordEncoder).encode(anyString());
        Mockito.verify(applicationUserRepository).save(a.get());

        ArgumentCaptor<ConfirmationToken> confirmationTokenArgumentCaptor = ArgumentCaptor.forClass(ConfirmationToken.class);

        Mockito.verify(confirmationTokenService).saveConfirmationToken(confirmationTokenArgumentCaptor.capture());

        ConfirmationToken ct = confirmationTokenArgumentCaptor.getValue();

        assertThat(ct).isNotNull();
    }

    @Test
    void testEnableApplicationUser() {

        Optional<ApplicationUser> a = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));
        Mockito.when(applicationUserRepository.enableApplicationUser(anyString())).thenReturn(1);

        underTest.enableApplicationUser(anyString());

        Mockito.verify(applicationUserRepository).enableApplicationUser(anyString());
    }

    @Test
    void testValidateEmail() {

        Optional<ApplicationUser> a = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));

        Mockito.when(applicationUserRepository.findByEmail(anyString())).thenReturn(a);

        underTest.validateEmail(anyString());

        Mockito.verify(applicationUserRepository, Mockito.times(2)).findByEmail(anyString());


        ArgumentCaptor<ConfirmationToken> confirmationTokenArgumentCaptor = ArgumentCaptor.forClass(ConfirmationToken.class);

        Mockito.verify(confirmationTokenService).saveConfirmationToken(confirmationTokenArgumentCaptor.capture());

        ConfirmationToken ct = confirmationTokenArgumentCaptor.getValue();

        assertThat(ct).isNotNull();

        Mockito.verify(emailSender).send(anyString(), anyString(), anyString());



    }

    @Test
    void testResetPassword() {

        Optional<ApplicationUser> a = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));

        Mockito.when(confirmationTokenService.getApplicationUserFromToken(anyString())).thenReturn(a.get());

        underTest.resetPassword("token", "password");

        Mockito.verify(bCryptPasswordEncoder).encode(anyString());

    }

    @Test
    void testGetUsers() {
        Optional<ApplicationUser> a1 = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));

        Optional<ApplicationUser> a2 = Optional.of(
                new ApplicationUser("test2", "p2", "test2@test.com", "test2-firstname", "test2-lastname", 50000.0, Role.USER));

        List<ApplicationUser> applicationUserList = new ArrayList<>();
        applicationUserList.add(a1.get());
        applicationUserList.add(a2.get());
        Mockito.when(applicationUserRepository.findAll()).thenReturn(applicationUserList);

        underTest.getUsers();

        Mockito.verify(applicationUserRepository).findAll();
    }

    @Test
    void testGetOneUser() {

        Optional<ApplicationUser> a1 = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));

        Mockito.when(applicationUserRepository.findById(anyLong())).thenReturn(a1);


        underTest.getOneUser(1L);

        Mockito.verify(applicationUserRepository).findById(1L);
    }

    @Test
    void testCreateUser() {

        underTest.createUser("test", "p", "test@test.com", "test-firstname", "test-lastname", Role.USER);

        Mockito.verify(bCryptPasswordEncoder).encode(anyString());

        Mockito.verify(applicationUserRepository).saveAndFlush(any());


    }

    @Test
    void testUpdateUser() {

        Optional<ApplicationUser> a1 = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));

        Mockito.when(applicationUserRepository.findById(anyLong())).thenReturn(a1);

        underTest.updateUser(1L,"test", "p", "test@test.com", "test-firstname", "test-lastname", Role.USER);

        Mockito.verify(applicationUserRepository).findById(1L);

        Mockito.verify(bCryptPasswordEncoder).encode(anyString());

        Mockito.verify(applicationUserRepository).save(any());

    }

    @Test
    void testDeleteUser() {

        underTest.deleteUser(1L);

        Mockito.verify(applicationUserRepository).deleteById(1L);
    }

    @Test
    void testUpdateVerified() {

        Mockito.when(applicationUserRepository.updateVerified(1L, true)).thenReturn(1);

        underTest.updateVerified(1L,true);

        Mockito.verify(applicationUserRepository).updateVerified(1L, true);
    }

    @Test
    void testUpdateAvailableFunds() {

        Mockito.when(applicationUserRepository.updateAvailableFunds(1L, 1)).thenReturn(1);

        underTest.updateAvailableFunds(1L,1);

        Mockito.verify(applicationUserRepository).updateAvailableFunds(1L, 1);

    }

    @Test
    void testGetAvailableFunds() {
        Optional<ApplicationUser> a = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));


        Mockito.when(applicationUserRepository.findById(1L)).thenReturn(a);


        underTest.getAvailableFunds(1L);

        Mockito.verify(applicationUserRepository, Mockito.times(1)).findById(anyLong());


    }
}
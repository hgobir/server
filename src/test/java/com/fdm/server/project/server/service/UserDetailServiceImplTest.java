package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import com.fdm.server.project.server.service.UserDetailServiceImpl;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceImplTest {

    @Mock
    private ApplicationUserRepository applicationUserRepository;
    private UserDetailServiceImpl underTest;


    @BeforeEach
    void setUp() {
        underTest = new UserDetailServiceImpl(applicationUserRepository);
    }

    @Test
    void testLoadUserByUsername() {

        Optional<ApplicationUser> a = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));
        Mockito.when(applicationUserRepository.findByUsername(anyString())).thenReturn(a);

        underTest.loadUserByUsername(anyString());

        Mockito.verify(applicationUserRepository).findByUsername(anyString());

    }
}
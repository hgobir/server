package com.fdm.server.project.server.repository;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.ConfirmationToken;
import com.fdm.server.project.server.entity.CreditCard;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import com.fdm.server.project.server.repository.ConfirmationTokenRepository;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class ConfirmationTokenRepositoryIntegrationTest {


    @Autowired
    private ConfirmationTokenRepository underTest;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;


    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        applicationUserRepository.deleteAll();
    }


    @Test
    void testFindByToken() {
        ApplicationUser a = entityManager.persistAndFlush(new ApplicationUser("test","p","name1", Role.USER));
        ConfirmationToken ct1 = entityManager.persistAndFlush(new ConfirmationToken("test-token", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), a,"Registration" ));
        underTest.save(ct1);
        Optional<ConfirmationToken> testConfirmationToken = underTest.findByToken("test-token");
        assertThat(testConfirmationToken.get()).isNotNull();
    }

    @Test
    void testUpdateConfirmedAt() {

        LocalDateTime oldDate = LocalDateTime.now();
        ApplicationUser a = entityManager.persistAndFlush(new ApplicationUser("test","p","name1", Role.USER));
        ConfirmationToken ct1 = entityManager.persistAndFlush(new ConfirmationToken("test-token", oldDate, LocalDateTime.now().plusMinutes(15), a,"Registration" ));
        underTest.save(ct1);
        underTest.updateConfirmedAt("test-token", LocalDateTime.MAX);
        LocalDateTime newDate = underTest.findByToken("test-token").get().getConfirmedAt();
        assertThat(oldDate).isNotEqualTo(newDate);
    }
}
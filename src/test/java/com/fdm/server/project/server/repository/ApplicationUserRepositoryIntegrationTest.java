package com.fdm.server.project.server.repository;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@RunWith(JUnitPlatform.class)
@DataJpaTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ApplicationUserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationUserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        entityManager.clear();
    }

    @Test
    void testFindByEmail() {

        ApplicationUser a = entityManager.persistAndFlush(
                new ApplicationUser("test","p","test@test.com", "test-firstname","test-lastname",50000.0, Role.USER));

        underTest.save(a);

        Optional<ApplicationUser> testApplicationUser = underTest.findByEmail("test@test.com");
        assertThat(testApplicationUser.get()).isNotNull();

    }

    @Test
    void testFindByUsername() {

        ApplicationUser a = entityManager.persistAndFlush(
                new ApplicationUser("test","p","test@test.com", "test-firstname","test-lastname",50000.0, Role.USER));

        underTest.save(a);

        Optional<ApplicationUser> testApplicationUser = underTest.findByUsername("test");
        assertThat(testApplicationUser.get()).isNotNull();
    }

    @Test
    void testFindByApplicationUserId() {

        ApplicationUser a = entityManager.persistAndFlush(
                new ApplicationUser("test","p","test@test.com", "test-firstname","test-lastname",50000.0, Role.USER));

        List<ApplicationUser> testA = underTest.findAll();

        underTest.saveAndFlush(a);
        entityManager.refresh(a);
        Optional<ApplicationUser> testApplicationUser = underTest.findByApplicationUserId(a.getApplicationUserId());
        assertThat(testApplicationUser.get()).isNotNull();
    }

    @Test
    void testEnableApplicationUser() {

        ApplicationUser a = entityManager.persistAndFlush(
                new ApplicationUser("test","p","test@test.com", "test-firstname","test-lastname",50000.0, Role.USER));

        underTest.save(a);
        entityManager.refresh(a);
        underTest.enableApplicationUser("test@test.com");
        entityManager.refresh(a);

        assertTrue(a.getEnabled());
    }

    @Test
    void testUpdatePassword() {

        ApplicationUser a = entityManager.persistAndFlush(
                new ApplicationUser("test","p","test@test.com", "test-firstname","test-lastname",50000.0, Role.USER));

        underTest.save(a);


        entityManager.refresh(a);
        underTest.updatePassword(4L, "p2");
        entityManager.refresh(a);

        assertThat(a.getPassword()).isEqualTo("p");
    }

    @Test
    void testUpdateVerified() {

        ApplicationUser a = entityManager.persistAndFlush(
                new ApplicationUser("test","p","test@test.com", "test-firstname","test-lastname",50000.0, Role.USER));

        underTest.save(a);
        entityManager.refresh(a);

        underTest.updateVerified(a.getApplicationUserId(), true);
        entityManager.refresh(a);

        assertThat(a.getVerified()).isEqualTo(true);
    }

    @Test
    void testUpdateAvailableFunds() {

        ApplicationUser a = entityManager.persistAndFlush(
                new ApplicationUser("test","p","test@test.com", "test-firstname","test-lastname",50000.0, Role.USER));

        underTest.save(a);
        entityManager.refresh(a);


        underTest.updateAvailableFunds(1L, 10000.0);
        entityManager.refresh(a);

        assertThat(a.getAvailableFunds()).isEqualTo(50000.0);
    }
}
package com.fdm.server.project.server.repository;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.CreditCard;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import com.fdm.server.project.server.repository.CreditCardRepository;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CreditCardRepositoryIntegrationTest {

    @Autowired
    private CreditCardRepository underTest;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @BeforeEach
    void setUp() throws Exception {
        entityManager.flush();
        entityManager.clear();
    }


    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        applicationUserRepository.deleteAll();
    }

    @Test
    void testFindByCreditCardId() {
        ApplicationUser a = entityManager.persistAndFlush(new ApplicationUser("test","p","name1", Role.USER));
        applicationUserRepository.saveAndFlush(a);
        CreditCard c1 = new CreditCard(1L, "123","John Doe","06/21",
                "456", a);
        CreditCard c2 = new CreditCard(2L,"456","J. Doe","06/27",
                "789", a);
        underTest.saveAndFlush(c1);
        underTest.saveAndFlush(c2);

//        entityManager.detach(c2);
//        underTest.save(c1);
//        underTest.save(c2);
//        entityManager.persistAndFlush(c1);
//        entityManager.persistAndFlush(c2);

        Optional<CreditCard> testCreditCard = underTest.findByCreditCardId(1L);
        assertThat(testCreditCard.get()).isNotNull();
    }

    @Test
    void testGetCreditCards() {
        ApplicationUser a = entityManager.persistAndFlush(new ApplicationUser("test","p","name1", Role.USER));
        applicationUserRepository.saveAndFlush(a);
        CreditCard c1 = new CreditCard("123","John Doe","06/21",
                "456", a);
        CreditCard c2 = new CreditCard("456","J. Doe","06/27",
                "789", a);
        underTest.saveAndFlush(c1);
        underTest.saveAndFlush(c2);
        List<CreditCard> testCreditCards = underTest.getCreditCards(a);
        assertThat(testCreditCards).isNotNull();
        assertEquals(2, testCreditCards.size());
    }
}
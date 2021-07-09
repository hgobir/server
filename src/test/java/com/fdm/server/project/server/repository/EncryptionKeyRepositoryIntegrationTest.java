package com.fdm.server.project.server.repository;


import com.fdm.server.project.server.entity.EncryptionKey;
import com.fdm.server.project.server.repository.EncryptionKeyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class EncryptionKeyRepositoryIntegrationTest {

    @Autowired
    private EncryptionKeyRepository underTest;

    @Autowired
    private TestEntityManager entityManager;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void testFindByEncryptionKeyId() {
        EncryptionKey ek1 = entityManager.persistAndFlush(new EncryptionKey(1L, 175675));
        underTest.save(ek1);
        Optional<EncryptionKey> testEncryptionKey = underTest.findByEncryptionKeyId(1L);
        assertThat(testEncryptionKey.get()).isNotNull();
    }
}
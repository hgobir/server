package com.fdm.server.project.server.repository;

import com.fdm.server.project.server.entity.EncryptionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface EncryptionKeyRepository extends JpaRepository<EncryptionKey, Long> {

    @Query(value = "SELECT e FROM EncryptionKey e WHERE e.encryptionKeyId = ?1")
    Optional<EncryptionKey> findByEncryptionKeyId(Long encryptionKeyId);
}

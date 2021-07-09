package com.fdm.server.project.server.repository;

import com.fdm.server.project.server.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    @Query("SELECT a FROM ApplicationUser a WHERE a.email = ?1")
    Optional<ApplicationUser> findByEmail(String email);

    @Query("SELECT a FROM ApplicationUser a WHERE a.username = ?1")
    Optional<ApplicationUser> findByUsername(String username);

    @Query("SELECT a FROM ApplicationUser a WHERE a.applicationUserId = ?1")
    Optional<ApplicationUser> findByApplicationUserId(Long applicationUserId);

    @Modifying
    @Query("UPDATE ApplicationUser a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableApplicationUser(String email);

    @Modifying
    @Query("UPDATE ApplicationUser a " +
            "SET a.password = ?2 " +
            "WHERE a.applicationUserId = ?1")
    int updatePassword(Long applicationUserId, String password);

    @Modifying
    @Query("UPDATE ApplicationUser a " +
            "SET a.verified = ?2 " +
            "WHERE a.applicationUserId = ?1")
    int updateVerified(Long applicationUserId, boolean verified);

    @Modifying
    @Query("UPDATE ApplicationUser a " +
            "SET a.availableFunds = ?2 " +
            "WHERE a.applicationUserId = ?1")
    int updateAvailableFunds(Long applicationUserId, double availableFunds);

}

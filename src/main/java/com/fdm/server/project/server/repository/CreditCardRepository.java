package com.fdm.server.project.server.repository;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    @Query("SELECT cc FROM CreditCard cc WHERE cc.creditCardId = ?1")
    Optional<CreditCard> findByCreditCardId(Long creditCardId);

    @Query("SELECT cc FROM CreditCard cc WHERE cc.applicationUser = ?1")
    List<CreditCard> getCreditCards(ApplicationUser applicationUser);
}

package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.CreditCard;
import com.fdm.server.project.server.repository.CreditCardRepository;
import com.fdm.server.project.server.security.encryption.EncryptionService;
import com.fdm.server.project.server.service.ApplicationUserService;
import com.fdm.server.project.server.service.CreditCardService;
import com.fdm.server.project.server.service.CreditCardServiceImpl;
import com.fdm.server.project.server.user.Role;
import com.fdm.server.project.server.util.converter.CreditCardToCreditCardResponseConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;



@ExtendWith(MockitoExtension.class)
class CreditCardServiceTest {

    @Mock
    private EncryptionService encryptionService;
    @Mock
    private CreditCardRepository creditCardRepository;
    @Mock
    private ApplicationUserService applicationUserService;
    @Mock
    private CreditCardToCreditCardResponseConverter creditCardToCreditCardResponseConverter;

    private CreditCardService underTest;


    @BeforeEach
    void setUp() {
        underTest = new CreditCardServiceImpl(encryptionService,creditCardRepository,applicationUserService,creditCardToCreditCardResponseConverter);
    }



    @Test
    void testCreateCreditCard() {

        underTest.createCreditCard(1L,"5785959595","test-name","06/21","061");

//        Mockito.when(applicationUserService.updateVerified(1L, true)).thenReturn(1);

        Mockito.verify(creditCardRepository).saveAndFlush(any());

    }

    @Test
    void testGetCreditCards() {

        underTest.getCreditCards(1L);

        Mockito.verify(creditCardRepository).getCreditCards(any());

    }
}
package com.fdm.server.project.server.util.converter;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.CreditCard;
import com.fdm.server.project.server.model.CreditCardResponse;
import com.fdm.server.project.server.repository.TradeRepository;
import com.fdm.server.project.server.security.encryption.EncryptionService;
import com.fdm.server.project.server.service.ApplicationUserService;
import com.fdm.server.project.server.service.ConfirmationTokenServiceImpl;
import com.fdm.server.project.server.service.TradeService;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class CreditCardToCreditCardResponseConverterTest {


    @Mock
    private EncryptionService encryptionService;

    private CreditCardToCreditCardResponseConverter underTest;

    @BeforeEach
    void setUp() {
        underTest = new CreditCardToCreditCardResponseConverter(encryptionService);
    }

    @Test
    void testConvert() {
        ApplicationUser a = new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER);

        List<CreditCard> testCards = new ArrayList<>();

        CreditCard c1 = new CreditCard(1L, "123456789","John Doe","06/21",
        "456", a);

        testCards.add(c1);

        Mockito.when(encryptionService.decryptValue("123456789")).thenReturn(("123456789"));

        List<CreditCardResponse> expectedResult = underTest.convert(testCards);


        Mockito.verify(encryptionService).decryptValue(anyString());

        assertNotNull(expectedResult);

        Object classResult =expectedResult.get(0).getClass();
        assertEquals(CreditCardResponse.class, classResult);
    }
}
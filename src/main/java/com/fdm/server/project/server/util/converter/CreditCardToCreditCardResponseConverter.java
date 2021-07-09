package com.fdm.server.project.server.util.converter;

import com.fdm.server.project.server.entity.CreditCard;
import com.fdm.server.project.server.model.CreditCardResponse;
import com.fdm.server.project.server.security.encryption.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
    todo: find better way to implement - must be able to inject implementation at runtime via lambdas but dont have enough time atm!!!!
 */

@Component
public class CreditCardToCreditCardResponseConverter implements ConverterService {

    private final EncryptionService encryptionService;

    @Autowired
    public CreditCardToCreditCardResponseConverter(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }


    public List<CreditCardResponse> convert(List<CreditCard> creditCards) {

        return Collections.unmodifiableList(creditCards.stream()
                .map(creditCard -> new CreditCardResponse(
                        creditCard.getCreditCardId(),
    "*******************" + encryptionService.decryptValue(creditCard.getCreditCardNumber()).substring(creditCard.getCreditCardNumber().length() - 4)))
                .collect(Collectors.toList()));
    }


}

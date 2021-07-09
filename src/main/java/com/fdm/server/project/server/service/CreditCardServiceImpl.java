package com.fdm.server.project.server.service;

import com.fdm.server.project.server.model.CreditCardResponse;
import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.CreditCard;
import com.fdm.server.project.server.repository.CreditCardRepository;
import com.fdm.server.project.server.security.encryption.EncryptionService;
import com.fdm.server.project.server.util.converter.CreditCardToCreditCardResponseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private final EncryptionService encryptionService;
    private final CreditCardRepository creditCardRepository;
    private final ApplicationUserService applicationUserService;
    private final CreditCardToCreditCardResponseConverter creditCardToCreditCardResponseConverter;

    @Autowired
    public CreditCardServiceImpl(EncryptionService encryptionService, CreditCardRepository creditCardRepository, ApplicationUserService applicationUserService, CreditCardToCreditCardResponseConverter creditCardToCreditCardResponseConverter) {
        this.encryptionService = encryptionService;
        this.creditCardRepository = creditCardRepository;
        this.applicationUserService = applicationUserService;
        this.creditCardToCreditCardResponseConverter = creditCardToCreditCardResponseConverter;
    }

    @Override
    @Transactional
    public ApplicationUser createCreditCard(Long applicationUserId, String number, String name, String expiry, String cvc) {
        ApplicationUser applicationUser = applicationUserService.getOneUser(applicationUserId);
        String encryptedNumber = encryptionService.encryptValue(number);
        String encryptedName = encryptionService.encryptValue(name);
        String encryptedExpiry = encryptionService.encryptValue(expiry);
        String encryptedCvc = encryptionService.encryptValue(cvc);
        CreditCard creditCard = new CreditCard(encryptedNumber, encryptedName, encryptedExpiry, encryptedCvc, applicationUser);
        creditCardRepository.saveAndFlush(creditCard);
        int resultingInt = applicationUserService.updateVerified(applicationUserId, true);
        System.out.println("this is what resulting int is from updateVerified method " + resultingInt);
        if(resultingInt == 1) {
            applicationUser.setVerified(true);
            return applicationUser;
        }

        return null;

    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditCardResponse> getCreditCards(Long applicationUserId) {

        ApplicationUser applicationUser = applicationUserService.getOneUser(applicationUserId);

        List<CreditCard> creditCardList = creditCardRepository.getCreditCards(applicationUser);

        return creditCardToCreditCardResponseConverter.convert(creditCardList);




    }

}

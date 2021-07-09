package com.fdm.server.project.server.service;

import com.fdm.server.project.server.model.CreditCardResponse;
import com.fdm.server.project.server.entity.ApplicationUser;

import java.util.List;

public interface CreditCardService {

    //todo: need to do credit card validation!
    ApplicationUser createCreditCard(Long applicationUserId, String name, String number, String expiry, String cvc);

    List<CreditCardResponse> getCreditCards(Long applicationUserId);
}

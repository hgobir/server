package com.fdm.server.project.server.model;

public class CreditCardResponse {

    private Long creditCardId;
    private String creditCardNumberStr;


    public CreditCardResponse(Long creditCardId, String creditCardNumberStr) {
        this.creditCardId = creditCardId;
        this.creditCardNumberStr = creditCardNumberStr;
    }

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getCreditCardNumberStr() {
        return creditCardNumberStr;
    }

    public void setCreditCardNumberStr(String creditCardNumberStr) {
        this.creditCardNumberStr = creditCardNumberStr;
    }

    @Override
    public String toString() {
        return "CreditCardResponse{" +
                "creditCardId=" + creditCardId +
                ", creditCardNumberStr='" + creditCardNumberStr + '\'' +
                '}';
    }
}

package com.fdm.server.project.server.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "CreditCard")
@Table(
        name = "credit_card"
)
public class CreditCard {

    @Id
    @SequenceGenerator(
            name = "credit_card_sequence",
            sequenceName = "credit_card_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "credit_card_sequence"
    )
    @Column(
            name = "credit_card_id",
            nullable = false,
            unique = true,
            columnDefinition = "bigint"
    )
    private Long creditCardId;

    @Column(
            name = "credit_card_number",
            nullable = false,
            columnDefinition = "text"
    )
    private String creditCardNumber;

    @Column(
            name = "name_on_credit_card",
            nullable = false,
            columnDefinition = "text"
    )
    private String nameOnCreditCard;


    @Column(
            name = "expires_end_date",
            nullable = false,
            columnDefinition = "text"
    )
    private String expiresEndDate;

    @Column(
            name = "cvc_number",
            nullable = false,
            columnDefinition = "text"
    )
    private String cvcNumber;

    @ManyToOne
    @JoinColumn(
            name = "application_user_id"
    )
    private ApplicationUser applicationUser;

    public CreditCard() {
    }

    public CreditCard(String creditCardNumber,
                      String nameOnCreditCard,
                      String expiresEndDate,
                      String cvcNumber,
                      ApplicationUser applicationUser
    ) {
        this.creditCardNumber = creditCardNumber;
        this.nameOnCreditCard = nameOnCreditCard;
        this.expiresEndDate = expiresEndDate;
        this.cvcNumber = cvcNumber;
        this.applicationUser = applicationUser;
    }

    public CreditCard(Long creditCardId,
                      String creditCardNumber,
                      String nameOnCreditCard,
                      String expiresEndDate,
                      String cvcNumber,
                      ApplicationUser applicationUser
    ) {
        this.creditCardId = creditCardId;
        this.creditCardNumber = creditCardNumber;
        this.nameOnCreditCard = nameOnCreditCard;
        this.expiresEndDate = expiresEndDate;
        this.cvcNumber = cvcNumber;
        this.applicationUser = applicationUser;
    }

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getNameOnCreditCard() {
        return nameOnCreditCard;
    }

    public void setNameOnCreditCard(String nameOnCreditCard) {
        this.nameOnCreditCard = nameOnCreditCard;
    }

    public String getExpiresEndDate() {
        return expiresEndDate;
    }

    public void setExpiresEndDate(String expiresEndDate) {
        this.expiresEndDate = expiresEndDate;
    }

    public String getCvvNumber() {
        return cvcNumber;
    }

    public void setCvvNumber(String cvcNumber) {
        this.cvcNumber = cvcNumber;
    }


    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "creditCardId=" + creditCardId +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", nameOnCreditCard='" + nameOnCreditCard + '\'' +
                ", expiresEndDate='" + expiresEndDate + '\'' +
                ", cvcNumber='" + cvcNumber + '\'' +
                ", applicationUser=" + applicationUser +
                '}';
    }
}

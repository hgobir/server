package com.fdm.server.project.server.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity(name = "ConfirmationToken")
@Table(
        name = "confirmation_token"
)
public class ConfirmationToken {

    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    @Column(
            name = "confirmation_token_id",
            nullable = false,
            unique = true,
            columnDefinition = "bigint"
    )
    private Long confirmationTokenId;

    @Column(
            name = "token",
            nullable = false,
            columnDefinition = "text"
    )
    private String token;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "timestamp"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "expires_at",
            nullable = false,
            columnDefinition = "timestamp"
    )
    private LocalDateTime expiresAt;

    @Column(
            name = "confirmed_at",
            columnDefinition = "timestamp"
    )
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "application_user_id"
    )
    private ApplicationUser applicationUser;

    @Column(
            name = "type",
            nullable = false,
            columnDefinition = "text"
    )
    private String type;

    public ConfirmationToken() {
    }

    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             ApplicationUser applicationUser,
                             String type) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.applicationUser = applicationUser;
        this.type = type;
    }

    public Long getConfirmationTokenId() {
        return confirmationTokenId;
    }

    public void setConfirmationTokenId(Long confirmationTokenId) {
        this.confirmationTokenId = confirmationTokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfirmationToken)) return false;
        ConfirmationToken that = (ConfirmationToken) o;
        return getConfirmationTokenId().equals(that.getConfirmationTokenId()) && getToken().equals(that.getToken()) && getCreatedAt().equals(that.getCreatedAt()) && Objects.equals(getExpiresAt(), that.getExpiresAt()) && Objects.equals(getConfirmedAt(), that.getConfirmedAt()) && getApplicationUser().equals(that.getApplicationUser()) && getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getConfirmationTokenId(), getToken(), getCreatedAt(), getExpiresAt(), getConfirmedAt(), getApplicationUser(), getType());
    }
}

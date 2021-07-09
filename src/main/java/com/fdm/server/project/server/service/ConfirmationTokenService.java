package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.ConfirmationToken;
import com.fdm.server.project.server.user.TokenType;

import java.util.Optional;

public interface ConfirmationTokenService {

    void saveConfirmationToken(ConfirmationToken confirmationToken);

    ApplicationUser getApplicationUserFromToken(String token);

    Optional<ConfirmationToken> getToken(String token);

    ConfirmationToken confirmToken(String token, TokenType tokenType);
}

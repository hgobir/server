package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.ConfirmationToken;
import com.fdm.server.project.server.exception.types.ConfirmationTokenExpiredException;
import com.fdm.server.project.server.exception.types.ConfirmationTokenNotFoundException;
import com.fdm.server.project.server.exception.types.EmailAlreadyVerifiedException;
import com.fdm.server.project.server.repository.ConfirmationTokenRepository;
import com.fdm.server.project.server.user.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    @Transactional
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationUser getApplicationUserFromToken(String token) {
        Optional<ConfirmationToken> confirmationToken = getToken(token);
        return confirmationToken.get().getApplicationUser();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }


    @Override
    @Transactional
    public ConfirmationToken confirmToken(String token, TokenType tokenType) {

        ConfirmationToken confirmationToken = getToken(token).orElseThrow(() -> new ConfirmationTokenNotFoundException("token not found!"));

        if( tokenType.equals(TokenType.REGISTRATION) && confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyVerifiedException("email already confirmed");

        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenExpiredException("token has expired");
        }

        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());

        return confirmationToken;
    }
}

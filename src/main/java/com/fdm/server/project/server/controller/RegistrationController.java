package com.fdm.server.project.server.controller;

import com.fdm.server.project.server.entity.ConfirmationToken;
import com.fdm.server.project.server.exception.types.EmailAlreadyExistsException;
import com.fdm.server.project.server.exception.types.MailNotSentException;
import com.fdm.server.project.server.exception.types.UsernameAlreadyExistsException;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import com.fdm.server.project.server.service.ConfirmationTokenService;
import com.fdm.server.project.server.service.ConfirmationTokenServiceImpl;
import com.fdm.server.project.server.service.RegistrationService;
import com.fdm.server.project.server.service.RegistrationServiceImpl;
import com.fdm.server.project.server.dto.RegistrationRequest;
import com.fdm.server.project.server.user.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/server/registration")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final ConfirmationTokenService confirmationTokenService;
    private final ApplicationUserRepository applicationUserService;


    @Autowired
    public RegistrationController(RegistrationService registrationService, ConfirmationTokenService confirmationTokenService, ApplicationUserRepository applicationUserService) {
        this.registrationService = registrationService;
        this.confirmationTokenService = confirmationTokenService;
        this.applicationUserService = applicationUserService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) throws EmailAlreadyExistsException, UsernameAlreadyExistsException, MailNotSentException {
        System.out.println("hit the registration api!!");
//        throw new EmailAlreadyExistsException("email already existst");

        String result = registrationService.register(registrationRequest);
        ResponseEntity<String> responseEntity =  ResponseEntity.ok().body(result);
        return responseEntity;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="confirm/{token}")
    public ResponseEntity<String> confirm(@PathVariable("token") String token) {
        ConfirmationToken confirmationToken =  confirmationTokenService.confirmToken(token, TokenType.REGISTRATION);
        applicationUserService.enableApplicationUser(confirmationToken.getApplicationUser().getEmail());

        ResponseEntity<String> responseEntity =  ResponseEntity.ok().body("confirmed");

        return responseEntity;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/unregister/{applicationUserId}")
    public ResponseEntity<Boolean> requestUnregister(@PathVariable("applicationUserId") Long applicationUserId,
                                                     @RequestBody String email) throws MailNotSentException {

        boolean unregisterSent = registrationService.unregister(applicationUserId, email);

        ResponseEntity<Boolean> responseEntity =  ResponseEntity.ok().body(unregisterSent);

        return responseEntity;
    }


}

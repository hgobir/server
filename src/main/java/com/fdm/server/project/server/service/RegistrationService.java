package com.fdm.server.project.server.service;

import com.fdm.server.project.server.dto.RegistrationRequest;

public interface RegistrationService {

    String register(RegistrationRequest registrationRequest);
    boolean unregister(Long applicationUserId, String email);

}

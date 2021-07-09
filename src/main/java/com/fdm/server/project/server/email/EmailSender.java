package com.fdm.server.project.server.email;

public interface EmailSender {

    void send(String to, String email, String subject);
}

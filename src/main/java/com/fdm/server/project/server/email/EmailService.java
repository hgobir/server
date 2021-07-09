package com.fdm.server.project.server.email;

import com.fdm.server.project.server.exception.types.MailNotSentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService implements EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

//    @Autowired
//    public EmailService(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }

    @Override
    @Async
    public void send(String to, String email, String subject) throws MailNotSentException{

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(email, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom("alphatrader.solutions@gmail.com");
//            Properties props = new Properties();
//            props.put("mail.transport.protocol", "smtp");
//            props.put("mail.smtp.starttls.enable","true");
//            props.put("mail.smtp.host", "smtp.gmail.com");
//            props.put("mail.smtp.auth", "true");
//            Session session = Session.getInstance(props, null);
//            mimeMessageHelper.
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new MailNotSentException("failed to send email!");
        }
    }

//    @Async
//    public void sendExceptionEmail(ExceptionModel exceptionModel) {
//
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
//            mimeMessageHelper.setText(email, true);
//            mimeMessageHelper.setTo(to);
//            mimeMessageHelper.setSubject(subject);
//            mimeMessageHelper.setFrom("alphatrader.solutions@gmail.com");
//            javaMailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            LOGGER.error("failed to send email", e);
//            throw new IllegalStateException("failed to send email!");
//        }
//    }
}

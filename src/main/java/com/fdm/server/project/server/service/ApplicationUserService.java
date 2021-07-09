package com.fdm.server.project.server.service;

import com.fdm.server.project.server.email.EmailSender;
import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.ConfirmationToken;
import com.fdm.server.project.server.exception.types.EmailAlreadyExistsException;
import com.fdm.server.project.server.exception.types.EmailDoesNotExistException;
import com.fdm.server.project.server.exception.types.UsernameAlreadyExistsException;
import com.fdm.server.project.server.exception.types.UsernameNotFoundInServerException;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import com.fdm.server.project.server.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class  ApplicationUserService implements UserDetailsService {

    private final static String USER_WITH_EMAIL_NOT_FOUND_MSG = "user with email %s not found";
    private final static String USER_WITH_USERNAME_NOT_FOUND_MSG = "user with username %s not found";
    private final ApplicationUserRepository applicationUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;


    @Autowired
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder,
                                  ConfirmationTokenService confirmationTokenService,
                                  EmailSender emailSender) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
    }

    @Transactional(readOnly = true)
    public ApplicationUser getUserByUsernameAfterAuthorization(String username) throws UsernameNotFoundException {
        return applicationUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundInServerException(String.format(USER_WITH_USERNAME_NOT_FOUND_MSG, 1)));
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByApplicationId(String email) throws UsernameNotFoundException {
        return applicationUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND_MSG, email)));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("inside applicationuserservice and attempting to load username method!");
        return applicationUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_WITH_USERNAME_NOT_FOUND_MSG, username)));
    }

    @Transactional
    public String signUpUser(ApplicationUser applicationUser) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {

//        Todo: again create more specific exceptions for email or username existing!
        boolean emailExists = applicationUserRepository.findByEmail(applicationUser.getEmail()).isPresent();
        if(emailExists) {
            throw new EmailAlreadyExistsException("email already exists");
        }
        boolean usernameExists = applicationUserRepository.findByUsername(applicationUser.getUsername()).isPresent();
        if(usernameExists) {

//            Todo - check if attributes are the same then...
//            Todo - ... if email not confirmed send confirmation email again!
//            Todo - fix 2 way encode
            throw new UsernameAlreadyExistsException("username already exists");
        }

//        System.out.println("this is username " + applicationUser.getUsername());
//        String encodedUsername = bCryptPasswordEncoder.encode(applicationUser.getUsername());
        applicationUser.setUsername(applicationUser.getUsername());
        String encodedPassword = bCryptPasswordEncoder.encode(applicationUser.getPassword());
        applicationUser.setPassword(encodedPassword);


        applicationUserRepository.save(applicationUser);


        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
//                Todo - put expiry date in config file!
                LocalDateTime.now().plusMinutes(15),
                applicationUser,
                "Registration"
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

//        Todo - send email
        return token;
    }

    @Transactional
    public synchronized int enableApplicationUser(String email) {
        return applicationUserRepository.enableApplicationUser(email);
    }

    @Transactional
    public String validateEmail(String email) throws EmailDoesNotExistException {

        boolean emailExists = applicationUserRepository.findByEmail(email).isPresent();

        if(!emailExists) {
            throw new EmailDoesNotExistException("email does not already exists");
        }

        ApplicationUser applicationUser = applicationUserRepository.findByEmail(email).get();

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
//                Todo - put expiry date in config file!
                LocalDateTime.now().plusDays(1),
                applicationUser,
                "Reset"
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

//        /account/reset-password?token=

        String link = "http://localhost:3000/account/reset-password?token=" + token;

        emailSender.send(
                email,
                buildResetEmail(applicationUser.getFirstName(), link), "Confirm Your Email");

        return "exists";
    }




    private String buildResetEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Reset Password Email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Please click the below link to reset your password </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Reset Password Now</a> </p></blockquote>\n , the link will be valid for 1 day: <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }


    @Transactional
    public int resetPassword(String token, String password) {

        ApplicationUser applicationUserToUpdate = confirmationTokenService.getApplicationUserFromToken(token);
        final String encodedPassword = bCryptPasswordEncoder.encode(password);

         return applicationUserRepository.updatePassword(applicationUserToUpdate.getApplicationUserId(), encodedPassword);
    }

    @Transactional(readOnly = true)
    public List<ApplicationUser> getUsers() {
//        return applicationUserRepository.findAll();

        List<ApplicationUser> usersList =  applicationUserRepository.findAll();

        System.out.println(usersList.size());
        return usersList;
    }

    @Transactional(readOnly = true)
    public ApplicationUser getOneUser(Long applicationId) {
        return applicationUserRepository.findById(applicationId).get();
    }

    @Transactional
    public Boolean createUser(String username, String password, String email, String firstName, String lastName, Role role) {
        final String encodedPassword = bCryptPasswordEncoder.encode(password);
        ApplicationUser applicationUser = new ApplicationUser(username, encodedPassword, email, firstName, lastName, 50000.0, role);
//        System.out.println("user created with id " + applicationUser.getApplicationUserId());
        applicationUserRepository.saveAndFlush(applicationUser);
        return true;
    }

    @Transactional
    public synchronized ApplicationUser updateUser(Long applicationUserId, String username, String password, String email, String firstName, String lastName, Role newRole) {

        Optional<ApplicationUser> userToUpdate = applicationUserRepository.findById(applicationUserId);
        if(userToUpdate.isPresent()) {
            ApplicationUser userUpdated = userToUpdate.get();
//            final String encodedUsername = bCryptPasswordEncoder.encode(username);
            userUpdated.setUsername(userUpdated.getUsername());
            final String encodedPassword = bCryptPasswordEncoder.encode(password);
            userUpdated.setPassword(encodedPassword);
            userUpdated.setEmail(email);
            userUpdated.setFirstName(firstName);
            userUpdated.setLastName(lastName);
            userUpdated.setRole(newRole);
            return applicationUserRepository.save(userUpdated);
        }
        return null;
    }

    @Transactional
    public synchronized boolean deleteUser(Long applicationUserId) {

        applicationUserRepository.deleteById(applicationUserId);
        return applicationUserRepository.findById(applicationUserId).isPresent();
    }

    @Transactional
    public synchronized int updateVerified(Long applicationUserId, boolean verified) {

        System.out.println("this is id in updateLocked method " + applicationUserId);
        return applicationUserRepository.updateVerified(applicationUserId, verified);
    }

    @Transactional
    public synchronized int updateAvailableFunds(Long applicationUserId, double availableFunds) {

//        System.out.println("this is id in updateLocked method " + applicationUserId);
        return applicationUserRepository.updateAvailableFunds(applicationUserId, availableFunds);
    }

    @Transactional(readOnly = true)
    public double getAvailableFunds(Long applicationUserId) {
        ApplicationUser applicationUser = getOneUser(applicationUserId);
        return applicationUser.getAvailableFunds();
    }


}

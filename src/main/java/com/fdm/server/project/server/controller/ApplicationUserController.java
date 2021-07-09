package com.fdm.server.project.server.controller;

import com.fdm.server.project.server.exception.types.EmailDoesNotExistException;
import com.fdm.server.project.server.exception.types.UsernameNotFoundInServerException;
import com.fdm.server.project.server.model.CreditCardResponse;
import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.ConfirmationToken;
import com.fdm.server.project.server.service.*;
import com.fdm.server.project.server.user.Role;
import com.fdm.server.project.server.user.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/server/applicationUser")
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;
    private final ConfirmationTokenService confirmationTokenService;
    private final CreditCardService creditCardService;

/*    todo: fix the forget password issue where registered users able to login without verifying email
            (possibly add logic in login to check if enabled == true)
 */
    @Autowired
    public ApplicationUserController(ApplicationUserService applicationUserService, ConfirmationTokenService confirmationTokenService, CreditCardService creditCardService) {
        this.applicationUserService = applicationUserService;
        this.confirmationTokenService = confirmationTokenService;
        this.creditCardService = creditCardService;
    }

    @GetMapping("/authorized/{username}")
    public ResponseEntity<ApplicationUser> authorizedUser(@PathVariable("username") String username) throws UsernameNotFoundInServerException {

        ApplicationUser authorisedUser = applicationUserService.getUserByUsernameAfterAuthorization(username);

        ResponseEntity<ApplicationUser> responseEntity = ResponseEntity.ok().body(authorisedUser);
        return responseEntity;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/accounts/forgotPassword/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable("email") String email) throws EmailDoesNotExistException {
        String result = applicationUserService.validateEmail(email);
        ResponseEntity<String> responseEntity = ResponseEntity.ok().body(result);
        return responseEntity;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/accounts/forgotPassword/validate/{token}")
    public String validate(@PathVariable("token") String token) {
        final ConfirmationToken confirmToken = confirmationTokenService.confirmToken(token, TokenType.RESET);
        return "validated";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/accounts/resetPassword/{token}/{password}")
    public int reset(@PathVariable("token") String token, @PathVariable("password") String password) {
        return applicationUserService.resetPassword(token, password);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/admin/users")
    public List<ApplicationUser> getAllUsers() {
        return applicationUserService.getUsers();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/admin/users/{applicationUserId}")
    public ApplicationUser getOneUser(@PathVariable("applicationUserId") Long applicationUserId) {
        return applicationUserService.getOneUser(applicationUserId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/admin/users/createUser/{username}/{password}/{email}/{firstName}/{lastName}/{role}")
    public Boolean createUser(@PathVariable("username") String username,
                               @PathVariable("password") String password,
                               @PathVariable("email") String email,
                               @PathVariable("firstName") String firstName,
                               @PathVariable("lastName") String lastName,
                               @PathVariable("role") String role) {
        Role newRole = Role.valueOf(role.toUpperCase());
        return applicationUserService.createUser(username, password, email, firstName, lastName, newRole);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(value = "/admin/users/changeUser/{applicationUserId}/{username}/{password}/{email}/{firstName}/{lastName}/{role}")
    public ApplicationUser updateUserDetails(@PathVariable("applicationUserId") Long applicationUserId,
                                             @PathVariable("username") String username,
                                             @PathVariable("password") String password,
                                             @PathVariable("email") String email,
                                             @PathVariable("firstName") String firstName,
                                             @PathVariable("lastName") String lastName,
                                             @PathVariable("role") String role) {
        Role newRole = Role.valueOf(role.toUpperCase());
        return applicationUserService.updateUser(applicationUserId, username, password, email, firstName, lastName, newRole);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(value = "/admin/users/{applicationUserId}")
    public boolean deleteUser(@PathVariable("applicationUserId") Long applicationUserId) {
        return applicationUserService.deleteUser(applicationUserId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/user/creditCards/{applicationUserId}/{number}/{name}/{expiry}/{cvc}")
    public ApplicationUser addCreditCard(@PathVariable("applicationUserId") Long applicationUserId,
                                             @PathVariable("name") String name,
                                             @PathVariable("number") String number,
                                             @PathVariable("expiry") String expiry,
                                             @PathVariable("cvc") String cvc) {
        return creditCardService.createCreditCard(applicationUserId, name, number, expiry, cvc);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/accounts/availableFunds/{applicationUserId}")
    public double getAvailableFunds(@PathVariable("applicationUserId") Long applicationUserId) {
        return applicationUserService.getAvailableFunds(applicationUserId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/accounts/creditCards/{applicationUserId}")
    public List<CreditCardResponse> getCreditCards(@PathVariable("applicationUserId") Long applicationUserId) {
        List<CreditCardResponse> creditCardResponses = creditCardService.getCreditCards(applicationUserId);
        System.out.println("size of list is " + creditCardResponses.size());

        creditCardResponses.forEach(creditCardResponse -> {
            System.out.println(creditCardResponse.toString());
        });

        return creditCardResponses;
    }

}

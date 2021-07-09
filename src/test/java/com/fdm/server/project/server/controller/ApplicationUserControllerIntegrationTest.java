package com.fdm.server.project.server.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdm.server.project.server.RabbitMqConfiguration;
import com.fdm.server.project.server.SecurityConfig;
import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.ConfirmationToken;
import com.fdm.server.project.server.entity.CreditCard;
import com.fdm.server.project.server.model.CreditCardResponse;
import com.fdm.server.project.server.service.*;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ApplicationUserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {RabbitMqConfiguration.class, SecurityConfig.class})
class ApplicationUserControllerIntegrationTest {

    private static String URL = "http://localhost:8081/api/v1/server/applicationUser/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApplicationUserService applicationUserService;

    @MockBean
    private ConfirmationTokenService confirmationTokenService;

    @MockBean
    private CreditCardService creditCardService;


    @Test
    void testGetAuthorizedApplicationUser() throws Exception {
        ApplicationUser a = new ApplicationUser();
        a.setApplicationUserId(1L);
        a.setUsername("test");
        a.setPassword("p");
        a.setFirstName("name1");
        a.setLastName("name2");
        a.setEmail("e");
        a.setAvailableFunds(50.0);
        a.setRole(Role.USER);

        String controlJson = mapToJson(a);

        Mockito.when(applicationUserService.getUserByUsernameAfterAuthorization(a.getUsername())).thenReturn(a);

        RequestBuilder builder = MockMvcRequestBuilders.get(URL + "/authorized/" + a.getUsername());
        MvcResult r = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = r.getResponse();
        String outputJson = response.getContentAsString();

        assertEquals(200, r.getResponse().getStatus());

        assertEquals(controlJson, outputJson);
    }

    @Test
    void testForgotPassword() throws Exception {
        Mockito.when(applicationUserService.validateEmail(anyString())).thenReturn("exists");

        RequestBuilder builder = MockMvcRequestBuilders.post(URL + "/accounts/forgotPassword/test");
        MvcResult r = mockMvc.perform(builder).andReturn();

        String result = r.getResponse().getContentAsString();
        assertEquals(200, r.getResponse().getStatus());
        assertEquals("exists", result);
    }

    @Test
    void testValidate() throws Exception {

        ApplicationUser a =  new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER);

        ConfirmationToken ct1 = new ConfirmationToken("test-token", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), a,"Registration" );

        Mockito.when(confirmationTokenService.confirmToken(any(), any())).thenReturn(ct1);

        RequestBuilder builder = MockMvcRequestBuilders.post(URL + "/accounts/forgotPassword/validate/test-token");
        MvcResult r = mockMvc.perform(builder).andReturn();

        assertEquals(200, r.getResponse().getStatus());
    }


    @Test
    void testReset() throws Exception {
        Mockito.when(applicationUserService.resetPassword(anyString(), anyString())).thenReturn(1);

        RequestBuilder builder = MockMvcRequestBuilders.post(URL + "/accounts/resetPassword/test-token/test-pass");
        MvcResult r = mockMvc.perform(builder).andReturn();

        String result = r.getResponse().getContentAsString();
        assertEquals(200, r.getResponse().getStatus());
        assertEquals("1", result);
    }


    @Test
    void testGetOneUser() throws Exception {

        Optional<ApplicationUser> a1 = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));

        Mockito.when(applicationUserService.getOneUser(anyLong())).thenReturn(a1.get());

        RequestBuilder builder = MockMvcRequestBuilders.get(URL + "/admin/users/" + 1L);
        MvcResult r = mockMvc.perform(builder).andReturn();

//        String result = r.getResponse().getContentAsString();
        assertEquals(200, r.getResponse().getStatus());
//        assertEquals("1", result);
    }

    @Test
    void testGetAllUsers() throws Exception {

        Optional<ApplicationUser> a1 = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));

        Optional<ApplicationUser> a2 = Optional.of(
                new ApplicationUser("test2", "p2", "test2@test.com", "test2-firstname", "test2-lastname", 50000.0, Role.USER));

        List<ApplicationUser> applicationUserList = new ArrayList<>();
        applicationUserList.add(a1.get());
        applicationUserList.add(a2.get());

        Mockito.when(applicationUserService.getUsers()).thenReturn(applicationUserList);

        RequestBuilder builder = MockMvcRequestBuilders.get(URL + "/admin/users");
        MvcResult r = mockMvc.perform(builder).andReturn();

//        String result = r.getResponse().getContentAsString();
        assertEquals(200, r.getResponse().getStatus());
//        assertEquals("1", result);
    }

    @Test
    void testCreateUser() throws Exception {

        Optional<ApplicationUser> a1 = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));

        Mockito.when(applicationUserService.createUser(anyString(), anyString(), anyString(), anyString(), anyString(), any())).thenReturn(true);

        RequestBuilder builder = MockMvcRequestBuilders.post(URL + "/admin/users/createUser/username/password/email/firstName/lastName/user");
        MvcResult r = mockMvc.perform(builder).andReturn();

        assertEquals(200, r.getResponse().getStatus());
    }



    @Test
    void testUpdateUserDetails() throws Exception {

        Optional<ApplicationUser> a1 = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));

        Mockito.when(applicationUserService.updateUser(anyLong(), anyString(), anyString(), anyString(), anyString(), anyString(), any())).thenReturn(a1.get());

        RequestBuilder builder = MockMvcRequestBuilders.put(URL + "/admin/users/changeUser/" +1L + "/username/password/email/firstName/lastName/user");
        MvcResult r = mockMvc.perform(builder).andReturn();

        assertEquals(200, r.getResponse().getStatus());
    }

    @Test
    void testDeleteUser() throws Exception {

        Mockito.when(applicationUserService.deleteUser(anyLong())).thenReturn(true);

        RequestBuilder builder = MockMvcRequestBuilders.delete(URL + "/admin/users/" +1L );
        MvcResult r = mockMvc.perform(builder).andReturn();

        assertEquals(200, r.getResponse().getStatus());
    }

    @Test
    void testAddCreditCard() throws Exception {

        Optional<ApplicationUser> a1 = Optional.of(
                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));

        Mockito.when(creditCardService.createCreditCard(anyLong(),anyString(), anyString(), anyString(), anyString())).thenReturn(a1.get());

        RequestBuilder builder = MockMvcRequestBuilders.post(URL + "/user/creditCards/" + 1L + "/625678/name/0412/123");
        MvcResult r = mockMvc.perform(builder).andReturn();

        assertEquals(200, r.getResponse().getStatus());
    }



    @Test
    void testGetAvailableFunds() throws Exception {

//        Optional<ApplicationUser> a1 = Optional.of(
//                new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER));

        Mockito.when(applicationUserService.getAvailableFunds(anyLong())).thenReturn(50000.0);

        RequestBuilder builder = MockMvcRequestBuilders.get(URL + "/accounts/availableFunds/" +1L );
        MvcResult r = mockMvc.perform(builder).andReturn();

        assertEquals(200, r.getResponse().getStatus());
    }


    @Test
    void testGetCreditCards() throws Exception {


        ApplicationUser a = new ApplicationUser("test","p","name1",Role.USER);
        CreditCardResponse c1 = new CreditCardResponse(1L, "456");
        CreditCardResponse c2 = new CreditCardResponse(1L, "456");
        List<CreditCardResponse> creditCardList = new ArrayList<>();
        creditCardList.add(c1);
        creditCardList.add(c2);
        Mockito.when(creditCardService.getCreditCards(anyLong())).thenReturn(creditCardList);

        RequestBuilder builder = MockMvcRequestBuilders.get(URL + "/accounts/creditCards/" +1L );
        MvcResult r = mockMvc.perform(builder).andReturn();

        assertEquals(200, r.getResponse().getStatus());
    }























    private String mapToJson(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);

    }


}

package com.fdm.server.project.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fdm.server.project.server.RabbitMqConfiguration;
import com.fdm.server.project.server.SecurityConfig;
import com.fdm.server.project.server.dto.RegistrationRequest;
import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.ConfirmationToken;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import com.fdm.server.project.server.service.ConfirmationTokenService;
import com.fdm.server.project.server.service.RegistrationService;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RegistrationControllerIntegrationTest.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {RabbitMqConfiguration.class, SecurityConfig.class})
public class RegistrationControllerIntegrationTest {


    private static String URL = "http://localhost:8081/api/v1/server/registration/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private ConfirmationTokenService confirmationTokenService;

    @MockBean
    private ApplicationUserRepository applicationUserService;

    @Test
    void testRegister() throws Exception {

        RegistrationRequest register = new RegistrationRequest("test", "test", "test", "test", "test@mail.com");

//        ApplicationUser a1 = new ApplicationUser("test1","p1","name1", Role.USER);
//        Stock s1 = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
//        Portfolio p1 =  new Portfolio(s1, 1L, 10.0 , a1, LocalDateTime.now(), 10.0);
//
//        List<Portfolio> test = new ArrayList<>();
//        test.add(p1);

        Mockito.when(registrationService.register(any())).thenReturn("random-token");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(register );

        RequestBuilder request = MockMvcRequestBuilders.post(URL).contentType(APPLICATION_JSON).content(requestJson);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Mockito.verify(registrationService).register(any());
    }

    @Test
    void testConfirm() throws Exception {

        ApplicationUser a =  new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER);

        ConfirmationToken ct1 = new ConfirmationToken("test-token", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), a,"Registration" );


        Mockito.when(confirmationTokenService.confirmToken(anyString(), any())).thenReturn(ct1);

        Mockito.when(applicationUserService.enableApplicationUser(any())).thenReturn(1);


        RequestBuilder builder = MockMvcRequestBuilders.post(URL + "confirm/token");
        MvcResult r = mockMvc.perform(builder).andReturn();

        assertEquals(200, r.getResponse().getStatus());
    }

    @Test
    void testRequestUnregister() throws Exception {

        Mockito.when(registrationService.unregister(anyLong(), anyString())).thenReturn(true);

        RequestBuilder request = MockMvcRequestBuilders.post(URL + "unregister/" + 1L).contentType(APPLICATION_JSON).content("random@mail.com");

        MvcResult r = mockMvc.perform(request).andReturn();

        assertEquals(200, r.getResponse().getStatus());
    }
}
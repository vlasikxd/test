package integration.controller;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.service.AccountDetailsIdService;
import com.bank.profile.service.imp.AccountDetailsIdServiceImp;
import com.bank.profile.supplier.AccountDetailsIdSupplier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bank.profile.configure.AbstractPostgresContainer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.netflix.appinfo.AmazonInfo.MetaDataKey.accountId;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@NoArgsConstructor
//@AllArgsConstructor
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class AccountDetailsIdControllerIT extends AbstractPostgresContainer {

    private static AccountDetailsIdDto accountDetails;

    ObjectMapper mapper;

    MockMvc mockMvc;

    @MockBean
    AccountDetailsIdService service;

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    public void createPositiveTest() throws Exception {
        doReturn(accountDetails).when(service).save(any());

        mockMvc.perform(post("/account/details-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(accountDetails)))
                .andExpectAll(status().isOk(),
                        jsonPath("$.accountId", is(accountId)),
                        jsonPath("$.profile", is(accountDetails.getProfile()))
                );
    }
}

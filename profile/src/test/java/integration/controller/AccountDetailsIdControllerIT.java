package integration.controller;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.service.AccountDetailsIdService;
import com.bank.profile.supplier.AccountDetailsIdSupplier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bank.profile.configure.AbstractPostgresContainer;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.netflix.appinfo.AmazonInfo.MetaDataKey.accountId;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AccountDetailsIdControllerIT extends AbstractPostgresContainer {

    private static AccountDetailsIdDto accountDetails;
    private static AccountDetailsIdSupplier accountDetailsIdSupplier;
    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @MockBean
    private AccountDetailsIdService service;
    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() throws Exception {
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

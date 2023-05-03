package integration.service;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.imp.AccountDetailsIdServiceImp;
import com.bank.profile.configure.AbstractPostgresContainer;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = AbstractPostgresContainer.class)
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class AccountDetailsIdServiceIT extends AbstractPostgresContainer {

    private static AccountDetailsIdEntity accountDetails;
    private static AccountDetailsIdEntity updatedAccountDetails;
    private static AccountDetailsIdDto updatedAccountDetailsDto;

    private final AccountDetailsIdServiceImp service;
    private final AccountDetailsIdRepository repository;

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void savePositiveTest() {

        final AccountDetailsIdDto result = service.save(updatedAccountDetailsDto);

        assertAll(
                () -> {
                    assertNull(result.getProfile());
                    assertEquals(updatedAccountDetailsDto.getId(), result.getId());
                    assertEquals(updatedAccountDetailsDto.getAccountId(), result.getAccountId());
                }
        );
    }

}

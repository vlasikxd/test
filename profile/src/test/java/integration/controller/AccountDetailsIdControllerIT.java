package integration.controller;

import com.bank.profile.service.imp.AccountDetailsIdServiceImp;
import integration.configure.AbstractPostgresContainer;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AbstractPostgresContainer.class)
@RequiredArgsConstructor
public class AccountDetailsIdControllerIT extends AbstractPostgresContainer {
    AccountDetailsIdServiceImp accountDetailsIdServiceImp;

    @Test
    public void findAccountById() {
        accountDetailsIdServiceImp.read(1L);
    }
}

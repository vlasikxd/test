package com.bank.authorization;

import com.bank.authorization.entity.Role;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class ParentTest {

    protected static final Long ONE = 1L;
    protected static final Long TWO = 2L;
    protected static final String USERNAME = "user";
    protected static final Role ROLE_USER = Role.USER;
    protected static final Role ROLE_ADMIN = Role.ADMIN;
    protected static final String PASSWORD = "password";
    protected static final String PASSWORD_ADMIN = "passwordAdmin";

}

package com.cedricmartens.hectogon.server.auth;

import com.cedricmartens.commons.networking.authentication.LoginStatus;
import com.cedricmartens.commons.networking.authentication.RegisterStatus;

public class AuthenticationMock implements AuthenticationService {

    @Override
    public LoginStatus login(String username, String password) {
        return LoginStatus.OK;
    }

    @Override
    public LoginStatus login(String token) {
        return null;
    }

    @Override
    public RegisterStatus register(String username, String email, String password)
    {
        return RegisterStatus.OK;
    }
}

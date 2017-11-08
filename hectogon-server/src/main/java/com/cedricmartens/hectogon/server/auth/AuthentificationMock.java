package com.cedricmartens.hectogon.server.auth;

import com.cedricmartens.commons.networking.authentification.LoginStatus;
import com.cedricmartens.commons.networking.authentification.RegisterStatus;

public class AuthentificationMock implements AuthentificationService {

    @Override
    public LoginStatus login(String username, String password) {
        return LoginStatus.OK;
    }

    @Override
    public RegisterStatus register(String username, String email, String password)
    {
        return RegisterStatus.OK;
    }
}

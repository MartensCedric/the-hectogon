package com.cedricmartens.hectogon.server.auth;

import com.cedricmartens.commons.networking.authentification.LoginStatus;
import com.cedricmartens.commons.networking.authentification.RegisterStatus;

public class DatabaseAuthentification implements AuthentificationService {
    @Override
    public LoginStatus login(String username, String password) {
        return null;
    }

    @Override
    public LoginStatus login(String token) {
        return null;
    }

    @Override
    public RegisterStatus register(String username, String email, String password)
    {
        if(!username.matches("[a-zA-Z0-9]{3,}"))
            return RegisterStatus.BAD_USERNAME;

        if(!email.contains("@"))
            return RegisterStatus.BAD_EMAIL;

        //if(!password.matches("")) //TODO enforce strong password

        return RegisterStatus.OK;
    }
}

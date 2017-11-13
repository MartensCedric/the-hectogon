package com.cedricmartens.hectogon.server.auth;

import com.cedricmartens.commons.networking.authentification.LoginStatus;
import com.cedricmartens.commons.networking.authentification.RegisterStatus;

public interface AuthentificationService
{
    LoginStatus login(String username, String password);
    LoginStatus login(String token);
    RegisterStatus register(String username, String email, String password);
}

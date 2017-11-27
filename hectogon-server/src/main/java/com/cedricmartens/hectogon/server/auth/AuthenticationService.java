package com.cedricmartens.hectogon.server.auth;

import com.cedricmartens.commons.networking.authentication.LoginStatus;
import com.cedricmartens.commons.networking.authentication.RegisterStatus;

public interface AuthenticationService
{
    LoginStatus login(String username, String password);
    LoginStatus login(String token);
    RegisterStatus register(String username, String email, String password);
}

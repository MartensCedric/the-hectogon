package com.cedricmartens.hectogon.client.core.game;

public interface AccountService
{
    void login(String username, String password);
    boolean hasCachedCredentials();
    String getCachedToken();
}

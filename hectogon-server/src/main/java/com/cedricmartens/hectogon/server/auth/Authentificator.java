package com.cedricmartens.hectogon.server.auth;


public class Authentificator
{
    private static AuthentificationService authentificationService;

    public static AuthentificationService getAuthentificationService() {
        if(authentificationService == null)
        {
            authentificationService = new AuthentificationMock();
        }

        return authentificationService;
    }
}

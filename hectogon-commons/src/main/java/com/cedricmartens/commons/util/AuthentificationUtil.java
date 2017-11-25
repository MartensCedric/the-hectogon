package com.cedricmartens.commons.util;


import java.security.MessageDigest;

public class AuthentificationUtil
{
    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static String saltString(String salt, String base)
    {
        return sha256(salt + base);
    }

    public static String hash(String base, String salt)
    {
        return saltString(salt, sha256(base));
    }
}


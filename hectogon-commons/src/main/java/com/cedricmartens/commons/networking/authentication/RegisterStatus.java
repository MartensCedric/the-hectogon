package com.cedricmartens.commons.networking.authentication;

public enum RegisterStatus
{
    OK,
    USERNAME_TAKEN,
    EMAIL_TAKEN,
    BAD_USERNAME,
    BAD_EMAIL,
    BAD_PASSWORD,
    UNEXPECTED_ERROR
}

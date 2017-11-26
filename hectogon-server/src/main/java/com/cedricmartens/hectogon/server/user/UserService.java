package com.cedricmartens.hectogon.server.user;

import com.cedricmartens.commons.User;
import com.cedricmartens.commons.UserNotFoundException;

public interface UserService
{
    User getUserById(int userId) throws UserNotFoundException;
    User getUserByUsername(String username) throws UserNotFoundException;

    boolean emailConfirmed(int userId) throws UserNotFoundException;
    boolean emailConfirmed(String username) throws UserNotFoundException;
    boolean emailConfirmed(User user) throws UserNotFoundException;
}

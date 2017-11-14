package com.cedricmartens.hectogon.server.user;

import com.cedricmartens.commons.User;

public interface UserService
{
    User getUserById(int userId);
    User getUserByUsername(String username);

    boolean emailConfirmed(int userId);
    boolean emailConfirmed(String username);
    boolean emailConfirmed(User user);
}

package com.cedricmartens.hectogon.server.user;

import com.cedricmartens.commons.User;

public class UserMock implements UserService
{
    @Override
    public User getUserById(int userId) {
        return new User(0, "Loomy" + userId);
    }

    @Override
    public User getUserByUsername(String username) {
        return new User(0, username);
    }

    @Override
    public boolean emailConfirmed(int userId) {
        return emailConfirmed(getUserById(userId));
    }

    @Override
    public boolean emailConfirmed(String username) {
        return emailConfirmed(getUserByUsername(username));
    }

    @Override
    public boolean emailConfirmed(User user) {
        return true;
    }
}

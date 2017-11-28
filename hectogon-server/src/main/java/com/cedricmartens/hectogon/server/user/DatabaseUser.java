package com.cedricmartens.hectogon.server.user;

import com.cedricmartens.commons.User;
import com.cedricmartens.commons.UserNotFoundException;
import com.cedricmartens.hectogon.server.db.DatabaseManager;

public class DatabaseUser implements UserService {
    @Override
    public User getUserById(int userId) throws UserNotFoundException {
        DatabaseManager dm = DatabaseManager.getDatabaseManager();
        try {
            String username = dm.getUsername(userId);
            return new User(userId, username);
        }catch (Exception e)
        {
            e.printStackTrace();
            throw new UserNotFoundException();
        }
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException{
        DatabaseManager dm = DatabaseManager.getDatabaseManager();
        try {
            int id = dm.getIdByUsername(username);
            if(id == dm.NO_RESULTS)
                throw new UserNotFoundException();

            return new User(id, username);
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new UserNotFoundException();
        }
    }

    @Override
    public boolean emailConfirmed(int userId) throws UserNotFoundException{
        return false;
    }

    @Override
    public boolean emailConfirmed(String username) throws UserNotFoundException{
        return false;
    }

    @Override
    public boolean emailConfirmed(User user) throws UserNotFoundException {
        return false;
    }
}

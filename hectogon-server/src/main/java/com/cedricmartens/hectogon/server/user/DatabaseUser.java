package com.cedricmartens.hectogon.server.user;

import com.cedricmartens.commons.User;
import com.cedricmartens.hectogon.server.db.DatabaseManager;

public class DatabaseUser implements UserService {
    @Override
    public User getUserById(int userId) throws UserNotFoundException {
        DatabaseManager dm = DatabaseManager.getDatabaseManager();
        try {
            String username = dm.getUsername(userId);
            User user = new User(userId, username);
            return user;
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

            User user = new User(id, username);
            return user;
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

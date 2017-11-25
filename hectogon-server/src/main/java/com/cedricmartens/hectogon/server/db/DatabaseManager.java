package com.cedricmartens.hectogon.server.db;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

/**
 * Created by martens on 17/03/17.
 */
public class DatabaseManager
{
    private static DatabaseManager databaseManager;
    private Connection db_conn;

    private DatabaseManager(String connection, String user, String password) throws SQLException {
        db_conn = DriverManager.getConnection(connection, user, password);
    }

    public static void initDatabaseManager(String connection, String user, String password) throws SQLException {
        databaseManager = new DatabaseManager(connection, user, password);
    }

    public static DatabaseManager getDatabaseManager()
    {
        if(databaseManager == null)
            throw new IllegalStateException();

        return databaseManager;
    }

    public static String get(String filename) throws IOException
    {
        Scanner sc = new Scanner(new File("src/com/cedricmartens/hectogon/sql/"+ filename + ".sql"));
        String str = "";
        while(sc.hasNextLine()){
            str += sc.nextLine();
        }
        return str;
    }

    public String getUsername(int playerId) throws QueryException, IllegalDatabaseStateException, java.sql.SQLException, IOException {

        String script = DatabaseManager.get("username_from_id");
        PreparedStatement statement = db_conn.prepareStatement(script);
        statement.setInt(1, playerId);
        ResultSet set = statement.executeQuery();

        if(!set.next())
            throw new QueryException();

        String username = set.getString("Username");
        if(set.next())
            throw new IllegalDatabaseStateException();

        return username;
    }

    public void createUser(String username, String email, String password) throws IOException, SQLException {
        String script = DatabaseManager.get("create_user");
        PreparedStatement statement = db_conn.prepareStatement(script);
    }
}

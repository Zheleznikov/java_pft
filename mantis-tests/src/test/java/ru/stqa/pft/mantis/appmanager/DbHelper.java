package ru.stqa.pft.mantis.appmanager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbHelper {

    private final ApplicationManager app;

    public DbHelper(ApplicationManager app) {
        this.app = app;

    }

    public List<Map<String, String>> getAllUsers() {
        Connection conn = null;
        try {
            String connection = "jdbc:mysql://localhost/bugtracker?user=root&password=";
            String query = "SELECT username, email FROM mantis_user_table WHERE username != 'administrator';";

            conn = DriverManager.getConnection(connection);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            List<Map<String, String>> users = new ArrayList<>();
            while (rs.next()) {
                users.add(Map.of(
                        "name", rs.getString("username"),
                        "email", rs.getString("email")
                ));
            }

            rs.close();
            st.close();
            conn.close();
            return users;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }
}

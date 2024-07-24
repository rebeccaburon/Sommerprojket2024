package app.persistence;

import app.entities.Customer;
import app.exceptions.DatabaseException;

import java.sql.*;

public class CustomerMapper {

    public static Customer login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userid = rs.getInt("userid");
                String name = rs.getString("name");
                String lastname = rs.getString("lastname");
                email = rs.getString("email");
                password = rs.getString("password");

                return new Customer( userid,name, email, password, lastname);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen");

            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }


    public static int createUser( String name,String lastName, String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO users (name,lastname, email, password) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, password);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            } else {
                throw new DatabaseException("Error with creating a new user");
            }
        } catch (SQLException e) {
            String msg = "ERROR, try again";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "E-mailen already excited. Chose another";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }
}



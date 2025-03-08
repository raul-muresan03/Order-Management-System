package org.example.BusinessLayer;

import org.example.DataAccesLayer.DAO.UserDAO;
import org.example.Model.Users;

import java.sql.SQLException;
import java.util.List;

public class ClientBus {

    private static UserDAO clientDAO = new UserDAO(Users.class);

    public static List<Users> findAll() throws SQLException {
        return clientDAO.findAll();
    }

    public static Users findById(int id) throws SQLException {
        return clientDAO.findById(id);
    }

    public static void insert(Users users) throws SQLException {
        clientDAO.insert(users);
    }

    public static void update(int id, Users users) throws SQLException {
        clientDAO.update(id, users);
    }

    public static void delete(int id) throws SQLException {
        clientDAO.delete(id);
    }

    public static String[] columnNames() {
        return clientDAO.columnNames();
    }
}

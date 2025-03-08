package org.example.DataAccesLayer.DAO;

import org.example.Model.Users;

public class UserDAO extends AbstractDAO<Users> {
    public UserDAO(Class<Users> type) {
        super(type);
    }
}
package com.module1.crud.users.model.service;

import com.module1.crud.users.model.dao.UsersDAO;
import com.module1.crud.users.model.dto.UsersDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsersService {

    private final UsersDAO usersDAO;
    private final Connection connection;

    public UsersService(Connection connection) {
        this.usersDAO = new UsersDAO(connection);
        this.connection = connection;
    }


    public List<UsersDTO> findAllUsers() {
        try {
            return usersDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

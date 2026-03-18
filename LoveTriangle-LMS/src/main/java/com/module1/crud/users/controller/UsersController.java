package com.module1.crud.users.controller;

import com.module1.crud.users.model.dto.UsersDTO;
import com.module1.crud.users.model.service.UsersService;

import java.util.List;

public class UsersController {

    private final UsersService service;
    public UsersController(UsersService usersService) {
        this.service = usersService;
    }


    public List<UsersDTO> findAllUsers(){

        return service.findAllUsers();
    }

}

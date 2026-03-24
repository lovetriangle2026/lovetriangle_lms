package com.module1.crud.users.view;

import com.module1.crud.users.model.dto.UsersDTO;

import java.util.List;

public class UsersOutputView {

    public void printMessage(String s) {
        System.out.println(s);
    }

    public void printSuccess(String message) {

        System.out.println("✅ " + message);
    }

    public void printError(String message) {

        System.out.println("🚨🚨 " + message);
    }
}

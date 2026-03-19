package com.module1.crud.global.loginpage.view;

public class LoginOutputView {

    public void printSuccess(String message) {

        System.out.println("✅ " + message);
    }

    public void printError(String message) {

        System.out.println("🚨🚨 " + message);
    }
}

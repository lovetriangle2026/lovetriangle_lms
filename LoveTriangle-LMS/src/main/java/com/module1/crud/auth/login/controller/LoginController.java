package com.module1.crud.auth.login.controller;

import com.module1.crud.auth.login.service.LoginService;

public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) { this.loginService = loginService; }

    public boolean login(String loginId, String password) {
        return loginService.login(loginId, password);
    }
}
package com.module1.crud.global.loginpage.controller;

import com.module1.crud.global.loginpage.model.service.LoginService;

public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    public boolean login(String loginId, String password) {
        // View에서 받은 아이디/비번을 서비스로 넘겨서 로그인 결과(T/F)를 받습니다.
        return loginService.login(loginId, password);
    }
}

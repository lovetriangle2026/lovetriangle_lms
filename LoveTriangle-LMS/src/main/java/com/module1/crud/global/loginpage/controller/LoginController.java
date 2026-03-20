package com.module1.crud.global.loginpage.controller;

import com.module1.crud.global.loginpage.model.service.LoginService;
import com.module1.crud.users.model.dto.UsersDTO;

import java.time.LocalDate;

public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    public boolean login(String loginId, String password) {
        // View에서 받은 아이디/비번을 서비스로 넘겨서 로그인 결과(T/F)를 받습니다.
        return loginService.login(loginId, password);
    }
    public Long createUser(String userCode, String loginId, String name, LocalDate birth, String telNum, String password, String pwAnswer, String userType) {
        /* comment.
         * 기존 학적 정보에 계정 정보(loginId, password 등)를 더하여 하나의 DTO로 묶습니다.
         * id는 DB에서 이미 발급되어 있으나 자바 단에서는 아직 모르므로 기본값(0)을 넘깁니다.
         * */
        UsersDTO newUser = new UsersDTO(0, userCode, loginId, name, birth, telNum, password, pwAnswer, userType);

        // 조립된 DTO를 Service 계층으로 넘겨 계정 활성화(UPDATE) 비즈니스 로직을 수행합니다.
        return loginService.saveUser(newUser);
    }
    // 💡 boolean에서 String으로 반환 타입 변경
    public String verifyUser(String userCode, String name, LocalDate birth, String userType) {
        return loginService.verifyUser(userCode, name, birth, userType);
    }

}



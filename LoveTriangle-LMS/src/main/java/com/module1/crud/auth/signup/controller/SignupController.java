package com.module1.crud.auth.signup.controller;

import com.module1.crud.auth.signup.service.SignupService;
import com.module1.crud.users.model.dto.UsersDTO;
import java.time.LocalDate;

public class SignupController {
    private final SignupService signupService;

    public SignupController(SignupService signupService) { this.signupService = signupService; }

    public String verifyUser(String userCode, String name, LocalDate birth, String userType) {
        return signupService.verifyUser(userCode, name, birth, userType);
    }

    public Long createUser(String userCode, String loginId, String name, LocalDate birth, String telNum, String password, String pwAnswer, String userType, int isHeartPublic) {
        UsersDTO newUser = new UsersDTO(0, userCode, loginId, name, birth, telNum, password, pwAnswer, userType, isHeartPublic);
        return signupService.saveUser(newUser);
    }
}
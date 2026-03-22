package com.module1.crud.auth.find.controller;

import com.module1.crud.auth.find.service.FindAccountService;

public class FindAccountController {
    private final FindAccountService findAccountService;

    public FindAccountController(FindAccountService findAccountService) { this.findAccountService = findAccountService; }

    public String findLoginId(String userCode, String name) { return findAccountService.findLoginId(userCode, name); }
    public boolean verifyPasswordHint(String loginId, String userCode, String answer) { return findAccountService.verifyPasswordHint(loginId, userCode, answer); }
    public boolean resetPassword(String loginId, String newPassword) { return findAccountService.resetPassword(loginId, newPassword); }

    public boolean verifyCurrentPassword(String loginId, String currentPw) {
        // 💡 서비스에 비밀번호 검증 요청
        return findAccountService.verifyCurrentPassword(loginId, currentPw);
    }
}
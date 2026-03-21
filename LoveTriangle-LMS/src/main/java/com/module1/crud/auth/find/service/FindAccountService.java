package com.module1.crud.auth.find.service;

import com.module1.crud.auth.dao.AuthDAO;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;

public class FindAccountService {
    private final AuthDAO authDAO;

    public FindAccountService(AuthDAO authDAO) { this.authDAO = authDAO; }

    public String findLoginId(String userCode, String name) {
        try { return authDAO.findLoginId(userCode, name); }
        catch (SQLException e) { return null; }
    }

    public boolean verifyPasswordHint(String loginId, String userCode, String answer) {
        try { return authDAO.verifyPasswordHint(loginId, userCode, answer); }
        catch (SQLException e) { return false; }
    }

    public boolean resetPassword(String loginId, String newPlainPassword) {
        try {
            String hashedNewPassword = BCrypt.hashpw(newPlainPassword, BCrypt.gensalt());
            return authDAO.updatePassword(loginId, hashedNewPassword) > 0;
        } catch (SQLException e) { return false; }
    }
}
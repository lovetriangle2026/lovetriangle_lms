package com.module1.crud.auth.login.service;

import com.module1.crud.auth.dao.AuthDAO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;

public class LoginService {
    private final AuthDAO authDAO; // 💡 공통 AuthDAO 사용

    public LoginService(AuthDAO authDAO) { this.authDAO = authDAO; }

    public boolean login(String loginId, String password) {
        try {
            UsersDTO user = authDAO.findByLoginId(loginId);
            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                SessionManager.getInstance().setLoggedInUser(user);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("로그인 처리 중 DB 오류 발생!!! 🚨", e);
        }
        return false;
    }
}
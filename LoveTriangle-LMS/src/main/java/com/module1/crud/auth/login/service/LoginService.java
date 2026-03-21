package com.module1.crud.auth.login.service;

import com.module1.crud.auth.dao.AuthDAO;
import com.module1.crud.global.config.JDBCTemplate; // 💡 임포트 추가
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginService {
    private final AuthDAO authDAO;

    // 💡 생성자 인자 제거. 내부에서 기본 생성자로 조립!
    public LoginService() {
        this.authDAO = new AuthDAO();
    }

    public boolean login(String loginId, String password) {
        try (Connection con = JDBCTemplate.getConnection()) {
            UsersDTO user = authDAO.findByLoginId(con, loginId);
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
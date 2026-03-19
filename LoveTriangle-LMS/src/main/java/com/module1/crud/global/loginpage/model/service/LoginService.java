package com.module1.crud.global.loginpage.model.service;

import com.module1.crud.global.loginpage.model.dao.LoginDAO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dao.UsersDAO;
import com.module1.crud.users.model.dto.UsersDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginService {

    private final LoginDAO loginDAO;
    private final Connection connection;

    public LoginService(Connection connection) {
        this.loginDAO = new LoginDAO(connection);
        this.connection = connection;
    }

    public boolean login(String loginId, String password) {
        try {
            // 1. DAO를 통해 DB에서 일치하는 회원이 있는지 조회합니다.
            UsersDTO user = loginDAO.findByLoginIdAndPassword(loginId, password);

            // 2. 결과가 null이 아니면 로그인 성공!
            if (user != null) {
                // 3. 💡 핵심: 공용 사물함(SessionManager)에 로그인한 유저 정보를 저장합니다.
                SessionManager.getInstance().setLoggedInUser(user);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("로그인 처리 중 DB 오류 발생!!! 🚨", e);
        }

        // 회원을 찾지 못했거나 오류가 나면 실패(false) 반환
        return false;
    }

}

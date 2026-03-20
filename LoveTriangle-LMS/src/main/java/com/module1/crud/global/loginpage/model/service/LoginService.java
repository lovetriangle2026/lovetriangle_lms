package com.module1.crud.global.loginpage.model.service;

import com.module1.crud.global.loginpage.model.dao.LoginDAO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dao.UsersDAO;
import com.module1.crud.users.model.dto.UsersDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

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
    public Long saveUser(UsersDTO newUser) {
        try {
            // DAO를 호출하여 DB의 기존 학적 레코드에 사용자 계정 정보를 업데이트합니다.
            return loginDAO.save(newUser);
        } catch (SQLException e) {
            // DB에서 발생한 예외를 런타임 예외로 전환하여 던집니다. (스택 트레이스 보존)
            throw new RuntimeException("회원 가입(계정 정보 업데이트) 처리 중 Error 발생!!! 🚨", e);
        }
    }

    public String verifyUser(String userCode, String name, LocalDate birth, String userType) {
        try {
            // 1. 학사 데이터 일치 여부 확인 (없으면 NOT_FOUND)
            boolean isSchoolMember = loginDAO.checkSchoolMember(userCode, name, birth, userType);
            if (!isSchoolMember) {
                return "NOT_FOUND";
            }

            // 2. 이미 ID를 생성한 가입자인지 확인 (있으면 ALREADY_REGISTERED)
            boolean isAlreadyRegistered = loginDAO.checkAlreadyRegistered(userCode);
            if (isAlreadyRegistered) {
                return "ALREADY_REGISTERED";
            }

            // 3. 학적도 있고 아이디도 없으면 가입 가능 (AVAILABLE)
            return "AVAILABLE";

        } catch (SQLException e) {
            System.out.println("🚨 [System Error] DB 통신 중 예외가 발생했습니다: " + e.getMessage());
            return "ERROR";
        }
    }
}

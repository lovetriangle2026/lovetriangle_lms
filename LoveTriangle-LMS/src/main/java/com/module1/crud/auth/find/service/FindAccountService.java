package com.module1.crud.auth.find.service;

import com.module1.crud.auth.dao.AuthDAO;
import com.module1.crud.global.config.JDBCTemplate; // 💡 임포트 추가
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.SQLException;

public class FindAccountService {
    private final AuthDAO authDAO;

    // 💡 생성자 인자 제거. 내부에서 기본 생성자로 조립!
    public FindAccountService() {
        this.authDAO = new AuthDAO();
    }

    public String findLoginId(String userCode, String name) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return authDAO.findLoginId(con, userCode, name);
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean verifyPasswordHint(String loginId, String userCode, String answer) {
        try (Connection con = JDBCTemplate.getConnection()) {
            return authDAO.verifyPasswordHint(con, loginId, userCode, answer);
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean resetPassword(String loginId, String newPlainPassword) {
        try (Connection con = JDBCTemplate.getConnection()) {
            String hashedNewPassword = BCrypt.hashpw(newPlainPassword, BCrypt.gensalt());
            return authDAO.updatePassword(con, loginId, hashedNewPassword) > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    // FindAccountService.java 에 추가

    public boolean verifyCurrentPassword(String loginId, String currentPw) {
        try (Connection con = JDBCTemplate.getConnection()) {
            // 1. DB에서 해당 아이디의 암호화된 비밀번호(Hashed PW)를 가져옵니다.
            String hashedPw = authDAO.getPassword(con, loginId);

            if (hashedPw == null) return false; // 유저가 없으면 실패

            // 2. BCrypt.checkpw(평문 암호, 해싱된 암호)로 일치 여부 확인
            return BCrypt.checkpw(currentPw, hashedPw);

        } catch (SQLException e) {
            System.err.println("🚨 비밀번호 검증 중 DB 에러 발생: " + e.getMessage());
            return false;
        }
    }
}
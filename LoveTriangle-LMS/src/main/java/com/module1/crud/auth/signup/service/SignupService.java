package com.module1.crud.auth.signup.service;

import com.module1.crud.auth.dao.AuthDAO;
import com.module1.crud.global.config.JDBCTemplate; // 💡 임포트 추가
import com.module1.crud.users.model.dto.UsersDTO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class SignupService {
    private final AuthDAO authDAO;

    // 💡 생성자 인자 제거. 내부에서 기본 생성자로 조립!
    public SignupService() {
        this.authDAO = new AuthDAO();
    }

    public String verifyUser(String userCode, String name, LocalDate birth, String userType) {
        // 💡 2개의 SELECT 쿼리가 같은 다리(Connection)를 재사용합니다! 효율성 UP!
        try (Connection con = JDBCTemplate.getConnection()) {
            if (!authDAO.checkSchoolMember(con, userCode, name, birth, userType)) return "NOT_FOUND";
            if (authDAO.checkAlreadyRegistered(con, userCode)) return "ALREADY_REGISTERED";
            return "AVAILABLE";
        } catch (SQLException e) {
            return "ERROR";
        }
    }

    public Long saveUser(UsersDTO newUser) {
        // 💡 UPDATE 와 SELECT 가 연속으로 발생하므로 Service가 트랜잭션을 지휘합니다!
        try (Connection con = JDBCTemplate.getConnection()) {
            con.setAutoCommit(false); // 🔒 트랜잭션 시작

            try {
                newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt()));
                Long generatedId = authDAO.save(con, newUser);

                if (generatedId == null) {
                    throw new RuntimeException("회원 정보 저장 실패");
                }

                con.commit(); // 🔓 완벽하게 저장되면 커밋
                return generatedId;

            } catch (Exception e) {
                try {
                    con.rollback(); // 🚨 에러 시 원상복구
                } catch (SQLException ex) {
                    throw new RuntimeException("회원 가입 롤백 Error", ex);
                }
                throw new RuntimeException("회원 가입 트랜잭션 Error", e);
            } finally {
                try {
                    con.setAutoCommit(true); // 오토커밋 복구
                } catch (SQLException ex) {
                    throw new RuntimeException("오토커밋 복구 Error", ex);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 Error", e);
        }
    }
}
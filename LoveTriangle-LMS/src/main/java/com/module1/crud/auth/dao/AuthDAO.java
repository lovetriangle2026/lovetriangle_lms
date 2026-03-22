package com.module1.crud.auth.dao;

import com.module1.crud.global.utils.QueryUtil;
import com.module1.crud.users.model.dto.UsersDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AuthDAO {

    // 💡 DAO는 가볍게! 필드와 생성자에서 Connection을 지웁니다.
    public AuthDAO() {
    }

    // 1. [로그인] 아이디로 회원 조회
    public UsersDTO findByLoginId(Connection con, String loginId) throws SQLException {
        String query = QueryUtil.getQuery("Users.findByLoginId");
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, loginId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new UsersDTO(
                            rs.getInt("id"), rs.getString("user_code"), rs.getString("login_id"),
                            rs.getString("name"), rs.getDate("birth").toLocalDate(), rs.getString("tel_num"),
                            rs.getString("password"), rs.getString("pw_answer"), rs.getString("user_type")
                    );
                }
            }
        }
        return null;
    }

    // 2. [회원가입] 학적 확인
    public boolean checkSchoolMember(Connection con, String userCode, String name, LocalDate birth, String userType) throws SQLException {
        String query = QueryUtil.getQuery("Users.checkSchoolMember");
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, userCode);
            pstmt.setString(2, name);
            pstmt.setDate(3, java.sql.Date.valueOf(birth));
            pstmt.setString(4, userType);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // 3. [회원가입] 중복 가입 확인
    public boolean checkAlreadyRegistered(Connection con, String userCode) throws SQLException {
        String query = QueryUtil.getQuery("Users.checkAlreadyRegistered");
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, userCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String loginId = rs.getString("login_id");
                    return loginId != null && !loginId.trim().isEmpty();
                }
            }
        }
        return false;
    }

    // 4. [회원가입] 계정 정보 업데이트 (생성)
    public Long save(Connection con, UsersDTO usersDTO) throws SQLException {
        String updateQuery = QueryUtil.getQuery("Users.updateAccount");
        try (PreparedStatement pstmt = con.prepareStatement(updateQuery)) {
            pstmt.setString(1, usersDTO.getLoginId());
            pstmt.setString(2, usersDTO.getPassword());
            pstmt.setString(3, usersDTO.getTelNum());
            pstmt.setString(4, usersDTO.getPwAnswer());
            pstmt.setString(5, usersDTO.getUserCode());

            if (pstmt.executeUpdate() > 0) {
                String selectQuery = QueryUtil.getQuery("Users.findIdByUserCode");
                // 💡 안쪽 try에서도 위에서 받은 con을 그대로 재사용합니다!
                try (PreparedStatement selectPstmt = con.prepareStatement(selectQuery)) {
                    selectPstmt.setString(1, usersDTO.getUserCode());
                    try (ResultSet rs = selectPstmt.executeQuery()) {
                        if (rs.next()) return rs.getLong("id");
                    }
                }
            }
        }
        return null;
    }

    // 5. [아이디 찾기]
    public String findLoginId(Connection con, String userCode, String name) throws SQLException {
        String query = QueryUtil.getQuery("Users.findLoginId");
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, userCode);
            pstmt.setString(2, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getString("login_id");
            }
        }
        return null;
    }

    // 6. [비밀번호 찾기] 힌트 검증
    public boolean verifyPasswordHint(Connection con, String loginId, String userCode, String answer) throws SQLException {
        String query = QueryUtil.getQuery("Users.verifyPasswordHint");
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, loginId);
            pstmt.setString(2, userCode);
            pstmt.setString(3, answer);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // 7. [비밀번호 변경] 업데이트
    public int updatePassword(Connection con, String loginId, String hashedPassword) throws SQLException {
        String query = QueryUtil.getQuery("Users.updatePassword");
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, hashedPassword);
            pstmt.setString(2, loginId);
            return pstmt.executeUpdate();
        }
    }

    // AuthDAO.java 에 추가

    public String getPassword(Connection con, String loginId) throws SQLException {
        String query = "SELECT user_pw FROM users WHERE login_id = ?"; // 💡 테이블/컬럼명은 환경에 맞게 수정

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, loginId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getString("user_pw");
                }
            }
        }
        return null; // 일치하는 아이디가 없을 경우
    }

}
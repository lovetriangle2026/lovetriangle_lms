package com.module1.crud.auth.dao;

import com.module1.crud.global.utils.QueryUtil;
import com.module1.crud.users.model.dto.UsersDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AuthDAO {
    private final Connection connection;

    public AuthDAO(Connection connection) {
        this.connection = connection;
    }

    // 1. [로그인] 아이디로 회원 조회
    public UsersDTO findByLoginId(String loginId) throws SQLException {
        String query = QueryUtil.getQuery("Users.findByLoginId");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
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
    public boolean checkSchoolMember(String userCode, String name, LocalDate birth, String userType) throws SQLException {
        String query = QueryUtil.getQuery("Users.checkSchoolMember");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
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
    public boolean checkAlreadyRegistered(String userCode) throws SQLException {
        String query = QueryUtil.getQuery("Users.checkAlreadyRegistered");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
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
    public Long save(UsersDTO usersDTO) throws SQLException {
        String updateQuery = QueryUtil.getQuery("Users.updateAccount");
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setString(1, usersDTO.getLoginId());
            pstmt.setString(2, usersDTO.getPassword());
            pstmt.setString(3, usersDTO.getTelNum());
            pstmt.setString(4, usersDTO.getPwAnswer());
            pstmt.setString(5, usersDTO.getUserCode());

            if (pstmt.executeUpdate() > 0) {
                String selectQuery = QueryUtil.getQuery("Users.findIdByUserCode");
                try (PreparedStatement selectPstmt = connection.prepareStatement(selectQuery)) {
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
    public String findLoginId(String userCode, String name) throws SQLException {
        String query = QueryUtil.getQuery("Users.findLoginId");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userCode);
            pstmt.setString(2, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getString("login_id");
            }
        }
        return null;
    }

    // 6. [비밀번호 찾기] 힌트 검증
    public boolean verifyPasswordHint(String loginId, String userCode, String answer) throws SQLException {
        String query = QueryUtil.getQuery("Users.verifyPasswordHint");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
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
    public int updatePassword(String loginId, String hashedPassword) throws SQLException {
        String query = QueryUtil.getQuery("Users.updatePassword");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, hashedPassword);
            pstmt.setString(2, loginId);
            return pstmt.executeUpdate();
        }
    }
}
package com.module1.crud.global.loginpage.model.dao;

import com.module1.crud.global.utils.QueryUtil;
import com.module1.crud.users.model.dto.UsersDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class LoginDAO {

    private final Connection connection;

    public LoginDAO(Connection connection) {
        this.connection = connection;
    }


    public UsersDTO findByLoginIdAndPassword(String loginId, String password) throws SQLException {
        // 1. XML에서 SELECT 쿼리문을 가져옵니다.
        String query = QueryUtil.getQuery("Users.findByLoginIdAndPassword");

        // 2. 쿼리 실행을 위한 PreparedStatement 준비 (자원 자동 해제)
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            // 3. 사용자가 입력한 ID와 PW를 쿼리의 ? 자리에 바인딩합니다.
            pstmt.setString(1, loginId);
            pstmt.setString(2, password);

            // 4. SELECT 쿼리는 executeQuery()로 실행하며, 결과는 ResultSet으로 받습니다.
            try (ResultSet rs = pstmt.executeQuery()) {

                // 5. 조건에 맞는 데이터가 존재한다면 (로그인 성공)
                if (rs.next()) {
                    // DB에서 꺼낸 데이터를 UsersDTO 객체로 조립하여 반환합니다.
                    return new UsersDTO(
                            rs.getInt("id"),
                            rs.getString("user_code"),
                            rs.getString("login_id"),
                            rs.getString("name"),
                            // 💡 핵심: DB의 Date 타입을 다시 자바의 LocalDate로 변환하여 꺼냅니다.
                            rs.getDate("birth").toLocalDate(),
                            rs.getString("tel_num"),
                            rs.getString("password"),
                            rs.getString("pw_answer"),
                            rs.getString("user_type")
                    );
                }
            }
        }return null;

    }


    public Long save(UsersDTO usersDTO) throws SQLException {
        // 1. 기존 학적 레코드의 빈칸(ID, 비번 등)을 채우는 UPDATE 쿼리
        String updateQuery = QueryUtil.getQuery("Users.updateAccount");

        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            // 채워 넣을 계정 정보들 바인딩
            pstmt.setString(1, usersDTO.getLoginId());
            pstmt.setString(2, usersDTO.getPassword());
            pstmt.setString(3, usersDTO.getTelNum());
            pstmt.setString(4, usersDTO.getPwAnswer());

            // WHERE 조건: 1단계에서 인증된 학번/사번
            pstmt.setString(5, usersDTO.getUserCode());

            int affectedRows = pstmt.executeUpdate();

            // 2. 업데이트가 성공적으로 적용되었다면, 뷰에 반환할 고유 ID(PK)를 조회합니다.
            if (affectedRows > 0) {
                String selectQuery = QueryUtil.getQuery("Users.findIdByUserCode");

                try (PreparedStatement selectPstmt = connection.prepareStatement(selectQuery)) {
                    selectPstmt.setString(1, usersDTO.getUserCode());

                    try (ResultSet rs = selectPstmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getLong("id"); // DB에 저장된 진짜 고유 ID 반환!
                        }
                    }
                }
            }
        }
        return null; // 업데이트 실패 시 null 반환
    }
// 1. 학적 정보(이름, 학번, 생년월일, 소속)가 존재하는지 확인
    public boolean checkSchoolMember(String userCode, String name, LocalDate birth, String userType) throws SQLException {
        // SELECT COUNT(*) FROM users WHERE user_code = ? AND name = ? AND birth = ? AND user_type = ?
        String query = QueryUtil.getQuery("Users.checkSchoolMember");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userCode);
            pstmt.setString(2, name);
            pstmt.setDate(3, java.sql.Date.valueOf(birth));
            pstmt.setString(4, userType);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // 2. 이미 가입된 유저인지 확인 (login_id가 채워져 있는지 검사)
    public boolean checkAlreadyRegistered(String userCode) throws SQLException {
        // SELECT login_id FROM users WHERE user_code = ?
        String query = QueryUtil.getQuery("Users.checkAlreadyRegistered");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String loginId = rs.getString("login_id");
                    // loginId가 null이 아니고 비어있지 않다면 이미 가입한 회원!
                    return loginId != null && !loginId.trim().isEmpty();
                }
            }
        }
        return false;
    }

}
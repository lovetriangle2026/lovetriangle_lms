package com.module1.crud.global.loginpage.model.dao;

import com.module1.crud.global.utils.QueryUtil;
import com.module1.crud.users.model.dto.UsersDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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

}
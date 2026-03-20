package com.module1.crud.users.model.dao;

import com.module1.crud.global.utils.QueryUtil;
import com.module1.crud.users.model.dto.UsersDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {

    private final Connection connection;

    public UsersDAO(Connection connection) {
        this.connection = connection;
    }

    public List<UsersDTO> findAll() throws SQLException {

        String query = QueryUtil.getQuery("users.findAll");
        List<UsersDTO> usersDTOList = new ArrayList<>();


        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                // 1. DB에서 값을 뽑아서 바로 생성자에 넣는 방식 (모든 필드 생성자가 있는 경우)
                UsersDTO user = new UsersDTO(
                        rset.getInt("id"),
                        rset.getString("user_code"),
                        rset.getString("login_id"),
                        rset.getString("name"),
                        // 🚨 DATE 타입은 LocalDate로 변환이 필요합니다!
                        rset.getDate("birth") != null ? rset.getDate("birth").toLocalDate() : null,
                        rset.getString("tel_num"),
                        rset.getString(" assword"),
                        rset.getString("pw_answer"),
                        rset.getString("user_type")
                );
                usersDTOList.add(user);
            }

        } return usersDTOList;

    }

    public int deleteById(int userId) throws SQLException {
        String query = QueryUtil.getQuery("Users.delete");

        // DELETE는 키를 생성하지 않으므로 RETURN_GENERATED_KEYS가 필요 없습니다.
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userId);

            // 삭제된 행의 개수를 반환합니다. (정상 삭제 시 1 반환)
            return pstmt.executeUpdate();
        }
    }

    public int update(UsersDTO usersDTO) throws SQLException {
        // QueryUtil에 "Users.update" 키로 쿼리를 미리 작성해두어야 합니다.
        // 쿼리 예시: UPDATE users SET password=?, tel_num=?, name=?, pw_answer=? WHERE id=?
        String query = QueryUtil.getQuery("Users.update");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // 수정 가능한 데이터만 바인딩 (교수/학생 타입 등은 제외)
            pstmt.setString(1, usersDTO.getPassword());
            pstmt.setString(2, usersDTO.getTelNum());
            pstmt.setString(3, usersDTO.getName());
            pstmt.setString(4, usersDTO.getPwAnswer());

            // WHERE 조건에 들어갈 고유 ID
            pstmt.setInt(5, usersDTO.getId());

            return pstmt.executeUpdate(); // 수정된 행의 개수 반환
        }
    }


}

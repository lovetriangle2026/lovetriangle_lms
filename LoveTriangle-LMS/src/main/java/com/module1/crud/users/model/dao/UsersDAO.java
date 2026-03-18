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
                        rset.getString("password"),
                        rset.getString("pw_answer"),
                        rset.getString("user_type")
                );
                usersDTOList.add(user);
            }

        } return usersDTOList;

    }




}

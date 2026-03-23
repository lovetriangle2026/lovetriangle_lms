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

    // 💡 생성자 인자 제거 및 필드 삭제
    public UsersDAO() {
    }

    public List<UsersDTO> findAll(Connection con) throws SQLException {

        String query = QueryUtil.getQuery("users.findAll");
        List<UsersDTO> usersDTOList = new ArrayList<>();


        try (PreparedStatement pstmt = con.prepareStatement(query)) {

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
                        rset.getString("password"), // ' assword' 오타 수정 유지
                        rset.getString("pw_answer"),
                        rset.getString("user_type"),
                        rset.getInt("is_heart_public")
                );
                usersDTOList.add(user);
            }

        } return usersDTOList;

    }

    public int deleteById(Connection con, int userId) throws SQLException {
        String query = QueryUtil.getQuery("Users.delete");

        // DELETE는 키를 생성하지 않으므로 RETURN_GENERATED_KEYS가 필요 없습니다.
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, userId);

            // 삭제된 행의 개수를 반환합니다. (정상 삭제 시 1 반환)
            return pstmt.executeUpdate();
        }
    }

    public int update(Connection con, UsersDTO usersDTO) throws SQLException {
        // QueryUtil에 "Users.update" 키로 쿼리를 미리 작성해두어야 합니다.
        // 쿼리 예시: UPDATE users SET password=?, tel_num=?, name=?, pw_answer=? WHERE id=?
        String query = QueryUtil.getQuery("Users.update");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
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

    /**
     * [추가] 로그인 ID를 기반으로 단일 사용자 정보를 조회합니다.
     * 수정 후 세션 갱신을 위해 사용됩니다.
     */
    public UsersDTO getUserInfo(Connection con, String loginId) throws SQLException {
        // 💡 QueryUtil에 "Users.findByLoginId" 키가 등록되어 있어야 합니다.
        String query = QueryUtil.getQuery("Users.findByLoginId");

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, loginId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return new UsersDTO(
                            rset.getInt("id"),
                            rset.getString("user_code"),
                            rset.getString("login_id"),
                            rset.getString("name"),
                            rset.getDate("birth") != null ? rset.getDate("birth").toLocalDate() : null,
                            rset.getString("tel_num"),
                            rset.getString("password"), // 💡 기존 코드의 ' assword' 오타 수정했습니다!
                            rset.getString("pw_answer"),
                            rset.getString("user_type"),
                            rset.getInt("is_heart_public")
                    );
                }
            }
        }
        return null; // 일치하는 유저가 없을 경우
    }

    // 1. 총 하트 개수 조회
    public int getTotalHearts(Connection con, int userId) throws SQLException {
        String query = QueryUtil.getQuery("Users.getTotalHearts");
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt("total_hearts");
                }
            }
        }
        return 0;
    }

    // 2. 상위 3개 태그 조회
    public List<String> getTop3Tags(Connection con, int userId) throws SQLException {
        String query = QueryUtil.getQuery("Users.getTop3Tags");
        List<String> topTags = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    topTags.add(rset.getString("tag_name") + " (" + rset.getInt("tag_count") + "회)");
                }
            }
        }
        return topTags;
    }

    // 3. 하트 공개 여부 토글 업데이트
    public int updateHeartPublic(Connection con, int userId, int isPublic) throws SQLException {
        String query = QueryUtil.getQuery("Users.updateHeartPublic");
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, isPublic);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate();
        }
    }
}
package com.module1.crud.users.service;

import com.module1.crud.global.config.JDBCTemplate; // 💡 임포트 추가
import com.module1.crud.users.model.dao.UsersDAO;
import com.module1.crud.users.model.dto.HeartStatsDTO;
import com.module1.crud.users.model.dto.UsersDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsersService {

    private final UsersDAO usersDAO;

    // 💡 생성자 인자 제거 및 기본 생성자로 DAO 조립
    public UsersService() {
        this.usersDAO = new UsersDAO();
    }


    public List<UsersDTO> findAllUsers() {
        try (Connection con = JDBCTemplate.getConnection()) {
            return usersDAO.findAll(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public boolean deleteUser(int userId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            int affectedRows = usersDAO.deleteById(con, userId);

            // 영향을 받은 행이 1개 이상이면 삭제 성공(true), 아니면 실패(false)
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("회원 탈퇴 처리 중 Error 발생!!! 🚨", e);
        }
    }

    public boolean updateUser(UsersDTO updatedUser) {
        /* comment.
         * 비즈니스 로직을 처리하는 곳입니다.
         * 필요하다면 이곳에서 전화번호 정규식 검사, 비밀번호 길이 검사 등을 수행할 수 있습니다.
         * */

        try (Connection con = JDBCTemplate.getConnection()) {
            int affectedRows = usersDAO.update(con, updatedUser);
            return affectedRows > 0; // 1줄 이상 수정되었다면 true 반환
        } catch (SQLException e) {
            System.out.println("🚨 DB 업데이트 중 오류 발생: " + e.getMessage());
            return false;
        }
    }

    // UsersService.java 예시
    public UsersDTO getUserInfo(String loginId) {
        // DAO를 호출하여 DB에서 해당 ID의 정보를 한 줄 읽어옴
        try (Connection con = JDBCTemplate.getConnection()) {
            return usersDAO.getUserInfo(con, loginId);
        } catch (SQLException e) {
            throw new RuntimeException("비밀번호 변경 처리 중 Error 발생!!! 🚨", e);
        }
    }

    public HeartStatsDTO getMyHeartStats(int userId) {
        try (Connection con = JDBCTemplate.getConnection()) {
            int totalHearts = usersDAO.getTotalHearts(con, userId);
            List<String> top3Tags = usersDAO.getTop3Tags(con, userId);
            return new HeartStatsDTO(totalHearts, top3Tags);
        } catch (SQLException e) {
            throw new RuntimeException("마이페이지 하트 통계 조회 중 Error 발생!!! 🚨", e);
        }
    }

    public boolean toggleHeartPublic(int userId, int currentStatus) {
        try (Connection con = JDBCTemplate.getConnection()) {
            int nextStatus = (currentStatus == 1) ? 0 : 1; // 1이면 0으로, 0이면 1로 토글
            return usersDAO.updateHeartPublic(con, userId, nextStatus) > 0;
        } catch (SQLException e) {
            System.out.println("🚨 공개여부 토글 중 오류 발생: " + e.getMessage());
            return false;
        }
    }

}
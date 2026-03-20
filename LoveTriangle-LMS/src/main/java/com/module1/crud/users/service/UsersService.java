package com.module1.crud.users.service;

import com.module1.crud.users.model.dao.UsersDAO;
import com.module1.crud.users.model.dto.UsersDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsersService {

    private final UsersDAO usersDAO;
    private final Connection connection;

    public UsersService(Connection connection) {
        this.usersDAO = new UsersDAO(connection);
        this.connection = connection;
    }


    public List<UsersDTO> findAllUsers() {
        try {
            return usersDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long saveUser(UsersDTO newUser) {
        try {
            // DAO를 호출하여 DB에 사용자 정보를 저장합니다.
            return usersDAO.save(newUser);
        } catch (SQLException e) {
            // DB에서 발생한 Checked Exception을 Unchecked Exception으로 전환하여 던집니다.
            // 원본 예외(e)를 함께 넘겨주면 나중에 로그를 확인할 때 진짜 원인을 찾기 쉽습니다.
            throw new RuntimeException("회원 가입 처리 중 Error 발생!!! 🚨", e);
        }
    }

    public boolean deleteUser(int userId) {
        try {
            int affectedRows = usersDAO.deleteById(userId);

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
        try {
            int affectedRows = usersDAO.update(updatedUser);
            return affectedRows > 0; // 1줄 이상 수정되었다면 true 반환
        } catch (SQLException e) {
            System.out.println("🚨 DB 업데이트 중 오류 발생: " + e.getMessage());
            return false;
        }
    }

}

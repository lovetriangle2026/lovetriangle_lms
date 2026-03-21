package com.module1.crud.users.controller;

import com.module1.crud.users.model.dto.UsersDTO;
import com.module1.crud.users.service.UsersService;

import java.time.LocalDate;
import java.util.List;

public class UsersController {

    private final UsersService service;
    public UsersController(UsersService usersService) {
        this.service = usersService;
    }


    public List<UsersDTO> findAllUsers(){

        return service.findAllUsers();
    }


    public boolean deleteUser(int userId) {
        // View에서 넘어온 탈퇴 요청을 Service로 전달합니다.
        return service.deleteUser(userId);
    }

    public boolean updateUser(UsersDTO updatedUser) {
        /* comment.
         * View에서 조립된 갱신용 DTO를 받아 Service 계층으로 전달합니다.
         * 팀원들이 작성한 Create 로직과 흐름을 동일하게 맞췄습니다.
         * */
        return service.updateUser(updatedUser);
    }
    public UsersDTO getUserInfo(String loginId) {
        // Service 계층으로 조회를 요청합니다.
        return service.getUserInfo(loginId);
    }

}

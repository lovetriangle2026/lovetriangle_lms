package com.module1.crud.users.controller;

import com.module1.crud.users.model.dto.UsersDTO;
import com.module1.crud.users.model.service.UsersService;

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


    public Long createUser(String userCode, String loginId, String name, LocalDate birth, String telNum, String password, String pwAnswer, String userType) {

        /* comment.
         * View에서 전달받은 개별 데이터들을 하나의 논리적인 묶음인 DTO로 조립합니다.
         * id는 DB의 AUTO_INCREMENT에 의해 자동 생성되므로 0(또는 임의의 기본값)을 넘깁니다.
         * */
        UsersDTO newUser = new UsersDTO(0, userCode, loginId, name, birth, telNum, password, pwAnswer, userType);

        // 조립된 DTO를 Service 계층으로 넘겨 비즈니스 로직을 수행합니다.
        return service.saveUser(newUser);
    }

    public boolean deleteUser(int userId) {
        // View에서 넘어온 탈퇴 요청을 Service로 전달합니다.
        return service.deleteUser(userId);
    }


}

package com.module1.crud.users.view;

import com.module1.crud.users.model.dto.UsersDTO;

import java.util.List;

public class UsersOutputView {

    public void printMessage(String s) {
        System.out.println(s);
    }

    public void printCourses(List<UsersDTO> usersList) {

        if (usersList == null || usersList.isEmpty()) {
            System.out.println("조회 된 강좌가 없습니다!!");
            return;
        }

        System.out.println("===============강의 전체 조회 목록 결과==================");
        for (UsersDTO usersDTO : usersList) {
            System.out.println(usersDTO);
        }

    }

}

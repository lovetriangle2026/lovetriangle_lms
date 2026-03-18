package com.module1.crud.users.view;
import com.module1.crud.users.controller.UsersController;
import com.module1.crud.users.model.dto.UsersDTO;
import com.module1.crud.users.model.service.UsersService;

import java.util.List;
import java.util.Scanner;

public class UsersInputView {


    private final UsersController controller;
    private final UsersOutputView outputView;
    private final Scanner sc = new Scanner(System.in);


    public UsersInputView(UsersController controller, UsersOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }

    public void UsersMainPage(){
        final Scanner sc = new Scanner(System.in);
        System.out.println("\n========= [회원관리 메인 메뉴] =========");
        System.out.println("1. 회원정보 조회");
        System.out.println("2. 수강내역조회");
        System.out.println("3. 강의조회");
        System.out.println("4. 과제조회");
        System.out.println("0. 로그아웃");
        System.out.print("▶ 메뉴 선택: ");
        String choice = sc.nextLine();


        switch (choice) {
            case "1":
                displayUsersList();
                System.out.println("👉 회원정보 조회합니다");

                break;

            case "2":
                // TODO: 출결관리 담당자
                System.out.println("👉 수강내역 조회합니다.");
                break;

            case "3":
                // TODO: 성적관리 담당자
                System.out.println("👉 강의조회합니다.");
                break;

            case "4":
                // TODO: 과제관리 담당자
                System.out.println("👉 과제조회합니다.");
                break;

            case "0":
                System.out.println("로그아웃 되었습니다.");
                return; // 이전 화면(시작 메뉴)으로 돌아감
            default:
                System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
        }
    }

    private void displayUsersList() {
        outputView.printMessage("\n--- [기초 실습] 강좌 목록 전체 조회 ---");

        List<UsersDTO> courseList = controller.findAllUsers();

        outputView.printCourses(courseList);
    }

}

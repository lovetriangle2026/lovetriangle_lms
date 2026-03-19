package com.module1.crud.users.view;
import com.module1.crud.users.controller.UsersController;
import com.module1.crud.users.model.dto.UsersDTO;
import com.module1.crud.users.model.service.UsersService;

import java.time.LocalDate;
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

    public void createUser() {
        System.out.println("\n=== 📝 신규 회원 가입 ===");

        System.out.print("사번/학번(User Code)을 입력해주세요 : ");
        String userCode = sc.nextLine();

        System.out.print("로그인 ID를 입력해주세요 : ");
        String loginId = sc.nextLine();

        System.out.print("이름을 입력해주세요 : ");
        String name = sc.nextLine();

        System.out.print("생년월일 (YYYY-MM-DD 형식)을 입력해주세요 : ");
        String birthInput = sc.nextLine();
        // 💡 문자열 입력을 LocalDate 객체로 변환합니다.
        LocalDate birth = LocalDate.parse(birthInput);

        System.out.print("전화번호를 입력해주세요 : ");
        String telNum = sc.nextLine();

        System.out.print("비밀번호를 입력해주세요 : ");
        String password = sc.nextLine();

        System.out.print("비밀번호 찾기 정답을 입력해주세요 : ");
        String pwAnswer = sc.nextLine();

        System.out.print("사용자 유형 (STUDENT / PROFESSOR)을 입력해주세요 : ");
        String userType = sc.nextLine();

        /* comment.
         * 입력받은 원시 데이터들을 Controller로 전달하여 처리를 위임합니다.
         * DB 처리 결과로 생성된 회원의 고유 ID(PK)가 반환됩니다.
         * */
        Long result = controller.createUser(userCode, loginId, name, birth, telNum, password, pwAnswer, userType);

        if (result != null && result > 0) {
            outputView.printSuccess("회원 가입 성공! 부여된 회원 고유 ID : " + result);
        } else {
            outputView.printError("회원 가입에 실패했습니다. 입력 정보를 확인해주세요.");
        }
    }

    // 현재 로그인한 사용자의 PK 값(loggedInUserId)을 알고 있다고 가정합니다.
    private void deleteMyAccount(Long loggedInUserId) {
        System.out.println("\n=== 🗑️ 회원 탈퇴 ===");
        System.out.print("정말 탈퇴하시겠습니까? 탈퇴 시 모든 데이터가 삭제됩니다. (Y/N) : ");
        String answer = sc.nextLine();

        if (answer.equalsIgnoreCase("Y")) {
            // Controller를 호출하여 삭제 수행
            boolean isDeleted = controller.deleteUser(loggedInUserId);

            if (isDeleted) {
                outputView.printSuccess("회원 탈퇴가 완료되었습니다. 그동안 이용해 주셔서 감사합니다.");
                // TODO: 여기서 로그아웃 처리 및 메인 화면으로 돌아가는 로직 추가
            } else {
                outputView.printError("회원 탈퇴에 실패했습니다. 관리자에게 문의하세요.");
            }
        } else {
            System.out.println("탈퇴를 취소하고 이전 화면으로 돌아갑니다.");
        }
    }



}

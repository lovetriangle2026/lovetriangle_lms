package com.module1.crud.users.view;
import com.module1.crud.global.session.SessionManager;
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


    public void usersMainPage() {
        while (true) {
            // 1. 세션에서 현재 로그인된 유저 정보 가져오기
            UsersDTO user = SessionManager.getInstance().getLoggedInUser();

            // 만약 세션이 비어있다면 (비정상 접근) 메인으로 튕겨내기
            if (user == null) {
                System.out.println("🚨 로그인 세션이 만료되었습니다. 다시 로그인해주세요.");
                return;
            }

            System.out.println("\n================ [ 회원관리 메인 메뉴 ] ================");

            // 2. 콘솔 프로필 캐릭터 출력 (ASCII Art)
            System.out.println("      ━━━━━━━");
            System.out.println("     ┃  ●  ●  ┃");
            System.out.println("     ┃    ▽   ┃  [ " + user.getName() + " ] 님 환영합니다.");
            System.out.println("      ━━━━━━━    ID: " + user.getLoginId() + " (" + user.getUserType() + ")");
            System.out.println("                 Code: " + user.getUserCode());
            System.out.println("======================================================");

            System.out.println(" 1. 회원정보 수정");
            System.out.println(" 2. 수강내역 조회");
            System.out.println(" 3. 나의 과제 조회");
            System.out.println(" 4. 나의 출결 조회");
            System.out.println(" 9. 회원 탈퇴 ");
            System.out.println(" 0. 메인메뉴로 돌아가기");
            System.out.println("======================================================");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
//                    updateUserInfo(); // 정보 수정 메서드 호출 (구현 예정)
                    break;
                case "2":
//                    courseHistory(); // 수강 내역 조회 (구현 예정)
                    break;
                case "3":
//                    myAssignments(); // 과제 조회 (구현 예정)
                    break;
                case "4":
//                    myAttendance(); // 출결 조회 (구현 예정)
                    break;
                case "9":
                    deleteMyAccount(user.getId()); // 앞서 만든 삭제 로직 연결
                    return; // 탈퇴 시 세션 종료 후 메인으로
                case "0":
                    System.out.println("메인메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
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

    private void deleteMyAccount(int loggedInUserId) {
        System.out.println("\n=== 🗑️ 회원 탈퇴 ===");
        System.out.print("정말 탈퇴하시겠습니까? 탈퇴 시 모든 데이터가 삭제됩니다. (Y/N) : ");
        String answer = sc.nextLine();

        if (answer.equalsIgnoreCase("Y")) {
            // Controller를 호출하여 삭제 수행
            boolean isDeleted = controller.deleteUser(loggedInUserId);

            if (isDeleted) {
                outputView.printSuccess("회원 탈퇴가 완료되었습니다. 그동안 이용해 주셔서 감사합니다.");
                SessionManager.getInstance().clearSession();
            } else {
                outputView.printError("회원 탈퇴에 실패했습니다. 관리자에게 문의하세요.");
            }
        } else {
            System.out.println("탈퇴를 취소하고 이전 화면으로 돌아갑니다.");
        }
    }



}

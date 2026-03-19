package com.module1.crud.global.loginpage.view;

import com.module1.crud.assignments.view.ProfessorAssignmentInputView;
import com.module1.crud.assignments.view.StudentAssignmentInputView;
import com.module1.crud.attendance.view.ProfessorAttendanceInputView;
import com.module1.crud.attendance.view.StudentAttendanceInputView;
import com.module1.crud.global.loginpage.controller.LoginController;
import com.module1.crud.grade.view.StudentGradeInputView;
import com.module1.crud.users.view.UsersInputView;

import java.util.Scanner;

public class LoginInputView {

        private final Scanner sc = new Scanner(System.in);
        private final LoginController controller;
        private final LoginOutputView outputView;
        private final UsersInputView usersInputView;

        // 추가된 의존성 필드 선언 (명확한 네이밍 적용)
        private final StudentAssignmentInputView studentAssignmentInputView;
        private final ProfessorAssignmentInputView professorAssignmentInputView;
        private final ProfessorAttendanceInputView professorAttendanceInputView;
        private final StudentAttendanceInputView studentAttendanceInputView;
        private final StudentGradeInputView studentGradeInputView;

        public LoginInputView(
                LoginController controller,
                LoginOutputView outputView,
                UsersInputView usersInputView,
                StudentAssignmentInputView studentAssignmentInputView,
                ProfessorAssignmentInputView professorAssignmentInputView,
                ProfessorAttendanceInputView professorAttendanceInputView,
                StudentAttendanceInputView studentAttendanceInputView,
                StudentGradeInputView studentGradeInputView) {

            this.controller = controller;
            this.outputView = outputView;
            this.usersInputView = usersInputView;

            // 추가된 객체 초기화
            this.studentAssignmentInputView = studentAssignmentInputView;
            this.professorAssignmentInputView = professorAssignmentInputView;
            this.professorAttendanceInputView = professorAttendanceInputView;
            this.studentAttendanceInputView = studentAttendanceInputView;
            this.studentGradeInputView = studentGradeInputView;
        }


    // 1. 시스템 시작 (최초 진입점)
    public void displayStartMenu() {
        while (true) {
            System.out.println("\n========= [LMS 시스템 시작] =========");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("0. 프로그램 종료");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine(); // nextInt() 대신 nextLine() 사용

            switch (choice) {
                case "1":
                    loginMenu(); // 로그인 화면으로 이동
                    break;
                case "2":
                    signUpMenu(); // 회원가입 화면으로 이동
                    break;
                case "0":
                    System.out.println("LMS 시스템을 종료합니다. 안녕히 가세요!");
                    return; // while 루프를 빠져나가 프로그램 종료
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    // 2. 로그인 화면 (하드코딩 분기)
    private void loginMenu() {
        System.out.println("\n--------- [로그인] --------- s는 학생 p는 교수 / pw는 아무거나 ");
        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("PW: ");
        String pw = sc.nextLine();

        // 💡 프로토타입용 하드코딩 테스트 로직 (실제로는 UserController가 DB를 조회해야 함)
        if (id.startsWith("s") || id.startsWith("S")) {
            System.out.println("✅ [학생] 계정으로 로그인 성공!");
            studentMainMenu();
            // 학생 MainMenu로 이동

        } else if (id.startsWith("p") || id.startsWith("P")) {
            System.out.println("✅ [교수] 계정으로 로그인 성공!");
            professorMainMenu();
            // 교수 MainMenu로 이동

        } else {
            System.out.println("🚨 로그인 실패: 테스트를 위해 ID를 's'나 'p'로 시작하게 입력해주세요.");
        }
    }

    // 2-1. 회원가입 화면
    private void signUpMenu() {
        System.out.println("\n--------- [회원가입] ---------");
        System.out.println("회원정보를 입력해주세요...");
        // TODO: 회원관리 담당자가 UserController.signUp()을 호출하도록 연결할 자리
        System.out.println("✅ 회원가입이 완료되었습니다. (임시 메시지)\n");
    }

    // 3. 학생 메인 메뉴
    private void studentMainMenu() {
        while (true) {
            System.out.println("\n========= [학생 메인 메뉴] =========");
            System.out.println("1. 강의관리");
            System.out.println("2. 출결관리");
            System.out.println("3. 성적관리");
            System.out.println("4. 과제관리");
            System.out.println("5. 회원관리 (내 정보 수정)");
            System.out.println("0. 로그아웃");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    // TODO: 강의관리 담당자 (예: courseController.displayStudentMenu())
                    System.out.println("👉 강의관리 모듈로 이동합니다.");
                    break;
                case "2":
                    studentAttendanceInputView.displayMenu();
                    System.out.println("👉 출결관리 모듈로 이동합니다.");
                    break;
                case "3":
                    // TODO: 성적관리 담당자
                    System.out.println("👉 성적관리 모듈로 이동합니다.");
                    studentGradeInputView.displayStudentMainMenu();
                    break;
                case "4":
                    studentAssignmentInputView.displaymainmenu();
                    System.out.println("👉 과제관리 모듈로 이동합니다.");
                    break;
                case "5":
                    usersInputView.UsersMainPage();
                    System.out.println("👉 회원관리 모듈로 이동합니다.");
                    break;
                case "0":
                    System.out.println("로그아웃 되었습니다.");
                    return; // 이전 화면(시작 메뉴)으로 돌아감
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    // 4. 교수 메인 메뉴
    private void professorMainMenu() {
        while (true) {
            System.out.println("\n========= [교수 메인 메뉴] =========");
            System.out.println("1. 강의관리");
            System.out.println("2. 출결관리");
            System.out.println("3. 성적관리");
            System.out.println("4. 과제관리");
            System.out.println("5. 회원관리 (학생 조회 등)");
            System.out.println("0. 로그아웃");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    // TODO: 강의관리 담당자 (예: courseController.displayStudentMenu())
                    System.out.println("👉 강의관리 모듈로 이동합니다.");
                    break;
                case "2":
                    // TODO: 출결관리 담당자
                    System.out.println("👉 출결관리 모듈로 이동합니다.");
                    break;
                case "3":
                    // TODO: 성적관리 담당자
                    System.out.println("👉 성적관리 모듈로 이동합니다.");

                    break;
                case "4":
                    // TODO: 과제관리 담당자
                    System.out.println("👉 과제관리 모듈로 이동합니다.");
                    break;
                case "5":
                    // TODO: 회원관리 담당자
                    System.out.println("👉 회원관리 모듈로 이동합니다.");
                    break;
                case "0":
                    System.out.println("로그아웃 되었습니다.");
                    return; // 이전 화면(시작 메뉴)으로 돌아감
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }
}
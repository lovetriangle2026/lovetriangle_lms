package com.module1.crud.global.loginpage.view;

import com.module1.crud.assignments.view.ProfessorAssignmentInputView;
import com.module1.crud.assignments.view.StudentAssignmentInputView;
import com.module1.crud.attendance.view.ProfessorAttendanceInputView;
import com.module1.crud.attendance.view.StudentAttendanceInputView;
import com.module1.crud.course.view.StudentCourseInputView;
import com.module1.crud.global.loginpage.controller.LoginController;
import com.module1.crud.grade.view.ProfessorGradeInputView;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;
import com.module1.crud.grade.view.StudentGradeInputView;
import com.module1.crud.users.view.UsersInputView;

import java.util.Scanner;

public class LoginInputView {

        private final UsersDTO loggedUser = SessionManager.getInstance().getLoggedInUser();

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
        private final ProfessorGradeInputView professorGradeInputView;
        private final StudentCourseInputView studentCourseInputView;

        public LoginInputView(
                LoginController controller,
                LoginOutputView outputView,
                UsersInputView usersInputView,
                StudentAssignmentInputView studentAssignmentInputView,
                ProfessorAssignmentInputView professorAssignmentInputView,
                ProfessorAttendanceInputView professorAttendanceInputView,
                StudentAttendanceInputView studentAttendanceInputView,
                StudentCourseInputView studentCourseInputView,
                StudentGradeInputView studentGradeInputView,
                ProfessorGradeInputView professorGradeInputView) {

            this.controller = controller;
            this.outputView = outputView;
            this.usersInputView = usersInputView;

            // 추가된 객체 초기화
            this.studentAssignmentInputView = studentAssignmentInputView;
            this.professorAssignmentInputView = professorAssignmentInputView;
            this.professorAttendanceInputView = professorAttendanceInputView;
            this.studentAttendanceInputView = studentAttendanceInputView;
            this.studentGradeInputView = studentGradeInputView;
            this.professorGradeInputView = professorGradeInputView;
            this.studentCourseInputView = studentCourseInputView;
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
                    usersInputView.createUser();
                    break;
                case "0":
                    System.out.println("LMS 시스템을 종료합니다. 안녕히 가세요!");
                    return; // while 루프를 빠져나가 프로그램 종료
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    private void loginMenu() {
        while (true) { // 💡 무한 루프 시작
            System.out.println("\n--------- [로그인] ---------");
            System.out.println("(이전 메뉴로 돌아가려면 '0'을 입력하세요)");

            System.out.print("ID: ");
            String id = sc.nextLine();

            // 탈출 로직: ID 입력 단계에서 0을 누르면 종료
            if ("0".equals(id)) {
                System.out.println("초기 메뉴로 돌아갑니다.");
                return;
            }

            System.out.print("PW: ");
            String pw = sc.nextLine();

            // 1. Controller를 통해 실제 DB 로그인 수행
            boolean loginSuccess = controller.login(id, pw);

            if (loginSuccess) {
                UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
                String userType = loggedInUser.getUserType();

                System.out.println("✅ [" + loggedInUser.getName() + "]님 환영합니다! (" + userType + ")");

                // 2. 권한에 따른 화면 분기 처리
                if ("STUDENT".equalsIgnoreCase(userType)) {
                    studentMainMenu();
                } else if ("PROFESSOR".equalsIgnoreCase(userType)) {
                    professorMainMenu();
                } else {
                    System.out.println("🚨 알 수 없는 권한입니다.");
                    SessionManager.getInstance().clearSession();
                }

                // 💡 로그인이 성공해서 메뉴에 들어갔다가 '로그아웃'해서 나오면
                // 이 loginMenu 메서드도 완전히 종료시켜서 '시작 메뉴'로 보내야 함
                return;

            } else {
                // 로그인 실패 시 루프가 돌면서 다시 입력을 받게 됨
                outputView.printError("로그인 실패: 아이디 또는 비밀번호가 일치하지 않습니다. 다시 시도해 주세요.");
            }
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
    // 3. 학생 메인 메뉴
    private void studentMainMenu() {
        while (true) {
            // 1. 💡 핵심: 루프 시작 시 마다 세션 상태 확인 (게이트키퍼 역할)
            UsersDTO user = SessionManager.getInstance().getLoggedInUser();

            // 세션이 없으면(탈퇴/로그아웃 등) 즉시 이 메뉴를 종료하고 시작 메뉴로 돌아감
            if (user == null) {
                return;
            }

            System.out.println("\n========= [학생 메인 메뉴] =========");
            System.out.println("로그인 유저: " + user.getName() + " (" + user.getUserCode() + ")"); // 사용자 정보 표시
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
                    System.out.println("👉 강의관리 모듈로 이동합니다.");
                    studentCourseInputView.displayStudentMenu();
                    break;
                case "2":
                    System.out.println("👉 출결관리 모듈로 이동합니다.");
                    studentAttendanceInputView.displayMenu();
                    break;
                case "3":
                    System.out.println("👉 성적관리 모듈로 이동합니다.");
                    studentGradeInputView.displayStudentMainMenu();
                    break;
                case "4":
                    System.out.println("👉 과제관리 모듈로 이동합니다.");
                    studentAssignmentInputView.displaymainmenu();
                    break;
                case "5":
                    System.out.println("👉 회원관리 모듈로 이동합니다.");
                    usersInputView.stutMainPage(loggedUser);
                    // 💡 여기서 탈퇴하고 돌아오면, 다음 루프 시작 시 (user == null) 체크에서 걸려 return 됨
                    break;
                case "0":
                    // 2. 💡 로그아웃 시 세션을 비워줍니다.
                    SessionManager.getInstance().clearSession();
                    System.out.println("✅ 로그아웃 되었습니다.");
                    return;
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
                    usersInputView.profMainPage(loggedUser);
                    System.out.println("👉 강의관리 모듈로 이동합니다.");
                    break;
                case "2":
                    professorAttendanceInputView.displayMenu();
                    System.out.println("👉 출결관리 모듈로 이동합니다.");
                    break;
                case "3":
                    // TODO: 성적관리 담당자
                    System.out.println("👉 성적관리 모듈로 이동합니다.");
                    professorGradeInputView.displayProfessorMainMenu();

                    break;
                case "4":
                    // TODO: 과제관리 담당자
                    System.out.println("👉 과제관리 모듈로 이동합니다.");
                    professorAssignmentInputView.displayMainmenu();
                    break;
                case "5":
                    usersInputView.profMainPage(loggedUser);
                    System.out.println("👉 회원관리 모듈로 이동합니다.");
                case "0":
                    // 2. 💡 로그아웃 시 세션을 비워줍니다.1
                    SessionManager.getInstance().clearSession();
                    System.out.println("✅ 로그아웃 되었습니다.");
                    return;
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }
}
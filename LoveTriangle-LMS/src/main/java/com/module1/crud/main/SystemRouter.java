package com.module1.crud.main;

import com.module1.crud.assignments.view.ProfessorAssignmentInputView;
import com.module1.crud.assignments.view.StudentAssignmentInputView;
import com.module1.crud.attendance.view.ProfessorAttendanceInputView;
import com.module1.crud.attendance.view.StudentAttendanceInputView;
import com.module1.crud.auth.find.view.FindAccountView;
import com.module1.crud.course.view.ProfInputView;
import com.module1.crud.course.view.StudentCourseInputView;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.grade.view.ProfessorGradeInputView;
import com.module1.crud.grade.view.StudentGradeInputView;
import com.module1.crud.users.model.dto.UsersDTO;
import com.module1.crud.users.view.UsersInputView;
import com.module1.crud.auth.login.view.LoginView;
import com.module1.crud.auth.signup.view.SignupView;

import java.util.Scanner;

public class SystemRouter {

        private final Scanner sc = new Scanner(System.in);

        // 💡 라우터가 안내할 모든 뷰(담당자) 필드 선언 (총 10개)
        private final LoginView loginView;
        private final SignupView signupView;
        private final StudentCourseInputView studentCourseInputView;
        private final ProfInputView professorCourseInputView;
        private final StudentAttendanceInputView studentAttendanceInputView;
        private final ProfessorAttendanceInputView professorAttendanceInputView;
        private final StudentGradeInputView studentGradeInputView;
        private final ProfessorGradeInputView professorGradeInputView;
        private final StudentAssignmentInputView studentAssignmentInputView;
        private final ProfessorAssignmentInputView professorAssignmentInputView;
        private final UsersInputView usersInputView;
        private final FindAccountView findAccountView;

        // 💡 생성자로 10개의 부품을 순서대로 주입받음
        public SystemRouter(LoginView loginView,
                            SignupView signupView,
                            FindAccountView findAccountView,
                            StudentCourseInputView studentCourseInputView,
                            ProfInputView professorCourseInputView,
                            StudentAttendanceInputView studentAttendanceInputView,
                            ProfessorAttendanceInputView professorAttendanceInputView,
                            StudentGradeInputView studentGradeInputView,
                            ProfessorGradeInputView professorGradeInputView,
                            StudentAssignmentInputView studentAssignmentInputView,
                            ProfessorAssignmentInputView professorAssignmentInputView,
                            UsersInputView usersInputView) {

            this.loginView = loginView;
            this.signupView = signupView;
            this.findAccountView = findAccountView;
            this.studentCourseInputView = studentCourseInputView;
            this.professorCourseInputView = professorCourseInputView;
            this.studentAttendanceInputView = studentAttendanceInputView;
            this.professorAttendanceInputView = professorAttendanceInputView;
            this.studentGradeInputView = studentGradeInputView;
            this.professorGradeInputView = professorGradeInputView;
            this.studentAssignmentInputView = studentAssignmentInputView;
            this.professorAssignmentInputView = professorAssignmentInputView;
            this.usersInputView = usersInputView;
        }


        // 1. 시스템 최초 진입점 (여기서 프로그램이 시작됩니다)
        public void start() {
            while (true) {
                System.out.println("\n========= [LMS 시스템 시작] =========");
                System.out.println("1. 로그인");
                System.out.println("2. 회원가입");
                System.out.println("3. 아이디/비밀번호 찾기");
                System.out.println("0. 프로그램 종료");
                System.out.print("▶ 메뉴 선택: ");

                String choice = sc.nextLine();

                switch (choice) {
                    case "1":
                        boolean isLoginSuccess = loginView.showLoginMenu();
                        if (isLoginSuccess) {
                            routeToMainMenu(); // 성공했으면 메인 메뉴로 이동
                        }
                        break;
                    case "2":
                        signupView.showSignupMenu();
                        break;
                    case "3":
                        findAccountView.showFindMenu();
                        break;
                    case "0":
                        System.out.println("LMS 시스템을 종료합니다. 안녕히 가세요!");
                        return;
                    default:
                        System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
                }
            }
        }

        // 2. 권한에 따른 라우팅 (경찰 역할)
        private void routeToMainMenu() {
            UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
            if (loggedInUser == null) return;

            String userType = loggedInUser.getUserType();
            System.out.println("✅ [" + loggedInUser.getName() + "]님 환영합니다! (" + userType + ")");

            if ("STUDENT".equalsIgnoreCase(userType)) {
                studentMainMenu(loggedInUser);
            } else if ("PROFESSOR".equalsIgnoreCase(userType)) {
                professorMainMenu(loggedInUser);
            } else {
                System.out.println("🚨 알 수 없는 권한입니다.");
                SessionManager.getInstance().clearSession();
            }
        }

        // 3. 학생 메인 메뉴 (LoginInputView에 있던 것 이동)
        private void studentMainMenu(UsersDTO user) {
            while (true) {
                if (SessionManager.getInstance().getLoggedInUser() == null) return;

                System.out.println("\n========= [학생 메인 메뉴] =========");
                System.out.println("로그인 유저: " + user.getName() + " (" + user.getUserCode() + ")");
                System.out.println("1. 강의관리 | 2. 출결관리 | 3. 성적관리 | 4. 과제관리 | 5. 내 정보 수정 | 0. 로그아웃");
                System.out.print("▶ 메뉴 선택: ");

                String choice = sc.nextLine();
                switch (choice) {
                    case "1": studentCourseInputView.displayStudentMenu(); break;
                    case "2": studentAttendanceInputView.displayMenu(); break;
                    case "3": studentGradeInputView.displayStudentMainMenu(); break;
                    case "4": studentAssignmentInputView.displaymainmenu(); break;
                    case "5": usersInputView.stutMainPage(user); break;
                    case "0":
                        SessionManager.getInstance().clearSession();
                        System.out.println("✅ 로그아웃 되었습니다.");
                        return;
                    default: System.out.println("🚨 잘못된 입력입니다.");
                }
            }
        }

        // 4. 교수 메인 메뉴 (LoginInputView에 있던 것 이동)
        private void professorMainMenu(UsersDTO user) {
            while (true) {
                if (SessionManager.getInstance().getLoggedInUser() == null) return;

                System.out.println("\n========= [교수 메인 메뉴] =========");
                System.out.println("1. 강의관리 | 2. 출결관리 | 3. 성적관리 | 4. 과제관리 | 5. 회원관리 | 0. 로그아웃");
                System.out.print("▶ 메뉴 선택: ");

                String choice = sc.nextLine();
                switch (choice) {
                    // ... 기존 professorMainMenu 스위치문 내용과 동일하게 위임 ...
                    case "1": professorCourseInputView.displayProfessorCourseMenu(); break;
                    case "2": professorAttendanceInputView.displayMenu(); break;
                    case "3": professorGradeInputView.displayProfessorMainMenu(); break;
                    case "4": professorAssignmentInputView.displayMainmenu(); break;
                    case "5": usersInputView.profMainPage(user); break;
                    case "0":
                        SessionManager.getInstance().clearSession();
                        System.out.println("✅ 로그아웃 되었습니다.");
                        return;
                    default: System.out.println("🚨 잘못된 입력입니다.");
                }
            }
        }
    }



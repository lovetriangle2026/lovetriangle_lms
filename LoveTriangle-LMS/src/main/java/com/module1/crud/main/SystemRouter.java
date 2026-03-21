package com.module1.crud.main;

import com.module1.crud.AppConfig;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.global.config.JDBCTemplate;
import com.module1.crud.users.model.dto.UsersDTO;

import java.sql.Connection;
import java.util.Scanner;

public class SystemRouter {

    private final Scanner sc = new Scanner(System.in);

    // 💡 10개나 되던 뷰 필드와 거대한 생성자가 전부 사라졌습니다!
    public SystemRouter() {
    }

    // 1. 시스템 최초 진입점
    public void start() {

// 🚀 [백그라운드 예열 타이머]
        new Thread(() -> {
            long dbStart = System.currentTimeMillis(); // DB 예열 시작 시간

            try (Connection warmup = JDBCTemplate.getConnection()) {
                long dbEnd = System.currentTimeMillis(); // DB 예열 완료 시간

                // 유저가 메뉴를 보고 있는 도중에 슬쩍 출력됩니다.
                System.out.printf("\n [System] DB 커넥션 풀 예열 완료! (소요 시간: %.2f초)%n", (dbEnd - dbStart) / 1000.0);
                System.out.print("▶ 메뉴 선택: "); // 출력 후 커서 위치 보정용

            } catch (Exception e) {
                // 예열 실패 시 무시
            }
        }).start();

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
                    // 💡 메뉴 진입 시 생성 -> 탈출 시 가비지 컬렉터가 자동 소멸
                    boolean isLoginSuccess = AppConfig.createLoginView().showLoginMenu();
                    if (isLoginSuccess) {
                        routeToMainMenu();
                    }
                    break;
                case "2":
                    AppConfig.createSignupView().showSignupMenu();
                    break;
                case "3":
                    AppConfig.createFindAccountView().showFindMenu();
                    break;
                case "0":
                    System.out.println("LMS 시스템을 종료합니다. 안녕히 가세요!");
                    return;
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    // 2. 권한에 따른 라우팅
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

    // 3. 학생 메인 메뉴
    private void studentMainMenu(UsersDTO user) {
        while (true) {
            if (SessionManager.getInstance().getLoggedInUser() == null) return;

            System.out.println("\n========= [학생 메인 메뉴] =========");
            System.out.println("로그인 유저: " + user.getName() + " (" + user.getUserCode() + ")");
            System.out.println("1. 강의관리 | 2. 출결관리 | 3. 성적관리 | 4. 과제관리 | 5. 내 정보 수정 | 0. 로그아웃");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1": AppConfig.createStudentCourseInputView().displayStudentMenu(); break;
                case "2": AppConfig.createStudentAttendanceInputView().displayMenu(); break;
                case "3": AppConfig.createStudentGradeInputView().displayStudentMainMenu(); break;
                case "4": AppConfig.createStudentAssignmentInputView().displaymainmenu(); break;
                case "5": AppConfig.createUsersInputView().stutMainPage(user); break;
                case "0":
                    SessionManager.getInstance().clearSession();
                    System.out.println("✅ 로그아웃 되었습니다.");
                    return;
                default: System.out.println("🚨 잘못된 입력입니다.");
            }
        }
    }

    // 4. 교수 메인 메뉴
    private void professorMainMenu(UsersDTO user) {
        while (true) {
            if (SessionManager.getInstance().getLoggedInUser() == null) return;

            System.out.println("\n========= [교수 메인 메뉴] =========");
            System.out.println("1. 강의관리 | 2. 출결관리 | 3. 성적관리 | 4. 과제관리 | 5. 회원관리 | 0. 로그아웃");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1": // 💡 ProfInputView 메서드명 확인 필요
                case "2": AppConfig.createProfessorAttendanceInputView().displayMenu(); break;
                case "3": AppConfig.createProfessorGradeInputView().displayProfessorMainMenu(); break;
                case "4": AppConfig.createProfessorAssignmentInputView().displayMainmenu(); break;
                case "5": AppConfig.createUsersInputView().profMainPage(user); break;
                case "0":
                    SessionManager.getInstance().clearSession();
                    System.out.println("✅ 로그아웃 되었습니다.");
                    return;
                default: System.out.println("🚨 잘못된 입력입니다.");
            }
        }
    }
}
package com.module1.crud.main;

import com.module1.crud.AppConfig;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.global.config.JDBCTemplate;
import com.module1.crud.users.model.dto.UsersDTO;

import java.sql.Connection;
import java.util.Scanner;

public class SystemRouter {

    private final Scanner sc = new Scanner(System.in);

    public SystemRouter() {
    }

    // 1. 시스템 최초 진입점
    public void start() {

        new Thread(() -> {
            try (Connection warmup = JDBCTemplate.getConnection()) {
            } catch (Exception e) {
            }
        }).start();

        while (true) {
            System.out.println("\n");
            System.out.println("      🌿🍃🌿🍃🌿🍃🌿🍃🌿🍃🌿🍃🌿🍃🌿🍃🌿🍃🌿🍃🌿");
            System.out.println("      🌿                                           🌿");
            System.out.println("      🍃      ╔══════════════════════════════╗     🍃");
            System.out.println("      🌿      ║                              ║     🌿");
            System.out.println("      🍃      ║      AMAZON UNIVERSITY       ║     🍃");
            System.out.println("      🌿      ║         LMS SYSTEM           ║     🌿");
            System.out.println("      🍃      ║                              ║     🍃");
            System.out.println("      🌿      ╚══════════════════════════════╝     🌿");
            System.out.println("      🍃                                           🍃");
            System.out.println("      🌿🍃🌿🍃🌿🍃🌿🍃🌿🍃🌿🍃🌿🍃🌿🍃🌿🍃🌿🍃🌿");
            System.out.println("\n========= [메인 메뉴] ==========");
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
            System.out.println(" 1. 로그인");
            System.out.println(" 2. 회원가입");
            System.out.println(" 3. 아이디/비밀번호 찾기");
            System.out.println(" 0. 로그아웃");
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
            System.out.println(" 1. 강의관리");
            System.out.println(" 2. 출결관리");
            System.out.println(" 3. 성적관리");
            System.out.println(" 4. 과제관리");
            System.out.println(" 5. 회원관리");
            System.out.println(" 0. 로그아웃");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1": AppConfig.createProfInputView().displayProfessorCourseMenu(); break;
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

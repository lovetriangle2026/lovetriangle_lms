package com.module1.crud.course.view;

import com.module1.crud.course.controller.CourseController;
import com.module1.crud.course.controller.SessionController;
import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.course.model.dto.SessionDTO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;

import java.util.List;
import java.util.Scanner;

public class ProfInputView {
    private Scanner sc = new Scanner(System.in);
    private final CourseController controller;
    private final ProfOutputView profOutputView;
    private final SessionController sessionController;

    public ProfInputView(CourseController controller, ProfOutputView profOutputView, SessionController sessionController) {
        this.controller = controller;
        this.profOutputView = profOutputView;
        this.sessionController = sessionController;
    }

    // 1. 메인 메뉴
    public void displayProfessorCourseMenu() {
        while (true) {
            System.out.println("\n============ 🏫 교수 강의관리 메뉴 ============");
            System.out.println("1. 담당 강의 조회 및 관리 (수정/조회)");
            System.out.println("2. 신규 강의 등록");
            System.out.println("0. 이전 메뉴로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");

            int menu = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기

            switch (menu) {
                case 1:
                    manageMyCourses();
                    break;
                case 2:
                    registerCourse();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 2. 핵심 로직: 강의 목록 조회 -> 강의 선택 -> 하위 메뉴(조회/수정)
    private void manageMyCourses() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        if (loggedInUser == null) {
            System.out.println("로그인 정보가 없습니다.");
            return;
        }

        int professorId = (int) loggedInUser.getId();
        List<CourseDTO> courseList = controller.findProfessorCourses(professorId);

        if (courseList == null || courseList.isEmpty()) {
            System.out.println("담당 중인 강의가 없습니다.");
            return;
        }

        profOutputView.displayCourseList(courseList);

        System.out.print("▶ 관리할 강의 번호를 선택하세요 (0: 이전) : ");
        int choice = sc.nextInt();
        sc.nextLine(); // 버퍼 비우기

        if (choice == 0) return;

        CourseDTO selectedCourse = null;
        for (CourseDTO course : courseList) {
            if (course.getId().intValue() == choice) {
                selectedCourse = course;
                break;
            }
        }

        // 목록에 없는 번호를 입력했을 경우 처리
        if (selectedCourse == null) {
            System.out.println("🚨 목록에 있는 번호를 입력해 주세요.");
            return;
        }

        // 선택한 강의에 대한 하위 메뉴 반복
        while (true) {
            System.out.println("\n📖 [" + selectedCourse.getTitle() + "] 상세 관리");
            System.out.println("1. 주차별 강의 내용 조회");
            System.out.println("2. 주차별 강의 내용 수정");
            System.out.println("0. 상위 메뉴로 돌아가기");
            System.out.print("선택 : ");

            int subMenu = sc.nextInt();
            sc.nextLine();

            if (subMenu == 1) {
                // 💡 주차별 조회 시 예쁜 표 출력
                List<SessionDTO> sessionList = sessionController.findSessionsByCourse(selectedCourse.getId().intValue());
                profOutputView.displaySessionList(sessionList);
            } else if (subMenu == 2) {
                // 💡 주차별 수정 프로세스로 이동
                updateSessionProcess(selectedCourse);
            } else if (subMenu == 0) {
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 3. 주차별 수정 로직
    private void updateSessionProcess(CourseDTO selectedCourse) {
        // 💡 수정하기 전에 기존 주차별 내용을 표 형태로 쫙 보여줍니다!
        System.out.println("\n🔍 현재 주차별 내용입니다.");
        List<SessionDTO> sessionList = sessionController.findSessionsByCourse(selectedCourse.getId().intValue());
        profOutputView.displaySessionList(sessionList);

        System.out.print("수정할 주차 입력 (1~15) : ");
        int week = sc.nextInt();
        sc.nextLine();

        if (week < 1 || week > 15) {
            System.out.println("잘못된 주차입니다.");
            return;
        }

        System.out.print("새로운 수업 내용(title) 입력 : ");
        String title = sc.nextLine();

        boolean result = sessionController.updateSessionTitle(
                selectedCourse.getId().intValue(),
                week,
                title
        );

        profOutputView.displaySessionUpdateResult(result);

        // 💡 수정 성공 시 바뀐 결과를 다시 예쁜 표로 보여줍니다!
        if (result) {
            List<SessionDTO> updatedList = sessionController.findSessionsByCourse(selectedCourse.getId().intValue());
            profOutputView.displaySessionList(updatedList);
        }
    }

    private void registerCourse() {
        CourseDTO newCourse = inputCourse();
        if (newCourse == null) return;

        boolean result = controller.insertCourse(newCourse);
        profOutputView.displayResult(result);
    }

    public CourseDTO inputCourse() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        if (loggedInUser == null) return null;

        int professorId = (int) loggedInUser.getId();

        System.out.println("\n============ 신규 강의 등록 ============");

        System.out.print("강의 제목 : ");
        String title = sc.nextLine();

        System.out.print("강의 설명 : ");
        String description = sc.nextLine();

        String semester;
        do {
            System.out.print("학기(예 : 2026-1) : ");
            semester = sc.nextLine();
            if (!semester.matches("\\d{4}-[1-2]")) {
                System.out.println("⚠️ 형식에 맞춰 '연도-학기(1 또는 2)'를 입력해 주세요.");
            }
        } while (!semester.matches("\\d{4}-[1-2]"));

        return new CourseDTO(0L, null, professorId, title, description, semester);
    }
}
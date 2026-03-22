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


    public ProfInputView(CourseController controller, ProfOutputView profOutputView,SessionController sessionController) {
        this.controller = controller;
        this.profOutputView = profOutputView;
        this.sessionController = sessionController;
    }

    public void displayProfessorCourseMenu() {
        while (true) {
            System.out.println("============ 교수 강의관리 메뉴 ============");
            System.out.println("1. 담당 강의 조회");
            System.out.println("2. 신규 강의 등록");
            System.out.println("3. 주차별 강의 내용 수정");
            System.out.println("4. 주차별 강의 내용 조회");
            System.out.println("0. 이전 메뉴로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");

            int menu = sc.nextInt();

            switch (menu) {
                case 1:
                    displayMyCourses();
                    break;
                case 2:
                    registerCourse();
                    break;
                case 3:
                    registerSessionTitle();
                    break;
                case 4:
                    displaySessionsByCourse();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void displayMyCourses() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();

        if (loggedInUser == null) {
            System.out.println("로그인 정보가 없습니다.");
            return;
        }

        long professorId = loggedInUser.getId();

        try {
            List<CourseDTO> courseList = controller.findProfessorCourses((int) professorId);
            profOutputView.displayCourseList(courseList);
        } catch (Exception e) {
            System.out.println("강의 조회 중 오류 발생!");
        }
    }

    private void registerCourse() {
        CourseDTO newCourse = inputCourse();

        if (newCourse == null) {
            System.out.println("강의 등록에 실패했습니다.");
            return;
        }

        boolean result = controller.insertCourse(newCourse);
        profOutputView.displayResult(result);
    }
    public CourseDTO inputCourse() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();

        if (loggedInUser == null) {
            System.out.println("로그인 정보가 없습니다.");
            return null;
        }

        int professorId = (int) loggedInUser.getId();

        sc.nextLine();
        System.out.println("============ 신규 강의 등록 ============");

        System.out.print("강의 제목 : ");
        String title = sc.nextLine();

        System.out.print("강의 설명 : ");
        String description = sc.nextLine();

        String semester;
        do {
            System.out.print("학기(예 : 2026-1) : ");
            semester = sc.nextLine();

            // 입력 형식이 '숫자4개-숫자1개'가 아니면 다시 입력받음
            if (!semester.matches("\\d{4}-[1-2]")) {
                System.out.println("⚠️ 형식에 맞춰 '연도-학기(1 또는 2)'를 입력해 주세요.");
            }
        } while (!semester.matches("\\d{4}-[1-2]"));

        return new CourseDTO(0L, null, professorId, title, description, semester);

    }
    private void registerSessionTitle() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();

        if (loggedInUser == null) {
            System.out.println("로그인 정보가 없습니다.");
            return;
        }
        int professorId = (int) loggedInUser.getId();
        List<CourseDTO> myCourses = controller.findProfessorCourses(professorId);

        if (myCourses == null || myCourses.isEmpty()) {
            System.out.println("담당 중인 강의가 없습니다.");
            return;
        }

        System.out.println("\n============ 담당 강의 목록 ============");
        for (int i = 0; i < myCourses.size(); i++) {
            System.out.println((i + 1) + ". " + myCourses.get(i).getTitle());
        }

        System.out.print("강의 번호 선택 : ");
        int courseNumber = sc.nextInt();
        sc.nextLine();

        if (courseNumber < 1 || courseNumber > myCourses.size()) {
            System.out.println("잘못된 강의 번호입니다.");
            return;
        }

        CourseDTO selectedCourse = myCourses.get(courseNumber - 1);

        System.out.print("주차 입력 (1~15) : ");
        int week = sc.nextInt();
        sc.nextLine();

        if (week < 1 || week > 15) {
            System.out.println("잘못된 주차입니다.");
            return;
        }

        System.out.print("수업 내용(title) 입력 : ");
        String title = sc.nextLine();

        boolean result = sessionController.updateSessionTitle(
                selectedCourse.getId().intValue(),
                week,
                title
        );

        profOutputView.displaySessionUpdateResult(result);
    }
    private void displaySessionsByCourse() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();

        if (loggedInUser == null) {
            System.out.println("로그인 정보가 없습니다.");
            return;
        }

        int professorId = (int) loggedInUser.getId();
        List<CourseDTO> myCourses = controller.findProfessorCourses(professorId);

        if (myCourses == null || myCourses.isEmpty()) {
            System.out.println("담당 중인 강의가 없습니다.");
            return;
        }

        System.out.println("\n============ 담당 강의 목록 ============");
        for (int i = 0; i < myCourses.size(); i++) {
            System.out.println((i + 1) + ". " + myCourses.get(i).getTitle());
        }

        System.out.print("강의 번호 선택 : ");
        int courseNumber = sc.nextInt();
        sc.nextLine();

        if (courseNumber < 1 || courseNumber > myCourses.size()) {
            System.out.println("잘못된 강의 번호입니다.");
            return;
        }

        CourseDTO selectedCourse = myCourses.get(courseNumber - 1);

        List<SessionDTO> sessionList =
                sessionController.findSessionsByCourse(selectedCourse.getId().intValue());

        profOutputView.displaySessionList(sessionList);
    }

}

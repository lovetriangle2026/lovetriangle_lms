package com.module1.crud.course.view;

import com.module1.crud.course.controller.CourseController;
import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;

import java.util.List;
import java.util.Scanner;


public class ProfInputView {
    private Scanner sc = new Scanner(System.in);
private final CourseController controller;
private final ProfOutputView profOutputView;


    public ProfInputView(CourseController controller, ProfOutputView profOutputView) {
        this.controller = controller;
        this.profOutputView = profOutputView;
    }

    public void displayProfessorCourseMenu() {
        while (true) {
            System.out.println("\n=================================");
            System.out.println("         교수 강의관리 메뉴");
            System.out.println("=================================");
            System.out.println("1. 담당 강의 조회");
            System.out.println("2. 신규 강의 등록");
            System.out.println("3. 주차별 강의 내용 등록");
            System.out.println("4. 주차별 강의 내용 조회");
            System.out.println("0. 이전 메뉴");
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
                    System.out.println("주차별 강의 내용 등록 기능은 다음 단계에서 구현");
                    break;
                case 4:
                    System.out.println("주차별 강의 내용 조회 기능은 다음 단계에서 구현");
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

        int result = controller.insertCourse(newCourse);
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
        System.out.println("=== 신규 강의 등록 ===");

        System.out.println("강의 제목 : ");
        String title = sc.nextLine();

        System.out.println("강의 설명 : ");
        String description = sc.nextLine();

        System.out.println("학기(예 : 2026-1) : ");
        String semester = sc.nextLine();

        return new CourseDTO(0L, null, professorId, title, description, semester);
    }

}

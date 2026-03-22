package com.module1.crud.course.view;

import com.module1.crud.course.controller.CourseController;
import com.module1.crud.course.controller.SessionController;
import com.module1.crud.course.model.dto.CourseDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.module1.crud.course.model.dto.SessionDTO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;
public class StudentCourseInputView {

    private final CourseController controller;
    private final StudentCourseOutputView outputView;
    private final Scanner sc = new Scanner(System.in);
    private final SessionController sessionController;

    // 생성자를 통한 final 변수 초기화
    public StudentCourseInputView(CourseController controller, StudentCourseOutputView outputView,SessionController sessionController) {
        this.controller = controller;
        this.outputView = outputView;
        this.sessionController = sessionController;
    }

    public void displayStudentMenu() {
        Scanner sc = new Scanner(System.in); //입력 받짜.
        System.out.println("1. 전체 강의 목록 조회");
        System.out.println("2. 수강 강의 조회");
        System.out.println("3. 수강 신청");
        System.out.println("4. 주차별 강의 내용 조회");
        System.out.println("0. 뒤로 가기");
        System.out.println("번호 선택 : ");

        //입력 숫자 받끼.
        int choice = sc.nextInt();

        if (choice ==1) {
            displayAllCourse();

        } else if(choice == 2) {
        displayEnrollableCourse();
        }else if(choice == 3) {
            enrollCourse();
        }else if(choice == 4){
            displaySessionsByMyCourse();
        }
        else if (choice ==0) {
            System.out.println("메뉴 종료합니다");
        }
    }

    private void displayEnrollableCourse() {
        UsersDTO user = SessionManager.getInstance().getLoggedInUser();

        if (user == null) {
            System.out.println("🚨 로그인 정보가 없습니다.");
            return;
        }

        int userId = user.getId(); // 또는 getId() (DTO 구조 확인 필요)

        // 2. 컨트롤러 호출
        List<CourseDTO> myCourseList = null;

            myCourseList = controller.findMyCourses(userId);


        // 3. 출력
        outputView.printCourses(myCourseList);

    }

    private void displayAllCourse() {

        outputView.printMessage("\n--- [기초 실습] 강좌 목록 전체 조회 ---");
        controller.findAllCourses();
//        outputView.printCourses(courseList);

    }
    //여기부터 본인이 신청한 강의 조회 기능
    private void FindMyCourses(int userId) throws SQLException {

        outputView.printMessage("--- 내가 신청한 강의 목록 조회 ---");
        // 1. 컨트롤러를 불러서 내 강의 목록 가져오기
        List<CourseDTO> myCourseList = controller.findMyCourses(userId);

        // 2. 출력 담당(outputView)에게 목록을 보여달라시키기
        outputView.printCourses(myCourseList);

    }

    private void enrollCourse() {
        UsersDTO user = SessionManager.getInstance().getLoggedInUser();

        if (user == null) {
            System.out.println("🚨 로그인 정보가 없습니다.");
            return;
        }

        List<CourseDTO> courseList = controller.findAllCourses();
        outputView.printCourses(courseList);

        System.out.print("수강신청할 course_id를 입력하세요 : ");
        int courseId = Integer.parseInt(sc.nextLine());

        boolean result = controller.enrollCourse(user.getId(), courseId);

        if (result) {
            System.out.println("✅ 수강신청이 완료되었습니다.");
        } else {
            System.out.println("🚨 이미 신청한 강의입니다.");
        }
    }

    private void displaySessionsByMyCourse() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();

        if (loggedInUser == null) {
            System.out.println("로그인 정보가 없습니다.");
            return;
        }

        int studentId = (int) loggedInUser.getId();
        List<CourseDTO> myCourses = controller.findMyCourses(studentId);

        if (myCourses == null || myCourses.isEmpty()) {
            System.out.println("수강 중인 강의가 없습니다.");
            return;
        }

        System.out.println("\n=== 수강 중인 강의 목록 ===");
        for (int i = 0; i < myCourses.size(); i++) {
            System.out.println((i + 1) + ". " + myCourses.get(i).getTitle());
        }

        System.out.print("강의 번호 선택 : ");

        int courseNumber;
        try {
            courseNumber = sc.nextInt();
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("강의 번호는 숫자로 입력해야 합니다.");
            sc.nextLine();
            return;
        }

        if (courseNumber < 1 || courseNumber > myCourses.size()) {
            System.out.println("잘못된 강의 번호입니다.");
            return;
        }

        CourseDTO selectedCourse = myCourses.get(courseNumber - 1);

        List<SessionDTO> sessionList =
                sessionController.findSessionsByCourse(selectedCourse.getId().intValue());

        outputView.displaySessionList(sessionList);
    }

}

package com.module1.crud.course.view;

import com.module1.crud.course.controller.CourseController;
import com.module1.crud.course.model.dto.CourseDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;
public class StudentCourseInputView {

    private final CourseController controller;
    private final StudentCourseOutputView outputView;
    private final Scanner sc = new Scanner(System.in);

    // 생성자를 통한 final 변수 초기화
    public StudentCourseInputView(CourseController controller, StudentCourseOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;
    }

    public void displayStudentMenu() {
        Scanner sc = new Scanner(System.in); //입력 받짜.
        System.out.println("1. 전체 강의 목록 조회");
        System.out.println("2. 수강 강의 조회");
        System.out.println("0. 뒤로 가기");
        System.out.println("번호 선택 : ");

        //입력 숫자 받끼.
        int choice = sc.nextInt();

        if (choice ==1) {
            displayAllCourse();

        } else if(choice == 2) {
        displayEnrollCourse();
        }
        else if (choice ==0) {
            System.out.println("메뉴 종료합니다");
        }
    }

    private void displayEnrollCourse() {
        UsersDTO user = SessionManager.getInstance().getLoggedInUser();

        if (user == null) {
            System.out.println("🚨 로그인 정보가 없습니다.");
            return;
        }

        int userId = user.getId(); // 또는 getId() (DTO 구조 확인 필요)

        // 2. 컨트롤러 호출
        List<CourseDTO> myCourseList = null;
        try {
            myCourseList = controller.findMyCourses(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 3. 출력
        outputView.printCourses(myCourseList);

    }

    private void displayAllCourse() {

        outputView.printMessage("\n--- [기초 실습] 강좌 목록 전체 조회 ---");
        List<CourseDTO> courseList = controller.findAllCourses();
        outputView.printCourses(courseList);

    }
    //여기부터 본인이 신청한 강의 조회 기능
    private void FindMyCourses(int userId) throws SQLException {

        outputView.printMessage("--- 내가 신청한 강의 목록 조회 ---");
        // 1. 컨트롤러를 불러서 내 강의 목록 가져오기
        List<CourseDTO> myCourseList = controller.findMyCourses(userId);

        // 2. 출력 담당(outputView)에게 목록을 보여달라시키기
        outputView.printCourses(myCourseList);

    }

}

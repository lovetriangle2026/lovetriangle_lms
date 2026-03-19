package com.module1.crud.course.view;

import com.module1.crud.course.controller.CourseController;
import com.module1.crud.course.model.dto.CourseDTO;

import java.util.List;
import java.util.Scanner;

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
        System.out.println("0. 뒤로 가기");
        System.out.println("번호 선택 : ");

        //입력 숫자 받끼.
        int choice = sc.nextInt();

        if (choice ==1) {
            displayAllCourse();
        } else if (choice ==0) {
            System.out.println("메뉴 종료합니다");
        }
    }

    private void displayAllCourse() {

        outputView.printMessage("\n--- [기초 실습] 강좌 목록 전체 조회 ---");
        List<CourseDTO> courseList = controller.findAllCourses();
        outputView.printCourses(courseList);

    }
}

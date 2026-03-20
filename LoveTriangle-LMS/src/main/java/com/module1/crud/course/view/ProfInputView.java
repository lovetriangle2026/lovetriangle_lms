package com.module1.crud.course.view;

import com.module1.crud.course.controller.CourseController;
import com.module1.crud.course.model.dto.CourseDTO;
import java.util.Scanner;


public class ProfInputView {
    private Scanner sc = new Scanner(System.in);
private final CourseController controller;
private final ProfOutputView profOutputView;


    public ProfInputView(CourseController controller, ProfOutputView profOutputView) {
        this.controller = controller;
        this.profOutputView = profOutputView;
    }



        public CourseDTO inputCourse () {
            sc.nextLine();
            System.out.println("=== 신규 강의 등록 ===");

            System.out.println("교수번호 (ID) : ");
            int professorId = sc.nextInt();
            sc.nextLine();

            System.out.println("강의 제목 : ");
            String title = sc.nextLine();

            System.out.println("강의 설명 : ");
            String description = sc.nextLine();

            System.out.println("학기(예 : 2026-1) : ");
            String semester = sc.nextLine();
            //주의 id(0)랑 course_code(null)  은 여기서 안채움
            // id는 db가 course_code 는 서비스가 채움
            return new CourseDTO(0L, null, professorId, title, description, semester);
        }
    }

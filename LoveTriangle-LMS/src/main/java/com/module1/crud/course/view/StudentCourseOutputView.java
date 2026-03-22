package com.module1.crud.course.view;

import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.course.model.dto.SessionDTO;

import java.util.List;

public class StudentCourseOutputView {

    public void printMessage(String s) {
        System.out.println(s);
    }

    public void printCourses(List<CourseDTO> courseList) {

        if(courseList == null || courseList.isEmpty()){
            System.out.println("조회 된 강좌가 없습니다!!");
            return;
        }
        System.out.println("============강의 전체 조회 목록 결과==============");
        for(CourseDTO courseDTO : courseList){
            System.out.println(courseDTO);
        }

    }

    public void displaySessionList(List<SessionDTO> sessionList) {
        System.out.println("\n========= 주차별 강의 목록 =========");

        if (sessionList == null || sessionList.isEmpty()) {
            System.out.println("등록된 주차별 강의가 없습니다.");
        } else {
            for (SessionDTO session : sessionList) {
                System.out.println(session);
            }
        }

        System.out.println("=================================\n");
    }
}

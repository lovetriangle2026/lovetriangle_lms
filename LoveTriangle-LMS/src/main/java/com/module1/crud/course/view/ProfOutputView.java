package com.module1.crud.course.view;

import com.module1.crud.course.model.dto.CourseDTO;

import java.util.List;

public class ProfOutputView {
    public void displayResult(int result) {
        if ( result >0) {
            System.out.println("성공 !! 새로운 강의가 등록되었습니다.");
        } else {
            System.out.println("실패!! 강의 등록에 실패했습니다.");
        }
    }


        // 🚀 2. [추가] 전체 강의 목록을 화면에 예쁘게 찍어주는 메서드
        public void displayCourseList(List<CourseDTO> courseList) {
            System.out.println("\n========= 📚 전체 강의 목록 조회 =========");

            if (courseList == null || courseList.isEmpty()) {
                System.out.println("🚨 등록된 강의가 하나도 없습니다.");
            } else {
                // 리스트를 돌면서 강의 정보를 하나씩 출력합니다.
                for (CourseDTO course : courseList) {
                    // CourseDTO 클래스에 @ToString이나 toString()이 구현되어 있어야 잘 보여요!
                    System.out.println(course);
                }
            }
            System.out.println("========================================\n");
        }
    }


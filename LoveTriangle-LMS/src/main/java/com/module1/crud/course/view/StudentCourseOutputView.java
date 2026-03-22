package com.module1.crud.course.view;

import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.course.model.dto.SessionDTO;

import java.util.List;

public class StudentCourseOutputView {

    public void printMessage(String s) {
        System.out.println(s);
    }

    public void printCourses(List<CourseDTO> courseList) {
        if (courseList == null || courseList.isEmpty()) {
            System.out.println("\n🚨 조회된 강좌가 없습니다!!");
            return;
        }

        System.out.println("\n================================= 📚 전체 강의 목록 조회 결과 =================================");
        // 컬럼 제목 출력 (ID, 강의코드, 교수ID, 학기, 강의 제목 순)
        System.out.printf("%-4s | %-12s | %-8s | %-8s | %-20s\n",
                "ID", "강의 코드", "교수 ID", "학기", "강의 제목");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (CourseDTO courseDTO : courseList) {
            // DTO 필드명(course_code, professor_id)에 맞춰서 Getter를 호출합니다.
            System.out.printf("%-4d | %-12s | %-8d | %-8s | %-20s\n",
                    courseDTO.getId(),
                    (courseDTO.getCourse_code() == null ? "미부여" : courseDTO.getCourse_code()),
                    courseDTO.getProfessor_id(),
                    courseDTO.getSemester(),
                    courseDTO.getTitle());
        }
        System.out.println("============================================================================================\n");
    }

    public void displaySessionList(List<SessionDTO> sessionList) {
        System.out.println("\n==================== 📘 주차별 강의 상세 조회 ====================");

        if (sessionList == null || sessionList.isEmpty()) {
            System.out.println("🚨 등록된 주차별 강의 내용이 없습니다.");
        } else {
            System.out.printf("%-10s | %-5s | %-25s | %-20s\n", "과목번호", "주차", "강의 제목", "일시");
            System.out.println("-----------------------------------------------------------------------");

            int week = 1;
            for (SessionDTO session : sessionList) {
                // 날짜 가공 (null 체크 포함)
                String formattedDate = (session.getStartAt() != null)
                        ? session.getStartAt().toString().replace("T", " ").substring(0, 16)
                        : "시간 정보 없음";

                System.out.printf("ID:%-7d | %-5d | %-25s | %-20s\n",
                        session.getCourseId(),
                        week++,
                        session.getTitle(),
                        formattedDate);
            }
        }
        System.out.println("=======================================================================\n");
    }
}

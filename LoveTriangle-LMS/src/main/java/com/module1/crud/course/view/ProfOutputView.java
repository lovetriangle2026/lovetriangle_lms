package com.module1.crud.course.view;

import com.module1.crud.course.model.dto.CourseDTO;
import com.module1.crud.course.model.dto.SessionDTO;

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
            System.out.println("\n============================= 📚 나의 담당 강의 목록 =============================");

            if (courseList == null || courseList.isEmpty()) {
                System.out.println("🚨 현재 담당하고 계신 강의가 없습니다.");
            } else {
                // 헤더 출력
                System.out.printf("%-4s | %-12s | %-6s | %-8s | %-20s\n",
                        "ID", "강의 코드", "교수ID", "학기", "강의 제목");
                System.out.println("--------------------------------------------------------------------------------");

                for (CourseDTO course : courseList) {
                    // DTO의 실제 메서드명에 맞춰 수정했습니다.
                    System.out.printf("%-4d | %-12s | %-8d | %-8s | %-20s\n",
                            course.getId(),                             // Long 타입은 %d로 자동 변환됩니다.
                            (course.getCourse_code() == null ? "N/A" : course.getCourse_code()),
                            course.getProfessor_id(),                   // getProfessorId -> getProfessor_id로 수정
                            course.getSemester(),
                            course.getTitle());
                }
            }
            System.out.println("================================================================================\n");
        }



    public void displayResult(boolean result) {
        if (result) {
            System.out.println("성공 !! 새로운 강의가 등록되었습니다.");
        } else {
            System.out.println("실패!! 강의 등록에 실패했습니다.");
        }
    }

    // 기존의 displaySessionList 메서드 내용을 아래로 교체하세요!
    public void displaySessionList(List<SessionDTO> sessionList) {
        System.out.println("\n==================== 📘 주차별 강의 상세 조회 ====================");

        if (sessionList == null || sessionList.isEmpty()) {
            System.out.println("🚨 등록된 주차별 강의 내용이 없습니다.");
        } else {
            // 표 헤더 출력
            System.out.printf("%-10s | %-5s | %-25s | %-20s\n", "과목번호", "주차", "강의 제목", "일시");
            System.out.println("-----------------------------------------------------------------------");

            int week = 1;
            for (SessionDTO session : sessionList) {
                // 날짜 가공 (T 빼고 초 단위 잘라내기)
                String formattedDate = session.getStartAt().toString().replace("T", " ").substring(0, 16);

                // 데이터 출력
                System.out.printf("ID:%-7d | %-5d | %-25s | %-20s\n",
                        session.getCourseId(),
                        week++,
                        session.getTitle(),
                        formattedDate);
            }
        }
        System.out.println("=======================================================================\n");
    }

    public void displaySessionUpdateResult(boolean result) {
        if (result) {
            System.out.println("주차별 강의 내용 수정 성공!");
        } else {
            System.out.println("주차별 강의 내용 수정 실패!");
        }
    }
    }


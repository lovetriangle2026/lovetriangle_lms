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


        public void displayCourseList(List<CourseDTO> courseList) {
            System.out.println("\n==================== 📚 전체 담당 강의 목록 ====================");

            if (courseList == null || courseList.isEmpty()) {
                System.out.println("🚨 현재 담당하고 계신 강의가 없습니다.");
            } else {
                // 표 헤더 출력 (ID, 과목코드, 강의명, 학기 순)
                System.out.printf("%-5s | %-12s | %-25s | %-10s\n", "번호", "과목코드", "강의 제목", "학기");
                System.out.println("-----------------------------------------------------------------------");

                for (CourseDTO course : courseList) {
                    // CourseDTO에서 필요한 정보만 쏙쏙 뽑아서 정렬 출력
                    System.out.printf("%-5d | %-12s | %-25s | %-10s\n",
                            course.getId(),
                            course.getCourse_code() != null ? course.getCourse_code() : "N/A",
                            course.getTitle(),
                            course.getSemester());
                }
            }
            System.out.println("=======================================================================\n");
        }



    public void displayResult(boolean result) {
        if (result) {
            System.out.println("성공 !! 새로운 강의가 등록되었습니다.");
        } else {
            System.out.println("실패!! 강의 등록에 실패했습니다.");
        }
    }

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


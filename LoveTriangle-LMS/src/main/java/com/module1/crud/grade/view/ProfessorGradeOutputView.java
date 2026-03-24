package com.module1.crud.grade.view;

import com.module1.crud.grade.model.dto.GradeEditDTO;
import com.module1.crud.grade.model.dto.GradeViewDTO;
import com.module1.crud.grade.model.dto.GradeRegisterDTO;

import java.util.List;

public class ProfessorGradeOutputView {

    public void printError(String s) {
        System.out.println(s);
    }

    public void printGrades(List<GradeViewDTO> gradeList) {
        System.out.println("============ 강의 전체 조회 목록 결과 ============");
        printstudentGrades(gradeList); // ⭐ 여기로 넘김
    }

    public void printstudentGrades(List<GradeViewDTO> gradeList) {
        if (gradeList == null || gradeList.isEmpty()) {
            System.out.println("조회된 성적이 없습니다.");
            return;
        }

        String line = "==========================================================================================================";
        String headLine = "----------------------------------------------------------------------------------------------------------";

        System.out.println("\n" + line);
        System.out.println("                                     📚 학생 성적 통합 조회");
        System.out.println(line);

        // 헤더 구성 (너비 조정)
        System.out.println(
                padRight("학번", 8) + " | " +
                        padRight("학생명", 10) + " | " +
                        padRight("과목명", 20) + " | " +
                        padRight("출석", 8) + " | " + // ⭐ 추가
                        padRight("중간(원/35)", 12) + " | " +
                        padRight("기말(원/35)", 12) + " | " +
                        padRight("과제", 6) + " | " +
                        padRight("총점", 8) + " | " +
                        padRight("등급", 5)
        );
        System.out.println(headLine);

        for (GradeViewDTO dto : gradeList) {
            // 중간/기말 원점수와 환산점수를 "85 / 29.7" 형태로 결합
            String midStr = value(dto.getMidterm_score()) + "/" + value(dto.getMidterm_35());
            String finStr = value(dto.getFinal_score()) + "/" + value(dto.getFinal_35());

            System.out.println(
                    padRight(String.valueOf(dto.getStudentId()), 8) + " | " +
                            padRight(dto.getStudentName(), 10) + " | " +
                            padRight(dto.getCourse_title(), 20) + " | " +
                            padRight(value(dto.getAttendance_score()), 8) + " | " + // ⭐ 추가
                            padRight(midStr, 12) + " | " +
                            padRight(finStr, 12) + " | " +
                            padRight(value(dto.getAssignment_score()), 6) + " | " +
                            padRight(value(dto.getTotal_score()), 8) + " | " +
                            padRight(value(dto.getGrade()), 5)
            );
        }
        System.out.println(line);
    }

    public void printmessage(String s) {
        System.out.println(s);
    }


    public void printEditableGradeList(List<GradeEditDTO> gradeList) {
        if (gradeList == null || gradeList.isEmpty()) {
            System.out.println("수정 가능한 성적 정보가 없습니다.");
            return;
        }

        // 상단 가로선 (전체 너비에 맞춰 조절 가능)
        String line = "==========================================================================================";

        System.out.println("\n" + padRight("번호", 6) + padRight("학생명", 12) + padRight("과목명", 24) + padRight("중간", 10) + padRight("기말", 10) + padRight("과제", 10));
        System.out.println(line);

        for (int i = 0; i < gradeList.size(); i++) {
            GradeEditDTO grade = gradeList.get(i);

            // 각 항목을 padRight로 감싸서 출력
            System.out.println(
                    padRight(String.valueOf(i + 1), 6) +
                            padRight(grade.getStudentName(), 12) +
                            padRight(grade.getCourseTitle(), 24) +
                            padRight(value(grade.getMidtermScore()), 10) +
                            padRight(value(grade.getFinalScore()), 10) +
                            padRight(value(grade.getAssignmentScore()), 10)
            );
        }
        System.out.println(line);
    }
    private String value(Object obj) {
        return obj == null ? "-" : obj.toString();
    }

    public void printAssignmentRegisterTargets(List<GradeRegisterDTO> registerList) {
        System.out.println("========= [과제 평가 등록 대상 목록] =========");


        for (int i = 0; i < registerList.size(); i++) {
            GradeRegisterDTO dto = registerList.get(i);
            System.out.println((i + 1) + ". 학생명 : " + dto.getStudentName()
                    + " / 과목명 : " + dto.getCourseTitle());
        }
    }

    private String padRight(String text, int width) {
        if (text == null) text = "-";
        int currentWidth = 0;
        for (char c : text.toCharArray()) {
            if (c >= 0xAC00 && c <= 0xD7A3) currentWidth += 2; // 한글은 2칸
            else currentWidth += 1; // 나머지는 1칸
        }
        int padding = width - currentWidth;
        return text + " ".repeat(Math.max(0, padding));
    }

}

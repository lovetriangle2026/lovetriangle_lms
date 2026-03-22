package com.module1.crud.grade.view;

import com.module1.crud.grade.model.dto.GradeViewDTO;

import java.util.List;

public class StudentGradeOutputView {
    public void printMessage(String s) {
    }

    public void printError(String s) {
    }

    public void printGrades(List<GradeViewDTO> gradeList) {
        if (gradeList == null || gradeList.isEmpty()) {
            System.out.println("조회된 성적이 없습니다.");
            return;
        }

        String line = "==========================================================================================";
        String subLine = "------------------------------------------------------------------------------------------";

        System.out.println(line);
        System.out.println(
                padRightKorean("과목코드", 10) +
                        padRightKorean("과목명", 22) +
                        padRightKorean("중간", 8) +
                        padRightKorean("기말", 8) +
                        padRightKorean("출석", 8) +
                        padRightKorean("과제", 8) +
                        padRightKorean("총점", 10) +
                        padRightKorean("등급", 6)
        );
        System.out.println(line);

        String prevCourse = null;

        for (GradeViewDTO dto : gradeList) {
            String currentCourse = value(dto.getCourse_title());

            if (prevCourse != null && !prevCourse.equals(currentCourse)) {
                System.out.println(subLine);
            }

            System.out.println(
                    padRightKorean(value(dto.getCourse_id()), 10) +
                            padRightKorean(currentCourse, 22) +
                            padRightKorean(value(dto.getMidterm_score()), 8) +
                            padRightKorean(value(dto.getFinal_score()), 8) +
                            padRightKorean(value(dto.getAttendance_score()), 8) +
                            padRightKorean(value(dto.getAssignment_score()), 8) +
                            padRightKorean(value(dto.getTotal_score()), 10) +
                            padRightKorean(value(dto.getGrade()), 6)
            );

            prevCourse = currentCourse;
        }

        System.out.println(line);
    }

    private String value(Object obj) {
        return obj == null ? "-" : obj.toString();
    }

    private String padRightKorean(String text, int width) {
        if (text == null) {
            text = "-";
        }

        int textWidth = getDisplayWidth(text);
        int padding = width - textWidth;

        if (padding <= 0) {
            return text + " ";
        }

        return text + " ".repeat(padding);
    }

    private int getDisplayWidth(String text) {
        int width = 0;

        for (char ch : text.toCharArray()) {
            if (ch >= 128) {
                width += 2;
            } else {
                width += 1;
            }
        }

        return width;
    }
}

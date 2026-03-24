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

        // ⭐ 너비를 미세하게 조정하여 가독성을 높였습니다. (전체 길이 106)
        String line = "==========================================================================================================";

        System.out.println("\n" + line);
        // ⭐ 헤더 출력 시에도 반드시 padRightKorean을 적용하여 데이터와 시작 위치를 맞춥니다.
        System.out.println(
                padRightKorean("과목명", 22) + " | " +
                        padRightKorean("출석", 8) + " | " +
                        padRightKorean("중간(원/35)", 18) + " | " + // ⭐ 너비를 늘려 삐뚤빼뚤 방지
                        padRightKorean("기말(원/35)", 18) + " | " + // ⭐ 너비를 늘려 삐뚤빼뚤 방지
                        padRightKorean("과제", 8) + " | " +
                        padRightKorean("총점", 10) + " | " +
                        padRightKorean("등급", 6)
        );
        System.out.println("----------------------------------------------------------------------------------------------------------");

        for (GradeViewDTO dto : gradeList) {
            String midStr = value(dto.getMidterm_score()) + " (" + value(dto.getMidterm_35()) + ")";
            String finStr = value(dto.getFinal_score()) + " (" + value(dto.getFinal_35()) + ")";

            // ⭐ 각 데이터 항목을 padRightKorean으로 감싸 완벽한 칼각 정렬을 만듭니다.
            // ⭐ 헤더와 동일한 너비(22, 8, 18, 18, 8, 10, 6)를 사용합니다.
            System.out.println(
                    padRightKorean(dto.getCourse_title(), 22) + " | " +
                            padRightKorean(value(dto.getAttendance_score()), 8) + " | " +
                            padRightKorean(midStr, 18) + " | " +
                            padRightKorean(finStr, 18) + " | " +
                            padRightKorean(value(dto.getAssignment_score()), 8) + " | " +
                            padRightKorean(value(dto.getTotal_score()), 10) + " | " +
                            padRightKorean(value(dto.getGrade()), 6)
            );
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

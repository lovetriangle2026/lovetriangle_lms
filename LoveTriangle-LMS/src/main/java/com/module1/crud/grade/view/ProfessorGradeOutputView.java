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

        System.out.println("================================================================================");
        System.out.printf("%-5s %-10s %-18s %-6s %-6s %-10s%n",
                "학번", "학생명", "과목명", "중간", "기말", "환산점수");
        System.out.println("================================================================================");

        String prevCourse = null; // ⭐ 이전 과목 저장

        for (GradeViewDTO dto : gradeList) {

            // ⭐ 과목이 바뀌면 구분선 출력
            if (prevCourse != null && !prevCourse.equals(dto.getCourse_title())) {
                System.out.println("--------------------------------------------------------------------------------");
            }

            System.out.printf("%-5d %-10s %-18s %-6s %-6s %-10s%n",
                    dto.getStudentId(),
                    dto.getStudentName(),
                    dto.getCourse_title(),
                    value(dto.getMidterm_score()),
                    value(dto.getFinal_score()),
                    value(dto.getMidterm_35()) // 원하는 값으로 바꿔도 됨
            );

            prevCourse = dto.getCourse_title(); // ⭐ 현재 과목 저장
        }

        System.out.println("================================================================================");
    }
    private String value(Object obj) {
        return obj == null ? "-" : obj.toString();
    }

    public void printmessage(String s) {
        System.out.println(s);
    }


    public void printEditableGradeList(List<GradeEditDTO> gradeList) {
        if (gradeList == null || gradeList.isEmpty()) {
            System.out.println("수정 가능한 성적 정보가 없습니다.");
            return;
        }

        System.out.println("================================================================================");
        System.out.printf("%-4s %-10s %-18s %-8s %-8s %-8s%n",
                "번호", "학생명", "과목명", "중간", "기말", "과제");
        System.out.println("================================================================================");

        String prevCourse = null;

        for (int i = 0; i < gradeList.size(); i++) {
            GradeEditDTO grade = gradeList.get(i);

            if (prevCourse != null && !prevCourse.equals(grade.getCourseTitle())) {
                System.out.println("--------------------------------------------------------------------------------");
            }

            System.out.printf("%-4d %-10s %-18s %-8s %-8s %-8s%n",
                    (i + 1),
                    value(grade.getStudentName()),
                    value(grade.getCourseTitle()),
                    value(grade.getMidtermScore()),
                    value(grade.getFinalScore()),
                    value(grade.getAssignmentScore()));

            prevCourse = grade.getCourseTitle();
        }

        System.out.println("================================================================================");
    }

    public void printAssignmentRegisterTargets(List<GradeRegisterDTO> registerList) {
        System.out.println("========= [과제 평가 등록 대상 목록] =========");


        for (int i = 0; i < registerList.size(); i++) {
            GradeRegisterDTO dto = registerList.get(i);
            System.out.println((i + 1) + ". 학생명 : " + dto.getStudentName()
                    + " / 과목명 : " + dto.getCourseTitle());
        }
    }
}

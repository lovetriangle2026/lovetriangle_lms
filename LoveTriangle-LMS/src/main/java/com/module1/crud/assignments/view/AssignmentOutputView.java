package com.module1.crud.assignments.view;

import com.module1.crud.assignments.model.dto.AssignmentDTO;

import java.util.List;

public class AssignmentOutputView {
    public void printMessage(String s) {
        System.out.println(s);
    }

    public void printError(String s) {
        System.out.println("🚨 " + s);
    }

    public void printAssignments(List<AssignmentDTO> assignmentList) {

        if (assignmentList == null || assignmentList.isEmpty()) {
            System.out.println("조회 된 과제가 없습니다!!");
            return;
        }

        System.out.println("=============== 수강 과제 조회 ===============");
        for (AssignmentDTO dto : assignmentList) {
            System.out.println("과제번호 : " + dto.getId());
            System.out.println("강의번호 : " + dto.getCourse_Id());
            System.out.println("과제명   : " + dto.getTitle());
            System.out.println("설명     : " + dto.getDescription());
            System.out.println("마감일   : " + dto.getDeadline());
            System.out.println("제출여부 : " + dto.getSubmissionStatus());
            System.out.println("제출내용 : " + (dto.getSubmissionContent() == null ? "-" : dto.getSubmissionContent()));
            System.out.println("제출일시 : " + (dto.getSubmittedAt() == null ? "-" : dto.getSubmittedAt()));
            System.out.println("--------------------------------------------------");
        }
    }
}

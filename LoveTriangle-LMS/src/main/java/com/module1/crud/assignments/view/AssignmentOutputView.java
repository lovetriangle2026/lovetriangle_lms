package com.module1.crud.assignments.view;

import com.module1.crud.assignments.model.dto.AssignmentDTO;

import java.util.List;

public class AssignmentOutputView {
    public void printMessage(String s) {
    }

    public void printError(String s) {
    }

    public void printAssignments(List<AssignmentDTO> assignmentList) {

        if (assignmentList == null || assignmentList.isEmpty()) {
            System.out.println("조회 된 과제가 없습니다!!");
            return;
        }

        System.out.println("=================과제 조회 목록 결과=================");
        for (AssignmentDTO courseDTO : assignmentList) {
            System.out.println(courseDTO);
        }
    }
}

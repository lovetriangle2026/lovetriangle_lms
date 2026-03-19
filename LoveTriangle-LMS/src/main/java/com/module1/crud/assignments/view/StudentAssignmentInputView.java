package com.module1.crud.assignments.view;

import com.module1.crud.assignments.controller.AssignmentController;
import com.module1.crud.assignments.model.dto.AssignmentDTO;

import java.util.List;
import java.util.Scanner;

public class StudentAssignmentInputView {

    private final AssignmentController controller;
    private final AssignmentOutputView outputView;
    private final Scanner sc = new Scanner(System.in);


    public StudentAssignmentInputView(AssignmentController controller, AssignmentOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;

    }

    public void displaymainmenu() {

        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("        [학생] 과제 관리");
            System.out.println("=================================");
            System.out.println("1. 과제 조회");
            System.out.println("2. 과제 제출");
            System.out.println("3. 제출 수정");
            System.out.println("4. 제출 삭제");
            System.out.println("5. 이전으로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu) {
                case 1:
                    findMyAssignments();
                    break;
                case 2:
                    createSubmission();
                    break;
                case 3:
                    updateSubmission();
                    break;
                case 4:
                    deleteSubmission();
                    break;
                case 5:
                    outputView.printMessage("이전 메뉴로 돌아갑니다.");
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }


    }

    private void deleteSubmission() {

    }

    private void updateSubmission() {

    }

    private void createSubmission() {

    }

    private void findMyAssignments() {
        outputView.printMessage("\n--- 수강 과목 과제 조회 ---");

        List<AssignmentDTO> AssignmentDTOS =  controller.findMyAssignments();

        outputView.printAssignments(AssignmentDTOS);



    }

    private int inputInt() {
        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }


}

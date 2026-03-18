package com.module1.crud.assignments.view;

import com.module1.crud.assignments.controller.AssignmentController;

import java.util.Scanner;

public class ProfessorAssignmentInputView {

    private final AssignmentController controller;
    private final AssignmentOutputView outputView;
    private final Scanner sc1 = new Scanner(System.in);


    public ProfessorAssignmentInputView(AssignmentController controller, AssignmentOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;

    }

    public void displaymainmenu() {

        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("        [교수] 과제 관리");
            System.out.println("=================================");
            System.out.println("1. 과제 조회");
            System.out.println("2. 과제 등록");
            System.out.println("3. 과제 수정");
            System.out.println("4. 과제 삭제");
            System.out.println("5. 이전으로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu) {
                case 1:
                    findAllAssignments();
                    break;
                case 2:
                    createAssignment();
                    break;
                case 3:
                    updateAssignment();
                    break;
                case 4:
                    deleteAssignment();
                    break;
                case 5:
                    outputView.printMessage("이전 메뉴로 돌아갑니다.");
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }


    }

    private void deleteAssignment() {

    }

    private void updateAssignment() {

    }

    private void createAssignment() {

    }

    private void findAllAssignments() {

    }

    private int inputInt() {
        while (true) {
            try {
                int value = Integer.parseInt(sc1.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }


}

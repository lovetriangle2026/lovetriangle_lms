package com.module1.crud.assignments.view;

import com.module1.crud.assignments.controller.AssignmentController;
import com.module1.crud.assignments.model.dto.AssignmentDTO;
import com.module1.crud.assignments.model.dto.AssignmentSubmissionDTO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;

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
        outputView.printMessage("\n--- 과제 제출 ---");

        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();

        if (loggedInUser == null) {
            outputView.printError("로그인 정보가 없습니다. 다시 로그인해주세요.");
            return;
        }

        Long studentId = (long) loggedInUser.getId();

        System.out.print("제출할 과제 번호를 입력하세요: ");
        Long assignmentId = Long.parseLong(sc.nextLine());

        System.out.print("제출 내용을 입력하세요: ");
        String content = sc.nextLine();

        AssignmentSubmissionDTO submissionDTO = new AssignmentSubmissionDTO(
                assignmentId,
                studentId,
                content
        );

        controller.createSubmission(submissionDTO);
        outputView.printMessage("과제 제출이 완료되었습니다.");
    }

    private void findMyAssignments() {
        outputView.printMessage("\n--- 수강 과목 과제 조회 ---");

        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();

        Long userId = (long) loggedInUser.getId();

        List<AssignmentDTO> assignmentDTOS = controller.findMyAssignments(userId);
        outputView.printAssignments(assignmentDTOS);


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

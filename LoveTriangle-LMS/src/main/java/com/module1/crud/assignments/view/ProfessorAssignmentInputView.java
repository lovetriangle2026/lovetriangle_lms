package com.module1.crud.assignments.view;

import com.module1.crud.assignments.controller.AssignmentController;
import com.module1.crud.assignments.model.dto.ProfessorAssignmentDTO;
import com.module1.crud.assignments.model.dto.ProfessorAssignmentSubmissionDTO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;

import java.util.List;
import java.util.Scanner;

public class ProfessorAssignmentInputView {

    private final AssignmentController controller;
    private final AssignmentOutputView outputView;
    private final Scanner sc1 = new Scanner(System.in);


    public ProfessorAssignmentInputView(AssignmentController controller, AssignmentOutputView outputView) {
        this.controller = controller;
        this.outputView = outputView;

    }

    public void displayMainmenu() {

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

    // ====================== 과제 조회 ========================
    private void findAllAssignments() {
        while (true) {
            System.out.println("\n---------------------------------");
            System.out.println("         [과제 조회]");
            System.out.println("---------------------------------");
            System.out.println("1. 생성한 과제 조회");
            System.out.println("2. 과제의 학생 제출 현황 조회");
            System.out.println("0. 이전으로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu) {
                case 1:
                    findMyAssignmentsOnly();
                    break;
                case 2:
                    findSubmissionStatusMenu();
                    break;
                case 0:
                    outputView.printMessage("이전 메뉴로 돌아갑니다.");
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }
    }

    private void findSubmissionStatusMenu() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long professorId = (long) loggedInUser.getId();

        while (true) {
            System.out.print("제출 현황을 조회할 과제 번호를 입력하세요 (취소: 0): ");

            try {
                Long assignmentId = Long.parseLong(sc1.nextLine());

                if (assignmentId == 0L) {
                    outputView.printMessage("조회가 취소되었습니다.");
                    return;
                }

                boolean exists = controller.existsProfessorAssignment(assignmentId, professorId);

                if (!exists) {
                    outputView.printError("본인이 생성한 과제가 아닙니다. 다시 입력해주세요.");
                    continue;
                }

                List<ProfessorAssignmentSubmissionDTO> list =
                        controller.findSubmissionStatusByAssignment(assignmentId, professorId);

                outputView.printProfessorSubmissionStatus(list);
                return;

            } catch (NumberFormatException e) {
                outputView.printError("과제 번호는 숫자로 입력해주세요.");
            } catch (RuntimeException e) {
                outputView.printError(e.getMessage());
                return;
            }
        }

    }

    private void findMyAssignmentsOnly() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long professorId = (long) loggedInUser.getId();

        try {
            List<ProfessorAssignmentDTO> assignmentList =
                    controller.findAssignmentsByProfessor(professorId);

            outputView.printProfessorAssignments(assignmentList);

        } catch (RuntimeException e) {
            outputView.printError(e.getMessage());
        }
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

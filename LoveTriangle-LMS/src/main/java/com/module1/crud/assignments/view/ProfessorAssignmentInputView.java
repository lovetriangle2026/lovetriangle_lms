package com.module1.crud.assignments.view;

import com.module1.crud.assignments.controller.AssignmentController;
import com.module1.crud.assignments.model.dto.ProfessorAssignmentDTO;
import com.module1.crud.assignments.model.dto.ProfessorAssignmentSubmissionDTO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;

import java.sql.Timestamp;
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
            System.out.println("2. 과제 생성");
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
    // ============== 과제 삭제 ====================
    private void deleteAssignment() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long professorId = (long) loggedInUser.getId();

        try {
            List<ProfessorAssignmentDTO> assignmentList =
                    controller.findAssignmentsByProfessor(professorId);

            outputView.printProfessorAssignments(assignmentList);

            if (assignmentList == null || assignmentList.isEmpty()) {
                return;
            }

            while (true) {
                System.out.print("삭제할 과제 번호를 입력하세요 (취소: 0): ");

                Long assignmentId;
                try {
                    assignmentId = Long.parseLong(sc1.nextLine());
                } catch (NumberFormatException e) {
                    outputView.printError("과제 번호는 숫자로 입력해주세요.");
                    continue;
                }

                if (assignmentId == 0L) {
                    outputView.printMessage("과제 삭제를 취소했습니다.");
                    return;
                }

                boolean exists = controller.existsProfessorAssignment(assignmentId, professorId);

                if (!exists) {
                    outputView.printError("본인이 생성한 과제가 아닙니다. 다시 입력해주세요.");
                    continue;
                }

                ProfessorAssignmentDTO selectedAssignment = null;
                for (ProfessorAssignmentDTO dto : assignmentList) {
                    if (dto.getAssignmentId().equals(assignmentId)) {
                        selectedAssignment = dto;
                        break;
                    }
                }

                if (selectedAssignment == null) {
                    outputView.printError("해당 과제를 찾을 수 없습니다.");
                    continue;
                }

                System.out.println("\n삭제 대상 과제 정보");
                System.out.println("과제 번호 : " + selectedAssignment.getAssignmentId());
                System.out.println("강의 번호 : " + selectedAssignment.getCourseId());
                System.out.println("과제명   : " + selectedAssignment.getAssignmentTitle());
                System.out.println("설명     : " + selectedAssignment.getDescription());
                System.out.println("마감일   : " + selectedAssignment.getDeadline());

                System.out.print("정말 삭제하시겠습니까? (Y/N): ");
                String confirm = sc1.nextLine().trim();

                if (confirm.equalsIgnoreCase("N")) {
                    outputView.printMessage("과제 삭제를 취소했습니다.");
                    return;
                }

                if (!confirm.equalsIgnoreCase("Y")) {
                    outputView.printError("Y 또는 N만 입력해주세요.");
                    continue;
                }

                controller.deleteProfessorAssignment(assignmentId, professorId);
                outputView.printMessage("✅ 과제 삭제가 완료되었습니다.");
                return;
            }

        } catch (RuntimeException e) {
            outputView.printError(e.getMessage());
        }

    }

    // =============== 과제 수정 =================
    private void updateAssignment() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long professorId = (long) loggedInUser.getId();

        try {
            List<ProfessorAssignmentDTO> assignmentList =
                    controller.findAssignmentsByProfessor(professorId);

            outputView.printProfessorAssignments(assignmentList);

            if (assignmentList == null || assignmentList.isEmpty()) {
                return;
            }

            while (true) {
                System.out.print("수정할 과제 번호를 입력하세요 (취소: 0): ");

                Long assignmentId;
                try {
                    assignmentId = Long.parseLong(sc1.nextLine());
                } catch (NumberFormatException e) {
                    outputView.printError("과제 번호는 숫자로 입력해주세요.");
                    continue;
                }

                if (assignmentId == 0L) {
                    outputView.printMessage("과제 수정을 취소했습니다.");
                    return;
                }

                boolean exists = controller.existsProfessorAssignment(assignmentId, professorId);

                if (!exists) {
                    outputView.printError("본인이 생성한 과제가 아닙니다. 다시 입력해주세요.");
                    continue;
                }

                ProfessorAssignmentDTO selectedAssignment = null;
                for (ProfessorAssignmentDTO dto : assignmentList) {
                    if (dto.getAssignmentId().equals(assignmentId)) {
                        selectedAssignment = dto;
                        break;
                    }
                }

                if (selectedAssignment == null) {
                    outputView.printError("해당 과제를 찾을 수 없습니다.");
                    continue;
                }

                System.out.println("\n현재 과제 정보");
                System.out.println("과제명 : " + selectedAssignment.getAssignmentTitle());
                System.out.println("설명   : " + selectedAssignment.getDescription());
                System.out.println("마감일 : " + selectedAssignment.getDeadline());

                System.out.print("새 과제명을 입력하세요 (수정하지 않으려면 엔터): ");
                String newTitle = sc1.nextLine().trim();
                if (newTitle.isEmpty()) {
                    newTitle = selectedAssignment.getAssignmentTitle();
                }

                System.out.print("새 과제 설명을 입력하세요 (수정하지 않으려면 엔터): ");
                String newDescription = sc1.nextLine().trim();
                if (newDescription.isEmpty()) {
                    newDescription = selectedAssignment.getDescription();
                }

                System.out.print("새 마감일을 입력하세요 (예: 2026-03-25 23:59:59 / 수정하지 않으려면 엔터): ");
                String deadlineInput = sc1.nextLine().trim();

                java.sql.Timestamp newDeadline;
                if (deadlineInput.isEmpty()) {
                    newDeadline = selectedAssignment.getDeadline();
                } else {
                    try {
                        newDeadline = java.sql.Timestamp.valueOf(deadlineInput);
                    } catch (IllegalArgumentException e) {
                        outputView.printError("마감일 형식이 올바르지 않습니다. 예: 2026-03-25 23:59:59");
                        continue;
                    }
                }
                if (newDeadline.before(new Timestamp(System.currentTimeMillis()))) {
                    outputView.printError("마감일은 현재 시각 이후로 입력해야 합니다.");
                    continue;
                }

                controller.updateProfessorAssignment(assignmentId, professorId, newTitle, newDescription, newDeadline);
                outputView.printMessage("✅ 과제 수정이 완료되었습니다.");
                return;
            }

        } catch (RuntimeException e) {
            outputView.printError(e.getMessage());
        }
    }


    // ============================ 과제 생성 ============================
    private void createAssignment() {
        outputView.printMessage("\n--- 과제 생성 ---");

        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long professorId = (long) loggedInUser.getId();

        while (true) {
            try {
                System.out.print("과제를 생성할 강의 번호를 입력하세요 (취소: 0): ");
                Long courseId = Long.parseLong(sc1.nextLine());

                if (courseId == 0L) {
                    outputView.printMessage("과제 생성을 취소했습니다.");
                    return;
                }

                boolean canCreate = controller.existsProfessorCourse(courseId, professorId);

                if (!canCreate) {
                    outputView.printError("본인이 담당하는 강의가 아닙니다. 다시 입력해주세요.");
                    continue;
                }

                boolean alreadyExists = controller.existsAssignmentByCourse(courseId);

                if (alreadyExists) {
                    outputView.printError("이미 과제가 생성되어 있습니다!");
                    continue;
                }

                System.out.print("과제명을 입력하세요: ");
                String title = sc1.nextLine().trim();
                if (title.isEmpty()) {
                    outputView.printError("과제명은 비워둘 수 없습니다.");
                    continue;
                }

                System.out.print("과제 설명을 입력하세요: ");
                String description = sc1.nextLine().trim();
                if (description.isEmpty()) {
                    outputView.printError("과제 설명은 비워둘 수 없습니다.");
                    continue;
                }

                System.out.print("마감일을 입력하세요 (예: 2026-03-25 23:59:59): ");
                String deadlineInput = sc1.nextLine().trim();

                Timestamp deadline;
                try {
                    deadline = Timestamp.valueOf(deadlineInput);
                } catch (IllegalArgumentException e) {
                    outputView.printError("마감일 형식이 올바르지 않습니다. 예: 2026-03-25 23:59:59");
                    continue;
                } if (deadline.before(new Timestamp(System.currentTimeMillis()))) {
                    outputView.printError("마감일은 현재 시각 이후로 입력해야 합니다.");
                    continue;
                }

                controller.createAssignment(courseId, title, description, deadline);
                outputView.printMessage("✅ 과제 생성이 완료되었습니다.");
                return;

            } catch (NumberFormatException e) {
                outputView.printError("강의 번호는 숫자로 입력해주세요.");
            } catch (RuntimeException e) {
                outputView.printError(e.getMessage());
                return;
            }
        }
    }

    // ====================== 과제 조회 ========================
    private void findAllAssignments() {
        while (true) {
            System.out.println("\n---------------------------------");
            System.out.println("         [과제 조회]");
            System.out.println("---------------------------------");
            System.out.println("1. 생성한 과제 조회");
            System.out.println("2. 학생 제출 현황 조회");
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

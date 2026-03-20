package com.module1.crud.assignments.view;

import com.module1.crud.assignments.controller.AssignmentController;
import com.module1.crud.assignments.model.dto.StudentAssignmentDTO;
import com.module1.crud.assignments.model.dto.StudentAssignmentSubmissionDTO;
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
            outputView.printMessage("\n--- 제출 과제 삭제 ---");

            UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
            Long studentId = (long) loggedInUser.getId();

            while (true) {
                try {
                    System.out.print("삭제할 과제 번호를 입력하세요 (취소: 0): ");
                    Long assignmentId = Long.parseLong(sc.nextLine());

                    if (assignmentId == 0L) {
                        outputView.printMessage("과제 삭제를 취소했습니다.");
                        return;
                    }

                    // 1. 내가 수강 중인 과제인지 확인
                    boolean canSubmit = controller.canSubmitAssignment(assignmentId, studentId);
                    if (!canSubmit) {
                        outputView.printError("없는 과제이거나, 수강 중인 과제가 아닙니다. 다시 입력해주세요.");
                        continue;
                    }

                    // 2. 이미 제출한 과제인지 확인
                    boolean alreadySubmitted = controller.isAlreadySubmitted(assignmentId, studentId);
                    if (!alreadySubmitted) {
                        outputView.printError("아직 제출하지 않은 과제입니다. 삭제할 과제가 없습니다.");
                        continue;
                    }

                    // 3. 삭제 확인
                    System.out.print("정말 삭제하시겠습니까? (Y/N): ");
                    String confirm = sc.nextLine().trim();

                    if (confirm.equalsIgnoreCase("N")) {
                        outputView.printMessage("과제 제출 삭제를 취소했습니다.");
                        return;
                    }

                    if (!confirm.equalsIgnoreCase("Y")) {
                        outputView.printError("Y 또는 N만 입력해주세요.");
                        continue;
                    }

                    // 4. 삭제 실행
                    controller.deleteSubmission(assignmentId, studentId);
                    outputView.printMessage("✅ 과제 제출 삭제가 완료되었습니다.");
                    return;

                } catch (NumberFormatException e) {
                    outputView.printError("과제 번호는 숫자로 입력해주세요.");
                } catch (RuntimeException e) {
                    outputView.printError(e.getMessage());
                    return;
                }
            }

    }

    private void updateSubmission() {
        outputView.printMessage("\n--- 과제 제출 수정 ---");

        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long studentId = (long) loggedInUser.getId();

        while (true) {
            try {
                System.out.print("수정할 과제 번호를 입력하세요 (취소: 0): ");
                Long assignmentId = Long.parseLong(sc.nextLine());

                if (assignmentId == 0L) {
                    outputView.printMessage("제출 수정을 취소했습니다.");
                    return;
                }

                // 1. 내가 수강 중인 과제인지 확인
                boolean canSubmit = controller.canSubmitAssignment(assignmentId, studentId);
                if (!canSubmit) {
                    outputView.printError("없는 과제이거나, 수강 중인 과제가 아닙니다. 다시 입력해주세요.");
                    continue;
                }

                // 2. 이미 제출한 과제인지 확인
                boolean alreadySubmitted = controller.isAlreadySubmitted(assignmentId, studentId);
                if (!alreadySubmitted) {
                    outputView.printError("아직 제출하지 않은 과제입니다. 수정할 수 없습니다.");
                    continue;
                }

                // 3. 새 제출 내용 입력
                System.out.print("새 제출 내용을 입력하세요: ");
                String newContent = sc.nextLine().trim();

                if (newContent.isEmpty()) {
                    outputView.printError("제출 내용은 비워둘 수 없습니다❗.");
                    continue;
                }

                // 4. 수정 실행
                controller.updateSubmission(assignmentId, studentId, newContent);
                outputView.printMessage("✅과제 수정이 완료되었습니다✅.");
                return;

            } catch (NumberFormatException e) {
                outputView.printError("과제 번호는 숫자로 입력해주세요.");
            } catch (RuntimeException e) {
                outputView.printError(e.getMessage());
                return;
            }
        }
    }
    // =========================== 과제 제출 파트 ==========================
    private void createSubmission() {
        outputView.printMessage("\n--- 과제 제출 ---");

        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long studentId = (long) loggedInUser.getId();

        while (true) {
            try {
                System.out.print("제출할 과제 번호를 입력하세요 (취소: 0): ");
                Long assignmentId = Long.parseLong(sc.nextLine());

                if (assignmentId == 0L) {
                    outputView.printMessage("과제 제출을 취소했습니다.");
                    return;
                }

                boolean canSubmit = controller.canSubmitAssignment(assignmentId, studentId);

                if (!canSubmit) {
                    outputView.printError("없는 과제이거나, 수강 중인 과제가 아닙니다. 다시 입력해주세요.");
                    continue;
                }

                boolean alreadySubmitted = controller.isAlreadySubmitted(assignmentId, studentId);

                if (alreadySubmitted) {
                    outputView.printError("이미 제출한 과제입니다.");
                    return;
                }

                System.out.print("제출 내용을 입력하세요: ");
                String content = sc.nextLine();

                StudentAssignmentSubmissionDTO submissionDTO =
                        new StudentAssignmentSubmissionDTO(assignmentId, studentId, content);

                controller.createSubmission(submissionDTO);
                outputView.printMessage("과제 제출이 완료되었습니다.");
                return;

            } catch (NumberFormatException e) {
                outputView.printError("과제 번호는 숫자로 입력해주세요.");
            } catch (RuntimeException e) {
                outputView.printError(e.getMessage());
                return;
            }
        }
    }
    // ======================== 과제 조회 파트 ===========================
    private void findMyAssignments() {
        outputView.printMessage("\n--- 수강 과목 과제 조회 ---");

        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long userId = (long) loggedInUser.getId();

        List<StudentAssignmentDTO> studentAssignmentDTOS = controller.findMyAssignments(userId);
        outputView.printAssignments(studentAssignmentDTOS);
    }

    private int inputInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }
}



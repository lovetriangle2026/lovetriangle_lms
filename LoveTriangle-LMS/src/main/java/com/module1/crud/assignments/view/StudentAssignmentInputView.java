package com.module1.crud.assignments.view;

import com.module1.crud.assignments.controller.AssignmentController;
import com.module1.crud.assignments.model.dto.StudentAssignmentDTO;
import com.module1.crud.assignments.model.dto.StudentAssignmentSubmissionDTO;
import com.module1.crud.assignments.model.dto.SubmissionRankResultDTO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;

import java.util.ArrayList;
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
            System.out.println("0. 이전으로 돌아가기");
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
                case 0:
                    outputView.printMessage("이전 메뉴로 돌아갑니다.");
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }
    }

    private List<StudentAssignmentDTO> getMyAssignments(Long studentId) {
        return controller.findMyAssignments(studentId);
    }

    private List<String> extractCourseTitles(List<StudentAssignmentDTO> assignmentList) {
        List<String> courseTitles = new ArrayList<>();

        for (StudentAssignmentDTO dto : assignmentList) {
            if (!courseTitles.contains(dto.getCourseTitle())) {
                courseTitles.add(dto.getCourseTitle());
            }
        }

        return courseTitles;
    }

    private List<StudentAssignmentDTO> filterAssignmentsByCourseTitle(List<StudentAssignmentDTO> assignmentList, String courseTitle) {
        List<StudentAssignmentDTO> filteredList = new ArrayList<>();

        for (StudentAssignmentDTO dto : assignmentList) {
            if (dto.getCourseTitle().equals(courseTitle)) {
                filteredList.add(dto);
            }
        }

        return filteredList;
    }

    private String selectCourseTitle(List<StudentAssignmentDTO> assignmentList, String actionName) {
        List<String> courseTitles = extractCourseTitles(assignmentList);

        if (courseTitles.isEmpty()) {
            outputView.printError("수강 중인 강의가 없습니다.");
            return null;
        }

        while (true) {
            outputView.printMessage("\n--- " + actionName + "할 강의 선택 ---");
            outputView.printStudentCourseMenu(courseTitles);
            System.out.print("번호를 입력해주세요: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 0) {
                    return null;
                }

                if (choice < 1 || choice > courseTitles.size()) {
                    outputView.printError("목록에 있는 번호만 입력해주세요.");
                    continue;
                }

                return courseTitles.get(choice - 1);

            } catch (NumberFormatException e) {
                outputView.printError("숫자로 입력해주세요.");
            }
        }
    }

    private StudentAssignmentDTO selectAssignment(List<StudentAssignmentDTO> filteredList, String courseTitle, String actionName) {
        if (filteredList == null || filteredList.isEmpty()) {
            outputView.printError("선택한 강의에 등록된 과제가 없습니다.");
            return null;
        }

        while (true) {
            outputView.printMessage("\n--- " + actionName + "할 과제 선택 ---");
            outputView.printStudentAssignmentMenu(courseTitle, filteredList);
            System.out.print("번호를 입력해주세요: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 0) {
                    return null;
                }

                if (choice < 1 || choice > filteredList.size()) {
                    outputView.printError("목록에 있는 번호만 입력해주세요.");
                    continue;
                }

                return filteredList.get(choice - 1);

            } catch (NumberFormatException e) {
                outputView.printError("숫자로 입력해주세요.");
            }
        }
    }

    private void findMyAssignments() {
        outputView.printMessage("\n=== 수강 과목 과제 조회 ===");

        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long studentId = (long) loggedInUser.getId();

        List<StudentAssignmentDTO> assignmentList = getMyAssignments(studentId);

        if (assignmentList == null || assignmentList.isEmpty()) {
            outputView.printError("조회할 과제가 없습니다.");
            return;
        }

        String courseTitle = selectCourseTitle(assignmentList, "조회");
        if (courseTitle == null) {
            outputView.printMessage("과제 조회를 취소했습니다.");
            return;
        }

        List<StudentAssignmentDTO> filteredList = filterAssignmentsByCourseTitle(assignmentList, courseTitle);
        StudentAssignmentDTO selectedAssignment = selectAssignment(filteredList, courseTitle, "조회");

        if (selectedAssignment == null) {
            outputView.printMessage("과제 조회를 취소했습니다.");
            return;
        }

        outputView.printStudentAssignmentDetail(selectedAssignment);
    }

    private void createSubmission() {
        outputView.printMessage("\n=== 과제 제출 ===");

        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long studentId = (long) loggedInUser.getId();

        while (true) {
            List<StudentAssignmentDTO> assignmentList = getMyAssignments(studentId);

            if (assignmentList == null || assignmentList.isEmpty()) {
                outputView.printError("제출할 과제가 없습니다.");
                return;
            }

            String courseTitle = selectCourseTitle(assignmentList, "제출");
            if (courseTitle == null) {
                outputView.printMessage("과제 제출을 취소했습니다.");
                return;
            }

            List<StudentAssignmentDTO> filteredList = filterAssignmentsByCourseTitle(assignmentList, courseTitle);
            StudentAssignmentDTO selectedAssignment = selectAssignment(filteredList, courseTitle, "제출");

            if (selectedAssignment == null) {
                outputView.printMessage("과제 선택을 다시 진행합니다.");
                continue;
            }

            outputView.printStudentAssignmentDetail(selectedAssignment);

            Long assignmentId = selectedAssignment.getId();

            boolean canSubmit = controller.canSubmitAssignment(assignmentId, studentId);
            if (!canSubmit) {
                outputView.printError("없는 과제이거나, 수강 중인 과제가 아닙니다. 다시 선택해주세요.");
                continue;
            }

            boolean alreadySubmitted = controller.isAlreadySubmitted(assignmentId, studentId);
            if (alreadySubmitted) {
                outputView.printError("이미 제출한 과제입니다. 다른 과제를 선택해주세요.");
                continue;
            }

            boolean deadlinePassed = controller.isAssignmentDeadlinePassed(assignmentId);

            while (true) {
                System.out.print("제출 내용을 입력해주세요: ");
                String content = sc.nextLine().trim();

                if (content.isEmpty()) {
                    outputView.printError("제출 내용은 비워둘 수 없습니다.");
                    continue;
                }

                StudentAssignmentSubmissionDTO submissionDTO =
                        new StudentAssignmentSubmissionDTO(assignmentId, studentId, content);

                SubmissionRankResultDTO result = controller.createSubmission(submissionDTO, deadlinePassed);
                outputView.printSubmissionResult(result);
                return;
            }
        }
    }

    private void updateSubmission() {
        outputView.printMessage("\n=== 과제 제출 수정 ===");

        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long studentId = (long) loggedInUser.getId();

        while (true) {
            List<StudentAssignmentDTO> assignmentList = getMyAssignments(studentId);

            if (assignmentList == null || assignmentList.isEmpty()) {
                outputView.printError("수정할 과제가 없습니다.");
                return;
            }

            String courseTitle = selectCourseTitle(assignmentList, "수정");
            if (courseTitle == null) {
                outputView.printMessage("과제 수정을 취소했습니다.");
                return;
            }

            List<StudentAssignmentDTO> filteredList = filterAssignmentsByCourseTitle(assignmentList, courseTitle);
            StudentAssignmentDTO selectedAssignment = selectAssignment(filteredList, courseTitle, "수정");

            if (selectedAssignment == null) {
                outputView.printMessage("과제 선택을 다시 진행합니다.");
                continue;
            }

            outputView.printStudentAssignmentDetail(selectedAssignment);

            Long assignmentId = selectedAssignment.getId();

            boolean canSubmit = controller.canSubmitAssignment(assignmentId, studentId);
            if (!canSubmit) {
                outputView.printError("없는 과제이거나, 수강 중인 과제가 아닙니다. 다시 선택해주세요.");
                continue;
            }

            boolean alreadySubmitted = controller.isAlreadySubmitted(assignmentId, studentId);
            if (!alreadySubmitted) {
                outputView.printError("아직 제출하지 않은 과제입니다. 다른 과제를 선택해주세요.");
                continue;
            }

            boolean deadlinePassed = controller.isAssignmentDeadlinePassed(assignmentId);
            if (deadlinePassed) {
                while (true) {
                    System.out.print("마감일이 지난 과제입니다. 수정하시겠습니까? (Y/N): ");
                    String confirmLateUpdate = sc.nextLine().trim();

                    if (confirmLateUpdate.equalsIgnoreCase("N")) {
                        outputView.printMessage("과제 수정을 취소했습니다.");
                        return;
                    }

                    if (confirmLateUpdate.equalsIgnoreCase("Y")) {
                        break;
                    }

                    outputView.printError("Y 또는 N만 입력해주세요.");
                }
            }

            while (true) {
                System.out.print("수정 내용을 입력해주세요: ");
                String newContent = sc.nextLine().trim();

                if (newContent.isEmpty()) {
                    outputView.printError("제출 내용은 비워둘 수 없습니다.");
                    continue;
                }

                controller.updateSubmission(assignmentId, studentId, newContent);
                outputView.printMessage("✅ 과제 수정이 완료되었습니다.");
                return;
            }
        }
    }

    private void deleteSubmission() {
        outputView.printMessage("\n=== 제출 과제 삭제 ===");

        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long studentId = (long) loggedInUser.getId();

        while (true) {
            List<StudentAssignmentDTO> assignmentList = getMyAssignments(studentId);

            if (assignmentList == null || assignmentList.isEmpty()) {
                outputView.printError("삭제할 과제가 없습니다.");
                return;
            }

            String courseTitle = selectCourseTitle(assignmentList, "삭제");
            if (courseTitle == null) {
                outputView.printMessage("과제 삭제를 취소했습니다.");
                return;
            }

            List<StudentAssignmentDTO> filteredList = filterAssignmentsByCourseTitle(assignmentList, courseTitle);
            StudentAssignmentDTO selectedAssignment = selectAssignment(filteredList, courseTitle, "삭제");

            if (selectedAssignment == null) {
                outputView.printMessage("과제 선택을 다시 진행합니다.");
                continue;
            }

            outputView.printStudentAssignmentDetail(selectedAssignment);

            Long assignmentId = selectedAssignment.getId();

            boolean canSubmit = controller.canSubmitAssignment(assignmentId, studentId);
            if (!canSubmit) {
                outputView.printError("없는 과제이거나, 수강 중인 과제가 아닙니다. 다시 선택해주세요.");
                continue;
            }

            boolean alreadySubmitted = controller.isAlreadySubmitted(assignmentId, studentId);
            if (!alreadySubmitted) {
                outputView.printError("아직 제출하지 않은 과제입니다. 다른 과제를 선택해주세요.");
                continue;
            }

            while (true) {
                boolean deadlinePassed = controller.isAssignmentDeadlinePassed(assignmentId);

                if (deadlinePassed) {
                    System.out.print("마감일이 지난 과제입니다. 삭제하시겠습니까? (Y/N): ");
                } else {
                    System.out.print("정말 삭제하시겠습니까? (Y/N): ");
                }

                String confirm = sc.nextLine().trim();

                if (confirm.equalsIgnoreCase("N")) {
                    outputView.printMessage("과제 제출 삭제를 취소했습니다.");
                    return;
                }

                if (!confirm.equalsIgnoreCase("Y")) {
                    outputView.printError("Y 또는 N만 입력해주세요.");
                    continue;
                }

                controller.deleteSubmission(assignmentId, studentId);
                outputView.printMessage("✅ 과제 제출 삭제가 완료되었습니다.");
                return;
            }
        }
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
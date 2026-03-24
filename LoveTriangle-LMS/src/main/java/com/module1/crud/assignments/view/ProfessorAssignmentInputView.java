package com.module1.crud.assignments.view;

import com.module1.crud.assignments.controller.AssignmentController;
import com.module1.crud.assignments.model.dto.ProfessorAssignmentDTO;
import com.module1.crud.assignments.model.dto.ProfessorAssignmentSubmissionDTO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            System.out.println("0. 이전으로 돌아가기");
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
                case 0:
                    outputView.printMessage("이전 메뉴로 돌아갑니다.");
                    return;
                default:
                    outputView.printError("다시 선택해주세요.");
            }
        }
    }

    private Map<Long, String> getProfessorCourses(Long professorId) {
        return controller.findProfessorCourses(professorId);
    }

    private List<ProfessorAssignmentDTO> getProfessorAssignments(Long professorId) {
        return controller.findAssignmentsByProfessor(professorId);
    }

    private List<ProfessorAssignmentDTO> filterAssignmentsByCourseId(List<ProfessorAssignmentDTO> assignmentList, Long courseId) {
        List<ProfessorAssignmentDTO> filteredList = new ArrayList<>();

        for (ProfessorAssignmentDTO dto : assignmentList) {
            if (dto.getCourseId().equals(courseId)) {
                filteredList.add(dto);
            }
        }

        return filteredList;
    }

    private Long selectCourseId(Map<Long, String> courseMap, String actionName) {
        if (courseMap == null || courseMap.isEmpty()) {
            outputView.printError("담당 중인 강의가 없습니다.");
            return null;
        }

        List<Long> courseIds = new ArrayList<>(courseMap.keySet());

        while (true) {
            outputView.printMessage("\n=== " + actionName + "할 강의 선택 ===");
            outputView.printProfessorCourseMenu(courseMap);
            System.out.print("번호를 입력해주세요: ");

            try {
                int choice = Integer.parseInt(sc1.nextLine());

                if (choice == 0) {
                    return null;
                }

                if (choice < 1 || choice > courseIds.size()) {
                    outputView.printError("목록에 있는 번호만 입력해주세요.");
                    continue;
                }

                return courseIds.get(choice - 1);

            } catch (NumberFormatException e) {
                outputView.printError("숫자로 입력해주세요.");
            }
        }
    }

    private ProfessorAssignmentDTO selectAssignment(List<ProfessorAssignmentDTO> filteredList, String courseTitle, String actionName) {
        if (filteredList == null || filteredList.isEmpty()) {
            outputView.printError("선택한 강의에 등록된 과제가 없습니다.");
            return null;
        }

        while (true) {
            outputView.printMessage("\n=== " + actionName + "할 과제 선택 ===");
            outputView.printProfessorAssignmentMenu(courseTitle, filteredList);
            System.out.print("번호를 입력해주세요: ");

            try {
                int choice = Integer.parseInt(sc1.nextLine());

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

    // ====================== 과제 삭제 ========================
    private void createAssignment() {
        outputView.printMessage("\n=== 과제 생성 ===");

        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long professorId = (long) loggedInUser.getId();

        while (true) {
            try {
                Map<Long, String> courseMap = getProfessorCourses(professorId);

                if (courseMap == null || courseMap.isEmpty()) {
                    outputView.printError("담당 중인 강의가 없습니다.");
                    return;
                }

                Long selectedCourseId = selectCourseId(courseMap, "생성");
                if (selectedCourseId == null) {
                    outputView.printMessage("과제 생성을 취소했습니다.");
                    return;
                }

                String selectedCourseTitle = courseMap.get(selectedCourseId);

                boolean canCreate = controller.existsProfessorCourse(selectedCourseId, professorId);
                if (!canCreate) {
                    outputView.printError("본인이 담당하는 강의가 아닙니다. 다시 선택해주세요.");
                    continue;
                }

                boolean alreadyExists = controller.existsAssignmentByCourse(selectedCourseId);
                if (alreadyExists) {
                    outputView.printError("이미 해당 강의에 과제가 생성되어 있습니다. 다시 선택해주세요.");
                    continue;
                }

                System.out.println("\n선택한 강의");
                System.out.println("강의명 : " + selectedCourseTitle);

                String title;
                while (true) {
                    System.out.print("과제명을 입력하세요: ");
                    title = sc1.nextLine().trim();

                    if (title.isEmpty()) {
                        outputView.printError("과제명은 비워둘 수 없습니다.");
                        continue;
                    }
                    break;
                }

                String description;
                while (true) {
                    System.out.print("과제 내용을 입력하세요: ");
                    description = sc1.nextLine().trim();

                    if (description.isEmpty()) {
                        outputView.printError("과제 내용은 비워둘 수 없습니다.");
                        continue;
                    }
                    break;
                }

                Timestamp deadline;
                while (true) {
                    System.out.print("마감일을 입력하세요 (예: 2026-03-25 23:59:59): ");
                    String deadlineInput = sc1.nextLine().trim();

                    try {
                        deadline = Timestamp.valueOf(deadlineInput);
                    } catch (IllegalArgumentException e) {
                        outputView.printError("마감일 형식이 올바르지 않습니다. 예: 2026-03-25 23:59:59");
                        continue;
                    }

                    if (deadline.before(new Timestamp(System.currentTimeMillis()))) {
                        outputView.printError("마감일은 현재 시각 이후로 입력해야 합니다.");
                        continue;
                    }
                    break;
                }

                controller.createAssignment(selectedCourseId, title, description, deadline);
                outputView.printMessage("✅ 과제 생성이 완료되었습니다.");
                return;

            } catch (RuntimeException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void updateAssignment() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long professorId = (long) loggedInUser.getId();

        while (true) {
            try {
                List<ProfessorAssignmentDTO> assignmentList = getProfessorAssignments(professorId);

                if (assignmentList == null || assignmentList.isEmpty()) {
                    outputView.printError("수정할 과제가 없습니다.");
                    return;
                }

                Map<Long, String> courseMap = getProfessorCourses(professorId);
                Long selectedCourseId = selectCourseId(courseMap, "수정");

                if (selectedCourseId == null) {
                    outputView.printMessage("과제 수정을 취소했습니다.");
                    return;
                }

                String selectedCourseTitle = courseMap.get(selectedCourseId);
                List<ProfessorAssignmentDTO> filteredList = filterAssignmentsByCourseId(assignmentList, selectedCourseId);

                ProfessorAssignmentDTO selectedAssignment = selectAssignment(filteredList, selectedCourseTitle, "수정");

                if (selectedAssignment == null) {
                    outputView.printMessage("과제 선택을 다시 진행합니다.");
                    continue;
                }

                outputView.printProfessorAssignmentDetail(selectedAssignment);

                System.out.print("새 과제명을 입력하세요 (수정하지 않으려면 엔터): ");
                String newTitle = sc1.nextLine().trim();
                if (newTitle.isEmpty()) {
                    newTitle = selectedAssignment.getAssignmentTitle();
                }

                System.out.print("새 과제 내용을 입력하세요 (수정하지 않으려면 엔터): ");
                String newDescription = sc1.nextLine().trim();
                if (newDescription.isEmpty()) {
                    newDescription = selectedAssignment.getDescription();
                }

                Timestamp newDeadline;
                while (true) {
                    System.out.print("새 마감일을 입력하세요 (예: 2026-03-25 23:59:59 / 수정하지 않으려면 엔터): ");
                    String deadlineInput = sc1.nextLine().trim();

                    if (deadlineInput.isEmpty()) {
                        newDeadline = selectedAssignment.getDeadline();
                        break;
                    }

                    try {
                        newDeadline = Timestamp.valueOf(deadlineInput);
                    } catch (IllegalArgumentException e) {
                        outputView.printError("마감일 형식이 올바르지 않습니다. 예: 2026-03-25 23:59:59");
                        continue;
                    }

                    if (newDeadline.before(new Timestamp(System.currentTimeMillis()))) {
                        outputView.printError("마감일은 현재 시각 이후로 입력해야 합니다.");
                        continue;
                    }
                    break;
                }

                controller.updateProfessorAssignment(
                        selectedAssignment.getAssignmentId(),
                        professorId,
                        newTitle,
                        newDescription,
                        newDeadline
                );

                outputView.printMessage("✅ 과제 수정이 완료되었습니다.");
                return;

            } catch (RuntimeException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void deleteAssignment() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long professorId = (long) loggedInUser.getId();

        while (true) {
            try {
                List<ProfessorAssignmentDTO> assignmentList = getProfessorAssignments(professorId);

                if (assignmentList == null || assignmentList.isEmpty()) {
                    outputView.printError("삭제할 과제가 없습니다.");
                    return;
                }

                Map<Long, String> courseMap = getProfessorCourses(professorId);
                Long selectedCourseId = selectCourseId(courseMap, "삭제");

                if (selectedCourseId == null) {
                    outputView.printMessage("과제 삭제를 취소했습니다.");
                    return;
                }

                String selectedCourseTitle = courseMap.get(selectedCourseId);
                List<ProfessorAssignmentDTO> filteredList = filterAssignmentsByCourseId(assignmentList, selectedCourseId);

                ProfessorAssignmentDTO selectedAssignment = selectAssignment(filteredList, selectedCourseTitle, "삭제");

                if (selectedAssignment == null) {
                    outputView.printMessage("과제 선택을 다시 진행합니다.");
                    continue;
                }

                outputView.printProfessorAssignmentDetail(selectedAssignment);

                while (true) {
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

                    controller.deleteProfessorAssignment(selectedAssignment.getAssignmentId(), professorId);
                    outputView.printMessage("✅ 과제 삭제가 완료되었습니다.");
                    return;
                }

            } catch (RuntimeException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    // ====================== 과제 조회 메뉴 ========================
    private void findAllAssignments() {
        while (true) {
            System.out.println("\n=================================");
            System.out.println("         [과제 조회]");
            System.out.println("=================================");
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

        try {
            List<ProfessorAssignmentDTO> assignmentList = getProfessorAssignments(professorId);

            if (assignmentList == null || assignmentList.isEmpty()) {
                outputView.printError("조회할 과제가 없습니다.");
                return;
            }

            Map<Long, String> courseMap = getProfessorCourses(professorId);
            Long selectedCourseId = selectCourseId(courseMap, "제출 현황 조회");

            if (selectedCourseId == null) {
                outputView.printMessage("조회가 취소되었습니다.");
                return;
            }

            String selectedCourseTitle = courseMap.get(selectedCourseId);
            List<ProfessorAssignmentDTO> filteredList = filterAssignmentsByCourseId(assignmentList, selectedCourseId);

            ProfessorAssignmentDTO selectedAssignment = selectAssignment(filteredList, selectedCourseTitle, "제출 현황 조회");

            if (selectedAssignment == null) {
                outputView.printMessage("조회가 취소되었습니다.");
                return;
            }

            outputView.printProfessorAssignmentDetail(selectedAssignment);

            List<ProfessorAssignmentSubmissionDTO> list =
                    controller.findSubmissionStatusByAssignment(selectedAssignment.getAssignmentId(), professorId);

            outputView.printProfessorSubmissionStatus(list);

        } catch (RuntimeException e) {
            outputView.printError(e.getMessage());
        }
    }

    private void findMyAssignmentsOnly() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long professorId = (long) loggedInUser.getId();

        try {
            List<ProfessorAssignmentDTO> assignmentList = getProfessorAssignments(professorId);

            if (assignmentList == null || assignmentList.isEmpty()) {
                outputView.printError("조회할 과제가 없습니다.");
                return;
            }

            Map<Long, String> courseMap = getProfessorCourses(professorId);
            Long selectedCourseId = selectCourseId(courseMap, "조회");

            if (selectedCourseId == null) {
                outputView.printMessage("조회가 취소되었습니다.");
                return;
            }

            String selectedCourseTitle = courseMap.get(selectedCourseId);
            List<ProfessorAssignmentDTO> filteredList = filterAssignmentsByCourseId(assignmentList, selectedCourseId);

            ProfessorAssignmentDTO selectedAssignment = selectAssignment(filteredList, selectedCourseTitle, "조회");

            if (selectedAssignment == null) {
                outputView.printMessage("조회가 취소되었습니다.");
                return;
            }

            outputView.printProfessorAssignmentDetail(selectedAssignment);

        } catch (RuntimeException e) {
            outputView.printError(e.getMessage());
        }
    }

    private int inputInt() {
        while (true) {
            try {
                return Integer.parseInt(sc1.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }
}
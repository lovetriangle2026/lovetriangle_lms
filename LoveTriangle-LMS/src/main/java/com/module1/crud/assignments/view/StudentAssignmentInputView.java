package com.module1.crud.assignments.view;

import com.module1.crud.assignments.controller.AssignmentController;
import com.module1.crud.assignments.model.dto.*;
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
            System.out.println("5. 팀원 리뷰하기 (동료 평가) ");
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
                case 5:
                    createPeerReview();
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

    private void createPeerReview() {
        outputView.printMessage("\n--- 팀원 동료 평가 (Peer Review) ---");
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
        Long studentId = (long) loggedInUser.getId();

        // 1. [변경] "팀플 전용 과제" 목록만 가져오기
        List<StudentTeamAssignmentDTO> assignmentList = controller.findMyTeamAssignments(studentId);
        if (assignmentList == null || assignmentList.isEmpty()) {
            outputView.printError("참여 중인 팀 프로젝트 과제가 없습니다.");
            return;
        }

        // 2. 강의 및 팀플 과제 선택 로직 구성
        System.out.println("\n=== [ 평가할 팀플 과제 선택 ] ===");
        for (int i = 0; i < assignmentList.size(); i++) {
            StudentTeamAssignmentDTO dto = assignmentList.get(i);
            System.out.println((i + 1) + ". [" + dto.getCourseTitle() + "] " + dto.getTitle() + " (마감: " + dto.getDeadline() + ")");
        }
        System.out.println("0. 뒤로가기");
        System.out.print("▶ 번호 입력: ");

        int assignChoice = inputInt();
        if (assignChoice == 0) return;
        if (assignChoice < 1 || assignChoice > assignmentList.size()) {
            outputView.printError("올바른 번호를 선택해주세요.");
            return;
        }

        Long assignmentId = assignmentList.get(assignChoice - 1).getId();
        String assignmentTitle = assignmentList.get(assignChoice - 1).getTitle();

        // 3. 평가할 팀원 목록 불러오기 (본인 제외, 이미 평가한 사람 제외)
        List<TeamMemberDTO> teamMembers = controller.findTeamMembers(studentId, assignmentId);
        if (teamMembers.isEmpty()) {
            System.out.println("---------------------------------------------------------");
            System.out.println("🌟 [" + assignmentTitle + "] 과제에 대한 모든 팀원 평가를 완료했거나 팀원이 없습니다!");
            System.out.println("---------------------------------------------------------");
            return;
        }

        // 4. 평가할 팀원 선택
        while (true) {
            System.out.println("\n=== [ 평가 대상 팀원 선택 ] ===");
            for (int i = 0; i < teamMembers.size(); i++) {
                System.out.println((i + 1) + ". " + teamMembers.get(i).getName());
            }
            System.out.println("0. 뒤로가기");
            System.out.print("▶ 번호 입력: ");

            int memberChoice = inputInt();
            if (memberChoice == 0) return;
            if (memberChoice < 1 || memberChoice > teamMembers.size()) {
                outputView.printError("올바른 번호를 선택해주세요.");
                continue;
            }

            Long revieweeId = teamMembers.get(memberChoice - 1).getStudentId();
            String revieweeName = teamMembers.get(memberChoice - 1).getName();

            // 5. 태그 조회 및 선택 (기존 정규식 로직 동일 유지)
            List<HeartTagDTO> tags = controller.findAllHeartTags();
            System.out.println("\n=== [ 💖 " + revieweeName + " 님에게 칭찬 태그 부여 (최대 3개) ] ===");
            for (HeartTagDTO tag : tags) {
                System.out.println(tag.getId() + ". " + tag.getTagName() + " - " + tag.getDescription());
            }
            System.out.println("---------------------------------------------------------");
            System.out.println("부여할 태그 번호를 쉼표(,)로 구분하여 입력하세요. (건너뛰려면 엔터)");
            System.out.print("▶ 입력 예시 (1,3,5): ");

            String tagInput = sc.nextLine().trim();
            List<Integer> selectedTagIds = new ArrayList<>();

            if (!tagInput.isEmpty()) {
                if (!tagInput.matches("^[0-9,\\s]+$")) {
                    outputView.printError("숫자와 쉼표(,)만 입력 가능합니다.");
                    continue;
                }

                String[] splitTags = tagInput.split(",");
                if (splitTags.length > 3) {
                    outputView.printError("태그는 최대 3개까지만 선택 가능합니다. 욕심내지 마세요!");
                    continue;
                }

                for (String t : splitTags) {
                    if(!t.trim().isEmpty()){
                        selectedTagIds.add(Integer.parseInt(t.trim()));
                    }
                }
            }

            // 6. DB 트랜잭션 저장
            try {
                controller.submitPeerReview(assignmentId, studentId, revieweeId, selectedTagIds);
                outputView.printMessage("✅ " + revieweeName + " 님에 대한 동료 평가가 성공적으로 등록되었습니다!");
                return; // 성공 시 메뉴로 깔끔하게 복귀
            } catch (RuntimeException e) {
                outputView.printError(e.getMessage());
                return;
            }
        }
    }
}
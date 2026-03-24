package com.module1.crud.users.view;
import com.module1.crud.assignments.view.ProfessorAssignmentInputView;
import com.module1.crud.assignments.view.StudentAssignmentInputView;
import com.module1.crud.attendance.view.ProfessorAttendanceInputView;
import com.module1.crud.attendance.view.StudentAttendanceInputView;
import com.module1.crud.auth.find.view.FindAccountView;
import com.module1.crud.course.view.ProfInputView;
import com.module1.crud.course.view.StudentCourseInputView;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.controller.UsersController;
import com.module1.crud.users.model.dto.HeartStatsDTO;
import com.module1.crud.users.model.dto.UsersDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
public class UsersInputView {

    // [1] 필드 추가
// [1] 필드 추가 (총 9개의 View 연동)
    private final UsersController controller;
    private final UsersOutputView outputView;
    private final FindAccountView findAccountView;
    private final StudentCourseInputView studentCourseInputView;
    private final StudentAssignmentInputView studentAssignmentInputView;
    private final StudentAttendanceInputView studentAttendanceInputView;
    private final ProfessorAssignmentInputView profAssignmentInputView;
    private final ProfessorAttendanceInputView profAttendanceInputView;

    // ⭐ 교수 강의 관리 뷰 추가
    private final ProfInputView profInputView;

    private final Scanner sc = new Scanner(System.in);

    // [2] 생성자 수정 (9개의 인자 주입)
    public UsersInputView(UsersController usersController,
                          UsersOutputView outputView,
                          StudentCourseInputView studentCourseInputView,
                          FindAccountView findAccountView,
                          StudentAssignmentInputView studentAssignmentInputView,
                          StudentAttendanceInputView studentAttendanceInputView,
                          ProfessorAssignmentInputView profAssignmentInputView,
                          ProfessorAttendanceInputView profAttendanceInputView,
                          ProfInputView profInputView) { // ⭐ 인자 추가
        this.controller = usersController;
        this.outputView = outputView;
        this.studentCourseInputView = studentCourseInputView;
        this.findAccountView = findAccountView;
        this.studentAssignmentInputView = studentAssignmentInputView;
        this.studentAttendanceInputView = studentAttendanceInputView;
        this.profAssignmentInputView = profAssignmentInputView;
        this.profAttendanceInputView = profAttendanceInputView;
        this.profInputView = profInputView; // ⭐ 의존성 주입 완료
    }


    // 2. 학생 전용 메인 페이지 (기존 코드를 리팩토링)
    public void stutMainPage(UsersDTO user) {
        while (true) {
            // [추가] 루프가 돌 때마다 DB에서 최신 하트 통계 및 공개 여부를 가져옵니다.
            HeartStatsDTO stats = controller.getMyHeartStats(user.getId());
            String publicStatus = (user.getIsHeartPublic() == 1) ? "🟢 공개" : "🔴 비공개";

            System.out.println("\n================ [ 학생 메인 메뉴 ] ================");
            System.out.println("      ━━━━━━━");
            System.out.println("     ┃  ●  ●  ┃");
            System.out.println("     ┃    ▽   ┃  [ " + user.getName() + " ] 님 환영합니다.");
            System.out.println("      ━━━━━━━    ID: " + user.getLoginId() + " (" + user.getUserType() + ")");
            System.out.println("                 Code: " + user.getUserCode());

            // ------------------ [신규] 하트 및 태그 통계 영역 ------------------
            System.out.println(" -----------------------------------------------------");
            System.out.println(" 🔒 타인에게 공개 여부 : " + publicStatus);
            System.out.println(" 💖 누적 하트 수 : " + stats.getTotalHearts() + "개");
            System.out.println(" 🏆 [나의 핵심 강점 태그 Top 3]");
            if (stats.getTop3Tags().isEmpty()) {
                System.out.println("    아직 받은 태그가 없습니다. 이번 과제에서 활약해보세요!");
            } else {
                for (int i = 0; i < stats.getTop3Tags().size(); i++) {
                    System.out.println("    " + (i + 1) + ". " + stats.getTop3Tags().get(i));
                }
            }
            // -------------------------------------------------------------------

            System.out.println("======================================================");
            System.out.println(" 1. 회원정보 수정");
            System.out.println(" 2. 수강내역 조회");
            System.out.println(" 3. 나의 과제 조회");
            System.out.println(" 4. 나의 출결 조회");
            System.out.println(" 9. 회원 탈퇴 ");
            System.out.println(" 0. 로그아웃 및 메인메뉴로 돌아가기");
            System.out.println("======================================================");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    updateUserInfo(user);
                    break;
                case "2": studentCourseInputView.displayStudentMenu(); break;
                case "3": studentAssignmentInputView.displaymainmenu(); break;
                case "4": studentAttendanceInputView.displayMenu(); break;
                case "9":
                    deleteMyAccount(user.getId());
                    return;
                case "0":
                    System.out.println("메인메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }


    // 3. 교수 전용 메인 페이지 (신규 생성)
    public void profMainPage(UsersDTO user) {
        while (true) {
            System.out.println("\n================ [ 교수 메인 메뉴 ] ================");
            System.out.println("      ━━━━━━━");
            System.out.println("     ┃  ■  ■  ┃");
            System.out.println("     ┃   ──   ┃  [ " + user.getName() + " ] 교수님 환영합니다.");
            System.out.println("      ━━━━━━━    ID: " + user.getLoginId() + " (" + user.getUserType() + ")");
            System.out.println("                 Code: " + user.getUserCode());
            System.out.println("======================================================");
            System.out.println(" 1. 회원정보 수정");
            System.out.println(" 2. 강의내역 조회");
            System.out.println(" 3. 과제 관리 이동");
            System.out.println(" 4. 출결 관리 이동");
            System.out.println(" 9. 회원 탈퇴 ");
            System.out.println(" 0. 뒤로가기");
            System.out.println("======================================================");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    updateUserInfo(user);
                    break; // ⭐ 기존 코드에 누락되어 있던 break 추가!
                case "2":
                    profInputView.displayProfessorCourseMenu();

                case "3":
                    profAssignmentInputView.displayMainmenu(); // ⭐ 과제 관리 연결
                    break;
                case "4":
                    profAttendanceInputView.displayMenu(); // ⭐ 출결 관리 연결
                    break;
                case "9":
                    deleteMyAccount(user.getId());
                    return;
                case "0":
                    System.out.println("메인메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }



    private void deleteMyAccount(int loggedInUserId) {
        System.out.println("\n=== 🗑️ 회원 탈퇴 ===");
        System.out.print("정말 탈퇴하시겠습니까? 탈퇴 시 모든 데이터가 삭제됩니다. (Y/N) : ");
        String answer = sc.nextLine();

        if (answer.equalsIgnoreCase("Y")) {
            outputView.printSuccess("회원 탈퇴가 완료되었습니다. 그동안 이용해 주셔서 감사합니다.");
            SessionManager.getInstance().clearSession();
        } else {
            System.out.println("탈퇴를 취소하고 이전 화면으로 돌아갑니다.");
        }
    }

    // 회원정보 수정 로직
    private void updateUserInfo(UsersDTO user) {
        while (true) {
            String publicStatus = (user.getIsHeartPublic() == 1) ? "공개" : "비공개";

            System.out.println("\n================ [ 회원정보 수정 ] ================");
            System.out.println(" [현재 내 정보]");
            System.out.println(" - 이름: " + user.getName());
            System.out.println(" - 전화번호: " + user.getTelNum());
            System.out.println(" - 비밀번호 찾기 답: " + user.getPwAnswer());
            System.out.println(" - 하트 공개 여부: " + publicStatus); // ⭐ UI 추가
            System.out.println(" ※ ID, 학번/사번, 생년월일, 소속(" + user.getUserType() + ")은 변경할 수 없습니다.");
            System.out.println("======================================================");
            System.out.println(" 1. 비밀번호 변경");
            System.out.println(" 2. 전화번호 변경");
            System.out.println(" 3. 이름 변경");
            System.out.println(" 4. 비밀번호 힌트 답 변경");
            System.out.println(" 5. 하트 공개 여부 변경"); // ⭐ 메뉴 추가
            System.out.println(" 0. 수정 종료 및 돌아가기");
            System.out.println("======================================================");
            System.out.print("▶ 수정할 항목 선택: ");

            String choice = sc.nextLine();

            if (choice.equals("0")) {
                System.out.println("회원정보 수정을 종료합니다.");
                return;
            }

            // 기존 정보를 담은 복사본을 만들어 수정할 부분만 덮어씌웁니다.
            UsersDTO updatedUser = new UsersDTO(
                    user.getId(), user.getUserCode(), user.getLoginId(), user.getName(),
                    user.getBirth(), user.getTelNum(), user.getPassword(), user.getPwAnswer(), user.getUserType(), user.getIsHeartPublic()
            );

            switch (choice) {
                case "1":
                    boolean isChanged = findAccountView.loggedResetPassword(user.getLoginId());
                    if (isChanged) {
                        UsersDTO latestUser = controller.getUserInfo(user.getLoginId());
                        SessionManager.getInstance().setLoggedInUser(latestUser);
                        user = latestUser;
                        System.out.println("✅ 회원 정보가 최신화되었습니다.");
                    }
                    continue;
                case "2":
                    System.out.print("새 전화번호 입력: ");
                    updatedUser.setTelNum(sc.nextLine());
                    break;
                case "3":
                    System.out.print("새 이름 입력: ");
                    updatedUser.setName(sc.nextLine());
                    break;
                case "4":
                    System.out.print("새 비밀번호 힌트 답 입력: ");
                    updatedUser.setPwAnswer(sc.nextLine());
                    break;
                case "5": // ⭐ 하트 공개 여부 토글 로직 이관
                    boolean success = controller.toggleHeartPublic(user.getId(), user.getIsHeartPublic());
                    if (success) {
                        int nextStatus = (user.getIsHeartPublic() == 1) ? 0 : 1;
                        user.setIsHeartPublic(nextStatus); // 현재 View의 객체 업데이트
                        SessionManager.getInstance().setLoggedInUser(user); // 세션 동기화
                        System.out.println("✅ 하트 공개 여부가 성공적으로 [" + (nextStatus == 1 ? "공개" : "비공개") + "] 로 변경되었습니다.");
                    } else {
                        System.out.println("🚨 변경에 실패했습니다.");
                    }
                    // 개별 API(updateHeartPublic)를 이미 호출했으므로,
                    // 하단의 통합 updateUser 로직을 타지 않게 continue 처리합니다.
                    continue;
                default:
                    System.out.println("🚨 잘못된 입력입니다.");
                    continue;
            }

            // [기존 통합 업데이트 로직 (2, 3, 4번 용도)]
            boolean isSuccess = controller.updateUser(updatedUser);

            if (isSuccess) {
                System.out.println("✅ 정보가 성공적으로 수정되었습니다.");
                // DB가 수정되었으므로 세션 동기화
                SessionManager.getInstance().setLoggedInUser(updatedUser);
                // 현재 while문을 돌고 있는 user 객체도 명시적으로 업데이트
                user.setName(updatedUser.getName());
                user.setTelNum(updatedUser.getTelNum());
                user.setPwAnswer(updatedUser.getPwAnswer());
            } else {
                System.out.println("🚨 정보 수정에 실패했습니다.");
            }
        }
    }




}

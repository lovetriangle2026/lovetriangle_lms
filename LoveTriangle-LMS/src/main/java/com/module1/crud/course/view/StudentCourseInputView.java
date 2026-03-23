package com.module1.crud.course.view;

import com.module1.crud.course.controller.CourseController;
import com.module1.crud.course.controller.SessionController;
import com.module1.crud.course.model.dto.CourseDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.module1.crud.course.model.dto.CourseStudentStatsDTO;
import com.module1.crud.course.model.dto.SessionDTO;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;
public class StudentCourseInputView {

    private final CourseController controller;
    private final StudentCourseOutputView outputView;
    private final Scanner sc = new Scanner(System.in);
    private final SessionController sessionController;

    // 생성자를 통한 final 변수 초기화
    public StudentCourseInputView(CourseController controller, StudentCourseOutputView outputView,SessionController sessionController) {
        this.controller = controller;
        this.outputView = outputView;
        this.sessionController = sessionController;
    }

    public void displayStudentMenu() {
        // 1. 스캐너는 메서드 밖의 필드를 쓰거나, 여기서 한 번만 선언하세요.
        while (true) {
            System.out.println("\n============ 🎓 학생 수강관리 메뉴 ============");
            System.out.println("1. 전체 강의 목록 조회");
            System.out.println("2. 수강 강의 조회");
            System.out.println("3. 수강 신청");
            System.out.println("4. 주차별 강의 내용 조회");
            System.out.println("0. 이전으로 돌아가기");
            System.out.print("번호 선택 : ");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine(); // 버퍼 비우기 (중요!)
            } catch (Exception e) {
                System.out.println("⚠️ 숫자만 입력해주세요.");
                sc.nextLine(); // 잘못된 입력 비우기
                continue;
            }

            if (choice == 1) {
                displayAllCourse(); // 실행 후 다시 while문 처음으로 돌아감
            } else if (choice == 2) {
                displayEnrollableCourse();
            } else if (choice == 3) {
                enrollCourse();
            } else if (choice == 4) {
                displaySessionsByMyCourse();
            } else if (choice == 0) {
                System.out.println("이전 메뉴로 돌아갑니다.");
                break; // 🚀 break를 해야만 while 루프를 빠져나갑니다.
            } else {
                System.out.println("잘못된 번호입니다. 다시 선택해주세요.");
            }
        }
    }

    private void displayEnrollableCourse() {
        UsersDTO user = SessionManager.getInstance().getLoggedInUser();

        if (user == null) {
            System.out.println("🚨 로그인 정보가 없습니다.");
            return;
        }

        int userId = user.getId();

        // 1. 내 수강 강의 목록 출력 (기존 로직 유지)
        List<CourseDTO> myCourseList = controller.findMyCourses(userId);
        outputView.printCourses(myCourseList);

        if (myCourseList == null || myCourseList.isEmpty()) {
            return; // 수강 중인 강의가 없으면 종료
        }

        // 2. [신규] 통계를 볼 강의 선택하기
        System.out.print("\n👉 수강생들의 동료 평가(하트/태그)를 조회할 강의 번호(ID)를 입력하세요 (취소: 0) : ");
        int courseId;
        try {
            courseId = Integer.parseInt(sc.nextLine());
            if (courseId == 0) return;
        } catch (NumberFormatException e) {
            System.out.println("⚠️ 숫자만 입력해주세요.");
            return;
        }

        // 3. [신규] 선택한 강의의 수강생 통계 출력
        List<CourseStudentStatsDTO> statsList = controller.findStudentStatsByCourse(courseId);

        System.out.println("\n================ [ 수강생 동료 평가 요약 ] ================");
        if (statsList.isEmpty()) {
            System.out.println("해당 강의에 수강생이 없거나 정보를 불러올 수 없습니다.");
        } else {
            for (CourseStudentStatsDTO stats : statsList) {
                System.out.println(" 👤 이름 : " + stats.getStudentName());

                // 프라이버시 보호 로직: 타인이면서 비공개 설정한 경우 숨김 처리
                if (stats.getIsHeartPublic() == 0 && stats.getStudentId() != userId) {
                    System.out.println("   🔒 [비공개] 해당 수강생이 평가 공개를 비활성화했습니다.");
                } else {
                    System.out.println("   💖 누적 하트 수 : " + stats.getTotalHearts() + "개");
                    System.out.print("   🏆 핵심 태그 : ");
                    if (stats.getTop3Tags().isEmpty()) {
                        System.out.println("아직 받은 태그가 없습니다.");
                    } else {
                        System.out.println(String.join(", ", stats.getTop3Tags()));
                    }
                }
                System.out.println("---------------------------------------------------------");
            }
        }
    }

    private void displayAllCourse() {
        outputView.printMessage("\n============강좌 목록 전체 조회 ============");
        List<CourseDTO> courseList = controller.findAllCourses(); // 데이터만 가져옴
        outputView.printCourses(courseList); // 여기서 예쁘게 출력!
    }
    //여기부터 본인이 신청한 강의 조회 기능
    private void FindMyCourses(int userId) throws SQLException {

        outputView.printMessage("============ 내가 신청한 강의 목록 조회 ============");
        // 1. 컨트롤러를 불러서 내 강의 목록 가져오기
        List<CourseDTO> myCourseList = controller.findMyCourses(userId);

        // 2. 출력 담당(outputView)에게 목록을 보여달라시키기
        outputView.printCourses(myCourseList);

    }

    private void enrollCourse() {
        UsersDTO user = SessionManager.getInstance().getLoggedInUser();
        if (user == null) {
            System.out.println("🚨 로그인 정보가 없습니다.");
            return;
        }

        int userId = (int) user.getId();

        // 1. 현재 내가 수강 중인 강의 목록 먼저 보여주기
        List<CourseDTO> myCourses = controller.findMyCourses(userId);
        System.out.println("\n[ 📋 현재 내가 수강 중인 강의 ]");
        if (myCourses == null || myCourses.isEmpty()) {
            System.out.println("-> 아직 신청한 강의가 없습니다.");
        } else {
            // 기존에 만든 예쁜 출력 메서드 활용 (혹은 간단히 제목만 출력)
            for (CourseDTO myCourse : myCourses) {
                System.out.printf("- [%d] %s\n", myCourse.getId(), myCourse.getTitle());
            }
        }

        // 2. 신청 가능한 전체 강의 목록 보여주기
        System.out.println("\n[ 🌐 신청 가능한 전체 강의 목록 ]");
        List<CourseDTO> allCourses = controller.findAllCourses();
        outputView.printCourses(allCourses); // 아까 만든 표 형식 출력

        if (allCourses == null || allCourses.isEmpty()) {
            System.out.println("🚨 현재 개설된 강의가 없습니다.");
            return;
        }

        // 3. 안전한 숫자 입력 받기 (Try-Catch)
        int courseId = -1;
        while (true) {
            try {
                System.out.print("\n👉 수강신청할 강의 번호(ID)를 입력하세요 (취소: 0) : ");
                String input = sc.nextLine();
                courseId = Integer.parseInt(input);

                if (courseId == 0) return;
                break;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ 숫자만 입력 가능합니다. 다시 입력해 주세요.");
            }
        }

        // 4. 수강 신청 로직 실행
        boolean result = controller.enrollCourse(userId, courseId);

        if (result) {
            System.out.println("✨ [성공] 수강신청이 완료되었습니다!");
        } else {
            System.out.println("❌ [실패] 이미 신청한 강의이거나 잘못된 번호입니다.");
        }
    }

    private void displaySessionsByMyCourse() {
        UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();

        if (loggedInUser == null) {
            System.out.println("로그인 정보가 없습니다.");
            return;
        }

        int studentId = (int) loggedInUser.getId();
        List<CourseDTO> myCourses = controller.findMyCourses(studentId);

        if (myCourses == null || myCourses.isEmpty()) {
            System.out.println("수강 중인 강의가 없습니다.");
            return;
        }

        System.out.println("\n============수강 중인 강의 목록 ============");
        for (int i = 0; i < myCourses.size(); i++) {
            System.out.println((i + 1) + ". " + myCourses.get(i).getTitle());
        }

        System.out.print("강의 번호 선택 : ");

        int courseNumber;
        try {
            courseNumber = sc.nextInt();
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("강의 번호는 숫자로 입력해야 합니다.");
            sc.nextLine();
            return;
        }

        if (courseNumber < 1 || courseNumber > myCourses.size()) {
            System.out.println("잘못된 강의 번호입니다.");
            return;
        }

        CourseDTO selectedCourse = myCourses.get(courseNumber - 1);

        List<SessionDTO> sessionList =
                sessionController.findSessionsByCourse(selectedCourse.getId().intValue());

        outputView.displaySessionList(sessionList);
    }

}

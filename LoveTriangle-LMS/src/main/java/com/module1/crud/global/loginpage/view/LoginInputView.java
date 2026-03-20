package com.module1.crud.global.loginpage.view;

import com.module1.crud.assignments.view.ProfessorAssignmentInputView;
import com.module1.crud.assignments.view.StudentAssignmentInputView;
import com.module1.crud.attendance.view.ProfessorAttendanceInputView;
import com.module1.crud.attendance.view.StudentAttendanceInputView;
import com.module1.crud.course.view.StudentCourseInputView;
import com.module1.crud.global.loginpage.controller.LoginController;
import com.module1.crud.grade.view.ProfessorGradeInputView;
import com.module1.crud.global.session.SessionManager;
import com.module1.crud.users.model.dto.UsersDTO;
import com.module1.crud.grade.view.StudentGradeInputView;
import com.module1.crud.users.view.UsersInputView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class LoginInputView {
        private final Scanner sc = new Scanner(System.in);
        private final LoginController controller;
        private final LoginOutputView outputView;
        private final UsersInputView usersInputView;

        // 추가된 의존성 필드 선언 (명확한 네이밍 적용)
        private final StudentAssignmentInputView studentAssignmentInputView;
        private final ProfessorAssignmentInputView professorAssignmentInputView;
        private final ProfessorAttendanceInputView professorAttendanceInputView;
        private final StudentAttendanceInputView studentAttendanceInputView;
        private final StudentGradeInputView studentGradeInputView;
        private final ProfessorGradeInputView professorGradeInputView;
        private final StudentCourseInputView studentCourseInputView;

        public LoginInputView(
                LoginController controller,
                LoginOutputView outputView,
                UsersInputView usersInputView,
                StudentAssignmentInputView studentAssignmentInputView,
                ProfessorAssignmentInputView professorAssignmentInputView,
                ProfessorAttendanceInputView professorAttendanceInputView,
                StudentAttendanceInputView studentAttendanceInputView,
                StudentCourseInputView studentCourseInputView,
                StudentGradeInputView studentGradeInputView,
                ProfessorGradeInputView professorGradeInputView) {

            this.controller = controller;
            this.outputView = outputView;
            this.usersInputView = usersInputView;

            // 추가된 객체 초기화
            this.studentAssignmentInputView = studentAssignmentInputView;
            this.professorAssignmentInputView = professorAssignmentInputView;
            this.professorAttendanceInputView = professorAttendanceInputView;
            this.studentAttendanceInputView = studentAttendanceInputView;
            this.studentGradeInputView = studentGradeInputView;
            this.professorGradeInputView = professorGradeInputView;
            this.studentCourseInputView = studentCourseInputView;
        }


    // 1. 시스템 시작 (최초 진입점)
    public void displayStartMenu() {
        while (true) {
            System.out.println("\n========= [LMS 시스템 시작] =========");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("0. 프로그램 종료");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine(); // nextInt() 대신 nextLine() 사용

            switch (choice) {
                case "1":
                    loginMenu(); // 로그인 화면으로 이동
                    break;
                case "2":
                    createUser2();
                    break;
                case "0":
                    System.out.println("LMS 시스템을 종료합니다. 안녕히 가세요!");
                    return; // while 루프를 빠져나가 프로그램 종료
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    private void loginMenu() {
        while (true) { // 💡 무한 루프 시작
            System.out.println("\n--------- [로그인] ---------");
            System.out.println("(이전 메뉴로 돌아가려면 '0'을 입력하세요)");

            System.out.print("ID: ");
            String id = sc.nextLine();

            // 탈출 로직: ID 입력 단계에서 0을 누르면 종료
            if ("0".equals(id)) {
                System.out.println("초기 메뉴로 돌아갑니다.");
                return;
            }

            System.out.print("PW: ");
            String pw = sc.nextLine();

            // 1. Controller를 통해 실제 DB 로그인 수행
            boolean loginSuccess = controller.login(id, pw);

            if (loginSuccess) {
                UsersDTO loggedInUser = SessionManager.getInstance().getLoggedInUser();
                String userType = loggedInUser.getUserType();

                System.out.println("✅ [" + loggedInUser.getName() + "]님 환영합니다! (" + userType + ")");

                // 2. 권한에 따른 화면 분기 처리
                if ("STUDENT".equalsIgnoreCase(userType)) {
                    studentMainMenu(loggedInUser);
                } else if ("PROFESSOR".equalsIgnoreCase(userType)) {
                    professorMainMenu(loggedInUser);
                } else {
                    System.out.println("🚨 알 수 없는 권한입니다.");
                    SessionManager.getInstance().clearSession();
                }

                // 💡 로그인이 성공해서 메뉴에 들어갔다가 '로그아웃'해서 나오면
                // 이 loginMenu 메서드도 완전히 종료시켜서 '시작 메뉴'로 보내야 함
                return;

            } else {
                // 로그인 실패 시 루프가 돌면서 다시 입력을 받게 됨
                outputView.printError("로그인 실패: 아이디 또는 비밀번호가 일치하지 않습니다. 다시 시도해 주세요.");
            }
        }
    }




    public void createUser2() {
// 💡 핵심 1: 2단계(계정 생성)에서도 재사용해야 하므로 변수들을 마스터 루프 밖에서 미리 선언합니다.
        String userCode = "";
        String name = "";
        LocalDate birth = null;
        String userType = "";

        System.out.println("\n=== 📝 신규 회원 가입 ===");

        // 💡 핵심 2: 1단계 전체를 감싸는 '마스터 루프'를 엽니다. (NOT_FOUND 시 여기로 돌아옴)
        while (true) {
            System.out.println("\n[1단계: 본인 인증 절차 (학적 검증)]");

            while (true) {
                System.out.print("사번/학번(User Code)을 입력해주세요 (예: S202604) : ");
                userCode = sc.nextLine();
                if (userCode.matches("^[A-Za-z]\\d{6}$")) break;
                System.out.println("🚨 잘못된 형식입니다. 영문자 1자리 + 숫자 6자리로 입력해주세요.");
            }

            while (true) {
                System.out.print("이름을 입력해주세요 : ");
                name = sc.nextLine();
                if (name.matches("^[가-힣A-Za-z]+$")) break;
                System.out.println("🚨 잘못된 형식입니다. 이름은 한글 또는 영문만 입력 가능합니다.");
            }

            while (true) {
                System.out.print("생년월일 (YYYY-MM-DD 형식)을 입력해주세요 : ");
                String birthInput = sc.nextLine();
                if (birthInput.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                    try {
                        birth = LocalDate.parse(birthInput);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("🚨 달력에 존재하지 않는 날짜입니다. 다시 확인해주세요.");
                    }
                } else {
                    System.out.println("🚨 잘못된 형식입니다. YYYY-MM-DD (예: 1999-01-01) 형식으로 입력해주세요.");
                }
            }

            // 💡 핵심 3: 사용자 유형도 오타 방지를 위해 무한 루프 + 숫자 선택 방식으로 변경
            while (true) {
                System.out.print("사용자 유형을 선택해주세요 (1. 학생 / 2. 교수) : ");
                String typeInput = sc.nextLine();
                if (typeInput.equals("1")) {
                    userType = "STUDENT";
                    break;
                } else if (typeInput.equals("2")) {
                    userType = "PROFESSOR";
                    break;
                }
                System.out.println("🚨 잘못된 입력입니다. 숫자 1 또는 2를 입력해주세요.");
            }


            // DB 검증을 Service로 요청
            String status = controller.verifyUser(userCode, name, birth, userType);

            System.out.println("status = " + status);

            if (status.equals("NOT_FOUND")) {
                System.out.println("🚨 등록된 학사 정보가 없습니다. 이름, 학번 등을 확인하고 처음부터 다시 입력해주세요.\n");
                continue; // 💡 가장 바깥쪽의 마스터 루프(학번 입력 부분)로 돌아가서 재시작

            } else if (status.equals("ALREADY_REGISTERED")) {
                System.out.println("🚨 이미 아이디가 존재하는 가입된 사용자입니다. 메인 메뉴에서 로그인을 진행해주세요.");
                return; // 💡 회원가입 메서드 자체를 완전히 종료하고 메인으로 복귀

            } else if (status.equals("ERROR")) {
                System.out.println("🚨 시스템 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
                return; // 💡 회원가입 메서드 종료

            } else if (status.equals("AVAILABLE")) {
                System.out.println("✅ 본인 인증이 완료되었습니다. 다음 가입 절차를 진행합니다.");
                break; // 💡 마스터 루프를 탈출하여 아래의 2단계 로직으로 넘어감
            }
        }

        // ---------------------------------------------------------
        // [2단계: 계정 정보 입력 및 생성]
        // ---------------------------------------------------------
        // 여기서부터 무사히 넘어온 userCode, name, birth, userType을 가지고
        // loginId, password 등을 마저 입력받습니다.

        String loginId = "";
        while (true) {
            System.out.print("사용할 로그인 ID를 입력해주세요 (영문/숫자 조합) : ");
            loginId = sc.nextLine();
            // 💡 영문 대소문자와 숫자만 허용 (최소 1자 이상)
            if (loginId.matches("^[A-Za-z0-9]+$")) break;
            System.out.println("🚨 잘못된 형식입니다. ID는 영문과 숫자만 사용할 수 있습니다.");
        }

        String password = "";
        while (true) {
            System.out.print("비밀번호를 입력해주세요 (영문, 숫자, 특수문자만 가능) : ");
            password = sc.nextLine();
            // 💡 영문, 숫자, 특수문자(!@#$%^&*()_+-= 등)만 허용
            if (password.matches("^[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+$")) break;
            System.out.println("🚨 잘못된 형식입니다. 비밀번호는 영문, 숫자, 특수문자만 포함해야 합니다.");
        }

        String telNum = "";
        while (true) {
            System.out.print("전화번호를 입력해주세요 (010-XXXX-XXXX 형식) : ");
            telNum = sc.nextLine();
            // 💡 정확히 010-숫자4자리-숫자4자리 형식만 허용
            if (telNum.matches("^010-\\d{4}-\\d{4}$")) break;
            System.out.println("🚨 잘못된 형식입니다. 010-1234-5678 형식으로 중간에 하이픈(-)을 넣어주세요.");
        }

        System.out.println("\n[비밀번호 찾기 질문] 나의 보물 1호는?");
        String pwAnswer = "";
        while (true) {
            System.out.print("정답을 입력해주세요 (30자 이내) : ");
            // 💡 trim()을 사용하여 사용자가 무의식적으로 넣은 좌우 공백을 제거합니다.
            pwAnswer = sc.nextLine().trim();

            // 💡 1글자 이상, 30글자 이하인지 확인 (엔터만 치거나 스페이스바만 누른 경우 차단)
            if (pwAnswer.length() > 0 && pwAnswer.length() <= 30) {
                break;
            }
            System.out.println("🚨 잘못된 형식입니다. 1자 이상 30자 이내로 입력해주세요.");
        }

        Long result = controller.createUser(userCode, loginId, name, birth, telNum, password, pwAnswer, userType);

        if (result != null && result > 0) {
            System.out.println("🎉 회원 가입 성공! 부여된 회원 고유 ID : " + result);
        } else {
            System.out.println("🚨 회원 가입에 실패했습니다. 관리자에게 문의하세요.");
        }
    }










    // 3. 학생 메인 메뉴
    // 3. 학생 메인 메뉴
    private void studentMainMenu(UsersDTO loggedInUser) {
        while (true) {
            // 1. 💡 핵심: 루프 시작 시 마다 세션 상태 확인 (게이트키퍼 역할)
            final UsersDTO user = loggedInUser;

            // 세션이 없으면(탈퇴/로그아웃 등) 즉시 이 메뉴를 종료하고 시작 메뉴로 돌아감
            if (user == null) {
                return;
            }

            System.out.println("\n========= [학생 메인 메뉴] =========");
            System.out.println("로그인 유저: " + user.getName() + " (" + user.getUserCode() + ")"); // 사용자 정보 표시
            System.out.println("1. 강의관리");
            System.out.println("2. 출결관리");
            System.out.println("3. 성적관리");
            System.out.println("4. 과제관리");
            System.out.println("5. 회원관리 (내 정보 수정)");
            System.out.println("0. 로그아웃");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("👉 강의관리 모듈로 이동합니다.");
                    studentCourseInputView.displayStudentMenu();
                    break;
                case "2":
                    System.out.println("👉 출결관리 모듈로 이동합니다.");
                    studentAttendanceInputView.displayMenu();
                    break;
                case "3":
                    System.out.println("👉 성적관리 모듈로 이동합니다.");
                    studentGradeInputView.displayStudentMainMenu();
                    break;
                case "4":
                    System.out.println("👉 과제관리 모듈로 이동합니다.");
                    studentAssignmentInputView.displaymainmenu();
                    break;
                case "5":
                    System.out.println("👉 회원관리 모듈로 이동합니다.");
                    usersInputView.stutMainPage(user);
                    // 💡 여기서 탈퇴하고 돌아오면, 다음 루프 시작 시 (user == null) 체크에서 걸려 return 됨
                    break;

                case "0":
                    // 2. 💡 로그아웃 시 세션을 비워줍니다.
                    SessionManager.getInstance().clearSession();
                    System.out.println("✅ 로그아웃 되었습니다.");
                    return;
                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    // 4. 교수 메인 메뉴
    private void professorMainMenu(UsersDTO loggedInUser) {
        final UsersDTO user = loggedInUser;
        while (true) {
            System.out.println("\n========= [교수 메인 메뉴] =========");
            System.out.println("1. 강의관리");
            System.out.println("2. 출결관리");
            System.out.println("3. 성적관리");
            System.out.println("4. 과제관리");
            System.out.println("5. 회원관리 (학생 조회 등)");
            System.out.println("0. 로그아웃");
            System.out.print("▶ 메뉴 선택: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("👉 강의관리 모듈로 이동합니다.");
                    break;

                case "2":
                    professorAttendanceInputView.displayMenu();
                    System.out.println("👉 출결관리 모듈로 이동합니다.");
                    break;

                case "3":
                    // TODO: 성적관리 담당자
                    System.out.println("👉 성적관리 모듈로 이동합니다.");
                    professorGradeInputView.displayProfessorMainMenu();
                    break;

                case "4":
                    // TODO: 과제관리 담당자
                    System.out.println("👉 과제관리 모듈로 이동합니다.");
                    break;

                case "5":
                    usersInputView.profMainPage(user);
                    System.out.println("👉 회원관리 모듈로 이동합니다.");

                case "0":
                    // 2. 💡 로그아웃 시 세션을 비워줍니다.1
                    SessionManager.getInstance().clearSession();
                    System.out.println("✅ 로그아웃 되었습니다.");
                    return;

                default:
                    System.out.println("🚨 잘못된 입력입니다. 다시 선택해주세요.");

            }
        }
    }
}
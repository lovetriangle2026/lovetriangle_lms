package com.module1.crud.auth.signup.view;

import com.module1.crud.auth.signup.controller.SignupController;
import com.module1.crud.global.utils.InputUtil;

import java.time.LocalDate;
import java.util.Scanner;

public class SignupView {
    private final Scanner sc = new Scanner(System.in);
    private final SignupController controller;

    // 💡 AppConfig에서 주입받을 컨트롤러 (추후 SignupController로 분리 예정)
    public SignupView(SignupController controller) {
        this.controller = controller;
    }

    public void showSignupMenu() {
        System.out.println("\n=== 📝 신규 회원 가입 ===");

        String userCode = "";
        String name = "";
        LocalDate birth = null;
        String userType = "";

        // ---------------------------------------------------------
        // [1단계: 본인 인증 절차 (학적 검증)]
        // ---------------------------------------------------------
        while (true) {
            System.out.println("\n[1단계: 본인 인증 절차 (학적 검증)]");

            // 💡 InputUtil 덕분에 끝없는 while문이 단 한 줄의 깔끔한 메서드로 변했습니다!
            userCode = InputUtil.getValueWithRegex(sc,
                    "사번/학번(User Code)을 입력해주세요 (예: S202604) : ",
                    "^[A-Za-z]\\d{6}$",
                    "영문자 1자리 + 숫자 6자리로 입력해주세요.");

            name = InputUtil.getValueWithRegex(sc,
                    "이름을 입력해주세요 : ",
                    "^[가-힣A-Za-z]+$",
                    "이름은 한글 또는 영문만 입력 가능합니다.");

            birth = InputUtil.getDate(sc,
                    "생년월일 (YYYY-MM-DD 형식)을 입력해주세요 : ",
                    "YYYY-MM-DD (예: 1999-01-01) 형식으로 입력해주세요.");

            String typeInput = InputUtil.getChoice(sc,
                    "사용자 유형을 선택해주세요 (1. 학생 / 2. 교수) : ",
                    new String[]{"1", "2"},
                    "숫자 1 또는 2를 입력해주세요.");

            userType = typeInput.equals("1") ? "STUDENT" : "PROFESSOR";

            // DB 검증을 Service로 요청
            String status = controller.verifyUser(userCode, name, birth, userType);

            if (status.equals("NOT_FOUND")) {
                System.out.println("🚨 등록된 학사 정보가 없습니다. 이름, 학번 등을 확인하고 처음부터 다시 입력해주세요.\n");
                continue;
            } else if (status.equals("ALREADY_REGISTERED")) {
                System.out.println("🚨 이미 아이디가 존재하는 가입된 사용자입니다. 메인 메뉴에서 로그인을 진행해주세요.");
                return;
            } else if (status.equals("ERROR")) {
                System.out.println("🚨 시스템 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
                return;
            } else if (status.equals("AVAILABLE")) {
                System.out.println("✅ 본인 인증이 완료되었습니다. 다음 가입 절차를 진행합니다.");
                break;
            }
        }

        // ---------------------------------------------------------
        // [2단계: 계정 정보 입력 및 생성]
        // ---------------------------------------------------------
        System.out.println("\n[2단계: 계정 정보 입력 및 생성]");

        String loginId = InputUtil.getValueWithRegex(sc,
                "사용할 로그인 ID를 입력해주세요 (영문/숫자 조합) : ",
                "^[A-Za-z0-9]+$",
                "ID는 영문과 숫자만 사용할 수 있습니다.");

        String password = InputUtil.getValueWithRegex(sc,
                "비밀번호를 입력해주세요 (영문, 숫자, 특수문자만 가능) : ",
                "^[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+$",
                "비밀번호는 영문, 숫자, 특수문자만 포함해야 합니다.");

        String telNum = InputUtil.getValueWithRegex(sc,
                "전화번호를 입력해주세요 (010-XXXX-XXXX 형식) : ",
                "^010-\\d{4}-\\d{4}$",
                "010-1234-5678 형식으로 중간에 하이픈(-)을 넣어주세요.");

        System.out.println("\n[비밀번호 찾기 질문] 나의 보물 1호는?");
        String pwAnswer = InputUtil.getValueWithRegex(sc,
                "정답을 입력해주세요 (1~30자 이내) : ",
                "^.{1,30}$",
                "1자 이상 30자 이내로 입력해주세요.");

        // 💡 모든 정보가 완벽하게 수집되었으니 DB에 저장 (CREATE -> UPDATE 패러다임 적용)
        Long result = controller.createUser(userCode, loginId, name, birth, telNum, password, pwAnswer, userType);

        if (result != null && result > 0) {
            System.out.println("🎉 회원 가입 성공! 부여된 회원 고유 ID : " + result);
        } else {
            System.out.println("🚨 회원 가입에 실패했습니다. 관리자에게 문의하세요.");
        }
    }
}
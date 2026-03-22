package com.module1.crud.auth.signup.view;

import com.module1.crud.auth.signup.controller.SignupController;
import com.module1.crud.global.utils.InputUtil;

import java.time.LocalDate;
import java.util.Scanner;

public class SignupView {
    private final Scanner sc = new Scanner(System.in);
    private final SignupController controller;

    public SignupView(SignupController controller) {
        this.controller = controller;
    }

    public void showSignupMenu() {
        System.out.println("\n========= [신규 회원 가입] =========");
        System.out.println("(이전 메뉴로 돌아가려면 '0'을 입력하세요)");

        String userCode = "";
        String name = "";
        LocalDate birth = null;
        String userType = "";

        // ---------------------------------------------------------
        // [1단계: 본인 인증 절차 (학적 검증)]
        // ---------------------------------------------------------
        while (true) {
            System.out.println("\n[1단계: 본인 인증 절차 (학적 검증)]");

            // 1. 학번/사번 입력
            userCode = InputUtil.getValueWithRegex(sc,
                    "사번/학번(User Code)을 입력해주세요 (예: S202604)  : ",
                    "^[A-Za-z]\\d{6}$|^0$", // 정규식에 |0 추가
                    "형식에 맞게 입력하거나 뒤로 가려면 0을 입력해주세요.");
            if ("0".equals(userCode)) return;

            // 2. 이름 입력
            name = InputUtil.getValueWithRegex(sc,
                    "이름을 입력해주세요  : ",
                    "^[가-힣A-Za-z]+$|^0$",
                    "이름은 한글 또는 영문만 가능합니다. ");
            if ("0".equals(name)) return;

            // 3. 생년월일 (날짜 입력은 InputUtil 내부 구조에 따라 0 처리가 필요함)
            // 💡 만약 InputUtil.getDate가 0을 처리 못한다면 getValueWithRegex로 날짜 형식을 받도록 대체 가능합니다.
            String birthInput = InputUtil.getValueWithRegex(sc,
                    "생년월일 (YYYY-MM-DD)을 입력해주세요  : ",
                    "^\\d{4}-\\d{2}-\\d{2}$|^0$",
                    "형식(1999-01-01)에 맞춰 입력해주세요. ");
            if ("0".equals(birthInput)) return;
            birth = LocalDate.parse(birthInput);

            // 4. 사용자 유형
            String typeInput = InputUtil.getChoice(sc,
                    "사용자 유형 선택 (1. 학생 / 2. 교수 ) : ",
                    new String[]{"1", "2", "0"},
                    "숫자 1, 2 를 입력해주세요.");
            if ("0".equals(typeInput)) return;
            userType = typeInput.equals("1") ? "STUDENT" : "PROFESSOR";

            // DB 검증
            String status = controller.verifyUser(userCode, name, birth, userType);

            if (status.equals("NOT_FOUND")) {
                System.out.println("🚨 등록된 정보가 없습니다. 다시 시도해주세요.");
                continue;
            } else if (status.equals("ALREADY_REGISTERED")) {
                System.out.println("🚨 이미 가입된 사용자입니다. 로그인을 진행해주세요.");
                return;
            } else if (status.equals("AVAILABLE")) {
                System.out.println("✅ 본인 인증 완료! 다음 단계로 넘어갑니다.");
                break;
            }
        }

        // ---------------------------------------------------------
        // [2단계: 계정 정보 입력 및 생성]
        // ---------------------------------------------------------
        System.out.println("\n[2단계: 계정 정보 입력 및 생성]");

        // 1. 로그인 ID
        String loginId = InputUtil.getValueWithRegex(sc,
                "사용할 로그인 ID를 입력해주세요  : ",
                "^[A-Za-z0-9]{4,}$|^0$",
                "ID는 영문/숫자 4자 이상이어야 합니다. ");
        if ("0".equals(loginId)) return;

        // 2. 비밀번호
        String password = InputUtil.getValueWithRegex(sc,
                "비밀번호 입력 (영문+숫자+특수문자 8자 이상) : ",
                "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$|^0$",
                "보안 형식에 맞춰 입력해주세요. ");
        if ("0".equals(password)) return;

        // 3. 전화번호
        String telNum = InputUtil.getValueWithRegex(sc,
                "전화번호 입력 (010-XXXX-XXXX) : ",
                "^010-\\d{4}-\\d{4}$|^0$",
                "형식에 맞춰 입력해주세요. ");
        if ("0".equals(telNum)) return;

        // 4. 비밀번호 찾기 답변
        System.out.println("\n✨ [비밀번호 찾기 질문] 나의 보물 1호는?");
        String pwAnswer = InputUtil.getValueWithRegex(sc,
                "정답을 입력해주세요  : ",
                "^.{1,30}$|^0$",
                "1~30자 이내로 입력해주세요. ");
        if ("0".equals(pwAnswer)) return;

        // 최종 생성
        Long result = controller.createUser(userCode, loginId, name, birth, telNum, password, pwAnswer, userType);

        if (result != null && result > 0) {
            System.out.println("🎉 Amazon 대학교의 일원이 되신 것을 환영합니다!");
        } else {
            System.out.println("🚨 가입 실패! 관리자에게 문의하세요.");
        }
    }
}
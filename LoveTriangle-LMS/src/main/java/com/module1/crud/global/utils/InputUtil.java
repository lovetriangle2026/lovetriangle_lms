package com.module1.crud.global.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtil {

    /**
     * 1. 정규식(Regex)을 이용한 공통 문자열 검증 메서드
     * 입력값이 정규식을 통과할 때까지 무한 반복하며, 통과하면 값을 반환합니다.
     */
    public static String getValueWithRegex(Scanner sc, String prompt, String regex, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim(); // 좌우 공백 제거
            if (input.matches(regex)) {
                return input;
            }
            System.out.println("🚨 " + errorMessage);
        }
    }

    /**
     * 2. 생년월일(LocalDate) 전용 검증 메서드
     * 형식이 맞는지(1차 검증), 실제 존재하는 날짜인지(2차 검증) 확인합니다.
     */
    public static LocalDate getDate(Scanner sc, String prompt, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();

            if (input.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                try {
                    return LocalDate.parse(input); // 문자열을 날짜 객체로 변환
                } catch (DateTimeParseException e) {
                    System.out.println("🚨 달력에 존재하지 않는 날짜입니다. 다시 확인해주세요.");
                }
            } else {
                System.out.println("🚨 " + errorMessage);
            }
        }
    }

    /**
     * 3. 특정 선택지(예: 1번, 2번)만 허용하는 검증 메서드
     */
    public static String getChoice(Scanner sc, String prompt, String[] validChoices, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();

            // 입력값이 유효한 선택지 배열 안에 있는지 확인
            for (String choice : validChoices) {
                if (input.equals(choice)) {
                    return input;
                }
            }
            System.out.println("🚨 " + errorMessage);
        }
    }
}
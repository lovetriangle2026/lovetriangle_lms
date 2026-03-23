package com.module1.crud.attendance.controller;

public class AttendanceStatusConverter {

    public static String toKorean(String status) {
        if (status == null) return "-";

        switch (status.toUpperCase()) {
            case "PRESENT":
                return "출석";
            case "LATE":
                return "지각";
            case "ABSENT":
                return "결석";
            case "EXCUSED":
                return "공결";
            default:
                return status;
        }
    }

    public static String toEnglish(String status) {
        if (status == null) return null;

        switch (status.trim()) {
            case "출석":
                return "PRESENT";
            case "지각":
                return "LATE";
            case "결석":
                return "ABSENT";
            case "공결":
                return "EXCUSED";
            default:
                return null;
        }
    }
}
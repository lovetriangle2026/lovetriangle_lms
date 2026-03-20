package com.module1.crud.attendance.view;

import com.module1.crud.attendance.model.dto.AttendanceDTO;

import java.util.List;

public class AttendanceOutputView {

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printSuccess(String message) {
        System.out.println(message);
    }

    public static void printError(String message) {
        System.out.println(message);
    }

    public void printAttendanceList(List<AttendanceDTO> attendanceList) {

        if (attendanceList == null || attendanceList.isEmpty()) {
            printMessage("조회된 출결 데이터가 없습니다.");
            return;
        }

        System.out.println();
        System.out.println("==============================================================================================");
        System.out.printf("%-8s %-10s %-18s %-6s %-12s %-20s%n",
                "학생ID", "학생이름", "강의명", "주차", "출결상태", "출석시간");
        System.out.println("==============================================================================================");

        for (AttendanceDTO attendance : attendanceList) {
            String checkedAt = attendance.getCheckedAt() == null
                    ? "-"
                    : attendance.getCheckedAt().toString();

            System.out.printf("%-8d %-10s %-18s %-6d %-12s %-20s%n",
                    attendance.getStudentId(),
                    attendance.getStudentName(),
                    attendance.getCourseTitle(),
                    attendance.getWeek(),
                    attendance.getAttendanceStatus(),
                    checkedAt
            );
        }

        System.out.println("==============================================================================================");
    }

    public void printAttendanceByWeek(List<AttendanceDTO> attendanceList) {

        if (attendanceList == null || attendanceList.isEmpty()) {
            printMessage("조회된 출결 데이터가 없습니다.");
            return;
        }

        String courseTitle = attendanceList.get(0).getCourseTitle();
        System.out.println("\n===== " + courseTitle + " 출결 현황 =====");

        int currentWeek = -1;

        for (AttendanceDTO attendance : attendanceList) {
            if (attendance.getWeek() != currentWeek) {
                currentWeek = attendance.getWeek();
                System.out.println();
                System.out.println(currentWeek + "주차");
            }

            String checkedAt = attendance.getCheckedAt() == null
                    ? "-"
                    : attendance.getCheckedAt().toString();

            System.out.println("- " + attendance.getStudentName()
                    + " : " + attendance.getAttendanceStatus()
                    + " (" + checkedAt + ")");
        }
    }
}
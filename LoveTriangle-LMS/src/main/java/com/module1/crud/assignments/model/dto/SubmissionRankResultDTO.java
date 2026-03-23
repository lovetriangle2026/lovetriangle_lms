package com.module1.crud.assignments.model.dto;



public class SubmissionRankResultDTO {

    private final int rank;
    private final int totalStudents;
    private final boolean first;
    private final boolean topTenPercent;
    private final boolean late;

    public SubmissionRankResultDTO(int rank, int totalStudents, boolean first, boolean topTenPercent, boolean late) {
        this.rank = rank;
        this.totalStudents = totalStudents;
        this.first = first;
        this.topTenPercent = topTenPercent;
        this.late = late;
    }

    public int getRank() {
        return rank;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isTopTenPercent() {
        return topTenPercent;
    }

    public boolean isLate() {
        return late;
    }

    public String getMessage() {
        if (first) {
            String coupon = generateCouponCode();

            return "🎉 축하합니다! 1빠입니다!!\n"
                    + "🎁 할인쿠폰이 지급되었습니다!\n"
                    + "쿠폰번호 : " + coupon;

        }
        if (topTenPercent) {
            return "🏆 축하합니다! 과제 제출 순위 상위 10%에 드셨습니다!!";
        }
        return "✅ 과제가 성공적으로 제출되었습니다.";
    }
    private String generateCouponCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            sb.append(chars.charAt((int)(Math.random() * chars.length())));
        }

        return "SAVE-2026-" + sb.toString();
    }
}

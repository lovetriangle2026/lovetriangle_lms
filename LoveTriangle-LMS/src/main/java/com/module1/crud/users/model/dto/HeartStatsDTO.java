package com.module1.crud.users.model.dto;

import java.util.List;

public class HeartStatsDTO {
    private int totalHearts;
    private List<String> top3Tags;

    public HeartStatsDTO(int totalHearts, List<String> top3Tags) {
        this.totalHearts = totalHearts;
        this.top3Tags = top3Tags;
    }

    public int getTotalHearts() {
        return totalHearts;
    }

    public void setTotalHearts(int totalHearts) {
        this.totalHearts = totalHearts;
    }

    public List<String> getTop3Tags() {
        return top3Tags;
    }

    public void setTop3Tags(List<String> top3Tags) {
        this.top3Tags = top3Tags;
    }

    @Override
    public String toString() {
        return "HeartStatsDTO{" +
                "totalHearts=" + totalHearts +
                ", top3Tags=" + top3Tags +
                '}';
    }
}
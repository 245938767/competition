package com.ruoyi.project.sc.competition.domain;

import java.io.Serializable;

public class CaseScoreVO implements Serializable {
    private Long id;
    private Float score;
    private int counts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }
}

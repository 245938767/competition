package com.ruoyi.project.sc.competition.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;

import java.util.List;

public class CompetitionScoreListExport {

    @Excel(name = "序号")
    private Long sort;
    @Excel(name = "学院")
    private String college;
    private List<CompetitionUserScoreExport> userScore;
    @Excel(name = "团体总分")
    private Float total;
    @Excel(name = "团体排名")
    private Long rank;

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public List<CompetitionUserScoreExport> getUserScore() {
        return userScore;
    }

    public void setUserScore(List<CompetitionUserScoreExport> userScore) {
        this.userScore = userScore;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }
}

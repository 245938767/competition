package com.ruoyi.project.sc.competition.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;

public class CompetitionUserScoreExport {
    @Excel(name = "参赛项目")
    private String item;
    @Excel(name = "姓名")
    private String name;

    @Excel(name = "笔试分数")
    private Float basicScore;

    @Excel(name = "研讨案例分数")
    private Float caseScore;
    @Excel(name = "案例研讨排名")
    private Long caseRank;
    @Excel(name = "谈心谈话分数")
    private Float talkScore;
    @Excel(name = "谈心谈话排名")
    private Long talkRank;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getBasicScore() {
        return basicScore;
    }

    public void setBasicScore(Float basicScore) {
        this.basicScore = basicScore;
    }

    public Float getCaseScore() {
        return caseScore;
    }

    public void setCaseScore(Float caseScore) {
        this.caseScore = caseScore;
    }

    public Long getCaseRank() {
        return caseRank;
    }

    public void setCaseRank(Long caseRank) {
        this.caseRank = caseRank;
    }

    public Float getTalkScore() {
        return talkScore;
    }

    public void setTalkScore(Float talkScore) {
        this.talkScore = talkScore;
    }

    public Long getTalkRank() {
        return talkRank;
    }

    public void setTalkRank(Long talkRank) {
        this.talkRank = talkRank;
    }
}

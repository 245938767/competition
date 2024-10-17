package com.ruoyi.project.sc.competition.domain;

import java.io.Serializable;

public class ScoreComputerVO implements Serializable {
    private Long collageId;
    private Float totalScore = (float) 0;
    private Float basicScore = (float) 0;
    private Float caseScore = (float) 0;
    private Float talkScore = (float) 0;

    public ScoreComputerVO(Float basicScore) {
        this.basicScore = basicScore;
    }

    public ScoreComputerVO() {
    }

    public Long getCollageId() {
        return collageId;
    }

    public void setCollageId(Long collageId) {
        this.collageId = collageId;
    }

    public Float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Float totalScore) {
        this.totalScore = totalScore;
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

    public Float getTalkScore() {
        return talkScore;
    }

    public void setTalkScore(Float talkScore) {
        this.talkScore = talkScore;
    }
}

package com.ruoyi.project.sc.competition.domain;


import com.ruoyi.project.sc.CollageScore.domain.ScCollageScore;

import java.io.Serializable;
import java.util.List;

public class CompetitionUser implements Serializable {
    public CompetitionUser(String name, String collage) {
        this.name = name;
        this.collage = collage;
    }

    public CompetitionUser(Long playerId, String name, String collage) {
        this.playerId = playerId;
        this.name = name;
        this.collage = collage;
    }

    public CompetitionUser(List<ScCollageScore> scCollageScores, String name, String collage) {
        this.scCollageScores = scCollageScores;
        this.name = name;
        this.collage = collage;
    }

    private Long playerId;
    private List<ScCollageScore> scCollageScores;
    private String name;
    private String collage;

    public List<ScCollageScore> getScCollageScores() {
        return scCollageScores;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public void setScCollageScores(List<ScCollageScore> scCollageScores) {
        this.scCollageScores = scCollageScores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }
}
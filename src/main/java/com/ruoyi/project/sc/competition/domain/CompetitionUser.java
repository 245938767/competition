package com.ruoyi.project.sc.competition.domain;


import com.ruoyi.project.sc.CollageScore.domain.ScCollageScore;

import java.io.Serializable;
import java.util.List;

public class CompetitionUser implements Serializable {
    public CompetitionUser(String name, String collage) {
        this.name = name;
        this.collage = collage;
    }

    public CompetitionUser(List<ScCollageScore> scCollageScores, String name, String collage) {
        this.scCollageScores = scCollageScores;
        this.name = name;
        this.collage = collage;
    }

    private List<ScCollageScore> scCollageScores;
    private String name;
    private String collage;

    public List<ScCollageScore> getScCollageScores() {
        return scCollageScores;
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
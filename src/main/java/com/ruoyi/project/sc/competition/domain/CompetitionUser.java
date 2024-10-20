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

    public CompetitionUser(Long collageId, Long sortId, Long playerId, String name, String collage,Long type) {
        this.collageId = collageId;
        SortId = sortId;
        this.playerId = playerId;
        this.name = name;
        this.collage = collage;
        this.type=type;
    }

    public CompetitionUser(List<ScCollageScore> scCollageScores, String name, String collage) {
        this.scCollageScores = scCollageScores;
        this.name = name;
        this.collage = collage;
    }

    private Long collageId;
    private Long SortId;
    private Long playerId;
    private List<ScCollageScore> scCollageScores;
    private String name;
    private String collage;
    private Long type;

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getCollageId() {
        return collageId;
    }

    public void setCollageId(Long collageId) {
        this.collageId = collageId;
    }

    public Long getSortId() {
        return SortId;
    }

    public void setSortId(Long sortId) {
        SortId = sortId;
    }

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
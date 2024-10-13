package com.ruoyi.project.sc.competition.domain;

import java.io.Serializable;

public class CompetitionListVO implements Serializable {
    private CompetitionUser UserA;
    private CompetitionUser UserB;
    private Long sort;
    private Long SortId;

    public Long getSortId() {
        return SortId;
    }

    public void setSortId(Long sortId) {
        SortId = sortId;
    }

    public CompetitionUser getUserA() {
        return UserA;
    }

    public void setUserA(CompetitionUser userA) {
        UserA = userA;
    }

    public CompetitionUser getUserB() {
        return UserB;
    }

    public void setUserB(CompetitionUser userB) {
        UserB = userB;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

}


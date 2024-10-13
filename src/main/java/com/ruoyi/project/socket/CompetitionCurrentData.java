package com.ruoyi.project.socket;

import com.ruoyi.project.sc.competition.domain.CompetitionUser;

import java.io.Serializable;

public class CompetitionCurrentData implements Serializable {
    /**
     * 当前排序
     */
    private Long currentSort;
    /**
     * 当前比赛类型
     */
    private Long currentType;
    private CompetitionUser UserA;
    private CompetitionUser UserB;

    public Long getCurrentSort() {
        return currentSort;
    }

    public void setCurrentSort(Long currentSort) {
        this.currentSort = currentSort;
    }


    public Long getCurrentType() {
        return currentType;
    }

    public void setCurrentType(Long currentType) {
        this.currentType = currentType;
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
}

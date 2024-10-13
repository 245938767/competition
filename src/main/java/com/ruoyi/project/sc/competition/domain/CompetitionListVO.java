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

    public static class CompetitionUser implements Serializable {
        public CompetitionUser(String name, String collage) {
            this.name = name;
            this.collage = collage;
        }

        private String name;
        private String collage;

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
}


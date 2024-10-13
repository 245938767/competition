package com.ruoyi.project.socket;

import com.ruoyi.project.sc.competition.domain.CompetitionListVO;

import java.io.Serializable;
import java.util.List;


public class NoticeWebsocketResp implements Serializable {
    private List<CompetitionListVO> competitionListVOList;
    private CompetitionCurrentData currentData;

    public List<CompetitionListVO> getCompetitionListVOList() {
        return competitionListVOList;
    }

    public void setCompetitionListVOList(List<CompetitionListVO> competitionListVOList) {
        this.competitionListVOList = competitionListVOList;
    }

    public CompetitionCurrentData getCurrentData() {
        return currentData;
    }

    public void setCurrentData(CompetitionCurrentData currentData) {
        this.currentData = currentData;
    }
}

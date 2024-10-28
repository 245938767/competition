package com.ruoyi.project.sc.competition.domain.export;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import org.apache.poi.ss.usermodel.IndexedColors;

@ExcelIgnoreUnannotated
public class CompetitionUserScoreExport {
    @Excel(name = "参赛项目", headerColor = IndexedColors.BLACK, headerBackgroundColor = IndexedColors.WHITE)
    private String item;
    @Excel(name = "姓名", headerColor = IndexedColors.BLACK, headerBackgroundColor = IndexedColors.WHITE)
    private String name;

    @Excel(name = "笔试分数", headerColor = IndexedColors.BLACK, headerBackgroundColor = IndexedColors.WHITE)
    private Float basicScore;

    @Excel(name = "研讨案例分数", headerColor = IndexedColors.BLACK, headerBackgroundColor = IndexedColors.WHITE)
    private Float caseScore;
    @Excel(name = "案例研讨排名", headerColor = IndexedColors.BLACK, headerBackgroundColor = IndexedColors.WHITE)
    private Long caseRank;
    @Excel(name = "谈心谈话分数", headerColor = IndexedColors.BLACK, headerBackgroundColor = IndexedColors.WHITE)
    private Float talkScore;
    @Excel(name = "谈心谈话排名", headerColor = IndexedColors.BLACK, headerBackgroundColor = IndexedColors.WHITE)
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

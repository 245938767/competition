package com.ruoyi.project.sc.competition.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;

public class CompetitionTalkListExport {

    @Excel(name = "序号")
    private Long sort;

    @Excel(name = "学院")
    private String collegeA;
    @Excel(name = "辅导员")
    private String userAExports;
}

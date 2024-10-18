package com.ruoyi.project.sc.competition.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;

/**
 * 比赛名单Excel导出模版数据
 */
public class CompetitionCaseListExport {
    @Excel(name = "序号")
    private Long sort;

    @Excel(name = "学院")
    private String collegeA;
    @Excel(name = "比赛选手A")
    private String userAExports;
    @Excel(name = "学院")
    private String collegeB;
    @Excel(name = "比赛选手B")
    private String userBExports;

    public String getCollegeA() {
        return collegeA;
    }

    public void setCollegeA(String collegeA) {
        this.collegeA = collegeA;
    }

    public String getUserAExports() {
        return userAExports;
    }

    public void setUserAExports(String userAExports) {
        this.userAExports = userAExports;
    }

    public String getCollegeB() {
        return collegeB;
    }

    public void setCollegeB(String collegeB) {
        this.collegeB = collegeB;
    }

    public String getUserBExports() {
        return userBExports;
    }

    public void setUserBExports(String userBExports) {
        this.userBExports = userBExports;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

}
//郑总，什么时候给我安排到新的项目
//        模版脚本写好了，newsletter目前没有什么新的优化点，正在把修改更新到旧的邮件中
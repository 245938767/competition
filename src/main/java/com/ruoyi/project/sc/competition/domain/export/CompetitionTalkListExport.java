package com.ruoyi.project.sc.competition.domain.export;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import org.apache.poi.ss.usermodel.IndexedColors;

@ExcelIgnoreUnannotated

public class CompetitionTalkListExport {

    @Excel(name = "序号", needMerge = false, width = 20, headerColor = IndexedColors.BLACK, headerBackgroundColor = IndexedColors.WHITE)
    @ExcelProperty(value = "序号", index = 0)
    private Long sort;


    @Excel(name = "学院", needMerge = false, width = 20, headerColor = IndexedColors.BLACK, headerBackgroundColor = IndexedColors.WHITE)
    @ExcelProperty(value = "学院", index = 1)
    private String collegeA;

    @Excel(name = "辅导员", needMerge = false, width = 20, headerColor = IndexedColors.BLACK, headerBackgroundColor = IndexedColors.WHITE)
    @ExcelProperty(value = "辅导员", index = 2)
    private String userAExports;

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

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
}

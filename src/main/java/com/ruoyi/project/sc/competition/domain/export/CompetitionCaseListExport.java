package com.ruoyi.project.sc.competition.domain.export;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.List;

/**
 * 比赛名单Excel导出模版数据
 */
@ExcelIgnoreUnannotated
public class CompetitionCaseListExport {
    @Excel(name = "序号", needMerge = true, headerColor = IndexedColors.BLACK, headerBackgroundColor = IndexedColors.WHITE)
    @ExcelProperty(value = "序号", index = 0)
    private Long sort;

    @Excel(name = "选手信息", needMerge = false, headerColor = IndexedColors.BLACK, headerBackgroundColor = IndexedColors.WHITE)
    @ExcelProperty(value = "选手信息", index = 1)
    private List<UserListExport> userListExportList;

    public List<UserListExport> getUserListExportList() {
        return userListExportList;
    }

    public void setUserListExportList(List<UserListExport> userListExportList) {
        this.userListExportList = userListExportList;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

}
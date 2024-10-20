package com.ruoyi.project.sc.competition.domain.export;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
@ExcelIgnoreUnannotated

public class UserListExport {

    @Excel(name = "学院",width = 20,headerColor = IndexedColors.BLACK,headerBackgroundColor = IndexedColors.WHITE,align = HorizontalAlignment.CENTER)
    @ExcelProperty(value = "学院", index = 0)
    private String college;
    @Excel(name = "选手",width = 20,headerColor = IndexedColors.BLACK,headerBackgroundColor = IndexedColors.WHITE,align = HorizontalAlignment.CENTER)
    @ExcelProperty(value = "选手", index = 1)
    private String userExports;

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getUserExports() {
        return userExports;
    }

    public void setUserExports(String userExports) {
        this.userExports = userExports;
    }
}

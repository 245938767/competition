package com.ruoyi.project.sc.competition.domain;

import java.util.List;

import com.ruoyi.project.sc.college.domain.ScCollege;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * competition对象 sc_competition
 * 
 * @author larthur
 * @date 2024-10-13
 */
public class ScCompetition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 比赛ID */
    private Long competiitonId;

    /** 比赛名称 */
    @Excel(name = "比赛名称")
    private String name;

    /** 当前出场学院 */
    @Excel(name = "当前出场学院")
    private Long collageId;

    /** 当前比赛类型  0-未开始 1-案例分析 3-谈心谈话*/
    @Excel(name = "当前比赛类型")
    private Long currentType;

    /** 当前排序状态 0-未开始 other-当前状态*/
    @Excel(name = "当前排序状态")
    private Long currentSort;

    /** college信息 */
    private List<ScCollege> scCollegeList;

    public void setCompetiitonId(Long competiitonId) 
    {
        this.competiitonId = competiitonId;
    }

    public Long getCompetiitonId() 
    {
        return competiitonId;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setCollageId(Long collageId) 
    {
        this.collageId = collageId;
    }

    public Long getCollageId() 
    {
        return collageId;
    }

    public void setCurrentType(Long currentType) 
    {
        this.currentType = currentType;
    }

    public Long getCurrentType() 
    {
        return currentType;
    }

    public void setCurrentSort(Long currentSort) 
    {
        this.currentSort = currentSort;
    }

    public Long getCurrentSort() 
    {
        return currentSort;
    }

    public List<ScCollege> getScCollegeList()
    {
        return scCollegeList;
    }

    public void setScCollegeList(List<ScCollege> scCollegeList)
    {
        this.scCollegeList = scCollegeList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("competiitonId", getCompetiitonId())
            .append("name", getName())
            .append("collageId", getCollageId())
            .append("currentType", getCurrentType())
            .append("currentSort", getCurrentSort())
            .append("scCollegeList", getScCollegeList())
            .toString();
    }
}

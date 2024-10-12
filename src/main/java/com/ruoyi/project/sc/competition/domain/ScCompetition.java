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
 * @date 2024-10-12
 */
public class ScCompetition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 比赛ID */
    private Long competiitonId;

    /** 比赛名称 */
    @Excel(name = "比赛名称")
    private String name;

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
            .append("scCollegeList", getScCollegeList())
            .toString();
    }
}

package com.ruoyi.project.sc.college.domain;

import java.util.List;

import com.ruoyi.project.sc.players.domain.ScPlayers;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * college对象 sc_college
 * 
 * @author larthur
 * @date 2024-10-12
 */
public class ScCollege extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 学院id */
    private Long collegeId;

    /** 学院名称 */
    @Excel(name = "学院名称")
    private String name;

    /** 关联的比赛 */
    @Excel(name = "关联的比赛")
    private Long competitionId;

    /** players信息 */
    private List<ScPlayers> scPlayersList;

    public void setCollegeId(Long collegeId) 
    {
        this.collegeId = collegeId;
    }

    public Long getCollegeId() 
    {
        return collegeId;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setCompetitionId(Long competitionId) 
    {
        this.competitionId = competitionId;
    }

    public Long getCompetitionId() 
    {
        return competitionId;
    }

    public List<ScPlayers> getScPlayersList()
    {
        return scPlayersList;
    }

    public void setScPlayersList(List<ScPlayers> scPlayersList)
    {
        this.scPlayersList = scPlayersList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("collegeId", getCollegeId())
            .append("name", getName())
            .append("competitionId", getCompetitionId())
            .append("scPlayersList", getScPlayersList())
            .toString();
    }
}

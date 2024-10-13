package com.ruoyi.project.sc.sort.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 比赛流程对象 sc_competition_sort
 * 
 * @author larthur
 * @date 2024-10-13
 */
public class ScCompetitionSort extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 用户1 */
    @Excel(name = "用户1")
    private Long user1;

    /** 用户2 */
    @Excel(name = "用户2")
    private Long user2;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 比赛ID */
    @Excel(name = "比赛ID")
    private Long competitionId;

    /** 比赛类型（1案例分析，2谈心谈话） */
    @Excel(name = "比赛类型", readConverterExp = "1=案例分析，2谈心谈话")
    private Long type;

    /** 学院ID */
    @Excel(name = "学院ID")
    private Long collageId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setUser1(Long user1) 
    {
        this.user1 = user1;
    }

    public Long getUser1() 
    {
        return user1;
    }

    public void setUser2(Long user2) 
    {
        this.user2 = user2;
    }

    public Long getUser2() 
    {
        return user2;
    }

    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }

    public void setCompetitionId(Long competitionId) 
    {
        this.competitionId = competitionId;
    }

    public Long getCompetitionId() 
    {
        return competitionId;
    }

    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }

    public void setCollageId(Long collageId) 
    {
        this.collageId = collageId;
    }

    public Long getCollageId() 
    {
        return collageId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("user1", getUser1())
            .append("user2", getUser2())
            .append("sort", getSort())
            .append("competitionId", getCompetitionId())
            .append("type", getType())
            .append("collageId", getCollageId())
            .toString();
    }
}

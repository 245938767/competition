package com.ruoyi.project.sc.players.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * players对象 sc_players
 * 
 * @author larthur
 * @date 2024-10-13
 */
public class ScPlayers extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 玩家ID */
    private Long playerId;

    /** 玩家名称 */
    @Excel(name = "玩家名称")
    private String name;

    /** 关联学院 */
    @Excel(name = "关联学院")
    private Long collegeId;

    /** 玩家类型（a,b,谈心谈话，学生） */
    @Excel(name = "玩家类型", readConverterExp = "a=,b,谈心谈话，学生")
    private Long type;

    public void setPlayerId(Long playerId) 
    {
        this.playerId = playerId;
    }

    public Long getPlayerId() 
    {
        return playerId;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setCollegeId(Long collegeId) 
    {
        this.collegeId = collegeId;
    }

    public Long getCollegeId() 
    {
        return collegeId;
    }

    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("playerId", getPlayerId())
            .append("name", getName())
            .append("collegeId", getCollegeId())
            .append("type", getType())
            .toString();
    }
}

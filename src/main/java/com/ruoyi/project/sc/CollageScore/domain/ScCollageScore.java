package com.ruoyi.project.sc.CollageScore.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * CollageScore对象 sc_collage_score
 * 
 * @author larthur
 * @date 2024-10-13
 */
public class ScCollageScore extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 学院 */
    @Excel(name = "学院")
    private Long collegeId;

    /** 玩家 */
    @Excel(name = "玩家")
    private Long playerId;

    /** 评委 */
    @Excel(name = "评委")
    private Long judgeId;

    /** 分数 */
    @Excel(name = "分数")
    private Long score;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCollegeId(Long collegeId) 
    {
        this.collegeId = collegeId;
    }

    public Long getCollegeId() 
    {
        return collegeId;
    }

    public void setPlayerId(Long playerId) 
    {
        this.playerId = playerId;
    }

    public Long getPlayerId() 
    {
        return playerId;
    }

    public void setJudgeId(Long judgeId) 
    {
        this.judgeId = judgeId;
    }

    public Long getJudgeId() 
    {
        return judgeId;
    }

    public void setScore(Long score) 
    {
        this.score = score;
    }

    public Long getScore() 
    {
        return score;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("collegeId", getCollegeId())
            .append("playerId", getPlayerId())
            .append("judgeId", getJudgeId())
            .append("score", getScore())
            .toString();
    }
}

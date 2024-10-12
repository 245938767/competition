package com.ruoyi.project.sc.competition.service;

import java.util.List;
import com.ruoyi.project.sc.competition.domain.ScCompetition;

/**
 * competitionService接口
 * 
 * @author larthur
 * @date 2024-10-12
 */
public interface IScCompetitionService 
{
    /**
     * 查询competition
     * 
     * @param competiitonId competition主键
     * @return competition
     */
    public ScCompetition selectScCompetitionByCompetiitonId(Long competiitonId);

    /**
     * 查询competition列表
     * 
     * @param scCompetition competition
     * @return competition集合
     */
    public List<ScCompetition> selectScCompetitionList(ScCompetition scCompetition);

    /**
     * 新增competition
     * 
     * @param scCompetition competition
     * @return 结果
     */
    public int insertScCompetition(ScCompetition scCompetition);

    /**
     * 修改competition
     * 
     * @param scCompetition competition
     * @return 结果
     */
    public int updateScCompetition(ScCompetition scCompetition);

    /**
     * 批量删除competition
     * 
     * @param competiitonIds 需要删除的competition主键集合
     * @return 结果
     */
    public int deleteScCompetitionByCompetiitonIds(String competiitonIds);

    /**
     * 删除competition信息
     * 
     * @param competiitonId competition主键
     * @return 结果
     */
    public int deleteScCompetitionByCompetiitonId(Long competiitonId);
}

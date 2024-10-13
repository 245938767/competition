package com.ruoyi.project.sc.competition.mapper;

import java.util.List;

import com.ruoyi.project.sc.college.domain.ScCollege;
import com.ruoyi.project.sc.competition.domain.ScCompetition;

/**
 * competitionMapper接口
 * 
 * @author larthur
 * @date 2024-10-13
 */
public interface ScCompetitionMapper 
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
     * 删除competition
     * 
     * @param competiitonId competition主键
     * @return 结果
     */
    public int deleteScCompetitionByCompetiitonId(Long competiitonId);

    /**
     * 批量删除competition
     * 
     * @param competiitonIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScCompetitionByCompetiitonIds(String[] competiitonIds);

    /**
     * 批量删除college
     * 
     * @param competiitonIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScCollegeByCompetitionIds(String[] competiitonIds);
    
    /**
     * 批量新增college
     * 
     * @param scCollegeList college列表
     * @return 结果
     */
    public int batchScCollege(List<ScCollege> scCollegeList);
    

    /**
     * 通过competition主键删除college信息
     * 
     * @param competiitonId competitionID
     * @return 结果
     */
    public int deleteScCollegeByCompetitionId(Long competiitonId);
}

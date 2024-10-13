package com.ruoyi.project.sc.sort.mapper;

import java.util.List;
import com.ruoyi.project.sc.sort.domain.ScCompetitionSort;

/**
 * 比赛流程Mapper接口
 * 
 * @author larthur
 * @date 2024-10-13
 */
public interface ScCompetitionSortMapper 
{
    /**
     * 查询比赛流程
     * 
     * @param id 比赛流程主键
     * @return 比赛流程
     */
    public ScCompetitionSort selectScCompetitionSortById(Long id);

    /**
     * 查询比赛流程列表
     * 
     * @param scCompetitionSort 比赛流程
     * @return 比赛流程集合
     */
    public List<ScCompetitionSort> selectScCompetitionSortList(ScCompetitionSort scCompetitionSort);

    /**
     * 新增比赛流程
     * 
     * @param scCompetitionSort 比赛流程
     * @return 结果
     */
    public int insertScCompetitionSort(ScCompetitionSort scCompetitionSort);

    /**
     * 修改比赛流程
     * 
     * @param scCompetitionSort 比赛流程
     * @return 结果
     */
    public int updateScCompetitionSort(ScCompetitionSort scCompetitionSort);

    /**
     * 删除比赛流程
     * 
     * @param id 比赛流程主键
     * @return 结果
     */
    public int deleteScCompetitionSortById(Long id);

    /**
     * 批量删除比赛流程
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScCompetitionSortByIds(String[] ids);
}

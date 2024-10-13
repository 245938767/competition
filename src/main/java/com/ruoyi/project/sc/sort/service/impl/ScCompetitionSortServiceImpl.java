package com.ruoyi.project.sc.sort.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.sc.sort.mapper.ScCompetitionSortMapper;
import com.ruoyi.project.sc.sort.domain.ScCompetitionSort;
import com.ruoyi.project.sc.sort.service.IScCompetitionSortService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 比赛流程Service业务层处理
 * 
 * @author larthur
 * @date 2024-10-13
 */
@Service
public class ScCompetitionSortServiceImpl implements IScCompetitionSortService 
{
    @Autowired
    private ScCompetitionSortMapper scCompetitionSortMapper;

    /**
     * 查询比赛流程
     * 
     * @param id 比赛流程主键
     * @return 比赛流程
     */
    @Override
    public ScCompetitionSort selectScCompetitionSortById(Long id)
    {
        return scCompetitionSortMapper.selectScCompetitionSortById(id);
    }

    /**
     * 查询比赛流程列表
     * 
     * @param scCompetitionSort 比赛流程
     * @return 比赛流程
     */
    @Override
    public List<ScCompetitionSort> selectScCompetitionSortList(ScCompetitionSort scCompetitionSort)
    {
        return scCompetitionSortMapper.selectScCompetitionSortList(scCompetitionSort);
    }

    /**
     * 新增比赛流程
     * 
     * @param scCompetitionSort 比赛流程
     * @return 结果
     */
    @Override
    public int insertScCompetitionSort(ScCompetitionSort scCompetitionSort)
    {
        return scCompetitionSortMapper.insertScCompetitionSort(scCompetitionSort);
    }

    /**
     * 修改比赛流程
     * 
     * @param scCompetitionSort 比赛流程
     * @return 结果
     */
    @Override
    public int updateScCompetitionSort(ScCompetitionSort scCompetitionSort)
    {
        return scCompetitionSortMapper.updateScCompetitionSort(scCompetitionSort);
    }

    /**
     * 批量删除比赛流程
     * 
     * @param ids 需要删除的比赛流程主键
     * @return 结果
     */
    @Override
    public int deleteScCompetitionSortByIds(String ids)
    {
        return scCompetitionSortMapper.deleteScCompetitionSortByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除比赛流程信息
     * 
     * @param id 比赛流程主键
     * @return 结果
     */
    @Override
    public int deleteScCompetitionSortById(Long id)
    {
        return scCompetitionSortMapper.deleteScCompetitionSortById(id);
    }
}

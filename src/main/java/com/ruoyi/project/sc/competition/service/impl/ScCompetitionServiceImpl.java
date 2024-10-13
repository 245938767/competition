package com.ruoyi.project.sc.competition.service.impl;

import java.util.List;

import com.ruoyi.project.sc.college.domain.ScCollege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.project.sc.competition.mapper.ScCompetitionMapper;
import com.ruoyi.project.sc.competition.domain.ScCompetition;
import com.ruoyi.project.sc.competition.service.IScCompetitionService;
import com.ruoyi.common.utils.text.Convert;

/**
 * competitionService业务层处理
 * 
 * @author larthur
 * @date 2024-10-13
 */
@Service
public class ScCompetitionServiceImpl implements IScCompetitionService 
{
    @Autowired
    private ScCompetitionMapper scCompetitionMapper;

    /**
     * 查询competition
     * 
     * @param competiitonId competition主键
     * @return competition
     */
    @Override
    public ScCompetition selectScCompetitionByCompetiitonId(Long competiitonId)
    {
        return scCompetitionMapper.selectScCompetitionByCompetiitonId(competiitonId);
    }

    /**
     * 查询competition列表
     * 
     * @param scCompetition competition
     * @return competition
     */
    @Override
    public List<ScCompetition> selectScCompetitionList(ScCompetition scCompetition)
    {
        return scCompetitionMapper.selectScCompetitionList(scCompetition);
    }

    /**
     * 新增competition
     * 
     * @param scCompetition competition
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScCompetition(ScCompetition scCompetition)
    {
        int rows = scCompetitionMapper.insertScCompetition(scCompetition);
        insertScCollege(scCompetition);
        return rows;
    }

    /**
     * 修改competition
     * 
     * @param scCompetition competition
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScCompetition(ScCompetition scCompetition)
    {
        scCompetitionMapper.deleteScCollegeByCompetitionId(scCompetition.getCompetiitonId());
        insertScCollege(scCompetition);
        return scCompetitionMapper.updateScCompetition(scCompetition);
    }

    /**
     * 批量删除competition
     * 
     * @param competiitonIds 需要删除的competition主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScCompetitionByCompetiitonIds(String competiitonIds)
    {
        scCompetitionMapper.deleteScCollegeByCompetitionIds(Convert.toStrArray(competiitonIds));
        return scCompetitionMapper.deleteScCompetitionByCompetiitonIds(Convert.toStrArray(competiitonIds));
    }

    /**
     * 删除competition信息
     * 
     * @param competiitonId competition主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScCompetitionByCompetiitonId(Long competiitonId)
    {
        scCompetitionMapper.deleteScCollegeByCompetitionId(competiitonId);
        return scCompetitionMapper.deleteScCompetitionByCompetiitonId(competiitonId);
    }

    /**
     * 新增college信息
     * 
     * @param scCompetition competition对象
     */
    public void insertScCollege(ScCompetition scCompetition)
    {
        List<ScCollege> scCollegeList = scCompetition.getScCollegeList();
        Long competiitonId = scCompetition.getCompetiitonId();
        if (StringUtils.isNotNull(scCollegeList))
        {
            List<ScCollege> list = new ArrayList<ScCollege>();
            for (ScCollege scCollege : scCollegeList)
            {
                scCollege.setCompetitionId(competiitonId);
                list.add(scCollege);
            }
            if (list.size() > 0)
            {
                scCompetitionMapper.batchScCollege(list);
            }
        }
    }
}

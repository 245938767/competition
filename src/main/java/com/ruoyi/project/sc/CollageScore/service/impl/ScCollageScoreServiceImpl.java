package com.ruoyi.project.sc.CollageScore.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.sc.CollageScore.mapper.ScCollageScoreMapper;
import com.ruoyi.project.sc.CollageScore.domain.ScCollageScore;
import com.ruoyi.project.sc.CollageScore.service.IScCollageScoreService;
import com.ruoyi.common.utils.text.Convert;

/**
 * CollageScoreService业务层处理
 * 
 * @author larthur
 * @date 2024-10-12
 */
@Service
public class ScCollageScoreServiceImpl implements IScCollageScoreService 
{
    @Autowired
    private ScCollageScoreMapper scCollageScoreMapper;

    /**
     * 查询CollageScore
     * 
     * @param id CollageScore主键
     * @return CollageScore
     */
    @Override
    public ScCollageScore selectScCollageScoreById(Long id)
    {
        return scCollageScoreMapper.selectScCollageScoreById(id);
    }

    /**
     * 查询CollageScore列表
     * 
     * @param scCollageScore CollageScore
     * @return CollageScore
     */
    @Override
    public List<ScCollageScore> selectScCollageScoreList(ScCollageScore scCollageScore)
    {
        return scCollageScoreMapper.selectScCollageScoreList(scCollageScore);
    }

    /**
     * 新增CollageScore
     * 
     * @param scCollageScore CollageScore
     * @return 结果
     */
    @Override
    public int insertScCollageScore(ScCollageScore scCollageScore)
    {
        return scCollageScoreMapper.insertScCollageScore(scCollageScore);
    }

    /**
     * 修改CollageScore
     * 
     * @param scCollageScore CollageScore
     * @return 结果
     */
    @Override
    public int updateScCollageScore(ScCollageScore scCollageScore)
    {
        return scCollageScoreMapper.updateScCollageScore(scCollageScore);
    }

    /**
     * 批量删除CollageScore
     * 
     * @param ids 需要删除的CollageScore主键
     * @return 结果
     */
    @Override
    public int deleteScCollageScoreByIds(String ids)
    {
        return scCollageScoreMapper.deleteScCollageScoreByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除CollageScore信息
     * 
     * @param id CollageScore主键
     * @return 结果
     */
    @Override
    public int deleteScCollageScoreById(Long id)
    {
        return scCollageScoreMapper.deleteScCollageScoreById(id);
    }
}

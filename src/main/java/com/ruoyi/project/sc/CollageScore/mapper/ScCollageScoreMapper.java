package com.ruoyi.project.sc.CollageScore.mapper;

import java.util.List;
import com.ruoyi.project.sc.CollageScore.domain.ScCollageScore;

/**
 * CollageScoreMapper接口
 * 
 * @author larthur
 * @date 2024-10-13
 */
public interface ScCollageScoreMapper 
{
    /**
     * 查询CollageScore
     * 
     * @param id CollageScore主键
     * @return CollageScore
     */
    public ScCollageScore selectScCollageScoreById(Long id);

    /**
     * 查询CollageScore列表
     * 
     * @param scCollageScore CollageScore
     * @return CollageScore集合
     */
    public List<ScCollageScore> selectScCollageScoreList(ScCollageScore scCollageScore);

    /**
     * 新增CollageScore
     * 
     * @param scCollageScore CollageScore
     * @return 结果
     */
    public int insertScCollageScore(ScCollageScore scCollageScore);

    /**
     * 修改CollageScore
     * 
     * @param scCollageScore CollageScore
     * @return 结果
     */
    public int updateScCollageScore(ScCollageScore scCollageScore);

    /**
     * 删除CollageScore
     * 
     * @param id CollageScore主键
     * @return 结果
     */
    public int deleteScCollageScoreById(Long id);

    /**
     * 批量删除CollageScore
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScCollageScoreByIds(String[] ids);
}

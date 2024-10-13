package com.ruoyi.project.sc.college.mapper;

import java.util.List;
import com.ruoyi.project.sc.college.domain.ScCollege;
import com.ruoyi.project.sc.players.domain.ScPlayers;

/**
 * collegeMapper接口
 * 
 * @author larthur
 * @date 2024-10-13
 */
public interface ScCollegeMapper 
{
    /**
     * 查询college
     * 
     * @param collegeId college主键
     * @return college
     */
    public ScCollege selectScCollegeByCollegeId(Long collegeId);

    /**
     * 查询college列表
     * 
     * @param scCollege college
     * @return college集合
     */
    public List<ScCollege> selectScCollegeList(ScCollege scCollege);

    /**
     * 新增college
     * 
     * @param scCollege college
     * @return 结果
     */
    public int insertScCollege(ScCollege scCollege);

    /**
     * 修改college
     * 
     * @param scCollege college
     * @return 结果
     */
    public int updateScCollege(ScCollege scCollege);

    /**
     * 删除college
     * 
     * @param collegeId college主键
     * @return 结果
     */
    public int deleteScCollegeByCollegeId(Long collegeId);

    /**
     * 批量删除college
     * 
     * @param collegeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScCollegeByCollegeIds(String[] collegeIds);

    /**
     * 批量删除players
     * 
     * @param collegeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScPlayersByCollegeIds(String[] collegeIds);
    
    /**
     * 批量新增players
     * 
     * @param scPlayersList players列表
     * @return 结果
     */
    public int batchScPlayers(List<ScPlayers> scPlayersList);
    

    /**
     * 通过college主键删除players信息
     * 
     * @param collegeId collegeID
     * @return 结果
     */
    public int deleteScPlayersByCollegeId(Long collegeId);
}

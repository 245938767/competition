package com.ruoyi.project.sc.college.service;

import java.util.List;
import com.ruoyi.project.sc.college.domain.ScCollege;

/**
 * collegeService接口
 * 
 * @author larthur
 * @date 2024-10-13
 */
public interface IScCollegeService 
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
     * 批量删除college
     * 
     * @param collegeIds 需要删除的college主键集合
     * @return 结果
     */
    public int deleteScCollegeByCollegeIds(String collegeIds);

    /**
     * 删除college信息
     * 
     * @param collegeId college主键
     * @return 结果
     */
    public int deleteScCollegeByCollegeId(Long collegeId);
}

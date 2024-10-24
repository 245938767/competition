package com.ruoyi.project.sc.college.service.impl;

import java.util.List;

import com.ruoyi.project.sc.CollageScore.domain.ScCollageScore;
import com.ruoyi.project.sc.CollageScore.mapper.ScCollageScoreMapper;
import com.ruoyi.project.sc.players.domain.ScPlayers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.project.sc.college.mapper.ScCollegeMapper;
import com.ruoyi.project.sc.college.domain.ScCollege;
import com.ruoyi.project.sc.college.service.IScCollegeService;
import com.ruoyi.common.utils.text.Convert;

/**
 * collegeService业务层处理
 * 
 * @author larthur
 * @date 2024-10-13
 */
@Service
public class ScCollegeServiceImpl implements IScCollegeService 
{
    @Autowired
    private ScCollegeMapper scCollegeMapper;
    @Autowired
    private ScCollageScoreMapper scCollageScoreMapper;

    /**
     * 查询college
     * 
     * @param collegeId college主键
     * @return college
     */
    @Override
    public ScCollege selectScCollegeByCollegeId(Long collegeId)
    {
        return scCollegeMapper.selectScCollegeByCollegeId(collegeId);
    }

    /**
     * 查询college列表
     * 
     * @param scCollege college
     * @return college
     */
    @Override
    public List<ScCollege> selectScCollegeList(ScCollege scCollege)
    {
        return scCollegeMapper.selectScCollegeList(scCollege);
    }

    /**
     * 新增college
     * 
     * @param scCollege college
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScCollege(ScCollege scCollege)
    {
        int rows = scCollegeMapper.insertScCollege(scCollege);
        insertScPlayers(scCollege);
        return rows;
    }

    /**
     * 修改college
     * 
     * @param scCollege college
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScCollege(ScCollege scCollege)
    {
        scCollegeMapper.deleteScPlayersByCollegeId(scCollege.getCollegeId());
        insertScPlayers(scCollege);
        return scCollegeMapper.updateScCollege(scCollege);
    }

    /**
     * 批量删除college
     * 
     * @param collegeIds 需要删除的college主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScCollegeByCollegeIds(String collegeIds)
    {
        scCollegeMapper.deleteScPlayersByCollegeIds(Convert.toStrArray(collegeIds));
        return scCollegeMapper.deleteScCollegeByCollegeIds(Convert.toStrArray(collegeIds));
    }

    /**
     * 删除college信息
     * 
     * @param collegeId college主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScCollegeByCollegeId(Long collegeId)
    {
        scCollegeMapper.deleteScPlayersByCollegeId(collegeId);
        return scCollegeMapper.deleteScCollegeByCollegeId(collegeId);
    }

    /**
     * 新增players信息
     * 
     * @param scCollege college对象
     */
    public void insertScPlayers(ScCollege scCollege)
    {
        List<ScPlayers> scPlayersList = scCollege.getScPlayersList();
        Long collegeId = scCollege.getCollegeId();
        if (StringUtils.isNotNull(scPlayersList))
        {
            List<ScPlayers> list = new ArrayList<ScPlayers>();
            for (ScPlayers scPlayers : scPlayersList)
            {
                scPlayers.setCollegeId(collegeId);
                list.add(scPlayers);
            }
            if (list.size() > 0)
            {
                scCollegeMapper.batchScPlayers(list);
            }
        }
    }
}

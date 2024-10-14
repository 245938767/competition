package com.ruoyi.project.sc.competition.service;

import java.util.List;

import com.ruoyi.project.sc.competition.domain.CompetitionListVO;
import com.ruoyi.project.sc.competition.domain.ScCompetition;
import com.ruoyi.project.socket.NoticeWebsocketResp;

/**
 * competitionService接口
 *
 * @author larthur
 * @date 2024-10-13
 */
public interface IScCompetitionService {
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

    /**
     * 清除比赛数据
     */
    public boolean cleanCompetitionData(Long id);

    /**
     * 保存比赛数据
     *
     * @return
     */
    public boolean saveCompetition();

    /**
     * 开始比赛
     *
     * @return
     */
    public boolean startCompetition(Long id, Long type);

    /**
     * 重置比赛出场顺序
     *
     * @param id
     * @return
     */
    public boolean restoreSort(Long id);


    /**
     * 获得比赛名单数据
     *
     * @param id
     * @return
     */
    public List<CompetitionListVO> selectbatchCompetitionList(Long id);

    public ScCompetition getCurrentCompetition(Long id);

    /**
     * 获得websocket数据
     *
     * @param id
     * @return
     */
    public NoticeWebsocketResp getCurrentCompetitionData(Long id);

    /**
     * 下一场
     *
     * @param id
     * @return
     */
    public boolean nextPlayer(Long id);

    /**
     * 上一场
     *
     * @param id
     * @return
     */
    public boolean lastPlayer(Long id);
}

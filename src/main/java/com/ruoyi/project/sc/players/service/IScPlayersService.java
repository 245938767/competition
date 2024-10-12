package com.ruoyi.project.sc.players.service;

import java.util.List;
import com.ruoyi.project.sc.players.domain.ScPlayers;

/**
 * playersService接口
 * 
 * @author larthur
 * @date 2024-10-12
 */
public interface IScPlayersService 
{
    /**
     * 查询players
     * 
     * @param playerId players主键
     * @return players
     */
    public ScPlayers selectScPlayersByPlayerId(Long playerId);

    /**
     * 查询players列表
     * 
     * @param scPlayers players
     * @return players集合
     */
    public List<ScPlayers> selectScPlayersList(ScPlayers scPlayers);

    /**
     * 新增players
     * 
     * @param scPlayers players
     * @return 结果
     */
    public int insertScPlayers(ScPlayers scPlayers);

    /**
     * 修改players
     * 
     * @param scPlayers players
     * @return 结果
     */
    public int updateScPlayers(ScPlayers scPlayers);

    /**
     * 批量删除players
     * 
     * @param playerIds 需要删除的players主键集合
     * @return 结果
     */
    public int deleteScPlayersByPlayerIds(String playerIds);

    /**
     * 删除players信息
     * 
     * @param playerId players主键
     * @return 结果
     */
    public int deleteScPlayersByPlayerId(Long playerId);
}

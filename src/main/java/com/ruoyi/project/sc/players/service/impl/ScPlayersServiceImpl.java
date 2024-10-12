package com.ruoyi.project.sc.players.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.sc.players.mapper.ScPlayersMapper;
import com.ruoyi.project.sc.players.domain.ScPlayers;
import com.ruoyi.project.sc.players.service.IScPlayersService;
import com.ruoyi.common.utils.text.Convert;

/**
 * playersService业务层处理
 * 
 * @author larthur
 * @date 2024-10-12
 */
@Service
public class ScPlayersServiceImpl implements IScPlayersService 
{
    @Autowired
    private ScPlayersMapper scPlayersMapper;

    /**
     * 查询players
     * 
     * @param playerId players主键
     * @return players
     */
    @Override
    public ScPlayers selectScPlayersByPlayerId(Long playerId)
    {
        return scPlayersMapper.selectScPlayersByPlayerId(playerId);
    }

    /**
     * 查询players列表
     * 
     * @param scPlayers players
     * @return players
     */
    @Override
    public List<ScPlayers> selectScPlayersList(ScPlayers scPlayers)
    {
        return scPlayersMapper.selectScPlayersList(scPlayers);
    }

    /**
     * 新增players
     * 
     * @param scPlayers players
     * @return 结果
     */
    @Override
    public int insertScPlayers(ScPlayers scPlayers)
    {
        return scPlayersMapper.insertScPlayers(scPlayers);
    }

    /**
     * 修改players
     * 
     * @param scPlayers players
     * @return 结果
     */
    @Override
    public int updateScPlayers(ScPlayers scPlayers)
    {
        return scPlayersMapper.updateScPlayers(scPlayers);
    }

    /**
     * 批量删除players
     * 
     * @param playerIds 需要删除的players主键
     * @return 结果
     */
    @Override
    public int deleteScPlayersByPlayerIds(String playerIds)
    {
        return scPlayersMapper.deleteScPlayersByPlayerIds(Convert.toStrArray(playerIds));
    }

    /**
     * 删除players信息
     * 
     * @param playerId players主键
     * @return 结果
     */
    @Override
    public int deleteScPlayersByPlayerId(Long playerId)
    {
        return scPlayersMapper.deleteScPlayersByPlayerId(playerId);
    }
}

package com.ruoyi.project.sc.players.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.sc.players.domain.ScPlayers;
import com.ruoyi.project.sc.players.service.IScPlayersService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * playersController
 * 
 * @author larthur
 * @date 2024-10-15
 */
@Controller
@RequestMapping("/players/players")
public class ScPlayersController extends BaseController
{
    private String prefix = "players/players";

    @Autowired
    private IScPlayersService scPlayersService;

    @RequiresPermissions("players:players:view")
    @GetMapping()
    public String players()
    {
        return prefix + "/players";
    }

    /**
     * 查询players列表
     */
    @RequiresPermissions("players:players:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ScPlayers scPlayers)
    {
        startPage();
        List<ScPlayers> list = scPlayersService.selectScPlayersList(scPlayers);
        return getDataTable(list);
    }

    /**
     * 导出players列表
     */
    @RequiresPermissions("players:players:export")
    @Log(title = "players", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScPlayers scPlayers)
    {
        List<ScPlayers> list = scPlayersService.selectScPlayersList(scPlayers);
        ExcelUtil<ScPlayers> util = new ExcelUtil<ScPlayers>(ScPlayers.class);
        return util.exportExcel(list, "players数据");
    }

    /**
     * 新增players
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存players
     */
    @RequiresPermissions("players:players:add")
    @Log(title = "players", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScPlayers scPlayers)
    {
        return toAjax(scPlayersService.insertScPlayers(scPlayers));
    }

    /**
     * 修改players
     */
    @RequiresPermissions("players:players:edit")
    @GetMapping("/edit/{playerId}")
    public String edit(@PathVariable("playerId") Long playerId, ModelMap mmap)
    {
        ScPlayers scPlayers = scPlayersService.selectScPlayersByPlayerId(playerId);
        mmap.put("scPlayers", scPlayers);
        return prefix + "/edit";
    }

    /**
     * 修改保存players
     */
    @RequiresPermissions("players:players:edit")
    @Log(title = "players", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScPlayers scPlayers)
    {
        return toAjax(scPlayersService.updateScPlayers(scPlayers));
    }

    /**
     * 删除players
     */
    @RequiresPermissions("players:players:remove")
    @Log(title = "players", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(scPlayersService.deleteScPlayersByPlayerIds(ids));
    }
}

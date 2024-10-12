package com.ruoyi.project.sc.competition.controller;

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
import com.ruoyi.project.sc.competition.domain.ScCompetition;
import com.ruoyi.project.sc.competition.service.IScCompetitionService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * competitionController
 * 
 * @author larthur
 * @date 2024-10-12
 */
@Controller
@RequestMapping("/competition/competition")
public class ScCompetitionController extends BaseController
{
    private String prefix = "competition/competition";

    @Autowired
    private IScCompetitionService scCompetitionService;

    @RequiresPermissions("competition:competition:view")
    @GetMapping()
    public String competition()
    {
        return prefix + "/competition";
    }

    /**
     * 查询competition列表
     */
    @RequiresPermissions("competition:competition:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ScCompetition scCompetition)
    {
        startPage();
        List<ScCompetition> list = scCompetitionService.selectScCompetitionList(scCompetition);
        return getDataTable(list);
    }

    /**
     * 导出competition列表
     */
    @RequiresPermissions("competition:competition:export")
    @Log(title = "competition", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScCompetition scCompetition)
    {
        List<ScCompetition> list = scCompetitionService.selectScCompetitionList(scCompetition);
        ExcelUtil<ScCompetition> util = new ExcelUtil<ScCompetition>(ScCompetition.class);
        return util.exportExcel(list, "competition数据");
    }

    /**
     * 新增competition
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存competition
     */
    @RequiresPermissions("competition:competition:add")
    @Log(title = "competition", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScCompetition scCompetition)
    {
        return toAjax(scCompetitionService.insertScCompetition(scCompetition));
    }

    /**
     * 修改competition
     */
    @RequiresPermissions("competition:competition:edit")
    @GetMapping("/edit/{competiitonId}")
    public String edit(@PathVariable("competiitonId") Long competiitonId, ModelMap mmap)
    {
        ScCompetition scCompetition = scCompetitionService.selectScCompetitionByCompetiitonId(competiitonId);
        mmap.put("scCompetition", scCompetition);
        return prefix + "/edit";
    }

    /**
     * 修改保存competition
     */
    @RequiresPermissions("competition:competition:edit")
    @Log(title = "competition", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScCompetition scCompetition)
    {
        return toAjax(scCompetitionService.updateScCompetition(scCompetition));
    }

    /**
     * 删除competition
     */
    @RequiresPermissions("competition:competition:remove")
    @Log(title = "competition", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(scCompetitionService.deleteScCompetitionByCompetiitonIds(ids));
    }
}

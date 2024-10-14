package com.ruoyi.project.sc.sort.controller;

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
import com.ruoyi.project.sc.sort.domain.ScCompetitionSort;
import com.ruoyi.project.sc.sort.service.IScCompetitionSortService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 比赛流程Controller
 *
 * @author larthur
 * @date 2024-10-14
 */
@Controller
@RequestMapping("/sort/sort")
public class ScCompetitionSortController extends BaseController
{
    private String prefix = "sort/sort";

    @Autowired
    private IScCompetitionSortService scCompetitionSortService;

    @RequiresPermissions("sort:sort:view")
    @GetMapping()
    public String sort()
    {
        return prefix + "/sort";
    }

    /**
     * 查询比赛流程列表
     */
    @RequiresPermissions("sort:sort:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ScCompetitionSort scCompetitionSort)
    {
        startPage();
        List<ScCompetitionSort> list = scCompetitionSortService.selectScCompetitionSortList(scCompetitionSort);
        return getDataTable(list);
    }

    /**
     * 导出比赛流程列表
     */
    @RequiresPermissions("sort:sort:export")
    @Log(title = "比赛流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScCompetitionSort scCompetitionSort)
    {
        List<ScCompetitionSort> list = scCompetitionSortService.selectScCompetitionSortList(scCompetitionSort);
        ExcelUtil<ScCompetitionSort> util = new ExcelUtil<ScCompetitionSort>(ScCompetitionSort.class);
        return util.exportExcel(list, "比赛流程数据");
    }

    /**
     * 新增比赛流程
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存比赛流程
     */
    @RequiresPermissions("sort:sort:add")
    @Log(title = "比赛流程", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScCompetitionSort scCompetitionSort)
    {
        return toAjax(scCompetitionSortService.insertScCompetitionSort(scCompetitionSort));
    }

    /**
     * 修改比赛流程
     */
    @RequiresPermissions("sort:sort:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ScCompetitionSort scCompetitionSort = scCompetitionSortService.selectScCompetitionSortById(id);
        mmap.put("scCompetitionSort", scCompetitionSort);
        return prefix + "/edit";
    }

    /**
     * 修改保存比赛流程
     */
    @RequiresPermissions("sort:sort:edit")
    @Log(title = "比赛流程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScCompetitionSort scCompetitionSort)
    {
        return toAjax(scCompetitionSortService.updateScCompetitionSort(scCompetitionSort));
    }

    /**
     * 删除比赛流程
     */
    @RequiresPermissions("sort:sort:remove")
    @Log(title = "比赛流程", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(scCompetitionSortService.deleteScCompetitionSortByIds(ids));
    }
}

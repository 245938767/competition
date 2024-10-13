package com.ruoyi.project.sc.CollageScore.controller;

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
import com.ruoyi.project.sc.CollageScore.domain.ScCollageScore;
import com.ruoyi.project.sc.CollageScore.service.IScCollageScoreService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * CollageScoreController
 * 
 * @author larthur
 * @date 2024-10-13
 */
@Controller
@RequestMapping("/CollageScore/CollageScore")
public class ScCollageScoreController extends BaseController
{
    private String prefix = "CollageScore/CollageScore";

    @Autowired
    private IScCollageScoreService scCollageScoreService;

    @RequiresPermissions("CollageScore:CollageScore:view")
    @GetMapping()
    public String CollageScore()
    {
        return prefix + "/CollageScore";
    }

    /**
     * 查询CollageScore列表
     */
    @RequiresPermissions("CollageScore:CollageScore:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ScCollageScore scCollageScore)
    {
        startPage();
        List<ScCollageScore> list = scCollageScoreService.selectScCollageScoreList(scCollageScore);
        return getDataTable(list);
    }

    /**
     * 导出CollageScore列表
     */
    @RequiresPermissions("CollageScore:CollageScore:export")
    @Log(title = "CollageScore", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScCollageScore scCollageScore)
    {
        List<ScCollageScore> list = scCollageScoreService.selectScCollageScoreList(scCollageScore);
        ExcelUtil<ScCollageScore> util = new ExcelUtil<ScCollageScore>(ScCollageScore.class);
        return util.exportExcel(list, "CollageScore数据");
    }

    /**
     * 新增CollageScore
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存CollageScore
     */
    @RequiresPermissions("CollageScore:CollageScore:add")
    @Log(title = "CollageScore", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScCollageScore scCollageScore)
    {
        return toAjax(scCollageScoreService.insertScCollageScore(scCollageScore));
    }

    /**
     * 修改CollageScore
     */
    @RequiresPermissions("CollageScore:CollageScore:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ScCollageScore scCollageScore = scCollageScoreService.selectScCollageScoreById(id);
        mmap.put("scCollageScore", scCollageScore);
        return prefix + "/edit";
    }

    /**
     * 修改保存CollageScore
     */
    @RequiresPermissions("CollageScore:CollageScore:edit")
    @Log(title = "CollageScore", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScCollageScore scCollageScore)
    {
        return toAjax(scCollageScoreService.updateScCollageScore(scCollageScore));
    }

    /**
     * 删除CollageScore
     */
    @RequiresPermissions("CollageScore:CollageScore:remove")
    @Log(title = "CollageScore", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(scCollageScoreService.deleteScCollageScoreByIds(ids));
    }
}

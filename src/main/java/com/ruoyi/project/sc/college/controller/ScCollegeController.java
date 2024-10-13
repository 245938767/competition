package com.ruoyi.project.sc.college.controller;

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
import com.ruoyi.project.sc.college.domain.ScCollege;
import com.ruoyi.project.sc.college.service.IScCollegeService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * collegeController
 * 
 * @author larthur
 * @date 2024-10-13
 */
@Controller
@RequestMapping("/college/college")
public class ScCollegeController extends BaseController
{
    private String prefix = "college/college";

    @Autowired
    private IScCollegeService scCollegeService;

    @RequiresPermissions("college:college:view")
    @GetMapping()
    public String college()
    {
        return prefix + "/college";
    }

    /**
     * 查询college列表
     */
    @RequiresPermissions("college:college:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ScCollege scCollege)
    {
        startPage();
        List<ScCollege> list = scCollegeService.selectScCollegeList(scCollege);
        return getDataTable(list);
    }

    /**
     * 导出college列表
     */
    @RequiresPermissions("college:college:export")
    @Log(title = "college", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ScCollege scCollege)
    {
        List<ScCollege> list = scCollegeService.selectScCollegeList(scCollege);
        ExcelUtil<ScCollege> util = new ExcelUtil<ScCollege>(ScCollege.class);
        return util.exportExcel(list, "college数据");
    }

    /**
     * 新增college
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存college
     */
    @RequiresPermissions("college:college:add")
    @Log(title = "college", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScCollege scCollege)
    {
        return toAjax(scCollegeService.insertScCollege(scCollege));
    }

    /**
     * 修改college
     */
    @RequiresPermissions("college:college:edit")
    @GetMapping("/edit/{collegeId}")
    public String edit(@PathVariable("collegeId") Long collegeId, ModelMap mmap)
    {
        ScCollege scCollege = scCollegeService.selectScCollegeByCollegeId(collegeId);
        mmap.put("scCollege", scCollege);
        return prefix + "/edit";
    }

    /**
     * 修改保存college
     */
    @RequiresPermissions("college:college:edit")
    @Log(title = "college", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ScCollege scCollege)
    {
        return toAjax(scCollegeService.updateScCollege(scCollege));
    }

    /**
     * 删除college
     */
    @RequiresPermissions("college:college:remove")
    @Log(title = "college", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(scCollegeService.deleteScCollegeByCollegeIds(ids));
    }
}

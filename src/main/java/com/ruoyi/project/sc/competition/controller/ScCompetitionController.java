package com.ruoyi.project.sc.competition.controller;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.framework.interceptor.annotation.RepeatSubmit;
import com.ruoyi.project.sc.CollageScore.domain.ScCollageScore;
import com.ruoyi.project.sc.competition.domain.*;
import com.ruoyi.project.sc.competition.domain.export.CompetitionCaseListExport;
import com.ruoyi.project.sc.competition.domain.export.CompetitionTalkListExport;
import com.ruoyi.project.sc.competition.domain.export.UserListExport;
import com.ruoyi.project.sc.players.domain.ScPlayers;
import com.ruoyi.project.sc.sort.domain.ScCompetitionSort;
import com.ruoyi.project.socket.CompetitionWebSocket;
import com.ruoyi.project.socket.NoticeWebsocketResp;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.sc.competition.service.IScCompetitionService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;

/**
 * competitionController
 *
 * @author larthur
 * @date 2024-10-13
 */
@Controller
@RequestMapping("/competition/competition")
public class ScCompetitionController extends BaseController {
    private String prefix = "competition/competition";

    @Autowired
    private IScCompetitionService scCompetitionService;
    @Autowired
    private CompetitionWebSocket competitionWebSocket;

    @RequiresPermissions("competition:competition:view")
    @GetMapping()
    public String competition() {
        return prefix + "/competition";
    }

    /**
     * 查询competition列表
     */
    @RequiresPermissions("competition:competition:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ScCompetition scCompetition) {
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
    public AjaxResult export(ScCompetition scCompetition) {
        List<ScCompetition> list = scCompetitionService.selectScCompetitionList(scCompetition);
        ExcelUtil<ScCompetition> util = new ExcelUtil<ScCompetition>(ScCompetition.class);
        return util.exportExcel(list, "competition数据");
    }

    /**
     * 新增competition
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存competition
     */
    @RequiresPermissions("competition:competition:add")
    @Log(title = "competition", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ScCompetition scCompetition) {
        return toAjax(scCompetitionService.insertScCompetition(scCompetition));
    }

    /**
     * 修改competition
     */
    @RequiresPermissions("competition:competition:edit")
    @GetMapping("/edit/{competiitonId}")
    public String edit(@PathVariable("competiitonId") Long competiitonId, ModelMap mmap) {
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
    public AjaxResult editSave(ScCompetition scCompetition) {
        return toAjax(scCompetitionService.updateScCompetition(scCompetition));
    }

    /**
     * 删除competition
     */
    @RequiresPermissions("competition:competition:remove")
    @Log(title = "competition", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(scCompetitionService.deleteScCompetitionByCompetiitonIds(ids));
    }

    /**
     * 清除比赛数据
     *
     * @return
     */
    @Log(title = "competition", businessType = BusinessType.DELETE)
    @PostMapping("/cleanCompetitionData/{id}")
    @ResponseBody
    public AjaxResult cleanCompetitionData(@PathVariable Long id) {
        if (scCompetitionService.cleanCompetitionData(id)) {

            competitionWebSocket.sendMessage();
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    /**
     * 保存当前数据为Excel
     *
     * @return
     */
    @Log(title = "competition", businessType = BusinessType.EXPORT)
    @PostMapping("/saveCompetitionData")
    @ResponseBody
    public AjaxResult saveCompetitionData() {

        if (scCompetitionService.saveCompetition()) {

            competitionWebSocket.sendMessage();
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    /**
     * 开始比赛
     *
     * @param id
     * @param type
     * @return
     */
    @Log(title = "competition", businessType = BusinessType.OTHER)
    @PostMapping("/startCompetition")
    @ResponseBody
    public AjaxResult startCompetitionApi(@Param("id") Long id, @Param("type") Long type) {

        if (scCompetitionService.startCompetition(id, type)) {
            competitionWebSocket.sendMessage();
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }


    @Log(title = "competition", businessType = BusinessType.OTHER)
    @PostMapping("/reloadSort/{id}")
    @ResponseBody
    public AjaxResult reloadSort(@PathVariable Long id) {
        List<CompetitionListVO> competitionListVOS = scCompetitionService.restoreSort(id);
        competitionWebSocket.sendMessage();
        return AjaxResult.success(competitionListVOS);
    }

    @Log(title = "competition", businessType = BusinessType.OTHER)
    @PostMapping("/reloadSortList")
    @ResponseBody
    public AjaxResult reloadSortList(@Param("id") Long id, @Param("type") Long type) {

        List<CompetitionListVO> competitionListVOS = scCompetitionService.selectbatchCompetitionList(id, type);
        return AjaxResult.success(competitionListVOS);
    }

    @PostMapping("/selectCompetitionVOList/{id}")
    @ResponseBody
    public AjaxResult selectCompetitionVOList(@PathVariable Long id) {

        ScCompetition currentCompetition = scCompetitionService.getCurrentCompetition(id);
        List<CompetitionListVO> competitionListVOS = scCompetitionService.selectbatchCompetitionList(id, currentCompetition.getCurrentType());
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("list", competitionListVOS);
        stringObjectMap.put("sort", currentCompetition.getCurrentSort());
        stringObjectMap.put("type", currentCompetition.getCurrentType());
        return AjaxResult.success(stringObjectMap);
    }


    @PostMapping("/selectWebSocketData/{id}")
    @ResponseBody
    public AjaxResult selectWebSocketData(@PathVariable Long id) {
        NoticeWebsocketResp currentCompetitionData = scCompetitionService.getCurrentCompetitionData(id);
        return AjaxResult.success(currentCompetitionData);
    }

    @PostMapping("/nextPlayer/{id}")
    @ResponseBody
    public AjaxResult nextPlayer(@PathVariable Long id) {

        if (scCompetitionService.nextPlayer(id)) {
            competitionWebSocket.sendMessage();
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    @PostMapping("/LastPlayer/{id}")
    @ResponseBody
    public AjaxResult lastPlayer(@PathVariable Long id) {

        if (scCompetitionService.lastPlayer(id)) {
            competitionWebSocket.sendMessage();
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    /**
     * 评委评分
     *
     * @param scCollageScore
     * @return
     */
    @PostMapping("/judgeScore")
    @ResponseBody
    public AjaxResult judgeScore(@RequestBody ScCollageScore scCollageScore) {

        if (scCompetitionService.judgeScore(scCollageScore)) {
            competitionWebSocket.sendMessage();
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    @PostMapping("/TournamentRanking")
    @ResponseBody
    public AjaxResult TournamentRanking(@Param("id") Long id, @Param("type") int type) {
        HashMap<String, List<RankVo>> stringListHashMap = new HashMap<>();
        List<RankVo> rankList = scCompetitionService.getRankList(id, type);
        stringListHashMap.put("TournamentRanking", rankList);
        return AjaxResult.success(stringListHashMap);
    }

    /**
     * 导出比赛名单
     */
    @Log(title = "competitionExport", businessType = BusinessType.EXPORT)
    @PostMapping("/competitionExport")
    @ResponseBody
    public AjaxResult competitionExport(HttpServletResponse response, @Param("id") Long id) throws IOException {

        List<CompetitionListVO> competitionListVOS = scCompetitionService.selectbatchCompetitionList(id, 1L);
        ArrayList<CompetitionCaseListExport> competitionCaseListExports = new ArrayList<>();
        for (CompetitionListVO competitionListVO : competitionListVOS) {
            CompetitionCaseListExport competitionCaseListExport = getCompetitionListExport(competitionListVO);
            competitionCaseListExports.add(competitionCaseListExport);
        }

        List<CompetitionListVO> competitionListVOStalk = scCompetitionService.selectbatchCompetitionList(id, 3L);
        ArrayList<CompetitionTalkListExport> competitionTalkListExports = new ArrayList<>();
        for (CompetitionListVO competitionListVO : competitionListVOStalk) {
            CompetitionTalkListExport competitionTalkListExport = new CompetitionTalkListExport();
            competitionTalkListExport.setSort(competitionListVO.getSort());
            competitionTalkListExport.setCollegeA(competitionListVO.getUserA().getCollage());
            competitionTalkListExport.setUserAExports(competitionListVO.getUserA().getName());
            competitionTalkListExports.add(competitionTalkListExport);
        }
        ExcelUtil<CompetitionTalkListExport> talk = new ExcelUtil<CompetitionTalkListExport>(CompetitionTalkListExport.class);

        AjaxResult ajaxResult1 = talk.exportExcel(competitionTalkListExports, "谈心谈话名单", "厦门理工学院首届辅导员素质能力大赛比赛名单");
        ExcelUtil<CompetitionCaseListExport> util = new ExcelUtil<CompetitionCaseListExport>(CompetitionCaseListExport.class);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        AjaxResult ajaxResult = util.exportExcel(competitionCaseListExports, "案例分析名单", "厦门理工学院首届辅导员素质能力大赛比赛名单");
        String casePath = ExcelUtil.getAbsoluteFile(ajaxResult.get("msg").toString());
        String talkPath = ExcelUtil.getAbsoluteFile(ajaxResult1.get("msg").toString());
        List<File> fileList = new ArrayList<>();
        fileList.add(new File(casePath));
        fileList.add(new File(talkPath));
        String absoluteFile = ExcelUtil.getAbsoluteFile(UUID.fastUUID() + "厦门理工学院首届辅导员素质能力大赛比赛名单.zip");
        FileOutputStream fos2 = new FileOutputStream(new File(absoluteFile));
        toZip(fileList, fos2);

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        FileUtils.setAttachmentResponseHeader(response, "厦门理工学院首届辅导员素质能力大赛比赛名单.zip");
        FileUtils.writeBytes(absoluteFile, response.getOutputStream());
        return AjaxResult.success(stringStringHashMap);

    }

    /**
     * 导出排名名单
     *
     * @param response
     * @param id
     * @return
     * @throws IOException
     */
    @PostMapping("rankExport/{id}")
    @ResponseBody
    public AjaxResult competitionRnakExport(HttpServletResponse response, @Param("id") Long id) throws IOException {
        String s = scCompetitionService.competitionRankExport(id);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        FileUtils.setAttachmentResponseHeader(response, "厦门理工学院首届辅导员素质能力大赛各项分数明细和排名.xlsx");
        FileUtils.writeBytes(s, response.getOutputStream());
        return AjaxResult.success();
    }

    @GetMapping("/insertData/{id}")
    @ResponseBody
    public AjaxResult insertData(@PathVariable Long id) {

        scCompetitionService.insertData(id);
        return AjaxResult.success();


    }

    private static CompetitionCaseListExport getCompetitionListExport(CompetitionListVO competitionListVO) {
        CompetitionCaseListExport competitionCaseListExport = new CompetitionCaseListExport();
        competitionCaseListExport.setSort(competitionListVO.getSort());
        UserListExport userListExport = new UserListExport();
        userListExport.setUserExports(competitionListVO.getUserA().getName());
        userListExport.setCollege(competitionListVO.getUserA().getCollage());
        UserListExport userListExportb = new UserListExport();
        userListExportb.setUserExports(competitionListVO.getUserB().getName());
        userListExportb.setCollege(competitionListVO.getUserB().getCollage());
        ArrayList<UserListExport> userListExports = new ArrayList<>();
        userListExports.add(userListExport);
        userListExports.add(userListExportb);
        competitionCaseListExport.setUserListExportList(userListExports);
        return competitionCaseListExport;
    }

    public static void toZip(List<File> srcFiles, OutputStream out) throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

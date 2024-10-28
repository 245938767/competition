package com.ruoyi.project.sc.competition.service.impl;

import java.util.*;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.sc.CollageScore.domain.ScCollageScore;
import com.ruoyi.project.sc.CollageScore.mapper.ScCollageScoreMapper;
import com.ruoyi.project.sc.college.domain.ScCollege;
import com.ruoyi.project.sc.college.mapper.ScCollegeMapper;
import com.ruoyi.project.sc.competition.domain.*;
import com.ruoyi.project.sc.competition.domain.export.CompetitionCaseListExport;
import com.ruoyi.project.sc.competition.domain.export.CompetitionScoreListExport;
import com.ruoyi.project.sc.competition.domain.export.CompetitionUserScoreExport;
import com.ruoyi.project.sc.players.domain.ScPlayers;
import com.ruoyi.project.sc.players.mapper.ScPlayersMapper;
import com.ruoyi.project.sc.sort.domain.ScCompetitionSort;
import com.ruoyi.project.sc.sort.mapper.ScCompetitionSortMapper;
import com.ruoyi.project.socket.CompetitionCurrentData;
import com.ruoyi.project.socket.NoticeWebsocketResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.project.sc.competition.mapper.ScCompetitionMapper;
import com.ruoyi.project.sc.competition.service.IScCompetitionService;
import com.ruoyi.common.utils.text.Convert;

/**
 * competitionService业务层处理
 *
 * @author larthur
 * @date 2024-10-13
 */
@Service
public class ScCompetitionServiceImpl implements IScCompetitionService {
    @Autowired
    private ScCompetitionMapper scCompetitionMapper;
    @Autowired
    private ScCollageScoreMapper scCollageScoreMapper;
    @Autowired
    private ScCollegeMapper scCollegeMapper;
    @Autowired
    private ScCompetitionSortMapper scCompetitionSortMapper;
    @Autowired
    private ScPlayersMapper scPlayersMapper;

    /**
     * 查询competition
     *
     * @param competiitonId competition主键
     * @return competition
     */
    @Override
    public ScCompetition selectScCompetitionByCompetiitonId(Long competiitonId) {
        return scCompetitionMapper.selectScCompetitionByCompetiitonId(competiitonId);
    }

    /**
     * 查询competition列表
     *
     * @param scCompetition competition
     * @return competition
     */
    @Override
    public List<ScCompetition> selectScCompetitionList(ScCompetition scCompetition) {
        return scCompetitionMapper.selectScCompetitionList(scCompetition);
    }

    /**
     * 新增competition
     *
     * @param scCompetition competition
     * @return 结果
     */
    @Transactional
    @Override
    public int insertScCompetition(ScCompetition scCompetition) {
        int rows = scCompetitionMapper.insertScCompetition(scCompetition);
        insertScCollege(scCompetition);
        return rows;
    }

    /**
     * 修改competition
     *
     * @param scCompetition competition
     * @return 结果
     */
    @Transactional
    @Override
    public int updateScCompetition(ScCompetition scCompetition) {
        scCompetitionMapper.deleteScCollegeByCompetitionId(scCompetition.getCompetiitonId());
        insertScCollege(scCompetition);
        return scCompetitionMapper.updateScCompetition(scCompetition);
    }

    /**
     * 批量删除competition
     *
     * @param competiitonIds 需要删除的competition主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScCompetitionByCompetiitonIds(String competiitonIds) {
        scCompetitionMapper.deleteScCollegeByCompetitionIds(Convert.toStrArray(competiitonIds));
        return scCompetitionMapper.deleteScCompetitionByCompetiitonIds(Convert.toStrArray(competiitonIds));
    }

    /**
     * 删除competition信息
     *
     * @param competiitonId competition主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteScCompetitionByCompetiitonId(Long competiitonId) {
        scCompetitionMapper.deleteScCollegeByCompetitionId(competiitonId);
        return scCompetitionMapper.deleteScCompetitionByCompetiitonId(competiitonId);
    }

    @Transactional
    @Override
    public boolean cleanCompetitionData(Long id) {
        try {

            // clean competition score and sort
            ScCompetitionSort scCompetitionSort = new ScCompetitionSort();
            scCompetitionSort.setCompetitionId(id);
            List<ScCompetitionSort> scCompetitionSorts = scCompetitionSortMapper.selectScCompetitionSortList(scCompetitionSort);
            if (!scCompetitionSorts.isEmpty()) {
                scCompetitionSortMapper.deleteScCompetitionSortByIds(scCompetitionSorts.stream().map(x -> String.valueOf(x.getId())).toArray(String[]::new));
            }
            //clear competition state
            ScCompetition scCompetition = scCompetitionMapper.selectScCompetitionByCompetiitonId(id);
            scCompetition.setCurrentSort(0L);
            scCompetition.setCurrentType(0L);
            scCompetitionMapper.updateScCompetition(scCompetition);
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    @Override
    public boolean saveCompetition() {
        // save to the file
        // save collage information average and total score
        return true;
    }

    @Transactional
    @Override
    public boolean startCompetition(Long id, Long type) {

        ScCompetitionSort scCompetitionSort = new ScCompetitionSort();
        scCompetitionSort.setCompetitionId(id);
        List<ScCompetitionSort> scCompetitionSorts = scCompetitionSortMapper.selectScCompetitionSortList(scCompetitionSort);
        if (scCompetitionSorts.isEmpty()) {
            throw new RuntimeException("没有创建比赛名单");
        }
        // set the competition current collage and competition process
        ScCompetition scCompetition = scCompetitionMapper.selectScCompetitionByCompetiitonId(id);
        scCompetition.setCurrentType(type);
        scCompetition.setCurrentSort(1L);
        return scCompetitionMapper.updateScCompetition(scCompetition) > 0;
    }

    @Transactional
    @Override
    public List<CompetitionListVO> restoreSort(Long id) {

        ScCompetitionSort scCompetitionSort = new ScCompetitionSort();
        scCompetitionSort.setCompetitionId(id);
        List<ScCompetitionSort> scCompetitionSorts = scCompetitionSortMapper.selectScCompetitionSortList(scCompetitionSort);
        if (!scCompetitionSorts.isEmpty()) {
            scCompetitionSortMapper.deleteScCompetitionSortByIds(scCompetitionSorts.stream().map(x -> String.valueOf(x.getId())).toArray(String[]::new));
        }
        // start the competition process
        //根据比赛中学院id，学生，进行分配，一个学院中有A学生和B学生需要把当前学院的A学生he另一个不是当前学院的B学生进行组合

        ScCollege scCollege = new ScCollege();
        scCollege.setCompetitionId(id);
        List<ScCollege> scColleges = scCollegeMapper.selectScCollegeList(scCollege);
        if (scColleges.isEmpty()) {
            throw new ServiceException("没有创建学院信息");
        }
        //清除collegesocre数据
        List<ScCollageScore> scCollageScores = scCollageScoreMapper.selectScCollageScoreList(null);
        for (ScCollageScore scCollageScore : scCollageScores) {
            scCollageScoreMapper.deleteScCollageScoreById(scCollageScore.getId());
        }

        ScCompetition scCompetition = scCompetitionMapper.selectScCompetitionByCompetiitonId(id);
        scCompetition.setCurrentType(0L);
        scCompetition.setCurrentSort(1L);
        scCompetitionMapper.updateScCompetition(scCompetition);
        List<ScCompetitionSort> combinations = createCombinations(scColleges, id);
        for (ScCompetitionSort combination : combinations) {
            scCompetitionSortMapper.insertScCompetitionSort(combination);
        }

        List<CompetitionListVO> list = new ArrayList<>();
        ScPlayers scPlayers = new ScPlayers();
        List<ScPlayers> scPlayers1 = scPlayersMapper.selectScPlayersList(scPlayers);
        if (scPlayers1.isEmpty()) {
            return list;
        }

        Map<Long, ScPlayers> playerCollegeMap = new HashMap<>();
        scPlayers1.forEach(x -> playerCollegeMap.put(x.getPlayerId(), x));
        for (ScCompetitionSort competitionSort : scCompetitionSorts) {
            CompetitionListVO competitionListVO = new CompetitionListVO();

            ScPlayers scPlayersA = playerCollegeMap.get(competitionSort.getUser1());
            competitionListVO.setUserA(new CompetitionUser(scPlayersA.getCollegeId(), competitionSort.getId(), scPlayersA.getPlayerId(), scPlayersA.getName(), scPlayersA.getScColleges().getName(), scPlayersA.getType()));

            ScPlayers scPlayersB = playerCollegeMap.get(competitionSort.getUser2());
            competitionListVO.setUserB(new CompetitionUser(scPlayersB.getCollegeId(), competitionSort.getId(), scPlayersB.getPlayerId(), scPlayersB.getName(), scPlayersB.getScColleges().getName(), scPlayersB.getType()));

            competitionListVO.setSort(competitionSort.getSort());
            competitionListVO.setSortId(competitionSort.getId());
            list.add(competitionListVO);
        }
        return list;
    }

    @Override
    public List<CompetitionListVO> selectbatchCompetitionList(Long id, Long type) {

        List<CompetitionListVO> list = new ArrayList<>();

        ScCompetitionSort scCompetitionSort = new ScCompetitionSort();
        scCompetitionSort.setCompetitionId(id);
        scCompetitionSort.setType(type);
        List<ScCompetitionSort> scCompetitionSorts = scCompetitionSortMapper.selectScCompetitionSortList(scCompetitionSort);
        if (scCompetitionSorts.isEmpty()) {
            return list;
        }
        ScPlayers scPlayers = new ScPlayers();
        List<ScPlayers> scPlayers1 = scPlayersMapper.selectScPlayersList(scPlayers);
        if (scPlayers1.isEmpty()) {
            return list;
        }

        Map<Long, ScPlayers> playerCollegeMap = new HashMap<>();
        scPlayers1.forEach(x -> playerCollegeMap.put(x.getPlayerId(), x));
        for (ScCompetitionSort competitionSort : scCompetitionSorts) {
            CompetitionListVO competitionListVO = new CompetitionListVO();

            ScPlayers scPlayersA = playerCollegeMap.get(competitionSort.getUser1());
            if (scPlayersA != null) {

                competitionListVO.setUserA(new CompetitionUser(scPlayersA.getCollegeId(), competitionSort.getId(), scPlayersA.getPlayerId(), scPlayersA.getName(), scPlayersA.getScColleges().getName(), scPlayersA.getType()));
            }

            ScPlayers scPlayersB = playerCollegeMap.get(competitionSort.getUser2());
            if (scPlayersB != null) {

                competitionListVO.setUserB(new CompetitionUser(scPlayersB.getCollegeId(), competitionSort.getId(), scPlayersB.getPlayerId(), scPlayersB.getName(), scPlayersB.getScColleges().getName(), scPlayersB.getType()));
            }

            competitionListVO.setSort(competitionSort.getSort());
            competitionListVO.setSortId(competitionSort.getId());
            list.add(competitionListVO);
        }
        return list;
    }

    @Override
    public ScCompetition getCurrentCompetition(Long id) {
        return scCompetitionMapper.selectScCompetitionByCompetiitonId(id);
    }

    @Override
    public NoticeWebsocketResp getCurrentCompetitionData(Long id) {
        NoticeWebsocketResp noticeWebsocketResp = new NoticeWebsocketResp();


        ScCompetition scCompetition = scCompetitionMapper.selectScCompetitionByCompetiitonId(id);
        CompetitionCurrentData competitionCurrentData = new CompetitionCurrentData();

        competitionCurrentData.setCurrentSort(scCompetition.getCurrentSort());
        competitionCurrentData.setCurrentType(scCompetition.getCurrentType());
        List<CompetitionListVO> competitionListVOS = selectbatchCompetitionList(id, scCompetition.getCurrentType());
        noticeWebsocketResp.setCompetitionListVOList(competitionListVOS);

        if (scCompetition.getCurrentSort() > 0L) {
            Optional<CompetitionListVO> first = competitionListVOS.stream().filter(x -> Objects.equals(x.getSort(), scCompetition.getCurrentSort())).findFirst();
            if (first.isPresent()) {
                //获得评委评分
                CompetitionUser competitionUserA = first.get().getUserA();
                CompetitionUser competitionUserB = first.get().getUserB();

                ScCollageScore scCollageScoreA = new ScCollageScore();
                scCollageScoreA.setPlayerId(competitionUserA.getPlayerId());
                List<ScCollageScore> scCollageScoresA = scCollageScoreMapper.selectScCollageScoreList(scCollageScoreA);


                ScCollageScore scCollageScoreB = new ScCollageScore();
                scCollageScoreB.setPlayerId(competitionUserB.getPlayerId());
                List<ScCollageScore> scCollageScoresB = scCollageScoreMapper.selectScCollageScoreList(scCollageScoreB);

                competitionUserA.setScCollageScores(scCollageScoresA);
                competitionUserB.setScCollageScores(scCollageScoresB);
                competitionCurrentData.setUserA(competitionUserA);
                competitionCurrentData.setUserB(competitionUserB);
            }
        }

        noticeWebsocketResp.setCurrentData(competitionCurrentData);
        return noticeWebsocketResp;
    }

    @Override
    public boolean nextPlayer(Long id) {
        ScCompetitionSort scCompetitionSort = new ScCompetitionSort();
        scCompetitionSort.setCompetitionId(id);
        List<ScCompetitionSort> scCompetitionSorts = scCompetitionSortMapper.selectScCompetitionSortList(scCompetitionSort);
        ScCompetition scCompetition = scCompetitionMapper.selectScCompetitionByCompetiitonId(id);
        long value = scCompetition.getCurrentSort() + 1;
        if (value > scCompetitionSorts.size()) {
            throw new ServiceException("已是最后一场");
        }
        scCompetition.setCurrentSort(value);
        return scCompetitionMapper.updateScCompetition(scCompetition) > 0;
    }

    @Override
    public boolean lastPlayer(Long id) {
        ScCompetition scCompetition = scCompetitionMapper.selectScCompetitionByCompetiitonId(id);
        long value = scCompetition.getCurrentSort() - 1;
        if (value < 0) {
            throw new ServiceException("已无场次");
        }
        scCompetition.setCurrentSort(value);
        return scCompetitionMapper.updateScCompetition(scCompetition) > 0;
    }

    @Override
    public boolean judgeScore(ScCollageScore scCollageScore) {
        if (scCollageScore.getScoreId() != null || scCollageScore.getScore() == null || scCollageScore.getJudgeId() == null || scCollageScore.getPlayerId() == null) {
            throw new ServiceException("请确保传入参数的完整性");
        }
        ScCollageScore scCollageScore1 = new ScCollageScore();
        scCollageScore1.setScoreId(scCollageScore.getScoreId());
        scCollageScore1.setJudgeId(scCollageScore.getJudgeId());
        scCollageScore1.setCollegeId(scCollageScore.getCollegeId());
        scCollageScore1.setPlayerId(scCollageScore.getPlayerId());
        List<ScCollageScore> scCollageScores = scCollageScoreMapper.selectScCollageScoreList(scCollageScore1);
        if (!scCollageScores.isEmpty()) {
            return scCollageScoreMapper.updateScCollageScore(scCollageScore) > 0;
        }
        return scCollageScoreMapper.insertScCollageScore(scCollageScore) > 0;
    }

    @Override
    public List<RankVo> getRankList(Long id, int type) {
        List<ScPlayers> scPlayers = scPlayersMapper.selectScPlayersList(null);
        ScCollege scCollege = new ScCollege();
        scCollege.setCompetitionId(id);
        List<ScCollege> scColleges = scCollegeMapper.selectScCollegeList(scCollege);
        ArrayList<RankVo> rankVos = new ArrayList<>();
        switch (type) {
            //一等奖
            //二等奖
            //三等奖
            case 1:
                List<RankVo> rankVosInner = getTotalRanking(scColleges);
                for (int i = 0; i < rankVosInner.size(); i++) {
                    RankVo rankVo = rankVosInner.get(i);
                    rankVo.setSort(i + 1);
                    if (rankVo.getSort() == 1) {
                        rankVo.setName("一等奖");
                        rankVos.add(rankVo);
                    } else if (rankVo.getSort() > 1 && rankVo.getSort() <= 3) {
                        rankVo.setName("二等奖");
                        rankVos.add(rankVo);
                    } else if (rankVo.getSort() > 3 && rankVo.getSort() <= 8) {
                        rankVo.setName("三等奖");
                        rankVos.add(rankVo);
                    }
                }

                break;
            case 4:

                //基础能力奖10
                List<ScPlayers> limit = scPlayers.stream().sorted(Comparator.comparing(ScPlayers::getBasicScore).reversed()).limit(10).collect(Collectors.toList());
                for (int i = 0; i < limit.size(); i++) {
                    ScPlayers x = limit.get(i);
                    RankVo rankVo = new RankVo();
                    rankVo.setCollegeId(x.getCollegeId());
                    rankVo.setName(scColleges.stream().filter(z -> z.getCollegeId().equals(x.getCollegeId())).findFirst().get().getName());
                    rankVo.setScore(Float.valueOf(x.getBasicScore()));
                    rankVo.setSort(i + 1);
                    rankVo.setUser(x.getName());
                    rankVos.add(rankVo);
                }


                break;
            //案例研讨奖A B组共6个
            case 5:
                List<CaseScoreVO> caseScoreVOS = scPlayersMapper.selectUserCaseScore();
                List<Long> collectA = scPlayers.stream().filter(x -> x.getType() == 1).map(ScPlayers::getPlayerId).collect(Collectors.toList());
                List<Long> collectB = scPlayers.stream().filter(x -> x.getType() == 2).map(ScPlayers::getPlayerId).collect(Collectors.toList());
                int acount = 0;
                int bcount = 0;
                List<CaseScoreVO> collect = caseScoreVOS.stream().sorted(Comparator.comparing(CaseScoreVO::getScore).reversed()).collect(Collectors.toList());
                for (CaseScoreVO caseScoreVO : collect) {

                    ScPlayers scPlayersUser = scPlayers.stream().filter(x -> x.getPlayerId().equals(caseScoreVO.getUserId())).findFirst().get();
                    if (collectA.contains(caseScoreVO.getUserId())) {
                        if (acount >= 3) {
                            continue;
                        }
                        RankVo rankVo = new RankVo();
                        rankVo.setUser(scPlayersUser.getName());
                        rankVo.setName(scColleges.stream().filter(z -> z.getCollegeId().equals(scPlayersUser.getCollegeId())).findFirst().get().getName());
                        rankVo.setSort(acount + 1);
                        rankVo.setScore(caseScoreVO.getScore());
                        rankVos.add(rankVo);
                        acount++;

                    } else if (collectB.contains(caseScoreVO.getUserId())) {
                        if (bcount >= 3) {
                            continue;
                        }
                        RankVo rankVo = new RankVo();
                        rankVo.setUser(scPlayersUser.getName());
                        rankVo.setName(scColleges.stream().filter(z -> z.getCollegeId().equals(scPlayersUser.getCollegeId())).findFirst().get().getName());
                        rankVo.setSort(bcount + 1);
                        rankVo.setScore(caseScoreVO.getScore());
                        rankVos.add(rankVo);
                        bcount++;

                    }
                }

                break;
            //谈心谈话3个
            case 6:

                List<CaseScoreVO> talkScoreVO = scPlayersMapper.selectUserCaseScore();
                List<Long> collectTalk = scPlayers.stream().filter(x -> x.getType() == 3).map(ScPlayers::getPlayerId).collect(Collectors.toList());
                List<CaseScoreVO> collect1 = talkScoreVO.stream().filter(x -> collectTalk.contains(x.getUserId())).sorted(Comparator.comparing(CaseScoreVO::getScore).reversed()).limit(3).collect(Collectors.toList());
                int count = 1;
                for (CaseScoreVO caseScoreVO : collect1) {
                    ScPlayers scPlayersUser = scPlayers.stream().filter(x -> x.getPlayerId().equals(caseScoreVO.getUserId())).findFirst().get();
                    RankVo rankVo = new RankVo();
                    rankVo.setUser(scPlayersUser.getName());
                    rankVo.setName(scColleges.stream().filter(z -> z.getCollegeId().equals(scPlayersUser.getCollegeId())).findFirst().get().getName());
                    rankVo.setSort(count++);
                    rankVo.setScore(caseScoreVO.getScore());
                    rankVos.add(rankVo);
                }

                break;
        }
        return rankVos;
    }

    private List<RankVo> getTotalRanking(List<ScCollege> scColleges) {
        //比赛名单
        List<ScCompetitionSort> scCompetitionSorts = scCompetitionSortMapper.selectScCompetitionSortList(null);
        //比赛案例/谈心谈话打分数据
        List<CaseScoreVO> selectCaseScore = scPlayersMapper.selectUserCaseScore();
        //比赛基础分数据
        List<CaseScoreVO> selectCollegeBasicScore = scPlayersMapper.selectBasicScore();
        //学院-分数对象
        HashMap<Long, ScoreComputerVO> longScoreComputerVOHashMap = new HashMap<>();
        // 基础能力
        selectCollegeBasicScore.forEach((x) -> longScoreComputerVOHashMap.put(x.getCollegeId(), new ScoreComputerVO(x.getScore())));
        // 案例分析

        List<Long> caseCollect = scCompetitionSorts.stream().filter(x -> x.getType() == 1).map(ScCompetitionSort::getUser1).collect(Collectors.toList());
        caseCollect.addAll(scCompetitionSorts.stream().filter(x -> x.getType() == 1).map(ScCompetitionSort::getUser2).collect(Collectors.toList()));
        List<CaseScoreVO> caseCollectList = selectCaseScore.stream().filter(x -> caseCollect.contains(x.getUserId())).collect(Collectors.toList());
        caseCollectList.forEach(x -> {
            ScoreComputerVO scoreComputerVO = longScoreComputerVOHashMap.get(x.getCollegeId());
            if (scoreComputerVO == null) {
                ScoreComputerVO scoreComputerVO1 = new ScoreComputerVO();
                scoreComputerVO1.setCaseScore(x.getScore());
                longScoreComputerVOHashMap.put(x.getCollegeId(), scoreComputerVO1);
            } else {
                scoreComputerVO.setCaseScore(scoreComputerVO.getCaseScore() + x.getScore());
            }
        });
        // 谈心谈话
        List<Long> talkCollect = scCompetitionSorts.stream().filter(x -> x.getType() == 3).map(ScCompetitionSort::getUser1).collect(Collectors.toList());
        List<CaseScoreVO> talkCollectList = selectCaseScore.stream().filter(x -> talkCollect.contains(x.getUserId())).collect(Collectors.toList());
        talkCollectList.forEach(x -> {
            ScoreComputerVO scoreComputerVO = longScoreComputerVOHashMap.get(x.getCollegeId());
            if (scoreComputerVO == null) {
                ScoreComputerVO scoreComputerVO1 = new ScoreComputerVO();
                scoreComputerVO1.setTalkScore(x.getScore());
                longScoreComputerVOHashMap.put(x.getCollegeId(), scoreComputerVO1);
            } else {
                scoreComputerVO.setTalkScore(x.getScore());
            }
        });
        List<RankVo> rankVosInner = new ArrayList<RankVo>();
        longScoreComputerVOHashMap.forEach((x, y) -> {
            RankVo rankVo = new RankVo();
            rankVo.setCollegeId(x);
            rankVo.setUser(scColleges.stream().filter(z -> z.getCollegeId().equals(x)).findFirst().get().getName());
            rankVo.setScore(Float.valueOf(String.format("%.2f", (y.getBasicScore() * 0.3 + (y.getCaseScore() / 2) * 0.4 + y.getTalkScore() * 0.3))));

            rankVosInner.add(rankVo);
        });

        rankVosInner.sort(Comparator.comparing(RankVo::getScore).reversed());
        for (int i = 0; i < rankVosInner.size(); i++) {
            rankVosInner.get(i).setSort(i + 1);
        }
        return rankVosInner;
    }

    @Override
    public String competitionRankExport(Long id) {
        ScCollege scCollege = new ScCollege();
        scCollege.setCompetitionId(id);
        List<ScCollege> scColleges = scCollegeMapper.selectScCollegeList(scCollege);
        List<ScPlayers> scPlayers = scPlayersMapper.selectScPlayersList(null);
        List<Long> collectA = scPlayers.stream().filter(x -> x.getType() <= 2).map(ScPlayers::getPlayerId).collect(Collectors.toList());
        List<Long> collectB = scPlayers.stream().filter(x -> x.getType() == 3).map(ScPlayers::getPlayerId).collect(Collectors.toList());
        HashMap<Long, RankVo> caseUserMap = new HashMap<>();
        HashMap<Long, RankVo> talkUserMap = new HashMap<>();
        List<CaseScoreVO> caseScoreVOS = scPlayersMapper.selectUserCaseScore();
        List<CaseScoreVO> caseColloge = caseScoreVOS.stream().sorted(Comparator.comparing(CaseScoreVO::getScore).reversed()).collect(Collectors.toList());
        int ac = 1;
        int bc = 1;
        for (int i = 0; i < caseColloge.size(); i++) {
            CaseScoreVO caseScoreVO = caseColloge.get(i);
            RankVo rankVo = new RankVo();
            rankVo.setScore(caseScoreVO.getScore());
            if (collectA.contains(caseScoreVO.getUserId())) {
                rankVo.setSort(ac++);
                caseUserMap.put(caseScoreVO.getUserId(), rankVo);
            } else if (collectB.contains(caseScoreVO.getUserId())) {
                rankVo.setSort(bc++);
                talkUserMap.put(caseScoreVO.getUserId(), rankVo);
            }
        }
        List<RankVo> totalRanking = getTotalRanking(scColleges);
        ArrayList<CompetitionScoreListExport> competitionScoreListExports = new ArrayList<>();
        for (RankVo rankVo : totalRanking) {
            CompetitionScoreListExport competitionScoreListExport = new CompetitionScoreListExport();
            competitionScoreListExport.setRank((long) rankVo.getSort());
            competitionScoreListExport.setTotal(rankVo.getScore());
            competitionScoreListExport.setCollege(scColleges.stream().filter(x -> Objects.equals(x.getCollegeId(), rankVo.getCollegeId())).findFirst().get().getName());
            List<CompetitionUserScoreExport> competitionUserScoreExports = new ArrayList<>();
            //基础能力奖10
            List<ScPlayers> collect = scPlayers.stream().filter(x -> Objects.equals(x.getCollegeId(), rankVo.getCollegeId())).sorted(Comparator.comparing(ScPlayers::getType).reversed()).collect(Collectors.toList());
            for (ScPlayers players : collect) {
                CompetitionUserScoreExport competitionUserScoreExport = new CompetitionUserScoreExport();
                competitionUserScoreExport.setBasicScore(Float.valueOf(players.getBasicScore()));
                competitionUserScoreExport.setName(players.getName());

//                competitionUserScoreExport.setTalkScore(0F);
//                competitionUserScoreExport.setTalkRank(0L);
//                competitionUserScoreExport.setCaseScore(0F);
//                competitionUserScoreExport.setCaseRank(0L);
                //case
                RankVo casescore = caseUserMap.get(players.getPlayerId());
                if (casescore != null) {

                    competitionUserScoreExport.setCaseScore(casescore.getScore());
                    competitionUserScoreExport.setCaseRank((long) casescore.getSort());
                    competitionUserScoreExports.add(competitionUserScoreExport);
                    continue;
                }
                // talk
                RankVo talkcase = talkUserMap.get(players.getPlayerId());
                if (talkcase != null) {

                    competitionUserScoreExport.setTalkScore(talkcase.getScore());
                    competitionUserScoreExport.setTalkRank((long) talkcase.getSort());
                    continue;
                }


            }
            competitionScoreListExport.setUserScore(competitionUserScoreExports);
            competitionScoreListExports.add(competitionScoreListExport);
        }

        ExcelUtil<CompetitionScoreListExport> util = new ExcelUtil<CompetitionScoreListExport>(CompetitionScoreListExport.class);
        AjaxResult ajaxResult = util.exportExcel(competitionScoreListExports, "分数明细和排名", "厦门理工学院首届辅导员素质能力大赛各项分数明细和排名");
        String casePath = ExcelUtil.getAbsoluteFile(ajaxResult.get("msg").toString());
        return casePath;
    }

    @Override
    public void insertData(Long id) {
        List<ScPlayers> scPlayers = scPlayersMapper.selectScPlayersList(null);
        for (ScPlayers scPlayer : scPlayers) {
            for (int i = 0; i < 7; i++) {

                ScCollageScore scCollageScore = new ScCollageScore();
                scCollageScore.setPlayerId(scPlayer.getPlayerId());
                scCollageScore.setCollegeId(scPlayer.getCollegeId());
                scCollageScore.setJudgeId((long) (i + 1));
                //生成随机的分数80-90之间
                scCollageScore.setScore((long) (Math.random() * 10 + 80));
                scCollageScoreMapper.insertScCollageScore(scCollageScore);
            }

        }
    }

    /**
     * 新增college信息
     *
     * @param scCompetition competition对象
     */
    public void insertScCollege(ScCompetition scCompetition) {
        List<ScCollege> scCollegeList = scCompetition.getScCollegeList();
        Long competiitonId = scCompetition.getCompetiitonId();
        if (StringUtils.isNotNull(scCollegeList)) {
            List<ScCollege> list = new ArrayList<ScCollege>();
            for (ScCollege scCollege : scCollegeList) {
                scCollege.setCompetitionId(competiitonId);
                list.add(scCollege);
            }
            if (!list.isEmpty()) {
                scCompetitionMapper.batchScCollege(list);
            }
        }
    }


    public static List<ScCompetitionSort> createCombinations(List<ScCollege> colleges, Long competiitonId) {
        List<ScCompetitionSort> combinations = new ArrayList<>();
        List<ScPlayers> allAPlayers = new ArrayList<>();
        List<ScPlayers> allBPlayers = new ArrayList<>();
        Map<ScPlayers, ScCollege> playerCollegeMap = new HashMap<>();
        Map<Long, ScCollege> playerCollegeTwoMap = new HashMap<>();

        // 收集所有玩家并建立玩家与学院的映射
        for (ScCollege college : colleges) {
            for (ScPlayers player : college.getScPlayersList()) {
                if (player.getType() == 1) {
                    allAPlayers.add(player);
                } else if (player.getType() == 2) {

                    allBPlayers.add(player);
                }

                playerCollegeMap.put(player, college);
            }
            playerCollegeTwoMap.put(college.getCollegeId(), college);
        }

        // 打乱玩家顺序以确保随机性
        Collections.shuffle(allAPlayers);
        Collections.shuffle(allBPlayers);

        for (int i = 0; i < allAPlayers.size(); i++) {
            ScPlayers playerA = allAPlayers.get(i);
            ScCollege collegeA = playerCollegeMap.get(playerA);

            for (int j = 0; j < allBPlayers.size(); j++) {
                ScPlayers playerB = allBPlayers.get(j);
                ScCollege collegeB = playerCollegeMap.get(playerB);

                // 检查是否满足匹配条件
                if (isValidCombination(playerA, playerB, collegeA.getName(), collegeB.getName())) {
                    ScCompetitionSort scCompetitionSort = new ScCompetitionSort();
                    scCompetitionSort.setCompetitionId(competiitonId);
                    scCompetitionSort.setSort(i + 1L);
                    scCompetitionSort.setCollageId(collegeA.getCollegeId());
                    scCompetitionSort.setUser1(playerA.getPlayerId());
                    scCompetitionSort.setUser2(playerB.getPlayerId());
                    scCompetitionSort.setType(1L);

                    combinations.add(scCompetitionSort);
                    allBPlayers.remove(j); // 移除已匹配的玩家B
                    break;
                }
            }
        }

        ArrayList<ScCompetitionSort> scCompetitions = new ArrayList<>();
        for (ScCompetitionSort combination : combinations) {

            ScCollege scCollege = playerCollegeTwoMap.get(combination.getCollageId());
            ScCompetitionSort scCompetitionSort = new ScCompetitionSort();
            scCompetitionSort.setCompetitionId(competiitonId);
            scCompetitionSort.setSort(combination.getSort());
            scCompetitionSort.setCollageId(combination.getCollageId());
            scCompetitionSort.setUser1(scCollege.getScPlayersList().get(2).getPlayerId());
            scCompetitionSort.setUser2(scCollege.getScPlayersList().get(3).getPlayerId());
            scCompetitionSort.setType(3L);
            scCompetitions.add(scCompetitionSort);
        }
        combinations.addAll(scCompetitions);
        return combinations;
    }

    private static boolean isValidCombination(ScPlayers playerA, ScPlayers playerB, String collegeA, String
            collegeB) {
        // 提取学院名称（去掉"A队"或"B队"）
        String collegeNameA = extractCollegeName(collegeA);
        String collegeNameB = extractCollegeName(collegeB);

        // 不同学院的玩家可以匹配
        if (!collegeNameA.equals(collegeNameB)) {
            return true;
        }
        return false;
        // 同一学院的玩家，必须来自不同队伍才能匹配
//        return !Objects.equals(playerA.getType(), playerB.getType());
    }

    private static String extractCollegeName(String fullName) {
        // 假设学院名称格式为"学院X A队"或"学院X B队"
        return fullName.replaceAll("\\s[12]队$", "");
    }

}

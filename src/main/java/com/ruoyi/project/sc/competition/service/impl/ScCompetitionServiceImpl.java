package com.ruoyi.project.sc.competition.service.impl;

import java.util.*;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.project.sc.CollageScore.domain.ScCollageScore;
import com.ruoyi.project.sc.CollageScore.mapper.ScCollageScoreMapper;
import com.ruoyi.project.sc.CollageScore.service.IScCollageScoreService;
import com.ruoyi.project.sc.college.domain.ScCollege;
import com.ruoyi.project.sc.college.mapper.ScCollegeMapper;
import com.ruoyi.project.sc.competition.domain.CompetitionListVO;
import com.ruoyi.project.sc.competition.domain.CompetitionUser;
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
import com.ruoyi.project.sc.competition.domain.ScCompetition;
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
    public boolean restoreSort(Long id) {

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

        List<ScCompetitionSort> combinations = createCombinations(scColleges, id);
        for (ScCompetitionSort combination : combinations) {
            scCompetitionSortMapper.insertScCompetitionSort(combination);
        }
        return true;
    }

    @Override
    public List<CompetitionListVO> selectbatchCompetitionList(Long id) {

        List<CompetitionListVO> list = new ArrayList<>();

        ScCompetitionSort scCompetitionSort = new ScCompetitionSort();
        scCompetitionSort.setCompetitionId(id);
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
            competitionListVO.setUserA(new CompetitionUser(scPlayersA.getPlayerId(), scPlayersA.getName(), scPlayersA.getScColleges().getName()));

            ScPlayers scPlayersB = playerCollegeMap.get(competitionSort.getUser2());
            competitionListVO.setUserB(new CompetitionUser(scPlayersB.getPlayerId(), scPlayersB.getName(), scPlayersB.getScColleges().getName()));

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

        List<CompetitionListVO> competitionListVOS = selectbatchCompetitionList(id);
        noticeWebsocketResp.setCompetitionListVOList(competitionListVOS);

        ScCompetition scCompetition = scCompetitionMapper.selectScCompetitionByCompetiitonId(id);
        CompetitionCurrentData competitionCurrentData = new CompetitionCurrentData();

        competitionCurrentData.setCurrentSort(scCompetition.getCurrentSort());
        competitionCurrentData.setCurrentType(scCompetition.getCurrentType());

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
        if (scCollageScore.getScoreId()!=null ||scCollageScore.getScore() == null || scCollageScore.getJudgeId() == null || scCollageScore.getPlayerId() == null) {
            throw new ServiceException("请确保传入参数的完整性");
        }
        return scCollageScoreMapper.insertScCollageScore(scCollageScore) > 0;
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
        List<ScPlayers> allPlayers = new ArrayList<>();
        Map<ScPlayers, ScCollege> playerCollegeMap = new HashMap<>();

        // 收集所有玩家并建立玩家与学院的映射
        for (ScCollege college : colleges) {
            for (ScPlayers player : college.getScPlayersList()) {
                if (player.getType() == 1 || player.getType() == 2) {
                    allPlayers.add(player);
                    playerCollegeMap.put(player, college);
                }
            }
        }

        // 打乱玩家顺序以确保随机性
        Collections.shuffle(allPlayers);

        for (int i = 0; i < allPlayers.size(); i++) {
            ScPlayers playerA = allPlayers.get(i);
            ScCollege collegeA = playerCollegeMap.get(playerA);

            for (int j = i + 1; j < allPlayers.size(); j++) {
                ScPlayers playerB = allPlayers.get(j);
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
                    allPlayers.remove(j); // 移除已匹配的玩家B
                    break;
                }
            }
        }

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
        // 同一学院的玩家，必须来自不同队伍才能匹配
        return !Objects.equals(playerA.getType(), playerB.getType());
    }

    private static String extractCollegeName(String fullName) {
        // 假设学院名称格式为"学院X A队"或"学院X B队"
        return fullName.replaceAll("\\s[12]队$", "");
    }

}

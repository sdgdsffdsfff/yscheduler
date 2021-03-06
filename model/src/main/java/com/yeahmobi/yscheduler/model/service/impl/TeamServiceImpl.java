package com.yeahmobi.yscheduler.model.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yeahmobi.yscheduler.common.Constants;
import com.yeahmobi.yscheduler.common.Paginator;
import com.yeahmobi.yscheduler.model.Team;
import com.yeahmobi.yscheduler.model.TeamExample;
import com.yeahmobi.yscheduler.model.dao.TeamDao;
import com.yeahmobi.yscheduler.model.service.TaskService;
import com.yeahmobi.yscheduler.model.service.TeamService;
import com.yeahmobi.yscheduler.model.service.UserService;
import com.yeahmobi.yscheduler.model.service.WorkflowDetailService;

/**
 * @author Leo Liang
 */
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamDao               teamDao;

    @Autowired
    private UserService           userService;

    @Autowired
    private TaskService           taskService;

    @Autowired
    private WorkflowDetailService workflowDetailService;

    public Team get(long id) {
        return this.teamDao.selectByPrimaryKey(id);
    }

    public List<Team> list(int pageNum, Paginator paginator) {
        TeamExample example = new TeamExample();

        example.setOrderByClause("create_time ASC");

        int count = this.teamDao.countByExample(example);

        paginator.setItemsPerPage(Constants.PAGE_SIZE);
        paginator.setItems(count);
        paginator.setPage(pageNum);

        int offset = paginator.getBeginIndex() - 1;
        int limit = Constants.PAGE_SIZE;

        RowBounds rowBounds = new RowBounds(offset, limit);

        List<Team> list = this.teamDao.selectByExampleWithRowbounds(example, rowBounds);

        return list;
    }

    public Team get(String teamName) {
        TeamExample example = new TeamExample();
        example.createCriteria().andNameEqualTo(teamName);
        List<Team> teams = this.teamDao.selectByExample(example);
        if (teams.isEmpty()) {
            throw new IllegalArgumentException(String.format("团队 %s 不存在", teamName));
        } else {
            return teams.get(0);
        }
    }

    private boolean nameExists(String name) {
        TeamExample example = new TeamExample();
        example.createCriteria().andNameEqualTo(name);
        return this.teamDao.selectByExample(example).size() != 0;
    }

    public void add(Team team) {
        if (nameExists(team.getName())) {
            throw new IllegalArgumentException(String.format("团队 %s 已经存在", team.getName()));
        }
        Date time = new Date();
        team.setCreateTime(time);
        team.setUpdateTime(time);
        this.teamDao.insertSelective(team);
        this.workflowDetailService.addTeamRootTask(this.taskService.addRootTaskIfAbsent(team.getName()));
    }

    public void remove(long id) {
        if (this.userService.hasTeamUser(id)) {
            throw new IllegalArgumentException("团队还有成员，不能删除");
        }

        this.teamDao.deleteByPrimaryKey(id);
    }

    public void updateName(long id, String name) {
        if (get(id) == null) {
            throw new IllegalArgumentException("团队不存在");
        }

        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("团队名字不能为空");
        }

        Team existsTeam = null;
        try {
            existsTeam = get(name);
        } catch (IllegalArgumentException e) {
            // ignore
        }
        if ((existsTeam != null) && !existsTeam.getId().equals(id)) {
            throw new IllegalArgumentException(String.format("团队 %s 已经存在", name));
        }

        this.taskService.updateTeamRootTaskName(get(id).getName(), name);
        Team team = new Team();
        team.setId(id);
        team.setName(name);
        team.setUpdateTime(new Date());
        this.teamDao.updateByPrimaryKeySelective(team);
    }

    public List<Team> list() {
        TeamExample example = new TeamExample();
        example.setOrderByClause("create_time ASC");
        return this.teamDao.selectByExample(example);
    }
}

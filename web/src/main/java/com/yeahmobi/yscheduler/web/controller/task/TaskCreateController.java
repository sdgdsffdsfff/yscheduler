package com.yeahmobi.yscheduler.web.controller.task;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yeahmobi.yscheduler.model.Task;
import com.yeahmobi.yscheduler.model.common.UserContextHolder;
import com.yeahmobi.yscheduler.model.service.AgentService;
import com.yeahmobi.yscheduler.model.service.TaskService;
import com.yeahmobi.yscheduler.model.service.UserService;
import com.yeahmobi.yscheduler.model.type.TaskType;
import com.yeahmobi.yscheduler.web.controller.AbstractController;

/**
 * @author Ryan Sun
 */
@Controller
@RequestMapping(value = { TaskCreateController.SCREEN_NAME })
public class TaskCreateController extends AbstractController {

    public static final String  SCREEN_NAME = "task/create";

    private static final Logger LOGGER      = LoggerFactory.getLogger(TaskCreateController.class);

    @Autowired
    private TaskService         taskService;

    @Autowired
    private UserService         userService;

    @Autowired
    private AgentService        agentService;

    @Autowired
    private TaskHelper          taskHelper;

    private String              uploadPath;

    @Value("#{confProperties['storageServerUri']}")
    private String              storageServerUri;

    @PostConstruct
    public void init() {
        this.uploadPath = this.storageServerUri + "/upload";
    }

    @RequestMapping(value = { "" }, method = RequestMethod.GET)
    public ModelAndView task() {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("types", TaskType.values());
        map.put("users", this.userService.list());
        long teamId = this.userService.get(UserContextHolder.getUserContext().getId()).getTeamId();
        map.put("agents", this.agentService.list(teamId, true));
        return screen(map, SCREEN_NAME);
    }

    @RequestMapping(value = { "name_exist" }, method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object nameExist(String name) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("exist", this.taskService.nameExist(name));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            map.put("exist", true);
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = { "serverExist" })
    @ResponseBody
    public Object serverExist(String path) {
        URL url;
        HttpURLConnection con = null;
        int state = -1;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            url = new URL(path);
            con = (HttpURLConnection) url.openConnection();
            state = con.getResponseCode();
            if (state == 400) {
                map.put("success", true);
            }
        } catch (IOException e) {
            map.put("notice", e.getMessage());
            map.put("success", false);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = { "" }, method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Task task = this.taskHelper.extractTaskFromRequest(request, true);
            this.taskService.add(task);

            map.put("notice", "添加成功");
            map.put("success", true);
            map.put("taskId", task.getId());
            map.put("uploadPath", this.uploadPath);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            map.put("notice", e.getMessage());
            map.put("success", false);
        }
        return JSON.toJSONString(map);
    }
}

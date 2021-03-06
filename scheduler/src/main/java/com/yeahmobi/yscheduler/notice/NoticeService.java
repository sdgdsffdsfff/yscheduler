package com.yeahmobi.yscheduler.notice;

import java.util.Date;
import java.util.List;

import com.yeahmobi.yscheduler.model.User;

/**
 * @author Ryan Sun
 */
public interface NoticeService {

    public void alert(String title, String content, List<User> to, boolean needSms);

    public void workflowSuccess(long id);

    public void workflowFail(long id);

    public void workflowTimeout(long id);

    public void workflowSkip(long id, Date scheduleTime);

    public void workflowCanncel(long id);

    public void taskSuccess(long id);

    public void taskFail(long id);

    public void taskTimeout(long id);

    public void taskSkip(long id, Date scheduleTime);

    public void taskCanncel(long id);

    public void alertInnerError(String msg);

}

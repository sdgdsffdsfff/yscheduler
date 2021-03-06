package com.yeahmobi.yscheduler.model.common;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.yeahmobi.yscheduler.common.Constants;

/**
 * @author Leo.Liang
 */
public class UserContext {

    private static final String FIELD_SEPARATOR = "\r";
    private long                id;
    private String              username;
    private String              password;
    private Date                expireDate;

    public UserContext(long id, String username, String password, Date expireDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.expireDate = expireDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getExpireDate() {
        return this.expireDate;
    }

    public long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isAdmin() {
        return (this.username != null) && Constants.ADMIN_NAME.equals(this.username);
    }

    @Override
    public String toString() {
        return this.id + FIELD_SEPARATOR + this.username + FIELD_SEPARATOR + this.password + FIELD_SEPARATOR
               + this.expireDate.getTime() + FIELD_SEPARATOR;
    }

    public static UserContext valueOf(String src) {
        String[] arr = StringUtils.splitByWholeSeparatorPreserveAllTokens(src, FIELD_SEPARATOR);
        if ((arr == null) || (arr.length < 4)) {
            return null;
        } else {
            return new UserContext(Long.valueOf(arr[0]), arr[1], arr[2], new Date(Long.valueOf(arr[3])));
        }
    }
}

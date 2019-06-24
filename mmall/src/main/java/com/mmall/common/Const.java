package com.mmall.common;

/**
 * Created by Administrator on 2019/6/22.
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";//当前用户

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface Role{//角色
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;//管理员
    }


}
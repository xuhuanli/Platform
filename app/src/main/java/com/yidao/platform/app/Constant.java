package com.yidao.platform.app;

public class Constant {

    //http://192.168.1.149:8080/product/style/get/1014051050182672384  test
    public static final String BASE_URL = "http://192.168.1.149:8080/";

    /**
     * 全局debug模式开关 在release时候设置为false
     */
    public static final boolean IS_DEBUG = true;
    /**
     * button防抖时间
     */
    public static final long THROTTLE_TIME = 500;
    /**
     * appname
     */
    public static final String APP_NAME = "platform";

    /**
     * 微信AppId
     */
    public static final String WX_LOGIN_APP_ID = "wx4bd23926604e2693";

    // TODO: 2018/7/13 0013 记得删除secret 这个只是测试用
    /**
     * 微信AppSecret
     */
    public static final String WX_LOGIN_APP_SECRET = "d0ec01859e2f3b97f3deff38682cb181";
}

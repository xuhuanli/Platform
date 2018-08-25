package com.yidao.platform.app;

public class Constant {

    //王鹏
    //public static final String BASE_URL = "http://10.10.20.8:8060/";
    //徐ww
    //public static final String BASE_URL = "http://10.10.20.24:8080/";
    //徐ly
    //public static final String BASE_URL = "http://10.10.20.21:8080/";
    //张zk
    //public static final String BASE_URL = "http://10.10.20.10:8081/";
    //陈dq
    //public static final String BASE_URL = "http://10.10.20.3:8080/platform-pc-web/";
    public static final String BASE_URL = "http://192.168.1.92:8082/";

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

    /**
     * Bugly App ID
     */
    public static final String BUGLY_ID = "59c35ade6f";
    public static final String WEBVIEW_PROGRESS = "webview_progress";
    /**
     * 图片压缩的最小KB
     */
    public static final int NEED_COMPRESS_SIZE = 300;
    /**
     * oss_id
     */
    public static final String OSS_ID = "STS.NJxAgpPpyN3b8zzmvxZQGH2Bq";
    /**
     * OSS_Secret
     */
    public static final String OSS_SECRET = "HTWvqV1DzHL2rNmLVVofUPZNGpUFZF64k1ddjBxxAUf";
    /**
     * OSS_Token
     */
    public static final String OSS_TOKEN = "CAISoQJ1q6Ft5B2yfSjIr4nNCt3Eva9Y+fGJOlzLiXYtVt5rp/fpkzz2IH5Oe3VtBe8WsvU/mm9Y7fYflqVoRoReREvCKMBt9YgPQu457DGF6aKP9rUhpMCPFAr6UmzzvqL7Z+H+U6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj+wIDLkQRRLqL0AYZrFsKxBltdUROFbIKP+pKWSKuGfLC1dysQcO+wEP4K+kkMqH8Uic3h+o2+MNo43tLJ++ao4uHu8mD4fqhLItKfGfinABu0Mazsos0vwYowWgl8qGHlxc7y+BN+fp6dB1JGd7HPNkQfEY8aSjxaEp6rKMx9+nlgw+NOVUQjnZQ5u73MzHFeWmO9A0b7/nPG7X1dSCJn9lU+XshshxGoABbyKk2qQMOzvsJ/+XKLmT3w5r/S+39OG80QfbcK2McfBxk3GwK60UOa4bETFLyneE/W3gAYhCT+Ruw4Q4hKHjf8WDfqV4Tg4xXhYoWVEx52nR5KKIdtng80rYZCPek+/Y8SIjR1EIaFizHPwjsHsUTzUuBWKEBPTLtFZ31nEfLe4=";
    /**
     * OSS_ENDPOINT
     */
    public static final String OSS_ENDPOINT = "https://oss-cn-hangzhou.aliyuncs.com/";
    /**
     * OSS_BUCKET_NAME
     */
    public static final String OSS_BUCKET_NAME = "ydplatform";
    /**
     * CATEGORY_ID_LIST
     */
    public static final long CATEGORY_ID_LABEL_1 = 5210479661809664L; //全球资讯榜
    public static final long CATEGORY_ID_LABEL_2 = 5211114973036544L; //产业资讯圈
    public static final long CATEGORY_ID_LABEL_3 = 5309401944162304L; //创业大方针
    public static final long CATEGORY_ID_LABEL_4 = 6355216141778944L; //投资活动汇
    public static final long CATEGORY_ID_LABEL_5 = 6355421843030016L; //创投直通车
    public static final long CATEGORY_ID_LABEL_6 = 6355476889075712L; //金融华尔街
    public static final long CATEGORY_ID_LABEL_7 = 6355509193605120L; //经营方法论
    /**
     * 更多列表 pageSize
     */
    public static final int PAGE_SIZE = 6;
    public static final String STRING_ART_ID = "artId";
    public static final String STRING_URL = "url";
    public static final String STRING_COLLECTION = "collection";
    public static final String STRING_DIAN_ZAN = "dian_zan";
    public static final String STRING_TITLE = "title";
    public static final String STRING_VALUE = "value";
    public static final String STRING_USER_ID = "userId";
    public static final String STRING_USER_NAME = "nickname";
    public static final String STRING_USER_SEX = "sex";
    public static final String STRING_USER_COUNTRY = "country";
    public static final String STRING_USER_PROVINCE = "province";
    public static final String STRING_USER_CITY = "city";
    public static final String STRING_USER_HEADIMGURL = "headImgUrl";
    public static final String STRING_USER_PRIVILEGELIST = "privilegeList";
    public static final String STRING_USER_TOKEN = "token";
    public static final String STRING_USER_REFRESHTOKEN = "refreshToken";
    public static final String STRING_USER_FIRSTLOGIN = "firstLogin";
    public static final String STRING_USER_BINDPHONE = "bindPhone";

    public static final String STRING_DEVICE_ID = "deviceId";
    public static final String STRING_BOTTLE = "bottle";
    public static final String STRING_BOTTLE_ID = "bottleId";
    public static final String STRING_SESSION_ID = "sessionId";
    public static final String STRING_FIND_ID = "findId";

    public static final String STRING_BOTTLE_PAGE_FROM = "bottle_from";
    public static final String USER_INFO = "user_info";
    public static final String STRING_HAS_GUIDE = "has_guide";
}

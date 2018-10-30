package com.yidao.platform.app.MyObserver;

import java.util.List;

public class BottleTest {
    /**
     * bottleId : 754833407148032
     * authorId : 9978363793506304
     * content : 这是瓶子里面的内容
     * sessionId : 2163711441174528
     * mess : [{"id":"2163711520866304","sessionId":"2163711441174528","parId":"2163711441174528","content":"牛皮纸","userId":"1881601438449664","parUserId":"9978363793506304","status":"1","createTime":"2018-08-22","bottleId":"754833407148032","userName":"However","parUserName":"徐添添"}]
     */

    private String bottleId;
    private String authorId;
    private String content;
    private String sessionId;
    private String headImg;
    private String timeStamp;
    private String userName;
    private List<MessBean> mess;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getBottleId() {
        return bottleId;
    }

    public void setBottleId(String bottleId) {
        this.bottleId = bottleId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<MessBean> getMess() {
        return mess;
    }

    public void setMess(List<MessBean> mess) {
        this.mess = mess;
    }

    public static class MessBean {
        /**
         * id : 2163711520866304
         * sessionId : 2163711441174528
         * parId : 2163711441174528
         * content : 牛皮纸
         * userId : 1881601438449664
         * parUserId : 9978363793506304
         * status : 1
         * createTime : 2018-08-22
         * bottleId : 754833407148032
         * userName : However
         * parUserName : 徐添添
         */

        private String id;
        private String sessionId;
        private String parId;
        private String content;
        private String userId;
        private String parUserId;
        private String status;
        private String createTime;
        private String bottleId;
        private String userName;
        private String parUserName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getParId() {
            return parId;
        }

        public void setParId(String parId) {
            this.parId = parId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getParUserId() {
            return parUserId;
        }

        public void setParUserId(String parUserId) {
            this.parUserId = parUserId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getBottleId() {
            return bottleId;
        }

        public void setBottleId(String bottleId) {
            this.bottleId = bottleId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getParUserName() {
            return parUserName;
        }

        public void setParUserName(String parUserName) {
            this.parUserName = parUserName;
        }
    }
}

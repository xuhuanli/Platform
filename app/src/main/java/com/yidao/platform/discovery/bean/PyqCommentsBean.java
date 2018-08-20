package com.yidao.platform.discovery.bean;

import java.util.List;

public class PyqCommentsBean {

    /**
     * status : true
     * result : [{"id":695106241363968,"memberId":695106241363968,"findId":695106241363968,"ownerId":695106241363968,"ownerName":"","deployId":0,"deployName":"","content":"test","status":1,"createTime":"2018-08-20"}]
     */

    private boolean status;
    private List<ResultBean> result;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 695106241363968
         * memberId : 695106241363968
         * findId : 695106241363968
         * ownerId : 695106241363968
         * ownerName :
         * deployId : 0
         * deployName :
         * content : test
         * status : 1
         * createTime : 2018-08-20
         */

        private long id;
        private long memberId;
        private long findId;
        private long ownerId;
        private String ownerName;
        private int deployId;
        private String deployName;
        private String content;
        private int status;
        private String createTime;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getMemberId() {
            return memberId;
        }

        public void setMemberId(long memberId) {
            this.memberId = memberId;
        }

        public long getFindId() {
            return findId;
        }

        public void setFindId(long findId) {
            this.findId = findId;
        }

        public long getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(long ownerId) {
            this.ownerId = ownerId;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public int getDeployId() {
            return deployId;
        }

        public void setDeployId(int deployId) {
            this.deployId = deployId;
        }

        public String getDeployName() {
            return deployName;
        }

        public void setDeployName(String deployName) {
            this.deployName = deployName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}

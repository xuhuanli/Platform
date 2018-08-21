package com.yidao.platform.discovery.bean;

import java.util.List;

public class FriendsListBean {

    /**
     * status : true
     * result : {"pageIndex":1,"pageSize":1,"total":30,"begIndex":0,"list":[{"findId":289957240963072,"likeAmount":0,"commAmount":0,"deployName":"","headImg":"","deployId":21211,"deployTime":"2018-08-17","isLike":false,"find":{"id":"","userId":"","userName":"","content":"test","status":"","createTime":"","isLike":"","imgs":""},"imgs":["http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKAdkwnQcUL8mw7ZgJVuia0urBfh5sduCPpFWMsHLsP3ZNqJWnS33CJDPHUiaDBrc8VjlMWj88kE5Zg/132","http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ0VgzegP9cLL8UuA3M80miaHt6C8uayh4SXyy7aMKrl3M7icSg9FPgkibViaZgv78HSOPCaCMVUIjMtg/132"]}],"status":true,"errCode":"1","info":""}
     */

    private boolean status;
    private ResultBean result;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * pageIndex : 1
         * pageSize : 1
         * total : 30
         * begIndex : 0
         * list : [{"findId":289957240963072,"likeAmount":0,"commAmount":0,"deployName":"","headImg":"","deployId":21211,"deployTime":"2018-08-17","isLike":false,"find":{"id":"","userId":"","userName":"","content":"test","status":"","createTime":"","isLike":"","imgs":""},"imgs":["http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKAdkwnQcUL8mw7ZgJVuia0urBfh5sduCPpFWMsHLsP3ZNqJWnS33CJDPHUiaDBrc8VjlMWj88kE5Zg/132","http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ0VgzegP9cLL8UuA3M80miaHt6C8uayh4SXyy7aMKrl3M7icSg9FPgkibViaZgv78HSOPCaCMVUIjMtg/132"]}]
         * status : true
         * errCode : 1
         * info :
         */

        private long pageIndex;
        private long pageSize;
        private long total;
        private long begIndex;
        private boolean status;
        private String errCode;
        private String info;
        private List<ListBean> list;

        public long getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(long pageIndex) {
            this.pageIndex = pageIndex;
        }

        public long getPageSize() {
            return pageSize;
        }

        public void setPageSize(long pageSize) {
            this.pageSize = pageSize;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public long getBegIndex() {
            return begIndex;
        }

        public void setBegIndex(long begIndex) {
            this.begIndex = begIndex;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getErrCode() {
            return errCode;
        }

        public void setErrCode(String errCode) {
            this.errCode = errCode;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * findId : 289957240963072
             * likeAmount : 0
             * commAmount : 0
             * deployName :
             * headImg :
             * deployId : 21211
             * deployTime : 2018-08-17
             * isLike : false
             * find : {"id":"","userId":"","userName":"","content":"test","status":"","createTime":"","isLike":"","imgs":""}
             * imgs : ["http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKAdkwnQcUL8mw7ZgJVuia0urBfh5sduCPpFWMsHLsP3ZNqJWnS33CJDPHUiaDBrc8VjlMWj88kE5Zg/132","http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ0VgzegP9cLL8UuA3M80miaHt6C8uayh4SXyy7aMKrl3M7icSg9FPgkibViaZgv78HSOPCaCMVUIjMtg/132"]
             */

            private long findId;
            private long likeAmount;
            private long commAmount;
            private String deployName;
            private String headImg;
            private long deployId;
            private String deployTime;
            private boolean isLike;
            private FindBean find;
            private List<String> imgs;

            public long getFindId() {
                return findId;
            }

            public void setFindId(long findId) {
                this.findId = findId;
            }

            public long getLikeAmount() {
                return likeAmount;
            }

            public void setLikeAmount(long likeAmount) {
                this.likeAmount = likeAmount;
            }

            public long getCommAmount() {
                return commAmount;
            }

            public void setCommAmount(long commAmount) {
                this.commAmount = commAmount;
            }

            public String getDeployName() {
                return deployName;
            }

            public void setDeployName(String deployName) {
                this.deployName = deployName;
            }

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public long getDeployId() {
                return deployId;
            }

            public void setDeployId(long deployId) {
                this.deployId = deployId;
            }

            public String getDeployTime() {
                return deployTime;
            }

            public void setDeployTime(String deployTime) {
                this.deployTime = deployTime;
            }

            public boolean isIsLike() {
                return isLike;
            }

            public void setIsLike(boolean isLike) {
                this.isLike = isLike;
            }

            public FindBean getFind() {
                return find;
            }

            public void setFind(FindBean find) {
                this.find = find;
            }

            public List<String> getImgs() {
                return imgs;
            }

            public void setImgs(List<String> imgs) {
                this.imgs = imgs;
            }

            public static class FindBean {
                /**
                 * id :
                 * userId :
                 * userName :
                 * content : test
                 * status :
                 * createTime :
                 * isLike :
                 * imgs :
                 */

                private String id;
                private String userId;
                private String userName;
                private String content;
                private String status;
                private String createTime;
                private String isLike;
                private String imgs;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
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

                public String getIsLike() {
                    return isLike;
                }

                public void setIsLike(String isLike) {
                    this.isLike = isLike;
                }

                public String getImgs() {
                    return imgs;
                }

                public void setImgs(String imgs) {
                    this.imgs = imgs;
                }
            }
        }
    }
}

package com.yidao.platform.app;

public class OssBean {

    /**
     * status : true
     * result : {"expiration":"20180824121606","accessKeyId":"STS.NKUwQPF5QTQQAVogrb4UhkQg6","accessKeySecret":"ATnvW2F1BhGGnJcLYkN98gtMohegRXLBywEp5FwFuca1","securityToken":"CAISoQJ1q6Ft5B2yfSjIr4jgPOvkq+pw45O6Q3Deg3I3ONpEhJTM1Dz2IH5Oe3VtBe8WsvU/mm9Y7fYflqVoRoReREvCKMBt9YgPeY461TSF6aKP9rUhpMCPFAr6UmzzvqL7Z+H+U6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj+wIDLkQRRLqL0AYZrFsKxBltdUROFbIKP+pKWSKuGfLC1dysQcO+wEP4K+kkMqH8Uic3h+o2+MNo43tLJ++ao4uHu8mD4fqhLItKfGfinABu0Mazsos0vwYowWgl8qGHlxc7y+BN+fp6dB1JGd7HPNkQfEY8aSjxaEp6rKMx9+nlgw+NOVUQjnZQ5u73MzHFeWmO9A0b7/nPG7X1dSCJn9lU+XshshxGoABEhdUvhWRm2iYMsEY6gWiwQK7JrXU/8JA5sO6FG5SRIyZoSBd2ZjKn6g+uxCd+1y5fMDS6Komk/xB/GbAZqffVDGU/EgdEZOMY8qwNSZFRlOKiGL5qkhtpu4xm1v2sgHOh+BUMASuXPMet6FBsYv3Fu0Ej7bl7LSXAWJNGqKlYPE=","code":"200","errorMess":""}
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
         * expiration : 20180824121606
         * accessKeyId : STS.NKUwQPF5QTQQAVogrb4UhkQg6
         * accessKeySecret : ATnvW2F1BhGGnJcLYkN98gtMohegRXLBywEp5FwFuca1
         * securityToken : CAISoQJ1q6Ft5B2yfSjIr4jgPOvkq+pw45O6Q3Deg3I3ONpEhJTM1Dz2IH5Oe3VtBe8WsvU/mm9Y7fYflqVoRoReREvCKMBt9YgPeY461TSF6aKP9rUhpMCPFAr6UmzzvqL7Z+H+U6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj+wIDLkQRRLqL0AYZrFsKxBltdUROFbIKP+pKWSKuGfLC1dysQcO+wEP4K+kkMqH8Uic3h+o2+MNo43tLJ++ao4uHu8mD4fqhLItKfGfinABu0Mazsos0vwYowWgl8qGHlxc7y+BN+fp6dB1JGd7HPNkQfEY8aSjxaEp6rKMx9+nlgw+NOVUQjnZQ5u73MzHFeWmO9A0b7/nPG7X1dSCJn9lU+XshshxGoABEhdUvhWRm2iYMsEY6gWiwQK7JrXU/8JA5sO6FG5SRIyZoSBd2ZjKn6g+uxCd+1y5fMDS6Komk/xB/GbAZqffVDGU/EgdEZOMY8qwNSZFRlOKiGL5qkhtpu4xm1v2sgHOh+BUMASuXPMet6FBsYv3Fu0Ej7bl7LSXAWJNGqKlYPE=
         * code : 200
         * errorMess :
         */

        private String expiration;
        private String accessKeyId;
        private String accessKeySecret;
        private String securityToken;
        private String code;
        private String errorMess;

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        public String getSecurityToken() {
            return securityToken;
        }

        public void setSecurityToken(String securityToken) {
            this.securityToken = securityToken;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getErrorMess() {
            return errorMess;
        }

        public void setErrorMess(String errorMess) {
            this.errorMess = errorMess;
        }
    }
}

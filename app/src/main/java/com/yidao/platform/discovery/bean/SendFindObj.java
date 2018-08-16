package com.yidao.platform.discovery.bean;

import java.util.List;

public class SendFindObj {

    /**
     * content : 内容
     * imgUrls : ["http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKAdkwnQcUL8mw7ZgJVuia0urBfh5sduCPpFWMsHLsP3ZNqJWnS33CJDPHUiaDBrc8VjlMWj88kE5Zg/132","http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ0VgzegP9cLL8UuA3M80miaHt6C8uayh4SXyy7aMKrl3M7icSg9FPgkibViaZgv78HSOPCaCMVUIjMtg/132"]
     * userId : 1
     */

    private String content;
    private int userId;
    private List<String> imgUrls;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }
}

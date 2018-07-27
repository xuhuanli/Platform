package com.yidao.platform.testpackage.bean;

import java.util.List;

public class TestBean {

    /**
     * code : 200
     * msg : SUCCESS
     * data : [{"id":"1016202055389282304","styleName":"333吊带短裤"},{"id":"1016201428877705216","styleName":"222吊带短裤"},{"id":"1016200455186808832","styleName":"111吊带短裤"},{"id":"1016198453291646976","styleName":"吊带短裤"},{"id":"1016197864776269824","styleName":"背心吊带短裤"},{"id":"1016168155355021312","styleName":"背心连身短裤"},{"id":"1016166593408794624","styleName":"肩带连身短裤32322"},{"id":"1015125946543374336","styleName":"无袖连身长短裤"},{"id":"1015125496641355776","styleName":"无袖连身长裤"},{"id":"1014820753557684224","styleName":"商品款式32322"},{"id":"1014820721504813056","styleName":"商品款式2"},{"id":"1014817165083148288","styleName":"商品款式1"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1016202055389282304
         * styleName : 333吊带短裤
         */

        private String id;
        private String styleName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStyleName() {
            return styleName;
        }

        public void setStyleName(String styleName) {
            this.styleName = styleName;
        }
    }
}

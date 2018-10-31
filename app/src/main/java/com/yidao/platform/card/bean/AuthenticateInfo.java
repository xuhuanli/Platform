package com.yidao.platform.card.bean;

import java.util.List;

public class AuthenticateInfo  {
    private List<ResultListBean> result_list;

    public List<ResultListBean> getResult_list() {
        return result_list;
    }

    public void setResult_list(List<ResultListBean> result_list) {
        this.result_list = result_list;
    }

    public static class ResultListBean {
        /**
         * code : 0
         * message : OK
         * filename : 1540871556146photoCut.jpeg
         * data : [{"item":"姓名","value":"潘灵军","confidence":0.9995600581169128},
         * {"item":"职位","value":"客户经理","confidence":0.9994072914123536}
         * ,{"item":"部门","value":"营业中心","confidence":0.9992565512657166},
         * {"item":"公司","value":"中国农业银行","confidence":0.9999746084213256},
         * {"item":"英文公司","value":"AGRICULTURALBANK OF CHINA","confidence":0.8049688935279846},
         * {"item":"公司","value":"杭州之江支行","confidence":0.9992724061012268},
         * {"item":"地址","value":"浙江省杭州市湖墅南路418号","confidence":0.9997962117195128}
         * ,{"item":"邮编","value":"310005","confidence":0.998188316822052},
         * {"item":"手机","value":"18758132682","confidence":0.99992573261261}
         * ,{"item":"电话","value":"86-571-88084311","confidence":0.9998918771743774}
         * ,{"item":"传真","value":"86-571-88077588","confidence":0.9998923540115356}]
         */

        private int code;
        private String message;
        private String filename;
        private List<DataBean> data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * item : 姓名
             * value : 潘灵军
             * confidence : 0.9995600581169128
             */

            private String item;
            private String value;
            private double confidence;

            public String getItem() {
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public double getConfidence() {
                return confidence;
            }

            public void setConfidence(double confidence) {
                this.confidence = confidence;
            }
        }
    }


//    {
//        "result_list": [
//        {
//            "code": 0,
//                "message": "OK",
//                "filename": "1540871556146photoCut.jpeg",
//                "data": [
//            {
//                "item": "姓名",
//                    "value": "潘灵军",
//                    "confidence": 0.9995600581169128
//            },
//            {
//                "item": "职位",
//                    "value": "客户经理",
//                    "confidence": 0.9994072914123536
//            },
//            {
//                "item": "部门",
//                    "value": "营业中心",
//                    "confidence": 0.9992565512657166
//            },
//            {
//                "item": "公司",
//                    "value": "中国农业银行",
//                    "confidence": 0.9999746084213256
//            },
//            {
//                "item": "英文公司",
//                    "value": "AGRICULTURALBANK OF CHINA",
//                    "confidence": 0.8049688935279846
//            },
//            {
//                "item": "公司",
//                    "value": "杭州之江支行",
//                    "confidence": 0.9992724061012268
//            },
//            {
//                "item": "地址",
//                    "value": "浙江省杭州市湖墅南路418号",
//                    "confidence": 0.9997962117195128
//            },
//            {
//                "item": "邮编",
//                    "value": "310005",
//                    "confidence": 0.998188316822052
//            },
//            {
//                "item": "手机",
//                    "value": "18758132682",
//                    "confidence": 0.99992573261261
//            },
//            {
//                "item": "电话",
//                    "value": "86-571-88084311",
//                    "confidence": 0.9998918771743774
//            },
//            {
//                "item": "传真",
//                    "value": "86-571-88077588",
//                    "confidence": 0.9998923540115356
//            }
//            ]
//        }
//    ]
//    }
}

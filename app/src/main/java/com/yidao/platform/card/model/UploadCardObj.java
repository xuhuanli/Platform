package com.yidao.platform.card.model;

import java.io.Serializable;
import java.util.List;

public class UploadCardObj implements Serializable {


    /**
     * userId : 435434534211111
     * name : 嘿嘿
     * company : 驿道科技
     * post : JJ开发
     * phoneNum : 17621104288
     * companyAddr : 杭州滨江
     * email : 107028109555@qq.com
     * cardUrl : QXXX
     * certType : 1
     * certNum : 420602199608130540
     * businessId : 1
     * masterLabelId : [1,2]
     * isMaster : 1
     */

    private long userId;
    private String name;
    private String company;
    private String post;
    private String phoneNum;
    private String companyAddr;
    private String email;
    private String cardUrl;
    private int certType;
    private String certNum;
    private int businessId;
    private int isMaster;
    private String wechatCode;
    private List<Integer> masterLabelId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardUrl() {
        return cardUrl;
    }

    public void setCardUrl(String cardUrl) {
        this.cardUrl = cardUrl;
    }

    public int getCertType() {
        return certType;
    }

    public void setCertType(int certType) {
        this.certType = certType;
    }

    public String getCertNum() {
        return certNum;
    }

    public void setCertNum(String certNum) {
        this.certNum = certNum;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(int isMaster) {
        this.isMaster = isMaster;
    }

    public List<Integer> getMasterLabelId() {
        return masterLabelId;
    }

    public void setMasterLabelId(List<Integer> masterLabelId) {
        this.masterLabelId = masterLabelId;
    }

    public String getWechatCode() {
        return wechatCode;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }
}
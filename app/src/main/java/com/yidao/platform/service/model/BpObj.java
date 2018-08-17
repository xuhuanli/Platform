package com.yidao.platform.service.model;

import android.support.annotation.NonNull;

public class BpObj {
    /**
     * 所在城市名称
     */
    private String cityName;
    /**
     * 公司估值
     */
    private String companyEvaluate;
    /**
     * 	企业行业概述
     */
    private String description;
    /**
     * 期望投资值
     */
    private String exceptInvest;
    /**
     * 	所处行业
     */
    private String industry;
    /**
     * 公司名称
     */
    @NonNull
    private String name;
    /**
     * 公司人数
     */
    private String numberOfPeople;
    /**
     *手机号码
     */
    @NonNull
    private String phoneNum;
    /**
     * 用户职业
     */
    private String profession;
    /**
     * 股权让出
     */
    private String transferStock;
    /**
     * 用户编号
     */
    @NonNull
    private String userId;

    public BpObj(@NonNull String name, @NonNull String phoneNum, @NonNull String userId) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.userId = userId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCompanyEvaluate() {
        return companyEvaluate;
    }

    public void setCompanyEvaluate(String companyEvaluate) {
        this.companyEvaluate = companyEvaluate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExceptInvest() {
        return exceptInvest;
    }

    public void setExceptInvest(String exceptInvest) {
        this.exceptInvest = exceptInvest;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    @NonNull
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(@NonNull String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getTransferStock() {
        return transferStock;
    }

    public void setTransferStock(String transferStock) {
        this.transferStock = transferStock;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }
}

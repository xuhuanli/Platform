package com.yidao.platform.info.model;

public class TagBean {
        public TagBean(String name, Boolean isselected,int id) {
            this.name = name;
            this.isselected = isselected;
            this.id = id;
        }

        private String name;
        private Boolean isselected;
        private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsselected() {
        return isselected;
    }

    public void setIsselected(Boolean isselected) {
        this.isselected = isselected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
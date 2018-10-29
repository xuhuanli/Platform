package com.yidao.platform.info.model;

public class TagBean {
        public TagBean(String name, Boolean isselected) {
            this.name = name;
            this.isselected = isselected;
        }

        private String name;
        private Boolean isselected;

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
}
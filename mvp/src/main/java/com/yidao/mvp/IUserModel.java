package com.yidao.mvp;

public interface IUserModel {
    void setID (int id);
    void setFirstName (String firstName);
    void setLastName (String lastName);
    UserBean load (int id);//通过id读取user信息,返回一个UserBean
}

package com.yidao.mvp;

public interface IUserPresenter {
    void saveUser(int id,String firstName,String lastName);
    void loadUser(int id);
}

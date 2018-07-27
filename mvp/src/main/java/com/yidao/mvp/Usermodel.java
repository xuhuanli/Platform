package com.yidao.mvp;

import android.util.SparseArray;

public class Usermodel implements IUserModel {

    private IUserPresenter mPresenter;
    private final UserBean userBean;
    private int mId;

    private SparseArray<UserBean> beanSparseArray = new SparseArray();

    public Usermodel(IUserPresenter presenter) {
        mPresenter = presenter;
        userBean = new UserBean();
    }

    @Override
    public void setID(int id) {
        mId = id;
        userBean.setId(id);
    }

    @Override
    public void setFirstName(String firstName) {
        userBean.setmFirstName(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        userBean.setmLastName(lastName);
        beanSparseArray.append(mId,userBean);
    }

    @Override
    public UserBean load(int id) {
        UserBean userBean = beanSparseArray.get(id);
        return userBean;
    }
}

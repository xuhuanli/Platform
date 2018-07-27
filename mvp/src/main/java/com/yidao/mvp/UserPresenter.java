package com.yidao.mvp;

public class UserPresenter implements IUserPresenter {
    private IUserView mView;
    private final IUserModel usermodel;

    public UserPresenter(IUserView iUserView) {
        mView =  iUserView;
        usermodel = new Usermodel(this);
    }

    @Override
    public void saveUser(int id, String firstName, String lastName) {
        usermodel.setID(id);
        usermodel.setFirstName(firstName);
        usermodel.setLastName(lastName);
    }

    @Override
    public void loadUser(int id) {
        UserBean load = usermodel.load(id);
        mView.setFirstName(load.getFirstName());
        mView.setLastName(load.getFirstName());
    }
}

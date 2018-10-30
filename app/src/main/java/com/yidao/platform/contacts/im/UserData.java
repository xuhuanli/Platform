package com.yidao.platform.contacts.im;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;

@Entity
public class UserData {
    @Id
    public long id;
    @Unique
    String userName;
    String userId;
    String portraitUri;

    public UserData(long id, String userId, String userName, String portraitUri) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.portraitUri = portraitUri;
    }

    public UserData() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }
}

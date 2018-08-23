package com.yidao.platform.info.view;

import com.yidao.platform.info.model.BottleMsgBean;
import com.yidao.platform.info.model.FindMsgBean;

import java.util.List;

public interface IViewMyMessage {
    void successBottle(BottleMsgBean.ResultBean.PageBean pageBean, List<BottleMsgBean.ResultBean.ListBean> listBeans);

    void successFind(FindMsgBean.ResultBean.PageBean pageBean, List<FindMsgBean.ResultBean.ListBean> listBeans);

    void successUpdate();
}

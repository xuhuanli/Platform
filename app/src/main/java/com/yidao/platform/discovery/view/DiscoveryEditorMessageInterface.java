package com.yidao.platform.discovery.view;

import com.yidao.platform.app.OssBean;

public interface DiscoveryEditorMessageInterface {
    void uploadPicFailed();

    void showDialog();

    void uploadSuccess();

    void saveOss(OssBean.ResultBean bean);

    void uploadFailed();
}

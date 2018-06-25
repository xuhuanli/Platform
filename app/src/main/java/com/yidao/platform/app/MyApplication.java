package com.yidao.platform.app;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

public class MyApplication extends TinkerApplication {

    public MyApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.yidao.platform.app.MyApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

    //Application被改造 在这个类禁止做任何操作！ 所有的操作请放到MyApplicationLike中处理
}

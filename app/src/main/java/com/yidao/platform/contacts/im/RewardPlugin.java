package com.yidao.platform.contacts.im;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.yidao.platform.R;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;

public class RewardPlugin implements IPluginModule {
    @Override
    public Drawable obtainDrawable(Context context) {
        //替换打赏icon
        return ContextCompat.getDrawable(context, io.rong.imkit.R.drawable.rc_ic_files_selector);
    }

    @Override
    public String obtainTitle(Context context) {
        return context.getString(R.string.reward);
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        Toast.makeText(fragment.getContext(), "打赏", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}

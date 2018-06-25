package com.yidao.platform.container;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yidao.platform.R;

import java.util.ArrayList;

public class ViewpagerAdapter extends FragmentPagerAdapter {

    public static final String[] TABNAMES = new String[]{"阅读", "发现", "服务", "我的"};
    public static final int[] DRAWABLERESIDS = {R.drawable.ic_assignment_black_24dp, R.drawable.ic_assignment_ind_black_24dp, R.drawable.ic_assignment_late_black_24dp, R.drawable.ic_assignment_return_black_24dp};
    private ArrayList<Fragment> fragmentArrayList;

    public ViewpagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList = fragmentArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}

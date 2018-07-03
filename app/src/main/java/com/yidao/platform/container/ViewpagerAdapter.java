package com.yidao.platform.container;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.yidao.platform.R;

import java.util.ArrayList;

public class ViewpagerAdapter extends FragmentPagerAdapter {

    public static final String[] TAB_NAMES = new String[]{"阅读", "发现", "服务", "我的"};
    public static final int[] DRAWABLE_RES_IDS = {R.drawable.ic_assignment_black_24dp, R.drawable.ic_assignment_ind_black_24dp, R.drawable.ic_assignment_late_black_24dp, R.drawable.ic_assignment_return_black_24dp};
    private ArrayList<Fragment> fragmentArrayList;

    ViewpagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
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

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}

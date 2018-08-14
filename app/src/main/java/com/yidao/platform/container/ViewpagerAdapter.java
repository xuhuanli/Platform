package com.yidao.platform.container;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.yidao.platform.R;

import java.util.ArrayList;

public class ViewpagerAdapter extends FragmentPagerAdapter {

    public static final String[] TAB_NAMES = new String[]{"阅读", "发现", "服务", "我的"};
    public static final int[] DRAWABLE_RES_UNSELECTED = {R.drawable.read_unselected, R.drawable.discovery_unselected, R.drawable.server_unselected, R.drawable.my_unselected};
    public static final int[] DRAWABLE_RES_SELECTED = {R.drawable.read_selected, R.drawable.discovery_selected, R.drawable.server_selected, R.drawable.my_selected};
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

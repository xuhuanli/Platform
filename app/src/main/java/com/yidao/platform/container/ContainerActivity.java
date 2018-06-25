package com.yidao.platform.container;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.discovery.DiscoveryFragment;
import com.yidao.platform.read.view.ReadFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * The type Container activity.
 *
 * @author xuhuanli
 */
public class ContainerActivity extends BaseActivity {

    @BindView(R.id.vp_container)
    ViewPager mViewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadTabData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_container;
    }

    private void initView() {
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new ReadFragment());
        list.add(new DiscoveryFragment());
        list.add(new ReadFragment());
        list.add(new ReadFragment());
        mViewPager.setAdapter(new ViewpagerAdapter(getSupportFragmentManager(), list));
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setCurrentItem(0, true);
    }

    private void loadTabData() {
        mTabLayout.setupWithViewPager(mViewPager, true);
        for (int i = 0; i < ViewpagerAdapter.DRAWABLERESIDS.length; i++) {
            mTabLayout.getTabAt(i).setText(ViewpagerAdapter.TABNAMES[i]);
            mTabLayout.getTabAt(i).setIcon(ViewpagerAdapter.DRAWABLERESIDS[i]);
        }
    }
}

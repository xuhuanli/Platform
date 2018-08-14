package com.yidao.platform.container;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.discovery.DiscoveryFragment;
import com.yidao.platform.info.view.MyInfoFragment;
import com.yidao.platform.read.view.ReadFragment;
import com.yidao.platform.service.ServiceFragment;

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
        list.add(new ServiceFragment());
        list.add(new MyInfoFragment());
        mViewPager.setAdapter(new ViewpagerAdapter(getSupportFragmentManager(), list));
        mViewPager.setOffscreenPageLimit(1);
        //smoothScroll  true:在点击tablayout时，vp会有滑动效果 false : 取消平滑效果
        //mViewPager.setCurrentItem(0, false);
    }

    private void loadTabData() {
        mTabLayout.setupWithViewPager(mViewPager, true);
        for (int i = 0; i < ViewpagerAdapter.DRAWABLE_RES_UNSELECTED.length; i++) {
            mTabLayout.getTabAt(i).setText(ViewpagerAdapter.TAB_NAMES[i]).setIcon(ViewpagerAdapter.DRAWABLE_RES_UNSELECTED[i]);
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mTabLayout.getTabAt(position).setIcon(ViewpagerAdapter.DRAWABLE_RES_SELECTED[position]);
                mViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mTabLayout.getTabAt(position).setIcon(ViewpagerAdapter.DRAWABLE_RES_UNSELECTED[position]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

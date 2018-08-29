package com.yidao.platform.container;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.discovery.view.DiscoveryFragment;
import com.yidao.platform.events.SignUpEvent;
import com.yidao.platform.info.view.MyInfoFragment;
import com.yidao.platform.read.view.ReadFragment;
import com.yidao.platform.service.ServiceFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * The type Container activity.
 *
 * @author xuhuanli
 */
public class ContainerActivity extends BaseActivity {

    private static final String mPageName = "ContainerActivity";

    @BindView(R.id.vp_container)
    ViewPager mViewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        EventBus.getDefault().register(this);
        initView();
        loadTabData();
        initNotificationChannel();
    }

    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "bottle";
            String channelName = "漂流瓶消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "comment";
            channelName = "评论消息";
            importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "system";
            channelName = "系统消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "article";
            channelName = "文章推送";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
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
        mViewPager.setOffscreenPageLimit(3);
        //smoothScroll  true:在点击tablayout时，vp会有滑动效果 false : 取消平滑效果
        //mViewPager.setCurrentItem(0, false);
    }

    private void loadTabData() {
        mTabLayout.setupWithViewPager(mViewPager, true);
        for (int i = 0; i < ViewpagerAdapter.DRAWABLE_RES_UNSELECTED.length; i++) {
            mTabLayout.getTabAt(i).setText(ViewpagerAdapter.TAB_NAMES[i]).setIcon(ViewpagerAdapter.DRAWABLE_RES_UNSELECTED[i]);
        }
        //不正经fix:修复tablayout默认选中bug
        mTabLayout.getTabAt(0).setIcon(ViewpagerAdapter.DRAWABLE_RES_SELECTED[0]);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeInfoEvent(SignUpEvent event) {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

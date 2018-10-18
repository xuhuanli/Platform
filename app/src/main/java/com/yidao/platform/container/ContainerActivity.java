package com.yidao.platform.container;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.contacts.ContactsFragment;
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
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int taskId = getTaskId();
        MyLogger.e("ContainerActivity:所在的任务的id为: " + taskId);
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
        list.add(new ContactsFragment());
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
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(R.layout.container_layout_tab_item);
            TextView tabText = tab.getCustomView().findViewById(R.id.tv_tab);
            tabText.setText(ViewpagerAdapter.TAB_NAMES[i]);
            tabText.setCompoundDrawablesWithIntrinsicBounds(0, ViewpagerAdapter.DRAWABLE_RES_UNSELECTED[i], 0, 0);
            if (i == 0) {
                tabText.setCompoundDrawablesWithIntrinsicBounds(0, ViewpagerAdapter.DRAWABLE_RES_SELECTED[0], 0, 0);
                tabText.setTextColor(getResources().getColor(R.color.FF007AFF));
            }
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView myTab = mTabLayout.getTabAt(position).getCustomView().findViewById(R.id.tv_tab);
                myTab.setCompoundDrawablesWithIntrinsicBounds(0, ViewpagerAdapter.DRAWABLE_RES_SELECTED[position], 0, 0);
                myTab.setTextColor(getResources().getColor(R.color.FF007AFF));
                mViewPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView myTab = mTabLayout.getTabAt(position).getCustomView().findViewById(R.id.tv_tab);
                myTab.setCompoundDrawablesWithIntrinsicBounds(0, ViewpagerAdapter.DRAWABLE_RES_UNSELECTED[position], 0, 0);
                myTab.setTextColor(getResources().getColor(R.color.FF999999));
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


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }
}

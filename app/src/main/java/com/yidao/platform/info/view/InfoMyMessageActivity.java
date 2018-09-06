package com.yidao.platform.info.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.xuhuanli.androidutils.sharedpreference.IPreference;
import com.yidao.platform.R;
import com.yidao.platform.app.Constant;
import com.yidao.platform.app.base.BaseActivity;
import com.yidao.platform.app.utils.MyLogger;
import com.yidao.platform.discovery.view.DiscoveryBottleDetailActivity;
import com.yidao.platform.discovery.view.FriendsGroupDetailActivity;
import com.yidao.platform.events.RefreshInfoEvent;
import com.yidao.platform.info.adapter.BottleViewAdapter;
import com.yidao.platform.info.adapter.CommentViewAdapter;
import com.yidao.platform.info.adapter.SystemViewAdapter;
import com.yidao.platform.info.model.BottleMsgBean;
import com.yidao.platform.info.model.FindMsgBean;
import com.yidao.platform.info.presenter.MyMessageActivityPresenter;
import com.yidao.platform.read.adapter.ErrorAdapter;
import com.yidao.platform.read.view.CustomDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.bingoogolapple.badgeview.BGABadgeTextView;
import io.reactivex.functions.Consumer;

public class InfoMyMessageActivity extends BaseActivity implements IViewMyMessage {

    private static final int MAX_MESSAGE = 65535;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tb_info_my_msg)
    TabLayout mTabLayout;
    @BindView(R.id.rv_msg)
    RecyclerView mRecyclerView;
    private BGABadgeTextView tvItem1;
    private BGABadgeTextView tvItem2;
    private BGABadgeTextView tvItem3;
    private MyMessageActivityPresenter mPresenter;
    private int mNextRequestPage = 1;
    private String userId;
    private List<BottleMsgBean.ResultBean.ListBean> bottleDataList;
    private List<FindMsgBean.ResultBean.ListBean> findDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MyMessageActivityPresenter(this);
        userId = IPreference.prefHolder.getPreference(this).get(Constant.STRING_USER_ID, IPreference.DataType.STRING);
        initView();
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MyLogger.e("onNewIntent executed");
        mPresenter.qryBottleMess(userId, mNextRequestPage, MAX_MESSAGE);
    }

    private void initView() {
        tvTitle.setText("我的消息");
        addDisposable(RxToolbar.navigationClicks(toolbar).throttleFirst(Constant.THROTTLE_TIME, TimeUnit.MILLISECONDS).subscribe(o -> {
            EventBus.getDefault().post(new RefreshInfoEvent());
            finish();
        }));
        initTabLayout();
    }

    private void initTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(R.layout.msg_layout_tab_item));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(R.layout.msg_layout_tab_item));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(R.layout.msg_layout_tab_item));
        tvItem1 = mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tv_item);
        tvItem2 = mTabLayout.getTabAt(1).getCustomView().findViewById(R.id.tv_item);
        tvItem3 = mTabLayout.getTabAt(2).getCustomView().findViewById(R.id.tv_item);
        tvItem1.setText(R.string.system_msg);
        tvItem2.setText(R.string.comment_msg);
        tvItem3.setText(R.string.bottle_msg);
        setDefaultTabView();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        ArrayList<String> systemDatas = new ArrayList<>();
                        SystemViewAdapter systemViewAdapter = new SystemViewAdapter(systemDatas);
                        setTabViewData(systemViewAdapter);
                        if (systemDatas.size() == 0) {
                            View view = LayoutInflater.from(InfoMyMessageActivity.this).inflate(R.layout.info_no_msg_layout, mRecyclerView, false);
                            ((TextView) view.findViewById(R.id.tv_tips)).setText(R.string.no_notify);
                            systemViewAdapter.setEmptyView(view);
                            systemViewAdapter.setNewData(null);
                        }
                        break;
                    case 1:
                        if (findDataList != null) {
                            CommentViewAdapter commentViewAdapter = new CommentViewAdapter(findDataList);
                            setTabViewData(commentViewAdapter);
                            commentViewAdapter.setOnItemClickListener((adapter, view, position) -> {
                                FindMsgBean.ResultBean.ListBean item = (FindMsgBean.ResultBean.ListBean) adapter.getItem(position);
                                mPresenter.upMessageStat(item.getMessageId());
                                Intent intent = new Intent(InfoMyMessageActivity.this, FriendsGroupDetailActivity.class);
                                intent.putExtra(Constant.STRING_FIND_ID, item.getFindId());
                                startActivity(intent);
                            });
                            if (findDataList.size() == 0) {
                                View view = LayoutInflater.from(InfoMyMessageActivity.this).inflate(R.layout.info_no_msg_layout, mRecyclerView, false);
                                ((TextView) view.findViewById(R.id.tv_tips)).setText(R.string.no_comments_message);
                                commentViewAdapter.setEmptyView(view);
                                commentViewAdapter.setNewData(null);
                            }
                        } else {
                            showError();
                        }
                        break;
                    case 2:
                        if (bottleDataList != null) {
                            BottleViewAdapter bottleViewAdapter = new BottleViewAdapter(bottleDataList);
                            setTabViewData(bottleViewAdapter);
                            bottleViewAdapter.setOnItemClickListener((adapter, view, position) -> {
                                BottleMsgBean.ResultBean.ListBean item = (BottleMsgBean.ResultBean.ListBean) adapter.getItem(position);
                                mPresenter.upMessageStat(item.getMessageId());
                                Intent intent = new Intent(InfoMyMessageActivity.this, DiscoveryBottleDetailActivity.class);
                                intent.putExtra(Constant.STRING_BOTTLE_ID, item.getBottleId());
                                intent.putExtra(Constant.STRING_SESSION_ID, item.getSessionId());
                                intent.putExtra(Constant.STRING_BOTTLE_PAGE_FROM, "3");
                                startActivity(intent);
                            });
                            if (bottleDataList.size() == 0) {
                                View view = LayoutInflater.from(InfoMyMessageActivity.this).inflate(R.layout.info_no_msg_layout, mRecyclerView, false);
                                ((TextView) view.findViewById(R.id.tv_tips)).setText(R.string.no_bottle_message);
                                bottleViewAdapter.setEmptyView(view);
                                bottleViewAdapter.setNewData(null);
                            }
                        } else {
                            showError();
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void showError() {
        ErrorAdapter adapter = new ErrorAdapter(null);
        adapter.bindToRecyclerView(mRecyclerView);
        View view = LayoutInflater.from(this).inflate(R.layout.info_no_msg_layout, mRecyclerView, false);
        ((TextView) view.findViewById(R.id.tv_tips)).setText(R.string.connection_failed);
        adapter.setEmptyView(view);
        adapter.setNewData(null);
    }

    private void setTabViewData(RecyclerView.Adapter adapter) {
        configRecyclerView(adapter);
    }

    private void setDefaultTabView() {
        ArrayList<String> systemDatas = new ArrayList<>();
        SystemViewAdapter systemViewAdapter = new SystemViewAdapter(systemDatas);
        setTabViewData(systemViewAdapter);
        if (systemDatas.size() == 0) {
            View view = LayoutInflater.from(InfoMyMessageActivity.this).inflate(R.layout.info_no_msg_layout, mRecyclerView, false);
            ((TextView) view.findViewById(R.id.tv_tips)).setText(R.string.no_notify);
            systemViewAdapter.setEmptyView(view);
            systemViewAdapter.setNewData(null);
        }
    }

    private void initData() {
        mPresenter.qryBottleMess(userId, mNextRequestPage, MAX_MESSAGE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_activity_my_message;
    }

    private void configRecyclerView(RecyclerView.Adapter adapter) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new CustomDecoration(this, 1, 16, 16));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void successBottle(BottleMsgBean.ResultBean.PageBean pageBean, List<BottleMsgBean.ResultBean.ListBean> listBeans) {
        mPresenter.qryFindMess(userId, mNextRequestPage, MAX_MESSAGE);
        if (pageBean != null) {
            String total = pageBean.getTotal();
            if (!"0".equals(total)) {
                tvItem3.showTextBadge(total);
            }
        }
        bottleDataList = listBeans;
    }

    @Override
    public void successFind(FindMsgBean.ResultBean.PageBean pageBean, List<FindMsgBean.ResultBean.ListBean> listBeans) {
        if (pageBean != null) {
            String total = pageBean.getTotal();
            if (!"0".equals(total)) {
                tvItem2.showTextBadge(total);
            }
        }
        findDataList = listBeans;
    }

    @Override
    public void successUpdate() {
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new RefreshInfoEvent());
        super.onBackPressed();
    }
}

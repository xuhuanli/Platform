package com.yidao.platform.contacts;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yidao.platform.R;
import com.yidao.platform.app.base.BaseFragment;
import com.yidao.platform.app.utils.MyLogger;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

import static com.yidao.platform.contacts.im.ConversationActivity.IM_TOKEN;

public class ContactsFragment extends BaseFragment implements IViewContactsFragment, Toolbar.OnMenuItemClickListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.tb_include)
    Toolbar toolbar;
    @BindView(R.id.recycleview)
    RecyclerView mRecyclerView;
    @BindView(R.id.tb_title)
    TextView tbTitle;
    private ContactsFragmentPresenter mPresenter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void initView() {
        initToolbar();
        mPresenter = new ContactsFragmentPresenter(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void initToolbar() {
        tbTitle.setText(R.string.contacts);
        toolbar.inflateMenu(R.menu.contacts_menu);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.contacts_fragment_content;
    }

    @Override
    protected void initData() {
//        mPresenter.getData();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        ContactsAdapter contactsAdapter = new ContactsAdapter(R.layout.contacts_card, list);
        mRecyclerView.setAdapter(contactsAdapter);
        contactsAdapter.addHeaderView(getHeaderView());
        contactsAdapter.setOnItemClickListener(this);
        //在这里链接im server
        if (RongIM.getInstance().getCurrentConnectionStatus().getValue() != RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED.getValue()) {
            MyLogger.e("没有连接server");
            RongIM.connect(IM_TOKEN, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    MyLogger.e("onTokenIncorrect");
                }

                @Override
                public void onSuccess(String s) {
                    MyLogger.e("onSuccess userId is " + s);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }

    private View getHeaderView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.contacts_header_view, (ViewGroup) mRecyclerView.getParent(), false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contacts_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_contacts:
                HashMap<String, Boolean> map = new HashMap<>();
                map.put(Conversation.ConversationType.PRIVATE.getName(), false);
                RongIM.getInstance().startConversationList(getActivity(), map);
                break;
        }
        return false;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        HashMap<String, Boolean> map = new HashMap<>();
        map.put(Conversation.ConversationType.PRIVATE.getName(), false);
        RongIM.getInstance().startPrivateChat(getActivity(), "888888", "cdq");
    }
}

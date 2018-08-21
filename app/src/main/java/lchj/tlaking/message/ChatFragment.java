package lchj.tlaking.message;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;

import com.dqjq.common.BaseFragment;

import butterknife.BindView;
import lchj.tlaking.R;
import lchj.tlaking.activity.MessageActivity;

public abstract class ChatFragment extends BaseFragment implements
        AppBarLayout.OnOffsetChangedListener {

    protected String mReceiverId;

    @BindView(R.id.chat_toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingLayout;

    @BindView(R.id.edit_content)
    EditText mContent;

    @BindView(R.id.btn_submit)
    View mSubmit;

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        mReceiverId = bundle.getString(MessageActivity.KEY_RECEIVER_ID);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_common;
    }

    // 得到顶部布局的资源Id
    @LayoutRes
    protected abstract int getHeaderLayoutId();

    @Override
    protected void initWidget(View root) {
        // 拿到占位布局
        // 替换顶部布局一定需要发生在super之前
        // 防止控件绑定异常
        ViewStub stub = root.findViewById(R.id.view_stub_header);
        stub.setLayoutResource(getHeaderLayoutId());
        stub.inflate();

        // 在这里进行了控件绑定
        super.initWidget(root);

        initToolbar();
        initAppbar();
        // RecyclerView基本设置
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initToolbar() {
        Toolbar toolbar = mToolbar;
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private void initAppbar() {
        mAppBarLayout.addOnOffsetChangedListener(this);
    }
}

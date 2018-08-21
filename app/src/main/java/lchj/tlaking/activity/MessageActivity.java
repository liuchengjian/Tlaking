package lchj.tlaking.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.dqjq.common.BaseActivty;
import com.dqjq.common.BaseFragment;

import lchj.tlaking.R;
import lchj.tlaking.fragment.message.ChatUserFragment;
import lchj.tlaking.message.ChatGroupFragment;


public class MessageActivity extends BaseActivty {
    // 接收者Id，可以是群，也可以是人的Id
    public static final String KEY_RECEIVER_ID = "KEY_RECEIVER_ID";
    // 是否是群
    private static final String KEY_RECEIVER_IS_GROUP = "KEY_RECEIVER_IS_GROUP";

    private String mReceiverId;
    private boolean mIsGroup;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_message;
    }
//    public static void show(Context context, Session session) {
//        if (session == null || context == null || TextUtils.isEmpty(session.getId()))
//            return;
//        Intent intent = new Intent(context, MessageActivity.class);
//        intent.putExtra(KEY_RECEIVER_ID, session.getId());
//        intent.putExtra(KEY_RECEIVER_IS_GROUP, session.getReceiverType() == Message.RECEIVER_TYPE_GROUP);
//        context.startActivity(intent);
//    }
    @Override
    protected boolean initArgs(Bundle bundle) {
        mReceiverId= getIntent().getStringExtra("0");
        mIsGroup = getIntent().getBooleanExtra("1",false);
        return !TextUtils.isEmpty(mReceiverId);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("");
        BaseFragment fragment;
        if (mIsGroup)
            fragment = new ChatGroupFragment();
        else
            fragment = new ChatUserFragment();

        // 从Activity传递参数到Fragment中去
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RECEIVER_ID, mReceiverId);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.message_container, fragment)
                .commit();
    }

}

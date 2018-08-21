package lchj.tlaking.fragment;


import android.content.Intent;

import com.dqjq.common.BaseFragment;
import com.dqjq.common.widget.PortraitView;

import butterknife.BindView;
import butterknife.OnClick;
import lchj.tlaking.R;
import lchj.tlaking.UserActivity;

/**
 *我的
 */
public class MyFragment extends BaseFragment {

    @BindView(R.id.MyPortraitView)
    PortraitView mPortraitView;


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_my;
    }

    @OnClick(R.id.MyPortraitView)
    void PortraitClick(){
        Intent intent = new Intent(getContext(), UserActivity.class);
        getContext().startActivity(intent);

    }

    @Override
    protected void initData() {
        super.initData();

    }


}

package com.dqjq.factory.presenter.contact;


import com.dqjq.common.data.DataSource;
import com.dqjq.common.recycler.BaseRecyclerAdapter;
import com.dqjq.factory.db.User;
import com.dqjq.factory.presenter.BasePresenter;

import java.util.List;


public class ContactPresenter extends BasePresenter<ContactContract.View>
        implements ContactContract.Presenter, DataSource.SucceedCallback<List<User>> {


    public ContactPresenter(ContactContract.View view) {
        super(view);
    }
    @Override
    public void start() {
        super.start();

        // 加载网络数据
//        UserHelper.refreshContacts();
    }
    @Override
    public void onDataLoaded(List<User> users) {
        // 无论怎么操作，数据变更，最终都会通知到这里来
        final ContactContract.View view = getView();
        if (view == null)
            return;

        BaseRecyclerAdapter<User> adapter = view.getRecyclerAdapter();
//        List<User> old = adapter.getItems();
//
//        // 进行数据对比
//        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, users);
//        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
//
//        // 调用基类方法进行界面刷新
//        refreshData(result, users);
    }
}

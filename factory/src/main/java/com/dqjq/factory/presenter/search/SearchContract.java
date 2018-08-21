package com.dqjq.factory.presenter.search;


import com.dqjq.factory.data.bean.UserCard;
import com.dqjq.factory.presenter.BaseContract;

import java.util.List;


/**
 */
public interface SearchContract {
    interface Presenter extends BaseContract.Presenter {
        // 搜索内容
        void search(String content);
    }

    // 搜索人的界面
    interface UserView extends BaseContract.View<Presenter> {
        void onSearchDone(List<UserCard> userCards);
    }

    // 搜索群的界面
//    interface GroupView extends BaseContract.View<Presenter> {
//        void onSearchDone(List<GroupCard> groupCards);
//    }

}

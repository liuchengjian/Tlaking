package com.dqjq.factory.presenter.account;


import com.dqjq.factory.presenter.BaseContract;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public interface RegisterContract {
    interface RegisterView extends BaseContract.View<RegisterPresenter> {
        // 注册成功
        void registerSuccess();
    }

    interface RegisterPresenter extends BaseContract.Presenter {
        // 发起一个注册
        void register(String phone, String name, String password);

        // 检查手机号是否正确
        boolean checkMobile(String phone);
    }

}

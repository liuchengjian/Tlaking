package com.dqjq.factory.presenter.account;


import com.dqjq.factory.presenter.BaseContract;

public interface LoginContract {
    interface LoginView extends BaseContract.View<LoginPresenter> {
        // 登录成功
        void loginSuccess();
    }


    interface LoginPresenter extends BaseContract.Presenter {
        // 发起一个登录
        void login(String phone, String password);
    }

}
package com.dqjq.factory.data.helper;


import com.dqjq.common.data.DataSource;
import com.dqjq.factory.Factory;
import com.dqjq.factory.R;
import com.dqjq.factory.RspModel;
import com.dqjq.factory.data.bean.UserCard;
import com.dqjq.factory.data.bean.UserUpdateModel;
import com.dqjq.factory.db.User;
import com.dqjq.factory.net.Network;
import com.dqjq.factory.net.RemoteService;
import com.dqjq.factory.utils.Account;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHelper {
    // 更新用户信息的操作，异步的
    public static void update(UserUpdateModel model, final DataSource.Callback<UserCard> callback) {
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        // 得到一个Call
        Call<RspModel<UserCard>> call = service.userUpdate(model);
        // 网络请求
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()) {
                    UserCard userCard = rspModel.getResult();
                    // 唤起进行保存的操作
//                    Factory.getUserCenter().dispatch(userCard);
                    // 返回成功
                    Account.saveUser(userCard.build());
                    callback.onDataLoaded(userCard);
                } else {
                    // 错误情况下进行错误分配
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    // 搜索的方法
    public static Call search(String name, final DataSource.Callback<List<UserCard>> callback) {
        RemoteService service = Network.remote();
        Call<RspModel<List<UserCard>>> call = service.userSearch(name);

        call.enqueue(new Callback<RspModel<List<UserCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                RspModel<List<UserCard>> rspModel = response.body();
                if (rspModel.success()) {
                    // 返回数据
                    callback.onDataLoaded(rspModel.getResult());
                } else {
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });

        // 把当前的调度者返回
        return call;
    }


    /**
     * 搜索一个用户，优先网络查询
     * 没有用然后再从本地缓存拉取
     */
    public static User searchFirstOfNet(String id) {
        User user = findFromNet(id);
        if (user == null) {
//            return findFromLocal(id);
        }
        return user;
    }


    // 关注的网络请求
    public static void follow(String id, final DataSource.Callback<UserCard> callback) {
        RemoteService service = Network.remote();
        Call<RspModel<UserCard>> call = service.userFollow(id);

        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()) {
                    UserCard userCard = rspModel.getResult();
                    // 唤起进行保存的操作
//                    Factory.getUserCenter().dispatch(userCard);
                    // 返回数据
                    callback.onDataLoaded(userCard);
                } else {
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }

        });

    }

    /**
     * 搜索一个用户，优先本地缓存，
     * 没有用然后再从网络拉取
     */
    public static User search(String id) {
//        User user = findFromLocal(id);
//        if (user == null) {
//            return findFromNet(id);
//        }
//        return user;
        return findFromNet(id);
    }

    // 从网络查询某用户的信息
    public static User findFromNet(String id) {
        RemoteService remoteService = Network.remote();
        try {
            Response<RspModel<UserCard>> response = remoteService.userFind(id).execute();
            UserCard card = response.body().getResult();
            if (card != null) {
                User user = card.build();
                // 数据库的存储并通知
//                Factory.getUserCenter().dispatch(card);
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    // 从本地查询一个用户的信息
//    public static User findFromLocal(String id) {
//        return SQLite.select()
//                .from(User.class)
//                .where(User_Table.id.eq(id))
//                .querySingle();
//    }
}

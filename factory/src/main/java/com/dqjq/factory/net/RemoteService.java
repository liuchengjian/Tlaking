package com.dqjq.factory.net;

import com.dqjq.factory.RspModel;
import com.dqjq.factory.data.bean.LoginBean;
import com.dqjq.factory.data.bean.RegisterBean;
import com.dqjq.factory.data.bean.UserCard;
import com.dqjq.factory.data.bean.UserUpdateModel;
import com.dqjq.factory.net.api.AccountRspModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 网络请求的所有的接口
 */
public interface RemoteService {
    /**
     * 注册接口
     *
     * @param bean 传入的是RegisterBean
     * @return 返回的是RspModel<AccountRspModel>
     */
    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterBean bean);

    /**
     * 登录接口
     *
     * @param bean LoginBean
     * @return RspModel<AccountRspModel>
     */
    @POST("account/login")
    Call<RspModel<AccountRspModel>> accountLogin(@Body LoginBean bean);


    // 查询某人的信息
    @GET("user/{userId}")
    Call<RspModel<UserCard>> userFind(@Path("userId") String userId);

    // 用户搜索的接口
    @GET("user/search/{name}")
    Call<RspModel<List<UserCard>>> userSearch(@Path("name") String name);

    // 用户关注接口
    @PUT("user/follow/{userId}")
    Call<RspModel<UserCard>> userFollow(@Path("userId") String userId);

    // 用户更新的接口
    @PUT("user")
    Call<RspModel<UserCard>> userUpdate(@Body UserUpdateModel model);

//    // 发送消息的接口
//    @POST("msg")
//    Call<RspModel<MessageCard>> msgPush(@Body MsgCreateModel model);

}

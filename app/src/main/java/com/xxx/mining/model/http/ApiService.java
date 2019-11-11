package com.xxx.mining.model.http;

import com.xxx.mining.model.http.bean.AddressBean;
import com.xxx.mining.model.http.bean.AppVersionBean;
import com.xxx.mining.model.http.bean.BannerBean;
import com.xxx.mining.model.http.bean.DepositBean;
import com.xxx.mining.model.http.bean.DepositRecordBean;
import com.xxx.mining.model.http.bean.HomeBean;
import com.xxx.mining.model.http.bean.IsSettingPayPswBean;
import com.xxx.mining.model.http.bean.LoginBean;
import com.xxx.mining.model.http.bean.MillDetailBean;
import com.xxx.mining.model.http.bean.MyMiningBean;
import com.xxx.mining.model.http.bean.MyNode;
import com.xxx.mining.model.http.bean.MyNodeBean;
import com.xxx.mining.model.http.bean.MyOrderBean;
import com.xxx.mining.model.http.bean.NodeCommod;
import com.xxx.mining.model.http.bean.NodePayBean;
import com.xxx.mining.model.http.bean.NoticeCenterBean;
import com.xxx.mining.model.http.bean.PayOrderBean;
import com.xxx.mining.model.http.bean.RecordDepositBean;
import com.xxx.mining.model.http.bean.RecordGiftBean;
import com.xxx.mining.model.http.bean.RecordMiningBean;
import com.xxx.mining.model.http.bean.RecordRechargeBean;
import com.xxx.mining.model.http.bean.RecordTeamBean;
import com.xxx.mining.model.http.bean.RecordWithdrawalBean;
import com.xxx.mining.model.http.bean.SelectCountyBean;
import com.xxx.mining.model.http.bean.ShopMiningBean;
import com.xxx.mining.model.http.bean.UserInfo;
import com.xxx.mining.model.http.bean.WDepathBean;
import com.xxx.mining.model.http.bean.WalletBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.http.bean.base.BooleanBean;
import com.xxx.mining.model.http.bean.base.PageBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    //获取首页列表
    @FormUrlEncoded
    @POST("/getCoinInfo")
    Observable<BaseBean<PageBean<HomeBean>>> getHomeList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取商品矿机
    @FormUrlEncoded
    @POST("/getCommodities")
    Observable<BaseBean<PageBean<ShopMiningBean>>> getShopMiningList(
            @Field("classId") String classId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //矿机banner
    @POST("/millBanner")
    Observable<BaseBean<BannerBean>> getBannerList();

    //商城Banner
    @POST("/storeBanner")
    Observable<BaseBean<BannerBean>> getShopBanner();

    //获取钱包列表
    @POST("/myWallet")
    Observable<BaseBean<WalletBean>> getWalletList();

    //获取我的矿机列表
    @FormUrlEncoded
    @POST("/myMills")
    Observable<BaseBean<PageBean<MyMiningBean>>> getMyMiningList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //挖矿
    @FormUrlEncoded
    @POST("/millDetail")
    Observable<BaseBean<MillDetailBean>> getmillDetail(
            @Field("orderId") int orderId
    );



    //获取我的节点列表
    @FormUrlEncoded
    @POST("/invest/getFinancingsIncomeLogs")
    Observable<BaseBean<List<MyNodeBean>>> getMyNodeList(
            @Field("userId") String userId,
            @Field("unit") String unit,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取我的订单列表
    @FormUrlEncoded
    @POST("/myOrder")
    Observable<BaseBean<PageBean<MyOrderBean>>> getMyOrderList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取公告中心列表
    @FormUrlEncoded
    @POST("/announcement/page")
    Observable<BaseBean<NoticeCenterBean>> getNoticeCenterList(
            @Field("pageNo") int pageNo,
            @Field("pageSize") int pageSize
    );


    //获取充值记录列表
    @FormUrlEncoded
    @POST("/getRechange")
    Observable<BaseBean<PageBean<RecordRechargeBean>>> getRechargeRecordList(
            @Field("coinId") int coinId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取提现记录列表
    @FormUrlEncoded
    @POST("/getChange")
    Observable<BaseBean<PageBean<RecordWithdrawalBean>>> getWithdrawalRecordList(
            @Field("coinId") int coinId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取理财转入记录列表
    @FormUrlEncoded
    @POST()
    Observable<BaseBean<List<DepositRecordBean>>> getDepositInRecordList(
            @Field("userId") String userId,
            @Field("unit") String coinId,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取理财转出记录列表
    @FormUrlEncoded
    @POST()
    Observable<BaseBean<List<DepositRecordBean>>> getDepositOutRecordList(
            @Field("userId") String userId,
            @Field("unit") String coinId,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取挖矿收益
    @FormUrlEncoded
    @POST("/millIncomes")
    Observable<BaseBean<RecordMiningBean>> getRecordMiningList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取团队收益
    @GET("/getTeamIncome")
    Observable<BaseBean<RecordTeamBean>> getRecordTeamList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    //获取分红收益列表
    @GET("/getBonusRecord")
    Observable<BaseBean<PageBean<RecordGiftBean>>> getRecordGiftList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取理财收益列表
    @FormUrlEncoded
    @POST("/getManageIncome")
    Observable<BaseBean<PageBean<RecordDepositBean>>> getRecordDepositList(
            @Field("coinId") String coinId,
            @Field("pageNum") int pageNo,
            @Field("pageSize") int pageSize
    );

    //获取理财收益
    @FormUrlEncoded
    @POST("/getManageIncome")
    Observable<BaseBean<PageBean<RecordDepositBean>>> getDepositList(
            @Field("pageNum") int pageNo,
            @Field("pageSize") int pageSize
    );


    //----------------------------------------------------------获取信息----------------------------------------------------------------------------------------------------------------------------//

    //获取理财收益
    @FormUrlEncoded
    @POST("/myManage")
    Observable<BaseBean<DepositBean>> getDepositInfo(
            @Field("coinId") String coinId
    );

    //----------------------------------------------------------操作----------------------------------------------------------------------------------------------------------------------------//

    //提现
    @POST("/doChange")
    @FormUrlEncoded
    Observable<BaseBean<Object>> withdrawal(
            @Field("coinId") int coinId,
            @Field("amount") double amount,
            @Field("fee") double fee,
            @Field("code") int code,
            @Field("address") String address,
            @Field("jyPassword") String jyPassword,
            @Field("remark") String remark
    );

    /**
     * 提现发送验证码
     */
    @POST("/mobile/withdraw/code")
    @FormUrlEncoded
    Observable<BaseBean<Object>> sendWithdrawal(
            @Field("phone") String phone,
            @Field("country") String country
    );

    //兑换
    @POST("/invest/compass/doExchange")
    @FormUrlEncoded
    Observable<BaseBean<Object>> exchange(
            @Field("userId") String userId,
            @Field("coinId") String coinId,
            @Field("value") double value
    );

    //理财转入
    @POST("/addManageInvest")
    @FormUrlEncoded
    Observable<BaseBean<BooleanBean>> depositIn(
            @Field("amount") String amount,
            @Field("coinId") String coinId
    );

    //理财转出
    @POST("/outManageInvest")
    @FormUrlEncoded
    Observable<BaseBean<BooleanBean>> depositOut(
            @Field("amount") String amount,
            @Field("coinId") String coinId,
            @Field("jyPassword") String jyPassword
    );

    //下单
    @POST("/addOrder")
    @FormUrlEncoded
    Observable<BaseBean<PayOrderBean>> place(
            @Field("commodityId") int commodityId,
            @Field("commodityNum") String commodityNum,
            @Field("payPassword") String payPassword,
            @Field("code") String code
    );

    //升级节点
    @FormUrlEncoded
    @POST("/upNode")
    Observable<BaseBean<NodePayBean>> uplace(
            @Field("commodityId") int commodityId,
            @Field("invCode") String commodityNum,
            @Field("payPassword") String payPassword,
            @Field("code") String code
    );

    //-------------------------------------------------------登录-------------------------------------------------------------------------------------------------------------------------------//


    //登录
    @FormUrlEncoded
    @POST("/login")
    Observable<BaseBean<LoginBean>> login(
            @Field("phone") String account,
            @Field("password") String password,
            @Field("area") String area
    );

    //发送短信验证码
    @FormUrlEncoded
    @POST("/sendPhoneMassage")
    Observable<BaseBean<Object>> sendSMSCode(
            @Field("phone") String phone,
            @Field("area") String area
    );

    //发送修改密码验证码
    @POST("/sendUpdatePhoneMassage")
    Observable<BaseBean<Object>> sendUpdateSMSCode();

    //注册
    @FormUrlEncoded
    @POST("/register")
    Observable<BaseBean<Object>> register(
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("code") String smsCode,
            @Field("area") String area
    );

    //忘记密码
    @FormUrlEncoded
    @POST("/forgetPassword")
    Observable<BaseBean<Object>> forgetPsw(
            @Field("phone") String phone,
            @Field("newPassword") String newPassword,
            @Field("code") String smsCode
    );

    //修改登录密码
    @FormUrlEncoded
    @POST("/updatePassword")
    Observable<BaseBean<Object>> modifyLoginPsw(
            @Field("oldPassword") String password,
            @Field("newPassword") String mode,
            @Field("code") String smsCode
    );

    //修改支付密码
    @FormUrlEncoded
    @POST("/updatePayPassword")
    Observable<BaseBean<Object>> modifyPayPsw(
            @Field("payPassword") String password,
            @Field("code") String smsCode
    );

    //设置支付密码
    @FormUrlEncoded
    @POST("/setPayPassword")
    Observable<BaseBean<Object>> settingPayPsw(
            @Field("payPassword") String payPassword
    );

    //获取城市编码
    @POST()
    Observable<BaseBean<List<SelectCountyBean>>> getCounty();

    //检查app版本
    @GET("/checkUpdate")
    Observable<BaseBean<AppVersionBean>> checkAppVersion();

    //退出登录
    @POST("/loginOut")
    Observable<BaseBean<Object>> outLogin();

    //检查是否设置过支付密码
    @POST("/selectPayPassword")
    Observable<BaseBean<IsSettingPayPswBean>> checkIsSettingPayPassword();

    //意见反馈
    @POST()
    @FormUrlEncoded
    Observable<BaseBean<Object>> submitFeedback(
            @Field("content") String amount,
            @Field("phone") String address
    );

    //获取用户信息
    @POST("/getUser")
    Observable<BaseBean<UserInfo>> getUserinfo();


    //提笔地址记录
    @FormUrlEncoded
    @POST("/myAddress")
    Observable<BaseBean<PageBean<AddressBean>>> getUserAddress(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //提币修改地址
    @FormUrlEncoded
    @POST("/my_addresses/edit")
    Observable<BaseBean<Object>> getupdateAddress(
            @Field("id") int id,
            @Field("address") String address,
            @Field("remarks") String remarks
    );

    //提币地址删除
    @FormUrlEncoded
    @POST("/my_addresses/delete")
    Observable<BaseBean<Object>> getdeleteAddress(
            @Field("my_address_id") int id
    );

    //获取节点商品
    @FormUrlEncoded
    @POST("/getNodeCommodities")
    Observable<BaseBean<PageBean<NodeCommod>>> getNodeCommodities(
            @Field("classId") int classId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取我的节点
    @POST("/myNode")
    Observable<BaseBean<MyNode>> getmyNode();

    //深度
    @POST("/myDepth")
    Observable<BaseBean<List<WDepathBean>>> getmyDepth();

    //宽度
    @POST("/myWidth")
    Observable<BaseBean<WDepathBean>> getWidth();


}
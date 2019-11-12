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

    String baseUrl = "/NoahWallet";
//    String baseUrl = " ";


    //获取首页列表
    @FormUrlEncoded
    @POST(baseUrl + "/getCoinInfo")
    Observable<BaseBean<PageBean<HomeBean>>> getHomeList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取商品矿机
    @FormUrlEncoded
    @POST(baseUrl + "/getCommodities")
    Observable<BaseBean<PageBean<ShopMiningBean>>> getShopMiningList(
            @Field("classId") String classId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //矿机banner
    @POST(baseUrl + "/millBanner")
    Observable<BaseBean<BannerBean>> getBannerList();

    //商城Banner
    @POST(baseUrl + "/storeBanner")
    Observable<BaseBean<BannerBean>> getShopBanner();

    //获取钱包列表
    @POST(baseUrl + "/myWallet")
    Observable<BaseBean<WalletBean>> getWalletList();

    //获取我的矿机列表
    @FormUrlEncoded
    @POST(baseUrl + "/myMills")
    Observable<BaseBean<PageBean<MyMiningBean>>> getMyMiningList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //挖矿
    @FormUrlEncoded
    @POST(baseUrl + "/millDetail")
    Observable<BaseBean<MillDetailBean>> getmillDetail(
            @Field("orderId") int orderId
    );


    //获取我的节点列表
    @FormUrlEncoded
    @POST(baseUrl + "/invest/getFinancingsIncomeLogs")
    Observable<BaseBean<List<MyNodeBean>>> getMyNodeList(
            @Field("userId") String userId,
            @Field("unit") String unit,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取我的订单列表
    @FormUrlEncoded
    @POST(baseUrl + "/myOrder")
    Observable<BaseBean<PageBean<MyOrderBean>>> getMyOrderList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取公告中心列表
    @GET(baseUrl + "/messages")
    Observable<BaseBean<PageBean<NoticeCenterBean>>> getNoticeCenterList(
            @Query("pageNum") int pageNo,
            @Query("pageSize") int pageSize
    );


    //获取充值记录列表
    @FormUrlEncoded
    @POST(baseUrl + "/getRechange")
    Observable<BaseBean<PageBean<RecordRechargeBean>>> getRechargeRecordList(
            @Field("coinId") int coinId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取提现记录列表
    @FormUrlEncoded
    @POST(baseUrl + "/getChange")
    Observable<BaseBean<PageBean<RecordWithdrawalBean>>> getWithdrawalRecordList(
            @Field("coinId") int coinId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取理财转入记录列表
    @FormUrlEncoded
    @POST(baseUrl + "")
    Observable<BaseBean<List<DepositRecordBean>>> getDepositInRecordList(
            @Field("userId") String userId,
            @Field("unit") String coinId,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取理财转出记录列表
    @FormUrlEncoded
    @POST(baseUrl + "")
    Observable<BaseBean<List<DepositRecordBean>>> getDepositOutRecordList(
            @Field("userId") String userId,
            @Field("unit") String coinId,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取挖矿收益
    @FormUrlEncoded
    @POST(baseUrl + "/millIncomes")
    Observable<BaseBean<RecordMiningBean>> getRecordMiningList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取团队收益
    @GET(baseUrl + "/getTeamIncome")
    Observable<BaseBean<RecordTeamBean>> getRecordTeamList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    //获取分红收益列表
    @GET(baseUrl + "/getBonusRecord")
    Observable<BaseBean<RecordGiftBean>> getRecordGiftList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    //获取理财收益列表
    @FormUrlEncoded
    @POST(baseUrl + "/getManageIncome")
    Observable<BaseBean<PageBean<RecordDepositBean>>> getRecordDepositList(
            @Field("coinId") int coinId,
            @Field("pageNum") int pageNo,
            @Field("pageSize") int pageSize
    );

    //获取理财收益
    @FormUrlEncoded
    @POST(baseUrl + "/getManageIncome")
    Observable<BaseBean<PageBean<RecordDepositBean>>> getDepositList(
            @Field("pageNum") int pageNo,
            @Field("pageSize") int pageSize
    );


    //----------------------------------------------------------获取信息----------------------------------------------------------------------------------------------------------------------------//

    //获取理财收益
    @FormUrlEncoded
    @POST(baseUrl + "/myManage")
    Observable<BaseBean<DepositBean>> getDepositInfo(
            @Field("coinId") int coinId
    );

    //----------------------------------------------------------操作----------------------------------------------------------------------------------------------------------------------------//

    //提现
    @POST(baseUrl + "/doChange")
    @FormUrlEncoded
    Observable<BaseBean<BooleanBean>> withdrawal(
            @Field("coinId") int coinId,
            @Field("amount") double amount,
            @Field("fee") double fee,
            @Field("code") int code,
            @Field("address") String address,
            @Field("jyPassword") String jyPassword,
            @Field("remark") String remark
    );

    //兑换
    @POST(baseUrl + "/invest/compass/doExchange")
    @FormUrlEncoded
    Observable<BaseBean<Object>> exchange(
            @Field("userId") String userId,
            @Field("coinId") String coinId,
            @Field("value") double value
    );

    //理财转入
    @POST(baseUrl + "/addManageInvest")
    @FormUrlEncoded
    Observable<BaseBean<Object>> depositIn(
            @Field("amount") double amount,
            @Field("coinId") int coinId
    );

    //理财转出
    @POST(baseUrl + "/outManageInvest")
    @FormUrlEncoded
    Observable<BaseBean<Object>> depositOut(
            @Field("amount") double amount,
            @Field("coinId") int coinId,
            @Field("code") String code,
            @Field("jyPassword") String jyPassword
    );

    //下单
    @POST(baseUrl + "/addOrder")
    @FormUrlEncoded
    Observable<BaseBean<PayOrderBean>> place(
            @Field("commodityId") int commodityId,
            @Field("commodityNum") String commodityNum,
            @Field("payPassword") String payPassword,
            @Field("code") String code
    );

    //升级节点
    @FormUrlEncoded
    @POST(baseUrl + "/upNode")
    Observable<BaseBean<NodePayBean>> uplace(
            @Field("commodityId") int commodityId,
            @Field("invCode") String commodityNum,
            @Field("payPassword") String payPassword,
            @Field("code") String code
    );

    //-------------------------------------------------------登录-------------------------------------------------------------------------------------------------------------------------------//


    //登录
    @FormUrlEncoded
    @POST(baseUrl + "/login")
    Observable<BaseBean<LoginBean>> login(
            @Field("phone") String account,
            @Field("password") String password,
            @Field("area") String area
    );

    //发送短信验证码
    @FormUrlEncoded
    @POST(baseUrl + "/sendPhoneMassage")
    Observable<BaseBean<Object>> sendSMSCode(
            @Field("phone") String phone,
            @Field("area") String area
    );

    //发送修改密码验证码
    @POST(baseUrl + "/sendUpdatePhoneMassage")
    Observable<BaseBean<Object>> sendUpdateSMSCode();

    //注册
    @FormUrlEncoded
    @POST(baseUrl + "/register")
    Observable<BaseBean<Object>> register(
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("code") String smsCode,
            @Field("area") String area
    );

    //忘记密码
    @FormUrlEncoded
    @POST(baseUrl + "/forgetPassword")
    Observable<BaseBean<Object>> forgetPsw(
            @Field("phone") String phone,
            @Field("newPassword") String newPassword,
            @Field("code") String smsCode
    );

    //修改登录密码
    @FormUrlEncoded
    @POST(baseUrl + "/updatePassword")
    Observable<BaseBean<Object>> modifyLoginPsw(
            @Field("oldPassword") String password,
            @Field("newPassword") String mode,
            @Field("code") String smsCode
    );

    //修改支付密码
    @FormUrlEncoded
    @POST(baseUrl + "/updatePayPassword")
    Observable<BaseBean<Object>> modifyPayPsw(
            @Field("payPassword") String password,
            @Field("code") String smsCode
    );

    //设置支付密码
    @FormUrlEncoded
    @POST(baseUrl + "/setPayPassword")
    Observable<BaseBean<Object>> settingPayPsw(
            @Field("payPassword") String payPassword
    );

    //获取城市编码
    @POST(baseUrl + "")
    Observable<BaseBean<List<SelectCountyBean>>> getCounty();

    //检查app版本
    @GET(baseUrl + "/checkUpdate")
    Observable<BaseBean<AppVersionBean>> checkAppVersion();

    //退出登录
    @POST(baseUrl + "/loginOut")
    Observable<BaseBean<Object>> outLogin();

    //检查是否设置过支付密码
    @POST(baseUrl + "/selectPayPassword")
    Observable<BaseBean<IsSettingPayPswBean>> checkIsSettingPayPassword();

    //意见反馈
    @POST(baseUrl + "")
    @FormUrlEncoded
    Observable<BaseBean<Object>> submitFeedback(
            @Field("content") String amount,
            @Field("phone") String address
    );

    //获取用户信息
    @POST(baseUrl + "/getUser")
    Observable<BaseBean<UserInfo>> getUserinfo();


    //提笔地址记录
    @FormUrlEncoded
    @POST(baseUrl + "/myAddress")
    Observable<BaseBean<PageBean<AddressBean>>> getUserAddress(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //提币修改地址
    @FormUrlEncoded
    @POST(baseUrl + "/my_addresses/edit")
    Observable<BaseBean<Object>> getupdateAddress(
            @Field("id") int id,
            @Field("address") String address,
            @Field("remarks") String remarks
    );

    //提币地址删除
    @FormUrlEncoded
    @POST(baseUrl + "/my_addresses/delete")
    Observable<BaseBean<Object>> getdeleteAddress(
            @Field("my_address_id") int id
    );

    //获取节点商品
    @FormUrlEncoded
    @POST(baseUrl + "/getNodeCommodities")
    Observable<BaseBean<PageBean<NodeCommod>>> getNodeCommodities(
            @Field("classId") int classId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取我的节点
    @POST(baseUrl + "/myNode")
    Observable<BaseBean<MyNode>> getmyNode();

    //深度
    @POST(baseUrl + "/myDepth")
    Observable<BaseBean<List<WDepathBean>>> getmyDepth();

    //宽度
    @POST(baseUrl + "/myWidth")
    Observable<BaseBean<List<WDepathBean>>> getWidth();


}
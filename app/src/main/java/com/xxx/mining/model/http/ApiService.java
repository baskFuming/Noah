package com.xxx.mining.model.http;

import com.xxx.mining.ConfigClass;
import com.xxx.mining.model.http.bean.AddressBean;
import com.xxx.mining.model.http.bean.AppVersionBean;
import com.xxx.mining.model.http.bean.BannerBean;
import com.xxx.mining.model.http.bean.DepositBean;
import com.xxx.mining.model.http.bean.DepositRecordBean;
import com.xxx.mining.model.http.bean.HomeBean;
import com.xxx.mining.model.http.bean.InviteCode;
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
    @POST(ConfigClass.BASE_URL_PATH + "/getCoinInfo")
    Observable<BaseBean<PageBean<HomeBean>>> getHomeList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取商品矿机
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/getCommodities")
    Observable<BaseBean<PageBean<ShopMiningBean>>> getShopMiningList(
            @Field("classId") String classId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //矿机banner
    @POST(ConfigClass.BASE_URL_PATH + "/millBanner")
    Observable<BaseBean<BannerBean>> getBannerList();

    //商城Banner
    @POST(ConfigClass.BASE_URL_PATH + "/storeBanner")
    Observable<BaseBean<BannerBean>> getShopBanner();

    //获取钱包列表
    @POST(ConfigClass.BASE_URL_PATH + "/myWallet")
    Observable<BaseBean<WalletBean>> getWalletList();

    //获取我的矿机列表
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/myMills")
    Observable<BaseBean<PageBean<MyMiningBean>>> getMyMiningList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //挖矿
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/millDetail")
    Observable<BaseBean<MillDetailBean>> getmillDetail(
            @Field("orderId") int orderId
    );


    //获取我的节点列表
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/invest/getFinancingsIncomeLogs")
    Observable<BaseBean<List<MyNodeBean>>> getMyNodeList(
            @Field("userId") String userId,
            @Field("unit") String unit,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取我的订单列表
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/myOrder")
    Observable<BaseBean<PageBean<MyOrderBean>>> getMyOrderList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取公告中心列表
    @GET(ConfigClass.BASE_URL_PATH + "/messages")
    Observable<BaseBean<PageBean<NoticeCenterBean>>> getNoticeCenterList(
            @Query("pageNum") int pageNo,
            @Query("pageSize") int pageSize
    );


    //获取充值记录列表
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/getRechange")
    Observable<BaseBean<PageBean<RecordRechargeBean>>> getRechargeRecordList(
            @Field("coinId") int coinId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取提现记录列表
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/getChange")
    Observable<BaseBean<PageBean<RecordWithdrawalBean>>> getWithdrawalRecordList(
            @Field("coinId") int coinId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取理财转入记录列表
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "")
    Observable<BaseBean<List<DepositRecordBean>>> getDepositInRecordList(
            @Field("userId") String userId,
            @Field("unit") String coinId,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取理财转出记录列表
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "")
    Observable<BaseBean<List<DepositRecordBean>>> getDepositOutRecordList(
            @Field("userId") String userId,
            @Field("unit") String coinId,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取挖矿收益
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/millIncomes")
    Observable<BaseBean<RecordMiningBean>> getRecordMiningList(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取团队收益
    @GET(ConfigClass.BASE_URL_PATH + "/getTeamIncome")
    Observable<BaseBean<RecordTeamBean>> getRecordTeamList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    //获取分红收益列表
    @GET(ConfigClass.BASE_URL_PATH + "/getBonusRecord")
    Observable<BaseBean<RecordGiftBean>> getRecordGiftList(
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );

    //获取理财收益列表
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/getManageIncome")
    Observable<BaseBean<PageBean<RecordDepositBean>>> getRecordDepositList(
            @Field("coinId") int coinId,
            @Field("pageNum") int pageNo,
            @Field("pageSize") int pageSize
    );

    //获取理财收益
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/getManageIncome")
    Observable<BaseBean<PageBean<RecordDepositBean>>> getDepositList(
            @Field("pageNum") int pageNo,
            @Field("pageSize") int pageSize
    );


    //----------------------------------------------------------获取信息----------------------------------------------------------------------------------------------------------------------------//

    //获取理财收益
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/myManage")
    Observable<BaseBean<DepositBean>> getDepositInfo(
            @Field("coinId") int coinId
    );

    //----------------------------------------------------------操作----------------------------------------------------------------------------------------------------------------------------//

    //提现
    @POST(ConfigClass.BASE_URL_PATH + "/doChange")
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
    @POST(ConfigClass.BASE_URL_PATH + "/invest/compass/doExchange")
    @FormUrlEncoded
    Observable<BaseBean<Object>> exchange(
            @Field("userId") String userId,
            @Field("coinId") String coinId,
            @Field("value") double value
    );

    //理财转入
    @POST(ConfigClass.BASE_URL_PATH + "/addManageInvest")
    @FormUrlEncoded
    Observable<BaseBean<Object>> depositIn(
            @Field("amount") double amount,
            @Field("coinId") int coinId
    );

    //理财转出
    @POST(ConfigClass.BASE_URL_PATH + "/outManageInvest")
    @FormUrlEncoded
    Observable<BaseBean<Object>> depositOut(
            @Field("amount") double amount,
            @Field("coinId") int coinId,
            @Field("code") String code,
            @Field("jyPassword") String jyPassword
    );

    //下单
    @POST(ConfigClass.BASE_URL_PATH + "/addOrder")
    @FormUrlEncoded
    Observable<BaseBean<PayOrderBean>> place(
            @Field("commodityId") int commodityId,
            @Field("commodityNum") String commodityNum,
            @Field("payPassword") String payPassword,
            @Field("code") String code
    );

    //升级节点
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/upNode")
    Observable<BaseBean<NodePayBean>> uplace(
            @Field("commodityId") int commodityId,
            @Field("invCode") String commodityNum,
            @Field("payPassword") String payPassword,
            @Field("code") String code
    );

    //-------------------------------------------------------登录-------------------------------------------------------------------------------------------------------------------------------//


    //登录
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/login")
    Observable<BaseBean<LoginBean>> login(
            @Field("phone") String account,
            @Field("password") String password,
            @Field("area") String area
    );

    //发送短信验证码
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/sendPhoneMassage")
    Observable<BaseBean<Object>> sendSMSCode(
            @Field("phone") String phone,
            @Field("area") String area
    );

    //发送修改密码验证码
    @POST(ConfigClass.BASE_URL_PATH + "/sendUpdatePhoneMassage")
    Observable<BaseBean<Object>> sendUpdateSMSCode();

    //注册
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/register")
    Observable<BaseBean<Object>> register(
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("code") String smsCode,
            @Field("area") String area
    );

    //忘记密码
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/forgetPassword")
    Observable<BaseBean<Object>> forgetPsw(
            @Field("phone") String phone,
            @Field("newPassword") String newPassword,
            @Field("code") String smsCode,
            @Field("area") String area
    );

    //修改登录密码
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/updatePassword")
    Observable<BaseBean<Object>> modifyLoginPsw(
            @Field("oldPassword") String password,
            @Field("newPassword") String mode,
            @Field("code") String smsCode
    );

    //修改支付密码
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/updatePayPassword")
    Observable<BaseBean<Object>> modifyPayPsw(
            @Field("payPassword") String password,
            @Field("code") String smsCode
    );

    //设置支付密码
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/setPayPassword")
    Observable<BaseBean<Object>> settingPayPsw(
            @Field("payPassword") String payPassword
    );

    //获取城市编码
    @POST(ConfigClass.BASE_URL_PATH + "")
    Observable<BaseBean<List<SelectCountyBean>>> getCounty();

    //检查app版本
    @GET(ConfigClass.BASE_URL_PATH + "/checkUpdate")
    Observable<BaseBean<AppVersionBean>> checkAppVersion();

    //退出登录
    @POST(ConfigClass.BASE_URL_PATH + "/loginOut")
    Observable<BaseBean<Object>> outLogin();

    //检查是否设置过支付密码
    @POST(ConfigClass.BASE_URL_PATH + "/selectPayPassword")
    Observable<BaseBean<IsSettingPayPswBean>> checkIsSettingPayPassword();

    //意见反馈
    @POST(ConfigClass.BASE_URL_PATH + "")
    @FormUrlEncoded
    Observable<BaseBean<Object>> submitFeedback(
            @Field("content") String amount,
            @Field("phone") String address
    );

    //获取用户信息
    @POST(ConfigClass.BASE_URL_PATH + "/getUser")
    Observable<BaseBean<UserInfo>> getUserinfo();


    //提笔地址记录
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/myAddress")
    Observable<BaseBean<PageBean<AddressBean>>> getUserAddress(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //提币修改地址
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/my_addresses/edit")
    Observable<BaseBean<Object>> getupdateAddress(
            @Field("id") int id,
            @Field("address") String address,
            @Field("remarks") String remarks
    );

    //提币地址删除
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/my_addresses/delete")
    Observable<BaseBean<Object>> getdeleteAddress(
            @Field("my_address_id") int id
    );

    //创建币种信息
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/createAddress")
    Observable<BaseBean<Object>> getaddAddress(
            @Field("coinName") String coinName,
            @Field("address") String address,
            @Field("remarks") String remarks

    );

    //获取节点商品
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/getNodeCommodities")
    Observable<BaseBean<PageBean<NodeCommod>>> getNodeCommodities(
            @Field("classId") int classId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //获取我的节点
    @POST(ConfigClass.BASE_URL_PATH + "/myNode")
    Observable<BaseBean<MyNode>> getmyNode();

    //深度
    @POST(ConfigClass.BASE_URL_PATH + "/myDepth")
    Observable<BaseBean<List<WDepathBean>>> getmyDepth();

    //宽度
    @POST(ConfigClass.BASE_URL_PATH + "/myWidth")
    Observable<BaseBean<List<WDepathBean>>> getWidth();

    //邀请码验证
    @FormUrlEncoded
    @POST(ConfigClass.BASE_URL_PATH + "/isNode")
    Observable<BaseBean<InviteCode>> getInviteCode(
            @Field("invCode") String invCode
    );
}
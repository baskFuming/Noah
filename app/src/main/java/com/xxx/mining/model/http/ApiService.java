package com.xxx.mining.model.http;

import com.xxx.mining.model.http.bean.AppVersionBean;
import com.xxx.mining.model.http.bean.BannerBean;
import com.xxx.mining.model.http.bean.DepositInfoBean;
import com.xxx.mining.model.http.bean.DepositRecordBean;
import com.xxx.mining.model.http.bean.HomeBean;
import com.xxx.mining.model.http.bean.IsSettingPayPswBean;
import com.xxx.mining.model.http.bean.LoginBean;
import com.xxx.mining.model.http.bean.MyMiningBean;
import com.xxx.mining.model.http.bean.MyNodeBean;
import com.xxx.mining.model.http.bean.MyOrderBean;
import com.xxx.mining.model.http.bean.NoticeCenterBean;
import com.xxx.mining.model.http.bean.RecordDepositBean;
import com.xxx.mining.model.http.bean.RecordGiftBean;
import com.xxx.mining.model.http.bean.RecordMiningBean;
import com.xxx.mining.model.http.bean.RecordRechargeBean;
import com.xxx.mining.model.http.bean.RecordTeamBean;
import com.xxx.mining.model.http.bean.RecordWithdrawalBean;
import com.xxx.mining.model.http.bean.SelectCountyBean;
import com.xxx.mining.model.http.bean.ShopMiningBean;
import com.xxx.mining.model.http.bean.WalletBean;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.http.bean.base.BooleanBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    //获取首页列表
    @POST("/CT/invest/getAllCoinInfo")
    Observable<BaseBean<List<HomeBean>>> getHomeList();

    //获取商品矿机
    @FormUrlEncoded
    @POST("/CT/invest/getAllCoinInfo")
    Observable<BaseBean<List<ShopMiningBean>>> getShopMiningList(
            @Field("userId") String userId,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取轮播图
    @FormUrlEncoded
    @POST("/uc/ancillary/system/advertise")
    Observable<BaseBean<List<BannerBean>>> getBannerList(
            @Field("sysAdvertiseLocation") String location
    );

    //获取钱包列表
    @FormUrlEncoded
    @POST("/CT/invest/getUserAssetInfo")
    Observable<BaseBean<WalletBean>> getWalletList(
            @Field("userId") String userId
    );    //获取我的矿机列表
    @FormUrlEncoded
    @POST("/CT/invest/getFinancingsIncomeLogs")
    Observable<BaseBean<List<MyMiningBean>>> getMyMiningList(
            @Field("userId") String userId,
            @Field("unit") String unit,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取我的节点列表
    @FormUrlEncoded
    @POST("/CT/invest/getFinancingsIncomeLogs")
    Observable<BaseBean<List<MyNodeBean>>> getMyNodeList(
            @Field("userId") String userId,
            @Field("unit") String unit,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取我的订单列表
    @FormUrlEncoded
    @POST("/CT/invest/getFinancingsIncomeLogs")
    Observable<BaseBean<List<MyOrderBean>>> getMyOrderList(
            @Field("userId") String userId,
            @Field("unit") String unit,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取公告中心列表
    @FormUrlEncoded
    @POST("/uc/announcement/page")
    Observable<BaseBean<NoticeCenterBean>> getNoticeCenterList(
            @Field("pageNo") int pageNo,
            @Field("pageSize") int pageSize
    );

    //获取充值记录列表
    @FormUrlEncoded
    @POST("/CT/invest/getDepositLogs")
    Observable<BaseBean<List<RecordRechargeBean>>> getRechargeRecordList(
            @Field("userId") String userId,
            @Field("unit") String unit,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取提现记录列表
    @FormUrlEncoded
    @POST("/CT/invest/getWithdrawLogs")
    Observable<BaseBean<List<RecordWithdrawalBean>>> getWithdrawalRecordList(
            @Field("userId") String userId,
            @Field("unit") String unit,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取理财转入记录列表
    @FormUrlEncoded
    @POST("/CT/invest/getFinancingsLogs")
    Observable<BaseBean<List<DepositRecordBean>>> getDepositInRecordList(
            @Field("userId") String userId,
            @Field("unit") String coinId,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取理财转出记录列表
    @FormUrlEncoded
    @POST("/CT/invest/getFinancingsDrawoutLogs")
    Observable<BaseBean<List<DepositRecordBean>>> getDepositOutRecordList(
            @Field("userId") String userId,
            @Field("unit") String coinId,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取挖矿收益
    @FormUrlEncoded
    @POST("/CT/invest/getFlashsaleIncomeLogs")
    Observable<BaseBean<List<RecordMiningBean>>> getRecordMiningList(
            @Field("userId") String userId,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取团队收益
    @FormUrlEncoded
    @POST("/CT/invest/getCommunityInfo")
    Observable<BaseBean<List<RecordTeamBean>>> getRecordTeamList(
            @Field("userId") String userId
    );

    //获取分红收益列表
    @FormUrlEncoded
    @POST("/CT/invest/getAllFinancingsIncomeLogs")
    Observable<BaseBean<List<RecordGiftBean>>> getRecordGiftList(
            @Field("userId") String userId,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );

    //获取理财收益列表
    @FormUrlEncoded
    @POST("/CT/invest/getFinancingsIncomeLogs")
    Observable<BaseBean<List<RecordDepositBean>>> getRecordDepositList(
            @Field("userId") String userId,
            @Field("unit") String unit,
            @Field("page") int pageNo,
            @Field("row") int pageSize
    );


    //----------------------------------------------------------获取信息----------------------------------------------------------------------------------------------------------------------------//


    //获取理财收益信息
    @FormUrlEncoded
    @POST("/CT/invest/getFinancingsInfo")
    Observable<BaseBean<DepositInfoBean>> getDepositProfitInfo(
            @Field("userId") String userId,
            @Field("coinId") String coinId
    );

    //----------------------------------------------------------操作----------------------------------------------------------------------------------------------------------------------------//


    //提现
    @POST("/uc/withdraw/apply/code")
    @FormUrlEncoded
    Observable<BaseBean<Object>> withdrawal(
            @Field("userId") String userId,
            @Field("unit") String unit,
            @Field("fee") double fee,
            @Field("amount") double amount,
            @Field("address") String address,
            @Field("jyPassword") String jyPassword,
            @Field("code") String code
    );

    /**
     * 提现发送验证码
     */
    @POST(" /uc/mobile/withdraw/code")
    @FormUrlEncoded
    Observable<BaseBean<Object>> sendWithdrawal(
            @Field("phone") String phone,
            @Field("country") String country
    );

    //兑换
    @POST("/CT/invest/compass/doExchange")
    @FormUrlEncoded
    Observable<BaseBean<Object>> exchange(
            @Field("userId") String userId,
            @Field("coinId") String coinId,
            @Field("value") double value
    );

    //理财转入
    @POST("/CT/invest/compass/doFinancings")
    @FormUrlEncoded
    Observable<BaseBean<BooleanBean>> depositIn(
            @Field("userId") String userId,
            @Field("coinId") String coinId,
            @Field("value") double value
    );

    //理财转出
    @POST("/CT/invest/compass/doFinancingsDrawout")
    @FormUrlEncoded
    Observable<BaseBean<BooleanBean>> depositOut(
            @Field("userId") String userId,
            @Field("coinId") String coinId,
            @Field("value") double value,
            @Field("jyPassword") String jyPassword
    );

    //下单
    @POST("/CT/invest/compass/doExchange")
    @FormUrlEncoded
    Observable<BaseBean<Object>> place(
            @Field("userId") String userId,
            @Field("password") String password,
            @Field("code") String code,
            @Field("shopId") int shopId,
            @Field("number") double number
    );

    //-------------------------------------------------------登录-------------------------------------------------------------------------------------------------------------------------------//


    //登录
    @FormUrlEncoded
    @POST("/uc/login")
    Observable<BaseBean<LoginBean>> login(
            @Field("username") String account,
            @Field("password") String password
    );

    //发送短信验证码
    @FormUrlEncoded
    @POST("/uc/mobile/code")
    Observable<BaseBean<Object>> sendSMSCode(
            @Field("phone") String phone,
            @Field("country") String country
    );

    //注册
    @FormUrlEncoded
    @POST("/uc/register/phone_ext")
    Observable<BaseBean<Object>> register(
            @Field("username") String account,
            @Field("phone") String phone,
            @Field("code") String smsCode,
            @Field("password") String password,
            @Field("country") String country
    );

    //忘记密码
    @FormUrlEncoded
    @POST("/uc/reset/login/password")
    Observable<BaseBean<Object>> forgetPsw(
            @Field("account") String account,
            @Field("code") String smsCode,
            @Field("password") String password,
            @Field("mode") String mode
    );

    //发送忘记密码短信验证码
    @FormUrlEncoded
    @POST("/uc/mobile/reset/code")
    Observable<BaseBean<Object>> sendForgetSMSCode(
            @Field("account") String phone,
            @Field("geetest_challenge") String a,
            @Field("geetest_validate") String b,
            @Field("geetest_seccode") String c
    );

    //发送修改登录密码短信验证码
    @POST("/uc/mobile/update/password/code")
    Observable<BaseBean<Object>> sendModifyLoginSMSCode();

    //发送修改支付密码短信验证码
    @POST("/uc/mobile/transaction/code")
    Observable<BaseBean<Object>> sendModifyPaySMSCode();

    //修改登录密码
    @FormUrlEncoded
    @POST("/uc/approve/update/password")
    Observable<BaseBean<Object>> modifyLoginPsw(
            @Field("code") String smsCode,
            @Field("oldPassword") String password,
            @Field("newPassword") String mode
    );

    //修改支付密码
    @FormUrlEncoded
    @POST("/uc/approve/reset/transaction/password")
    Observable<BaseBean<Object>> modifyPayPsw(
            @Field("code") String smsCode,
            @Field("newPassword") String password
    );

    //设置支付密码
    @FormUrlEncoded
    @POST("/uc/approve/transaction/password")
    Observable<BaseBean<Object>> settingPayPsw(
            @Field("jyPassword") String smsCode
    );

    //获取城市编码
    @POST("/uc/support/country")
    Observable<BaseBean<List<SelectCountyBean>>> getCounty();

    //检查app版本
    @POST("/uc/ancillary/system/app/version/0")
    Observable<BaseBean<AppVersionBean>> checkAppVersion();

    //退出登录
    @POST("/uc/loginout")
    Observable<BaseBean<Object>> outLogin();

    //检查是否设置过支付密码
    @POST("/uc/approve/security/setting")
    Observable<BaseBean<IsSettingPayPswBean>> checkIsSettingPayPassword();

    //意见反馈
    @POST("/CT/invest/getUsdtFee")
    @FormUrlEncoded
    Observable<BaseBean<Object>> submitFeedback(
            @Field("content") String amount,
            @Field("phone") String address
    );

}
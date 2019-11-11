package com.xxx.mining.ui.my.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxx.mining.ConfigClass;
import com.xxx.mining.R;
import com.xxx.mining.base.activity.BaseTitleActivity;
import com.xxx.mining.model.http.Api;
import com.xxx.mining.model.http.ApiCallback;
import com.xxx.mining.model.http.bean.MyNode;
import com.xxx.mining.model.http.bean.NodeCommod;
import com.xxx.mining.model.http.bean.base.BaseBean;
import com.xxx.mining.model.http.bean.base.PageBean;
import com.xxx.mining.model.utils.KeyBoardUtil;
import com.xxx.mining.model.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的节点
 */
public class MyNodeActivity extends BaseTitleActivity {

    public static void actionStart(Activity activity, boolean flag) {
        Intent intent = new Intent(activity, MyNodeActivity.class);
        intent.putExtra("flag", flag);
        activity.startActivity(intent);
    }

    public void initBundle() {
        Intent intent = getIntent();
        flag = intent.getBooleanExtra("flag", true);
    }

    @BindView(R.id.main_not_data)
    LinearLayout mNotData;

    @BindView(R.id.node_up)
    TextView mNodeUp;
    @BindView(R.id.li_visible)
    LinearLayout mLin;
    @BindView(R.id.node_number_null)
    TextView mnodeNull;
    @BindView(R.id.node_number)
    TextView mnodeNumber;
    @BindView(R.id.node_invite)
    TextView mnodeInvite;
    //深度
    @BindView(R.id.te_countDepth)
    TextView mCountDepth;
    @BindView(R.id.te_depthAmount)
    TextView mDepthAmount;
    //宽度
    @BindView(R.id.te_countWidth)
    TextView mCountWidth;
    @BindView(R.id.te_widthAmount)
    TextView mWidthAmount;
    //节点总业绩
    @BindView(R.id.te_countNode)
    TextView mCountNode;
    @BindView(R.id.te_nodeAmount)
    TextView mNodeAmount;


    private Boolean flag;

    private int imageUrl;
    private int price;
    private int dwttprice;
    private int shopId;
    private int usdeprice;
    private String invite_code;


    @Override
    protected String initTitle() {
        return getString(R.string.main_my_node);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_node;
    }

    @Override
    protected void initData() {
        initBundle();
        if (!flag) {
            mNodeUp.setVisibility(View.VISIBLE);
            mLin.setVisibility(View.GONE);
            mnodeNull.setVisibility(View.VISIBLE);
            mnodeNumber.setVisibility(View.GONE);
            mnodeInvite.setVisibility(View.GONE);
        } else {
            mNodeUp.setVisibility(View.GONE);
        }
        loadMyNode();
    }

    @OnClick({R.id.mill_record, R.id.node_up, R.id.node_invite, R.id.re_Depth, R.id.re_Width})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.mill_record://矿机记录
                MillRecordActivity.actionStartS(this);
                break;
            case R.id.node_up://竟升节点
                nodeList();
                break;
            case R.id.node_invite://节点邀请码
                KeyBoardUtil.copy(this, invite_code);
                break;
            case R.id.re_Depth:
                DepathActivity.actionStartS(this);
                break;
            case R.id.re_Width:
                WidthActivity.actionStartS(this);
                break;
        }
    }

    //获取节点矿机商品
    private void nodeList() {
        Api.getInstance().getNodeCommodities(1, 1, ConfigClass.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<PageBean<NodeCommod>>(this) {

                    @Override
                    public void onSuccess(BaseBean<PageBean<NodeCommod>> bean) {
                        if (bean != null) {
                            List<NodeCommod> list = bean.getData().getList();
                            shopId = list.get(0).getId();
                            imageUrl = list.get(0).getImg();
                            price = list.get(0).getPrice();
                            dwttprice = list.get(0).getDwttPrice();
                            usdeprice = list.get(0).getUsdtPrice();
                            AscendingNodeActivity.actionStart(MyNodeActivity.this, shopId, imageUrl, price, dwttprice, usdeprice);

                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                });
    }

    /**
     * @Model 获取我的节点
     */

    private void loadMyNode() {
        Api.getInstance().getmyNode()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<MyNode>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(BaseBean<MyNode> bean) {
                        if (bean != null) {
                            MyNode data = bean.getData();
                            if (data != null) {
                                invite_code = data.getNodeCode();
                                mnodeNumber.setText(getString(R.string.node_number) + data.getNodeNum());
                                mnodeInvite.setText(invite_code);
                                mCountDepth.setText(String.valueOf(data.getCountDepth()));
                                mDepthAmount.setText(String.valueOf(data.getDepthAmount()));
                                mCountWidth.setText(String.valueOf(data.getCountWidth()));
                                mWidthAmount.setText(String.valueOf(data.getWidthAmount()));
                                mCountNode.setText(String.valueOf(data.getCountNode()));
                                mNodeAmount.setText(String.valueOf(data.getNodeAmount()));
                            }
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        ToastUtil.showToast(errorMessage);
                    }

                    @Override
                    public void onStart(Disposable d) {
                        super.onStart(d);
                        if (mLoadingDialog != null) {
                            mLoadingDialog.show();
                        }
                    }

                    @Override
                    public void onEnd() {
                        super.onEnd();
                        if (mLoadingDialog != null) {
                            mLoadingDialog.dismiss();
                        }
                    }
                });
    }


}

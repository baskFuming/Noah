package com.xxx.mining.model.http.bean;

import com.xxx.mining.model.utils.StringUtil;

public class HomeBean {
    /**
     * coinId : 10000001
     * coinFluctuation : +NaN%
     * coinSymbol : BTC
     * coinPic : img/btc_default_pic.png
     * coinPriceRMB : 65589.8
     * coinPriceUSDT : 9238.00
     */

    private int coinId;
    private String coinFluctuation;
    private String coinSymbol;
    private String coinPic;
    private double coinPriceRMB;
    private String coinPriceUSDT;

    public int getCoinId() {
        return coinId;
    }


    public String getCoinFluctuation() {
        return coinFluctuation;
    }


    public String getCoinSymbol() {
        return coinSymbol;
    }


    public String getCoinPic() {
        return coinPic;
    }


    public double getCoinPriceRMB() {
        return coinPriceRMB;
    }


    public String getCoinPriceUSDT() {
        try {
            return "" + StringUtil.getMoney(coinPriceUSDT == null || coinPriceUSDT.equals("") ? 0 : Double.parseDouble(coinPriceUSDT));
        } catch (Exception e) {
            return "0.0";
        }
    }
}

package com.gupaoedu.template;

public class PayStrategy {

    public static final PayMent getPay(final String payType) {
        if ("AliPay".equals(payType)) {
            return new AliPay();
        } else if ("WechatPay".equals(payType)) {
            return new WechatPay();
        }
        return new AliPay();
    }
}

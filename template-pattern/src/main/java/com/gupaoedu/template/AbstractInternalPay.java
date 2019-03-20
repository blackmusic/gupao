package com.gupaoedu.template;

/**
 * 国内支付模板
 */
public abstract class AbstractInternalPay implements PayMent {


    /**
     * 发起支付
     */
    @Override
    public final void pay() {

        // 1.构建支付参数
        generateParams();

        // 2.检测签名
        checkParams();

        // 3. 发起支付
        request();

        // 4.处理返回结果
        response();

    }

    /**
     * 处理返回结果
     */
    protected abstract void response();

    /**
     * 泛起支付请求
     */
    protected abstract void request();

    /**
     * 构建支付参数
     */
    protected abstract void generateParams();


    /**
     * 检测参数
     */
    protected abstract void checkParams();


}

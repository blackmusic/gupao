package com.gupaoedu.template;

public class PayTest {

    public static void main(String[] args) {

        PayMent payMent = PayStrategy.getPay("AliPay");
        payMent.pay();
    }
}

package com.gupaoedu.decorate;

public class Coffee implements  IRushCoffee {

    @Override
    public void operate() {
        System.out.println("冲一杯咖啡");
    }
}

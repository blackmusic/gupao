package com.gupaoedu.decorate;

public class Milk implements IRushCoffee {


    private IRushCoffee iRushCoffee;

    public Milk(IRushCoffee iRushCoffee) {
        this.iRushCoffee = iRushCoffee;
    }

    @Override
    public void operate() {
        iRushCoffee.operate();

        System.out.println("加一勺牛奶");

    }
}

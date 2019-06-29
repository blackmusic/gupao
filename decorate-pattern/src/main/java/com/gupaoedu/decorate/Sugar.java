package com.gupaoedu.decorate;

public class Sugar implements IRushCoffee {

    private IRushCoffee iRushCoffee;

    public Sugar(IRushCoffee iRushCoffee) {
        this.iRushCoffee = iRushCoffee;
    }

    @Override
    public void operate() {
        iRushCoffee.operate();

        System.out.println("加一勺糖");
    }
}

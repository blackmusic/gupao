package com.gupaoedu.decorate;

public class CoffeeAdapterTest {

    public static void main(String[] args) {

        IRushCoffee iRushCoffee = new Coffee();

        IRushCoffee milk = new Milk(iRushCoffee);

        IRushCoffee milk1 = new Milk(milk);

        IRushCoffee sugar = new Sugar(milk1);

        sugar.operate();

    }
}

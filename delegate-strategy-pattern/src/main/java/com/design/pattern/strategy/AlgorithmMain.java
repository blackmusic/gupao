package com.design.pattern.strategy;

public class AlgorithmMain {

    public static void main(String[] args) {
        String  algorithmKey="";
        new AlgorithmActivity(AlgorithmFactory.getAlgorithm(algorithmKey)).execute();
    }
}

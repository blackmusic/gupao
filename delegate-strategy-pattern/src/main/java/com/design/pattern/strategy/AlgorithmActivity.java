package com.design.pattern.strategy;

/**
 * 算法方案
 */
public class AlgorithmActivity {

    private Algorithm algorithm;

    public AlgorithmActivity(Algorithm algorithm){
         this.algorithm=algorithm;
    }


    public void execute(){
        algorithm.doAlgorithm();
    }
}

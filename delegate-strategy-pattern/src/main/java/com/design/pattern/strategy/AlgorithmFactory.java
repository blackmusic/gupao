package com.design.pattern.strategy;


import java.util.HashMap;
import java.util.Map;

/**
 * 算法工厂
 */
public class AlgorithmFactory {

    private static Map<String,Algorithm> ALGORITHM_MAP = new HashMap<String,
                Algorithm>();


    static {
        ALGORITHM_MAP.put(AlgorithmKey.CLASSIFICATION,new ClassificationAlgorithm());
        ALGORITHM_MAP.put(AlgorithmKey.CLUSTERING,new ClusteringAlgorithm());
        ALGORITHM_MAP.put(AlgorithmKey.REGRESSION,new RegressionAlgorithm());
        ALGORITHM_MAP.put(AlgorithmKey.RECOMMENDATION,new RecommendationAlgorithm());
    }

    private  AlgorithmFactory(){}

    private static  final  Algorithm CLUSTERING_ALGORITHM=new ClusteringAlgorithm();//默认聚合算法


    public  static Algorithm getAlgorithm(String algorithmKey){
        Algorithm algorithm = ALGORITHM_MAP.get(algorithmKey);
        return algorithm==null? CLUSTERING_ALGORITHM:algorithm;

    }


    private interface AlgorithmKey{
        String CLASSIFICATION = "CLASSIFICATION";//分类算法
        String CLUSTERING = "CLUSTERING";//聚合算法
        String REGRESSION = "REGRESSION";//回归算法
        String RECOMMENDATION = "RECOMMENDATION";//推荐算法
    }
}

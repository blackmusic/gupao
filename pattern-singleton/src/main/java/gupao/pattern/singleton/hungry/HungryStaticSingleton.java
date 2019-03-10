package gupao.pattern.singleton.hungry;

/**
 * 【饿汉式静态块单例】
 *
 * created by xuyahui on 2019/3/10
 */
public class HungryStaticSingleton {

    private static final HungryStaticSingleton staticHungry;

    static {
        staticHungry = new HungryStaticSingleton();
    }

    private HungryStaticSingleton(){}

    public static HungryStaticSingleton getInstance(){
        return staticHungry;
    }


}

package gupao.pattern.singleton.lazy;

/**
 * 【懒汉式单例】
 *  优点：在外部需要使用的时候才进行实例化
 *
 *  缺点：线程不安全,当多个线程同时访问时，会创建多个实例
 *
 * created by xuyahui on 2019/3/10
 */
public class LazySimpleSingleton {

    // 静态块，公共内存区域
    private static LazySimpleSingleton lazy = null;

    private LazySimpleSingleton(){}

    public synchronized static LazySimpleSingleton getInstance(){
        if(null == lazy){
            lazy = new LazySimpleSingleton();
        }
        return lazy;
    }

}

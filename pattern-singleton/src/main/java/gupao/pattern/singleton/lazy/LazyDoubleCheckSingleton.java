package gupao.pattern.singleton.lazy;

/**
 * 【双重检查方案】
 *  双重检查可以解决线程不安全的问题
 * created by xuyahui on 2019/3/10
 */
public class LazyDoubleCheckSingleton {

    private volatile static LazyDoubleCheckSingleton lazyDoubleCheck = null;

    private LazyDoubleCheckSingleton(){}

    public static LazyDoubleCheckSingleton getInstance(){
        if(null == lazyDoubleCheck){
            synchronized (LazyDoubleCheckSingleton.class){
                lazyDoubleCheck = new LazyDoubleCheckSingleton();
                /**
                 * 1.分配内存给这个对象
                 * 2.初始化对象
                 * 3.设置lazy指向刚分配的内存地址
                 * 4.初次访问对象
                 */
            }
        }
        return lazyDoubleCheck;
    }

}

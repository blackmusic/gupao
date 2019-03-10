package gupao.pattern.singleton.lazy;

/**
 * created by xuyahui on 2019/3/10
 */
public class LazySingletonTest {

    public static void main(String[] args) {
        LazySimpleSingleton simpleLazy1 = LazySimpleSingleton.getInstance();
        LazySimpleSingleton simpleLazy2 = LazySimpleSingleton.getInstance();


        LazyInnerClassSingleton innerLazy1 = LazyInnerClassSingleton.getInstance();
        LazyInnerClassSingleton innerLazy2 = LazyInnerClassSingleton.getInstance();

        LazyDoubleCheckSingleton doubleCheckLazy1 = LazyDoubleCheckSingleton.getInstance();
        LazyDoubleCheckSingleton doubleCheckLazy2 = LazyDoubleCheckSingleton.getInstance();



        System.out.println(simpleLazy1 == simpleLazy2);
        System.out.println(innerLazy1 == innerLazy2);
        System.out.println(doubleCheckLazy1 == doubleCheckLazy2);
    }
}

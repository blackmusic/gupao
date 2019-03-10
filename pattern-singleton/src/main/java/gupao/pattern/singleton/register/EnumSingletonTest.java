package gupao.pattern.singleton.register;

import gupao.pattern.singleton.lazy.LazySimpleSingleton;
import gupao.pattern.singleton.lazy.LazySingletonTest;

/**
 * created by xuyahui on 2019/3/10
 */
public class EnumSingletonTest {

    public static void main(String[] args) {

        EnumSingleton enum1 = EnumSingleton.getInstance();
        EnumSingleton enum2 = EnumSingleton.getInstance();

        LazySingletonTest containerSingleton1 = (LazySingletonTest)ContainerSingleton.getInstance("gupao.pattern.singleton.lazy.LazySingletonTest");
        LazySingletonTest containerSingleton2 = (LazySingletonTest)ContainerSingleton.getInstance("gupao.pattern.singleton.lazy.LazySingletonTest");


        System.out.println(enum1 == enum2);
        System.out.println(containerSingleton1 == containerSingleton2);

    }
}

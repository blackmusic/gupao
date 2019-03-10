package gupao.pattern.singleton.hungry;
/**
 *
 *created by xuyahui on 2019/3/10
 *
 */
public class HungrySingletonTest {

    public static void main(String[] args) {

        HungrySingleton hungrySingleton1 = HungrySingleton.getInstance();
        HungrySingleton hungrySingleton2 = HungrySingleton.getInstance();

        HungryStaticSingleton staticSingleton1 = HungryStaticSingleton.getInstance();
        HungryStaticSingleton staticSingleton2 = HungryStaticSingleton.getInstance();

        // 两个对象相等，说明是同一个实例对象
        System.out.println(hungrySingleton1 == hungrySingleton2);
        System.out.println(staticSingleton1 == staticSingleton2);


    }

}

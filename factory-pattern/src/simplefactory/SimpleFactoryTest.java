package simplefactory;

public class SimpleFactoryTest {

    public static void main(String[] args) {

        try {
            IPhone apple = SimpleFactory.createPhone("apple");
            System.out.println(apple.toString());

            IPhone huawei = SimpleFactory.createPhone("huawei");
            System.out.println(huawei.toString());

            IPhone xiaomi = SimpleFactory.createPhone("xiaomi");
            System.out.println(xiaomi.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

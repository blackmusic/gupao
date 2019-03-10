package funfactory;

public class FunFactoryTest {

    public static void main(String[] args) {

        IPhoneFactory appleFactory = new ApplePhoneFactory();
        IPhone applePhone = appleFactory.createPhone();
        System.out.println(applePhone.toString());

        IPhoneFactory huaweiFactory = new HuaWeiPhoneFactory();
        IPhone huaweiPhone = huaweiFactory.createPhone();
        System.out.println(huaweiPhone.toString());



    }

}

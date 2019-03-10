package abstfactory;

public class AbstrFactoryTest {

    public static void main(String[] args) {

        AbstractPhoneFactory abstractPhoneFactory = new ApplePhoneFactory();

        System.out.println(abstractPhoneFactory.createChargerl());

        System.out.println(abstractPhoneFactory.createPhone().toString());

        AbstractPhoneFactory huaWeiPhoneFactory = new HuaWeiPhoneFactory();

        System.out.println(huaWeiPhoneFactory.createChargerl());

        System.out.println(huaWeiPhoneFactory.createPhone().toString());



    }


}

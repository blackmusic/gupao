package abstfactory;

public class HuaWeiPhoneFactory extends AbstractPhoneFactory {
    @Override
    public IPhone createPhone() {
        return new HuaWeiPhone();
    }

    @Override
    public ICharger createChargerl() {
        return new HuaWeiCharger();
    }
}

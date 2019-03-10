package abstfactory;

public class ApplePhoneFactory extends AbstractPhoneFactory {
    @Override
    public IPhone createPhone() {
        return new ApplePhone();
    }

    @Override
    public ICharger createChargerl() {
        return new AppleCharger();
    }
}

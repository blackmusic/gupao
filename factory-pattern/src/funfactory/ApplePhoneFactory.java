package funfactory;

public class ApplePhoneFactory implements IPhoneFactory{

    @Override
    public IPhone createPhone() {
        return new ApplePhone();
    }
}

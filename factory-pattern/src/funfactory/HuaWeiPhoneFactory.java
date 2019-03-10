package funfactory;

public class HuaWeiPhoneFactory implements IPhoneFactory {
    @Override
    public IPhone createPhone() {
        return new HuaWeiPhone();
    }
}

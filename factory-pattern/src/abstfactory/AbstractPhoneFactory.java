package abstfactory;

public abstract class AbstractPhoneFactory {

    //创建手机
    public abstract IPhone createPhone();

    //充电器
    public abstract ICharger createChargerl();

}

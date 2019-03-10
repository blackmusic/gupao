package simplefactory;

public class SimpleFactory {

    public static IPhone createPhone(String type) throws Exception {
        IPhone iPhone = null;
        switch (type){
            case "apple":
                iPhone = new ApplePhone();
                break;
            case "huawei":
                iPhone = new HuaWeiPhone();
                break;
                default:
                    throw  new Exception("该工厂不能制造"+type+"品牌的手机");
        }
        return iPhone;
    }

}

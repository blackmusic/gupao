package abstfactory;

public class HuaWeiPhone implements IPhone {

    private String name = "华为";

    private String specification = "6寸";

    @Override
    public String getSpecification() {
        return this.specification;
    }

    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public String toString() {
        return "simplefactory.HuaWeiPhone{" +
                "name='" + name + '\'' +
                ", specification='" + specification + '\'' +
                '}';
    }
}

package funfactory;

public class ApplePhone implements IPhone {

    private String name = "苹果";

    private String specification = "5寸";

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
        return "simplefactory.ApplePhone{" +
                "name='" + name + '\'' +
                ", specification='" + specification + '\'' +
                '}';
    }
}

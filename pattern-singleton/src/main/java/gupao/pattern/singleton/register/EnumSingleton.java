package gupao.pattern.singleton.register;

/**
 * created by xuyahui on 2019/3/10
 *
 * 【注册式单例】-枚举式单例
 *  《effective Java》书中推荐的单例实现写法
 *
 */
public enum  EnumSingleton {

    INSTANCE;

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}

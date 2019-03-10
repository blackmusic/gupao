package gupao.pattern.singleton.seriable;

import java.io.Serializable;

/**
 * created by xuyahui on 2019/3/10
 *
 * 【序列化实现单例】
 * 添加readResolve方法 ，可以使反序列化时单例破坏
 *
 */
public class SeriableSingleton implements Serializable {


    /**
     * 序列化
     * 就是说把内存中的状态通过转换成字节码的形式
     * 从而转换一个IO流，写入到其他地方(可以是磁盘、网络IO)
     * 内存中状态给永久保存下来了
     *
     * 反序列化
     * 讲已经持久化的字节码内容，转换为IO流
     * 通过IO流的读取，进而将读取的内容转换为Java对象
     * 在转换过程中会重新创建对象new
     */
    private static final SeriableSingleton INSTANCE = new SeriableSingleton();

    private SeriableSingleton(){}

    public static SeriableSingleton getInstance(){
        return INSTANCE;
    }

    private Object readResolve(){
        return INSTANCE;
    }

}

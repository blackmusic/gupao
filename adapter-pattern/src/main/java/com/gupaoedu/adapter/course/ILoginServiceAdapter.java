package com.gupaoedu.adapter.course;

public interface ILoginServiceAdapter extends ILoginService {

    void loginVithWechat(String name);

    void loginWithQQ(String name);
}

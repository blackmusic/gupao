package com.gupaoedu.adapter.course;

public class LoginServiceAdapter implements  ILoginServiceAdapter {

    private ILoginService iLoginService;

    public LoginServiceAdapter(ILoginService iLoginService) {
        this.iLoginService = iLoginService;
    }

    @Override
    public void login(String userName, String password) {
        this.iLoginService.login(userName, password);
    }

    @Override
    public void loginVithWechat(String name) {

    }

    @Override
    public void loginWithQQ(String name) {

    }
}

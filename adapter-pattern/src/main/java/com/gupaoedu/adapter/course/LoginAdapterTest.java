package com.gupaoedu.adapter.course;

public class LoginAdapterTest {
    public static void main(String[] args) {
        ILoginService iLoginService =  new LoginServiceImpl();

        ILoginServiceAdapter iLoginServiceAdapter = new LoginServiceAdapter(iLoginService);

        iLoginService.login("a","a");
    }
}

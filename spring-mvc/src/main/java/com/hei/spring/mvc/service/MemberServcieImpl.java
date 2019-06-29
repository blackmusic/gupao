package com.hei.spring.mvc.service;

import com.hei.spring.mvc.annotation.HService;

/**
 * @author heizq
 * @date 2019-03-25 16:32
 * @since v1.0.0
 */
@HService("memberServcie")
public class MemberServcieImpl implements MemberServcie {

    @Override
    public String getMember() {
        System.out.println("=======================有请求到来");
        return "Tom";
    }

    @Override
    public String queryByName(String name) {
        System.out.println("=======================有请求到来,name:"+name);
        return "name";
    }
}

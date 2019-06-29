package com.hei.spring.mvc.controller;

import com.hei.spring.mvc.annotation.HAutowired;
import com.hei.spring.mvc.annotation.HController;
import com.hei.spring.mvc.annotation.HRequestMapping;
import com.hei.spring.mvc.annotation.HRequestParam;
import com.hei.spring.mvc.service.MemberServcie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author heizq
 * @date 2019-03-25 16:32
 * @since v1.0.0
 */
@HController
@HRequestMapping("/h")
public class MemberController {

    @HAutowired
    private MemberServcie memberServcie;

    @HRequestMapping("/get/member")
    public void getMember() {
        memberServcie.getMember();
    }

    @HRequestMapping("/get/member_by_name")
    public void getMemberByName(HttpServletRequest request, HttpServletResponse response, @HRequestParam("name") String name) {
        memberServcie.queryByName(name);
    }
}

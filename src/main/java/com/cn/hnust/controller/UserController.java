package com.cn.hnust.controller;

import com.alibaba.fastjson.JSON;
import com.cn.hnust.domain.Tutorial;
import com.cn.hnust.pojo.User;
import com.cn.hnust.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
    //	@Resource
    private IUserService userService;

    @RequestMapping("/showUser.htm")
    public String toIndex(HttpServletRequest request, Model model) {
        int userId = Integer.parseInt(request.getParameter("id"));
//		User user = this.userService.getUserById(userId);
        User user = new User();
        user.setAge(10);
        user.setPassword("aa");
        user.setUserName("bb");
        model.addAttribute("user", user);
        return "showUser";
    }

    @RequestMapping(value = "saveUser.htm", method = {RequestMethod.POST },produces={"application/json"},consumes={"application/json"})
    @ResponseBody
    public void saveUser(@RequestBody Tutorial user) {

        System.out.println(JSON.toJSONString(user));
    }



}

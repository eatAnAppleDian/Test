package com.orange.controller;

import com.orange.bean.User;
import com.orange.service.UserService;
import com.orange.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<User> login(String username, String password){
        User user = userService.findByNameAndPass(username, password);
        if(user != null){
            return R.success().data(user);
        }else{
            return R.fail();
        }
    }
}

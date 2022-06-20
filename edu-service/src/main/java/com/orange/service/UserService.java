package com.orange.service;

import com.orange.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
public interface UserService extends IService<User> {

    /**
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    User findByNameAndPass(String username, String password);
}

package com.orange.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orange.bean.Permission;
import com.orange.bean.Role;
import com.orange.bean.User;
import com.orange.mapper.UserMapper;
import com.orange.security.entity.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author orange
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据源中读取用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("用户['%s']不存在", username));
        }

        // 读取用户的所有角色
        List<Role> roleList = userMapper.selectRoleForUsername(user.getId());
        // 读取用户的所有权限
        List<Permission> permissionList = userMapper.selectPermissionForUsername(user.getId());
        // 构造security用户
        return new SecurityUser(user, roleList, permissionList);
    }
}
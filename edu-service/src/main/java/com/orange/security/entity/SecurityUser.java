package com.orange.security.entity;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.orange.bean.Permission;
import com.orange.bean.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author orange
 */
public class SecurityUser extends User {
    /**
     * 当前登录用户的明细信息
     */
    @Getter
    private com.orange.bean.User currentUser;

    /**
     *自定义构造器，处理角色权限
     */
    public SecurityUser(com.orange.bean.User currentUser, List<Role> roleList, List<Permission> permissionList) {
        this(currentUser.getUsername(), currentUser.getPassword(), true, true, true, true, parsePermission(roleList, permissionList));
        this.currentUser = currentUser;
    }

    public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    /**
     * 将RBAC中的角色和权限 转换为 SpringSecurity中的GrantedAuthority
     */
    private static Collection<? extends GrantedAuthority> parsePermission(List<Role> roleList, List<Permission> permissionList) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for(Role role : roleList) {
            if(StringUtils.isEmpty(role.getRoleCode())) {
                continue;
            }

            // 生成授权对象
            // SpringSecurity规定角色必须有前缀"ROLE_"
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getRoleCode());
            authorities.add(authority);
        }

        for(Permission permission : permissionList) {
            if(StringUtils.isEmpty(permission.getPermissionValue())) {
                continue;
            }
            // 生成授权对象
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission.getPermissionValue());
            authorities.add(authority);
        }

        return authorities;
    }
}

package com.orange.mapper;

import com.orange.bean.Permission;
import com.orange.bean.Role;
import com.orange.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select distinct p.* from acl_user_role ur, acl_role_permission rp, acl_permission p where ur.user_id=#{id} and ur.role_id = rp.role_id and rp.permission_id = p.id and p.type=1 and p.is_deleted=0")
    List<Permission> selectMenuForUsername(Long id);


    /**
     查询用户的所有权限。
     */
    @Select("select distinct p.* from acl_user_role ur, acl_role_permission rp, " +
            "acl_permission p where ur.user_id=#{id} and ur.role_id = rp.role_id " +
            "and rp.permission_id = p.id")
    List<Permission> selectPermissionForUsername(Long id);

    /**
     查询用户的所有角色
     */
    @Select("select distinct r.* from acl_user_role ur, acl_role r where ur.user_id=#{id} " +
            "and ur.role_id = r.id")
    List<Role> selectRoleForUsername(Long id);

}

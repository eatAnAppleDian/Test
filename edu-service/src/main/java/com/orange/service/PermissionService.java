package com.orange.service;

import com.orange.bean.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.vo.MenuVo;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
public interface PermissionService extends IService<Permission> {

    List<MenuVo> getUserRouters(Long userId);
}

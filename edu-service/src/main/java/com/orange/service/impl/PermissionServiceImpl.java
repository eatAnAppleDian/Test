package com.orange.service.impl;

import com.orange.bean.Permission;
import com.orange.mapper.PermissionMapper;
import com.orange.mapper.UserMapper;
import com.orange.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<MenuVo> getUserRouters(Long userId) {
        // 获取分配给用户的所有菜单项
        List<Permission> permissionList = userMapper.selectMenuForUsername(userId);

        // 将菜单项转换为List<MenuVo>
        List<MenuVo> menuVoList = new ArrayList<>();
        permissionList.forEach(permission -> {
            MenuVo menuVo = new MenuVo();
            menuVo.setId(permission.getId());
            menuVo.setPid(permission.getPid());
            menuVo.setName(permission.getName());
            menuVo.setPath(permission.getPath());
            menuVo.setComponent(permission.getComponent());

            MenuVo.Meta meta = new MenuVo.Meta();
            meta.setHidden(false);
            meta.setTitle(permission.getName());
            meta.setIcon(permission.getIcon());
            menuVo.setMeta(meta);

            menuVoList.add(menuVo);
        });

        // 获得所有根菜单项
        List<MenuVo> rootTree = getMenu(menuVoList, 1L);

        // 将所有菜单项按父子关系组织为嵌套结构
        getChildren(rootTree, menuVoList);

        return rootTree;
    }

    // 设置下级子菜单
    private void getChildren(List<MenuVo> rootTree, List<MenuVo> menuVoList) {
        rootTree.forEach(menuVo -> {
            List<MenuVo> children = this.getMenu(menuVoList, menuVo.getId());
            if (children.size() > 0) {
                // 设置直接的下级子菜单
                menuVo.setChildren(children);
                // 递归
                getChildren(menuVo.getChildren(), menuVoList);
            }
        });
    }

    // 获得下级的子菜单项
    private List<MenuVo> getMenu(List<MenuVo> menus, Long parentId) {
        // 下级子菜单的集合
        List<MenuVo> tempList = new ArrayList<>();
        for (int i = 0; i < menus.size(); i++) {
            MenuVo menu = menus.get(i);
            if (menu.getPid().equals(parentId)) {
                tempList.add(menu);
            }
        }
        // 移除所有选中的元素
        menus.removeAll(tempList);

        return tempList;
    }
}

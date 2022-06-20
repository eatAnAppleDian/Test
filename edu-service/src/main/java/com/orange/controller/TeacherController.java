package com.orange.controller;

import com.orange.bean.Teacher;
import com.orange.service.PermissionService;
import com.orange.service.TeacherService;
import com.orange.vo.MenuVo;
import com.orange.vo.PageVO;
import com.orange.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@RestController
@RequestMapping("/edu")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping(value = "", params = {"userId","type=menu"})
    public R<List<MenuVo>> getRouters(Long userId) {
        List<MenuVo> routers = permissionService.getUserRouters(userId);
        return R.success().data(routers);
    }

    /**
     * 自定义权限检查
     */
    @PreAuthorize("@pms.hasPermission('teacher.list')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/teachers")
    public R<PageVO<Teacher>> showTeachers(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "3") Integer count,
                                         Teacher teacher){
        PageVO<Teacher> pageVO = teacherService.findByPages(page, count, teacher);
        return R.success().data(pageVO);
    }
}

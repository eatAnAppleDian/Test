package com.orange.service;

import com.orange.bean.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.vo.PageVO;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
public interface TeacherService extends IService<Teacher> {

    PageVO<Teacher> findByPages(Integer page, Integer count, Teacher teacher);
}

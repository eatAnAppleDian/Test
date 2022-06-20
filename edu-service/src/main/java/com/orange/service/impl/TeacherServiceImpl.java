package com.orange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.bean.Teacher;
import com.orange.mapper.TeacherMapper;
import com.orange.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.vo.PageVO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public PageVO<Teacher> findByPages(Integer page, Integer count, Teacher teacher) {
        Page<Teacher> teacherPage = new Page<>();
        teacherPage.setCurrent(page)
                .setSize(count);
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if(teacher != null ){
            if(teacher.getId() != null){
                queryWrapper.eq("id", teacher.getId());
            }
        }
        Page<Teacher> selectPage = baseMapper.selectPage(teacherPage, queryWrapper);
        return new PageVO<Teacher>()
                .setDataList(selectPage.getRecords())
                .setCount((int) selectPage.getTotal())
                .setCurrentPage(page);
    }
}

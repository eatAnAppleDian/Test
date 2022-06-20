package com.orange.service.impl;

import com.orange.bean.Course;
import com.orange.mapper.CourseMapper;
import com.orange.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

}

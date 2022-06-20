package com.orange.service.impl;

import com.orange.bean.Subject;
import com.orange.mapper.SubjectMapper;
import com.orange.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

}

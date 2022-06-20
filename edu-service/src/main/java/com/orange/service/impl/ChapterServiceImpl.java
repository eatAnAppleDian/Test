package com.orange.service.impl;

import com.orange.bean.Chapter;
import com.orange.mapper.ChapterMapper;
import com.orange.service.ChapterService;
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
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

}

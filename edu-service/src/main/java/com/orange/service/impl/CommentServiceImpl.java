package com.orange.service.impl;

import com.orange.bean.Comment;
import com.orange.mapper.CommentMapper;
import com.orange.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}

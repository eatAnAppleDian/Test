package com.orange.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.orange.entity.BaseEntity;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 课程收藏
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@Getter
@Setter
@TableName("edu_course_collect")
public class CourseCollect extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 课程讲师ID
     */
    @TableField("course_id")
    private Long courseId;

    /**
     * 课程专业ID
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}

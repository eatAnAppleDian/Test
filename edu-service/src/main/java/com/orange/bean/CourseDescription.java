package com.orange.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.orange.entity.BaseEntity;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 课程简介
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@Getter
@Setter
@TableName("edu_course_description")
public class CourseDescription extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 课程简介
     */
    @TableField("description")
    private String description;


}

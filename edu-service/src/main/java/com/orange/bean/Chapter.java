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
 * 课程
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@Getter
@Setter
@TableName("edu_chapter")
public class Chapter extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 课程ID
     */
    @TableField("course_id")
    private Long courseId;

    /**
     * 章节名称
     */
    @TableField("title")
    private String title;

    /**
     * 显示排序
     */
    @TableField("sort")
    private Integer sort;


}

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
 * 课程科目
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@Getter
@Setter
@TableName("edu_subject")
public class Subject extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 类别名称
     */
    @TableField("title")
    private String title;

    /**
     * 父ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 排序字段
     */
    @TableField("sort")
    private Integer sort;


}

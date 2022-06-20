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
 * 网站统计日数据
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@Getter
@Setter
@TableName("statistics_daily")
public class StatisticsDaily extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 统计日期
     */
    @TableField("date_calculated")
    private String dateCalculated;

    /**
     * 注册人数
     */
    @TableField("register_num")
    private Integer registerNum;

    /**
     * 登录人数
     */
    @TableField("login_num")
    private Integer loginNum;

    /**
     * 每日播放视频数
     */
    @TableField("video_view_num")
    private Integer videoViewNum;

    /**
     * 每日新增课程数
     */
    @TableField("course_num")
    private Integer courseNum;


}

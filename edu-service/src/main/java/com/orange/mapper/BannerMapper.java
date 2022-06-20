package com.orange.mapper;

import com.orange.bean.Banner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 首页banner Mapper 接口
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
public interface BannerMapper extends BaseMapper<Banner> {


    int BatchInsert(@Param("banners")List<Banner> banners);

}

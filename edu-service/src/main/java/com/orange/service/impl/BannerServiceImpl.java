package com.orange.service.impl;

import com.orange.bean.Banner;
import com.orange.mapper.BannerMapper;
import com.orange.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 首页banner 服务实现类
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

}

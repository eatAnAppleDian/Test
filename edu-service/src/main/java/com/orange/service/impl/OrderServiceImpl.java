package com.orange.service.impl;

import com.orange.bean.Order;
import com.orange.mapper.OrderMapper;
import com.orange.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author orange
 * @since 2022-05-23
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}

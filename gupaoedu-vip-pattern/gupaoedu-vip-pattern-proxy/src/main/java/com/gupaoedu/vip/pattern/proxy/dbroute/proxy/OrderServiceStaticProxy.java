package com.gupaoedu.vip.pattern.proxy.dbroute.proxy;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.gupaoedu.vip.pattern.proxy.dbroute.IOrderService;
import com.gupaoedu.vip.pattern.proxy.dbroute.Order;
import com.gupaoedu.vip.pattern.proxy.dbroute.db.DynamicDataSourceEntity;

/**
 * Created by Tom on 2019/3/10.
 */
public class OrderServiceStaticProxy implements IOrderService
{
	private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

	private IOrderService orderService;

	public OrderServiceStaticProxy(IOrderService orderService)
	{
		this.orderService = orderService;
	}

	@Override
	public int createOrder(Order order)
	{
		Long time = order.getCreateTime();
		Integer dbRouter = Integer.valueOf(yearFormat.format(new Date(time)));
		System.out.println("静态代理类自动分配到【DB_" + dbRouter + "】数据源处理数据");
		DynamicDataSourceEntity.set(dbRouter);

		this.orderService.createOrder(order);
		DynamicDataSourceEntity.restore();

		return 0;
	}
}

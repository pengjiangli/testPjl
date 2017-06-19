package com.gt.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
public class MqUtil {
	public static ConnectionFactory factory;
	public MqUtil() throws IOException, TimeoutException{
		this.MqContent();
	}
	public void MqContent() throws IOException, TimeoutException{
		factory = new ConnectionFactory();  
		factory.setHost(PropertiesUtil.getMqUrl());  
		factory.setUsername(PropertiesUtil.getMqUser());
		factory.setPassword(PropertiesUtil.getMqPassWord());
		factory.setAutomaticRecoveryEnabled(true);	//自动从网络错误恢复
		factory.setConnectionTimeout(500000);	//连接超时时间，单位毫秒
	}
	//商城内部的消息队列 exchange转换器，queueName队列
	public static void MqMessage(String exchange,String queueName,String message) throws IOException, TimeoutException{
		Connection connection = factory.newConnection();  
		Channel channel = connection.createChannel();  //创建消息弹到
		channel.exchangeDeclare(exchange, "direct", true);//声明转发器和类型
		channel.queueDeclare(queueName, true, false, false, null);  
		channel.basicPublish("", queueName, null, message.getBytes());
	}
	
	
	public static void main(String[] args) throws IOException, TimeoutException {
		try {
			JSONObject obj = new JSONObject();
			obj.put("orderId", 189);
			obj.put("detailId", 165);
			obj.put("proSpecificas", "82,93");
			obj.put("productId", "75");
			obj.put("productNum", 1);
			obj.put("memberId", 200);
			obj.put("db", "df");
			obj.put("model", "2");
			MqUtil mq = new MqUtil();
//			MqUtil.MqMessage("gt.e.mall", "gt.e.mall.conversion.seckill",obj.toString());
			MqUtil.MqMessage("gt.exchange.message","gt.queueName.message", obj.toString());
			System.out.println("--发送");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

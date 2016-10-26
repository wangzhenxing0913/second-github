package com.umpay.book.redis;

import redis.clients.jedis.Jedis;

public class Main {
	public static void main(String[] args) {
		new RedisClient().show();
	/*	// 连接本地的 Redis 服务
		Jedis jedis = new Jedis("localhost");
		System.out.println("Connection to server sucessfully");
		// 查看服务是否运行
		System.out.println("Server is running: " + jedis.ping());*/
	}

}

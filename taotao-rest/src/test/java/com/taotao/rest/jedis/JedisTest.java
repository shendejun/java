package com.taotao.rest.jedis;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	@Test
	public void testJeisSingle() {
		// 创建一个jedis的对象
		Jedis jedis = new Jedis("localhost", 6379);
		// 调用jedis对象的方法，方法名称和redis的命令一致
		jedis.set("key1", "jedis test");
		String string = jedis.get("key1");
		System.out.print(string);
		// 关闭jedis
		jedis.close();
	}

	/*
	 * 使用连接池
	 */
	@Test
	public void testJedisPool() {
		// 创建jedis连接池
		JedisPool pool = new JedisPool("localhost", 6379);
		// 从连接池中获得jedis对象
		Jedis jedis = pool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		// 关闭jedis对象
		jedis.close();
		pool.close();
	}

	/*
	 * 连接集群
	 */
	public void testJedisCluster() {
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("localhost", 7001));
		nodes.add(new HostAndPort("localhost", 7002));
		nodes.add(new HostAndPort("localhost", 7003));
		nodes.add(new HostAndPort("localhost", 7004));
		nodes.add(new HostAndPort("localhost", 7005));
		nodes.add(new HostAndPort("localhost", 7006));
		// 自带连接池
		JedisCluster cluster = new JedisCluster(nodes);
		cluster.set("key1", "1000");
		String string = cluster.get("key1");
		System.out.println(string);
		cluster.close();
	}

	/*
	 * 单机版整合srping
	 */
	@Test
	public void testSpringJedisSingle() {
		// 创建容器，加载配置文件
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		// 创建一个实例
		JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
		Jedis jedis = pool.getResource();
		String string = jedis.get("key1");
		System.out.println("jedis整合spring输出：" + string);
		jedis.close();
		pool.close();
	}

	/*
	 * 集群版整合spring
	 * */
	@Test
	private void testSpringJedisCluster() {
		// 创建容器，加载配置文件
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		// 创建一个实例
		JedisCluster jedisCluster= (JedisCluster) applicationContext.getBean("redisClient");
		String string=jedisCluster.get("key1");
		System.out.println(string);
		jedisCluster.close();
	}
}

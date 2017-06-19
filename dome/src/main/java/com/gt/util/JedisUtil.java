package com.gt.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * 
 * redis  命名规则，新写接口是：key 头上请加文件PropertiesUtil.redisNamePrefix()
 * @author 李逢喜
 * @version 创建时间：2015年9月7日 下午7:14:20
 * 
 */
public class JedisUtil {
	private static JedisPool pool = null;

	public static JedisPool getPool() {
		if (pool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
			// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
			config.setMaxTotal(500);
			// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
			config.setMaxIdle(5);
			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWaitMillis(1000 * 100);
			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(true);
			String ip = PropertiesUtil.getRedisIp();
			Integer port = PropertiesUtil.getRedisPort();
			if(CommonUtil.isNotEmpty(PropertiesUtil.getRedisPwd())){
				pool = new JedisPool(config,ip,port,60000,PropertiesUtil.getRedisPwd());
			}else{				
				pool = new JedisPool(config,ip,port,60000);
			}
		}
		return pool;
	}

	/**
	 * 返还到连接池
	 * 
	 * @param pool
	 * @param redis
	 */
	public static void returnResource(JedisPool pool, Jedis redis) {
		if (redis != null) {
			pool.returnResource(redis);
		}
	}

	/**
	 * 设置缓存
	 * 
	 * @param key
	 * @param value
	 * @param time
	 */
	public static void set(Integer select,String key, String value) {

		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			jedis.set(PropertiesUtil.redisNamePrefix()+key, value);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}

	}

	/**
	 * 设置缓存
	 * 
	 * @param key
	 * @param value
	 * @param nxxx
	 *            NX XX
	 * @param expx
	 *            时间单位 ex:秒 px:毫秒
	 * @param time
	 */
	public static void set(Integer select,String key, String value, int seconds) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			jedis.setex(PropertiesUtil.redisNamePrefix()+key, seconds, value);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}

	}

	/**
	 * 设置缓存
	 * 
	 * @param key
	 * @param value
	 * @param nxxx
	 *            NX XX
	 */
	public static void set(Integer select,String key, String value, String nxxx) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			jedis.set(PropertiesUtil.redisNamePrefix()+key, value, nxxx);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public static String get(Integer select,String key) {
		String value = null;

		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			value = jedis.get(PropertiesUtil.redisNamePrefix()+key);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}

		return value;

	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 * @return
	 */
	public static Long del(Integer select,String key) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			return jedis.del(PropertiesUtil.redisNamePrefix()+key);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
			return null;
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}

	/**
	 * 存储成map对象，对应的key中添加对应的键值对值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void map(Integer select,String key, String field, String value) {

		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			jedis.hset(PropertiesUtil.redisNamePrefix()+key, field, value);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}

	}

	/**
	 * 根据key，同时根据map当中的key删除数据
	 * 
	 * @param key
	 * @param field
	 */
	public static long mapdel(Integer select,String key, String field) {
		long num = 0;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			num = jedis.hdel(PropertiesUtil.redisNamePrefix()+key, field);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return num;
	}

	/**
	 * 查看map对象数据
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static String maoget(Integer select,String key, String field) {
		String value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			value = jedis.hget(PropertiesUtil.redisNamePrefix()+key, field);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return value;
	}

	/**
	 * 存储成list，对应的key中添加对应的键值对值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void rPush(Integer select,String key, String value) {

		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			jedis.rpush(PropertiesUtil.redisNamePrefix()+key, value);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}

	}

	/**
	 * 存储成list，对应的key中添加对应的键值对值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void lPush(Integer select,String key, String value) {

		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			jedis.lpush(PropertiesUtil.redisNamePrefix()+key, value);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}

	}

	/**
	 * 查看队列数据
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static List lpoplist(Integer select,String key, long start, long end) {
		List list = new ArrayList();
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			list = jedis.lrange(PropertiesUtil.redisNamePrefix()+key, start, end);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return list;
	}

	public static long listLen(Integer select,String key) {
		long len = 0;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			len = jedis.llen(PropertiesUtil.redisNamePrefix()+key);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return len;
	}

	/**
	 * 删除list
	 * 
	 * @param key
	 * @param count
	 *            当count>0时，从表头开始查找，移除count个；当count=0时，从表头开始查找，移除所有等于value的；
	 *            当count<0时，从表尾开始查找，移除|count| 个
	 */
	public static void listDel(Integer select,String key, int count, String value) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			if (count == 10)
				jedis.del(PropertiesUtil.redisNamePrefix()+key);
			jedis.lrem(PropertiesUtil.redisNamePrefix()+key, count, value);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}

	/**
	 * 检查给定 key 是否存在。
	 * 
	 * @param key
	 */
	public static boolean exists(Integer select,String key) {

		boolean flag = false;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			flag = jedis.exists(PropertiesUtil.redisNamePrefix()+key);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return flag;
	}

	/**
	 * 检查给定 key对应的field 是否存在。
	 * 
	 * @param key
	 */
	public static boolean hExists(Integer select,String key, String field) {

		boolean flag = false;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			flag = jedis.hexists(PropertiesUtil.redisNamePrefix()+key, field);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return flag;
	}

	/**
	 * 查看map所有对象数据。
	 * 
	 * @param key
	 */
	public static Map<String, String> mapGetAll(Integer select,String key) {

		Map<String, String> map = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			map = jedis.hgetAll(PropertiesUtil.redisNamePrefix()+key);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return map;
	}

	/**
	 * 存放一个Map<String, String>到redis中
	 * 
	 * @param key
	 * @param map
	 */
	public static Boolean hmset(Integer select,String key, Map<String, String> map) {
		JedisPool pool = null;
		Jedis jedis = null;
		Boolean bool = false;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			String rsult = jedis.hmset(PropertiesUtil.redisNamePrefix()+key, map);
			if ("OK".equals(rsult)) {
				bool = true;
			}
		} catch (Exception e) {
			// 释放redis对象
			pool.close();
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return bool;
	}

	/**
	 * 仅仅通过key获取一个Map集合
	 * 
	 * @param key
	 * @return
	 */
	public static Map<String, String> hmget(Integer select,String key) {
		Map<String, String> map = new HashMap<>();
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			// 获取map中的key
			Set<String> keySet = jedis.hkeys(PropertiesUtil.redisNamePrefix()+key);
			for (String string : keySet) {
				// 获取value，还可以通过 jedis.hvals(key)方式获取
				List<String> valueLs = jedis.hmget(key, string);
				if (valueLs != null && valueLs.size() > 0) {
					map.put(string, valueLs.get(0));
				}
			}
		} catch (Exception e) {
			// 释放redis对象
			pool.close();
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return map;
	}

	/**
	 * 通过resdis中的key和map中的一个或者多个key获取map中对应的值
	 * 
	 * @param key
	 * @param keys
	 * @return
	 */
	public static List<String> hmgetByKeys(Integer select,String key, String... fields) {
		JedisPool pool = null;
		Jedis jedis = null;
		List<String> valueLs = new ArrayList<>();
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			valueLs = jedis.hmget(PropertiesUtil.redisNamePrefix()+key, fields);
		} catch (Exception e) {
			// 释放redis对象
			pool.close();
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return valueLs;
	}

	/**
	 * 删除Map中某个或多个key
	 * 
	 * @param key
	 * @param fields
	 * @return
	 */
	public static Boolean hdel(Integer select,String key, String... fields) {
		JedisPool pool = null;
		Jedis jedis = null;
		Boolean bool = false;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			Long l = jedis.hdel(PropertiesUtil.redisNamePrefix()+key, fields);
			if (l > 0) {
				bool = true;
			}
		} catch (Exception e) {
			// 释放redis对象
			pool.close();
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return bool;
	}

	

	

	public static void main(String[] args) {
		/*Map<String, Integer > params=new HashMap<String, Integer>();
		params.put("total",36002 );
		params.put("daysCount",211 );
		set(0,"busCount", JSONObject.fromObject(params).toString());*/
	}
	/**
	 * 存储成set 对象，对应的key中添加对应的键值对值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void zAdd(Integer select,String key, double score, String value) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			jedis.zadd(PropertiesUtil.redisNamePrefix()+key, score, value);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
	}

	/**
	 * 给sort set 排序
	 * 
	 * @param key
	 * @param start
	 * @param end
	 */
	public static Set<String> zSort(Integer select,String key, int start, int end) {
		JedisPool pool = null;
		Jedis jedis = null;
		Set<String> set = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			set = jedis.zrevrange(PropertiesUtil.redisNamePrefix()+key, start, end);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return set;
	}
	
	public static Set<String>  keys(Integer select,String likekeys){
		JedisPool pool = null;
		Jedis jedis = null;
		Set<String> set = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			set = jedis.keys(PropertiesUtil.redisNamePrefix()+likekeys);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return set;
		
	}

	/**
	 * 给sort set 排序
	 * 
	 * @param key
	 * @param start
	 * @param end
	 */
	public static Double zScore(Integer select,String key, String value) {
		JedisPool pool = null;
		Jedis jedis = null;
		Double score = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			if(CommonUtil.isNotEmpty(select)){
				jedis.select(select);
			}
			score = jedis.zscore(PropertiesUtil.redisNamePrefix()+key, value);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			returnResource(pool, jedis);
		}
		return score;
	}

}

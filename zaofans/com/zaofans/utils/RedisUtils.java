package com.zaofans.utils;

import com.bokesoft.myerp.common.SharedBundle;
import com.bokesoft.myerp.common.StringUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {

	private static JedisPool jedisPool = null;
	
	/**
	 * 初始化Reids连接池
	 */
	static{
		try{
			String server = SharedBundle.getProperties("server.soa.redis.server");
			if(StringUtil.isBlankOrNull(server)){
				throw new Exception("server.soa.redis.server can not be empty");
			}
			String[] serverInfo = server.split(":");
			String host = serverInfo[0];
			int port = Integer.valueOf(serverInfo[1]);
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxActive(20);
			config.setMaxIdle(5);
			config.setMaxWait(1000L);
			config.setTestOnBorrow(false);
			jedisPool = new JedisPool(config, host, port);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

	/**
	 * 查询数据
	 */
	public static String  find(String key){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();  
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{  
            jedisPool.returnResource(jedis);  
        }
	}
	
	/**
	 * 查询特定字符串
	 */
	public static String findSubStr(String key,Integer startOffset,Integer endOffset){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();  
			return jedis.getrange(key, startOffset, endOffset);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{  
            jedisPool.returnResource(jedis);  
        }
	}
	 /** 
     * 向缓存中设置字符串内容 新增数据|修改
     * @param key key 
     * @param value value 
     * @return 
     * @throws Exception 
     */  
    public static int add(String key,String value) throws Exception{  
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();  
            jedis.set(key, value);  
            return 0;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return -1;  
        }finally{  
            jedisPool.returnResource(jedis);  
        }  
    }  
	
	/** 
     * 删除缓存中得对象，根据key 
     * @param key 
     * @return 
     */  
    public static int del(String key){  
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();  
            jedis.del(key);  
            return 0;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return -1;  
        }finally{  
            jedisPool.returnResource(jedis);  
        }  
    }  
}

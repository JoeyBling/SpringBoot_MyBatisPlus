package com.wstro.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * EhCache 缓存工具类
 *
 * @author Joey
 * @Email 2434387555@qq.com
 */
@Component
public class EhcacheUtil {
    /**
     * 管理员菜单缓存
     */
    public final static String ADMINMENUEHCACHENAME = "adminMenuCache";

    @Resource
    public CacheManager manager;

    /**
     * 获得一个Cache
     *
     * @param cacheName 缓存名
     * @param key       缓存key
     * @return Object
     */
    public Object get(String cacheName, Object key) {
        Cache cache = manager.getCache(cacheName);
        if (cache != null) {
            Element element = cache.get(key);
            if (element != null) {
                return element.getObjectValue();
            }
        }
        return null;
    }

    /**
     * 添加一个Cache
     *
     * @param cacheName 缓存名
     * @param key       缓存key
     * @param value     缓存value
     */
    public void put(String cacheName, Object key, Object value) {
        Cache cache = manager.getCache(cacheName);
        if (cache != null) {
            cache.put(new Element(key, value));
        }
    }

    /**
     * 移除一个Cache
     *
     * @param cacheName 缓存名
     * @param key       缓存key
     * @return boolean
     */
    public boolean remove(String cacheName, Object key) {
        Cache cache = manager.getCache(cacheName);
        if (cache != null) {
            return cache.remove(key);
        }
        return false;
    }

}

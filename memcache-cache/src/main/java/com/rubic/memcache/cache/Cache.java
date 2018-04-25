package com.rubic.memcache.cache;

import com.rubic.memcache.common.store.pojo.Item;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/4/3.
 */
public class Cache {
    public static ConcurrentHashMap<String, Item> CACHE = new ConcurrentHashMap<String, Item>();

}

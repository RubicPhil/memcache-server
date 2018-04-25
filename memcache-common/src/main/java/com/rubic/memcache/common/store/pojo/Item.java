package com.rubic.memcache.common.store.pojo;

/**
 * Created by Administrator on 2018/4/3.
 */
public class Item {
    private String value;

    private int flags;

    private int bytes;

    private long lastUsedTime;  // 最近一次被使用的时间戳

    private long expireTime;    //过期时间（单位：秒）

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public long getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(long lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}

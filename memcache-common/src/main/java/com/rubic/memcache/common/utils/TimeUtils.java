package com.rubic.memcache.common.utils;

import com.rubic.memcache.common.Constants;

/**
 * 时间处理工具类
 * Created by Administrator on 2018/4/3.
 */
public class TimeUtils {

    public static long SERVER_STARTED = 0L;

    /**
     * 转换时间戳（单位微秒）：expire大于30天，则转换成Unix时间戳;小于0立即过期
     * @return
     */
    public static long getUnixTimeStamp(long expire) {
        if (expire == 0) {
            return expire;
        } else if (expire < 0) {
            return System.currentTimeMillis();
        } else if (expire > Constants.REALTIME_MAXDELTA) {
            if (expire <= SERVER_STARTED) {
                return 1 * 1000L + SERVER_STARTED;
            }

            return expire;
        }

        return expire * 1000L + System.currentTimeMillis();
    }
}

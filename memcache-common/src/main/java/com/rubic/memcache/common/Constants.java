package com.rubic.memcache.common;

/**
 * Created by Administrator on 2018/4/3.
 * define constant meaning
 */
public class Constants {

    public static final long REALTIME_MAXDELTA = 30 * 24 * 60 * 60;

    public static final int KEY_MAX_LENGTH = 250;

    public static final String NORMAL_FLAG = "SUCCESS";

    public static final String NORMAL_END = "END";

    public static final String NORMAL_STRORED = "STORED";

    public static final String NORMAL_NONSTORED = "NOT_STORED";

    public static final String NORMAL_DELETED = "DELETED";

    public static final String NORMAL_NONDELETED = "NOT_FOUND";

    public static final String NORMAL_VALUE = "VALUE";

    public static final String NORMAL_ERROR = "ERROR";

    public static final String CLIENT_ERROR = "CLIENT_ERROR";

    public static final String SERVER_ERROR = "SERVER_ERROR";

    public static final String FORMAT_ERROR = "bad command line format";

    public static final String DATA_ERROR = "bad data chunk";

}

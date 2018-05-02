package com.rubic.memcache.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * Created by Administrator on 2018/4/3.
 */
public class StringUtils {
    /**
     * 找出每条命令中的单词
     * @param str
     * @return
     */
    public static List<String> getWords(String str) {
        if (str == null || str == "") {
            return null;
        }

        String[] strings = str.split(" ");
        List<String> orderList = new ArrayList<String>();
        for (int i = 0; i < strings.length; i++) {
            String temp = strings[i];
            if (temp.length() != 0) {
                orderList.add(temp);
            }
        }

        return orderList;
    }

    /**
     * 根据正则式判断字符串是否为正整数
     * @param str
     * @return
     */
    public static boolean isPureDigital(String str) {
        if (str == null || str == "") {
            return false;
        }

        String regEx = "\\d+";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(regEx);
        matcher = pattern.matcher(str);

        return matcher.matches();
    }

    /**
     * 判断字符串中是否全为数字
     * @param str
     * @return
     */
    public static boolean isDigital(String str) {
        if (str == null || str == "") {
            return false;
        }

        String regEx = "^[-\\+]?[\\d]*$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(regEx);
        matcher = pattern.matcher(str);

        return matcher.matches();
    }
}

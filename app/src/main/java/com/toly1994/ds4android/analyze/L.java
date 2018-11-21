package com.toly1994.ds4android.analyze;

import android.util.Log;


public class L {

    /**
     * 获取当前的类名
     *
     * @return 当前的类名(simpleName)
     */
    private static String getClassName() {
        String result;
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }

    /**
     * 获取当前的类全名
     *
     * @return 当前的类名（全名）
     */
    public static String getFullClassName() {
        String result;
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[1];
        result = thisMethodStack.getClassName();
        return result;
    }

    /**
     * log这个方法就可以显示超链
     *
     * @return 结果
     */
    public static String l() {
        String result = "   \n:at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[1];
        result += thisMethodStack.getClassName() + ".";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }

    /**
     * 获取信息字符串
     *
     * @param o 信息对象
     * @return 信息对象toString
     */
    public static String getMsg(Object o) {
        return o == null ? "null" : o.toString();
    }

    /**
     * 日志输出级别NONE
     */
    public static final int LEVEL_NONE = 0;
    /**
     * 日志输出级别E
     */
    public static final int LEVEL_ERROR = 1;
    /**
     * 日志输出级别W
     */
    public static final int LEVEL_WARN = 2;
    /**
     * 日志输出级别I
     */
    public static final int LEVEL_INFO = 3;
    /**
     * 日志输出级别D
     */
    public static final int LEVEL_DEBUG = 4;
    /**
     * 日志输出级别V
     */
    public static final int LEVEL_VERBOSE = 5;


    /**
     * 是否允许输出log
     */
    private static int mDebuggable = LEVEL_VERBOSE;

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void v(Object msg) {
        if (mDebuggable >= LEVEL_VERBOSE) {
            Log.v(getClassName(), getMsg(msg));
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(Object msg) {
        if (mDebuggable >= LEVEL_DEBUG) {
            Log.d(getClassName(), getMsg(msg));
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d() {
        d("================华丽分割线================");
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public static void i(String msg) {
        if (mDebuggable >= LEVEL_INFO) {
            Log.i(getClassName(), getMsg(msg));
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public static void w(String msg) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(getClassName(), getMsg(msg));
        }
    }

    /**
     * 以级别为 w 的形式输出Throwable
     */
    public static void w(Throwable tr) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(getClassName(), "", tr);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG信息和Throwable
     */
    public static void w(String msg, Throwable tr) {
        if (mDebuggable >= LEVEL_WARN && null != msg) {
            Log.w(getClassName(), getMsg(msg), tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String msg) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(getClassName(), getMsg(msg));
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String tag, String msg) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(tag, getMsg(msg));
        }
    }

    /**
     * 以级别为 e 的形式输出Throwable
     */
    public static void e(Throwable tr) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(getClassName(), "", tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG信息和Throwable
     */
    public static void e(String msg, Throwable tr) {
        if (mDebuggable >= LEVEL_ERROR && null != msg) {
            Log.e(getClassName(), getMsg(msg), tr);
        }
    }


}

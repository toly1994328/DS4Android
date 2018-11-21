package com.toly1994.ds4android.analyze.data;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/8/27 0027:12:37<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：随机类拓展
 */
public class ZRandom {
    /**
     * 小写字母集
     */
    public static String[] abc = new String[]{
            "a", "b", "c", "d", "e", "f", "g",
            "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u",
            "v", "w", "x", "y", "z"};
    /**
     * 大写字母集
     */
    public static String[] ABC = new String[]{
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    /**
     * 部分常见姓氏
     */
    public static String[] XING = new String[]{
            "赵", "钱", "孙", "李", "周", "吴", "郑",
            "王", "冯", "程", "楚", "魏", "蒋", "沈",
            "韩", "杨", "欧阳", "诸葛", "九天", "司马",
            "公孙", "上官", "包", "郭", "范", "唐",
            "林", "孔", "解"};

    public static String[] MING = new String[]{
            "风", "花", "雪", "月", "雷", "电", "风",
            "霜", "星", "阳", "日", "天", "龙", "凤",
            "洪", "梦", "瑶", "茜", "玉", "富", "轩",
            "玉", "宝", "智", "峰", "贵", "城", "强", "德", "美"};
    ;

    /**
     * 给定字符串数组,返回随机个数字符串拼接
     *
     * @param len   个数
     * @param chars 字符串数组
     * @return 随机个数字符串拼接
     */
    public static String rangeChar(int len, String[] chars) {
        if (len >= chars.length) {
            return "越界";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(chars[rangeInt(0, chars.length - 1)]);
        }
        return sb.toString();
    }


    /**
     * 获取随机英文名字
     *
     * @return 随机英文名字
     */
    public static String randomEnName() {
        String firstName = upAChar(rangeChar(4, abc));
        String lastName = upAChar(rangeChar(6, abc));
        return firstName + " " + lastName;
    }

    /**
     * 获取随机中文名字
     *
     * @return 随机中文名字
     */
    public static String randomCnName() {
        StringBuilder sb = new StringBuilder();

        if (rangeInt(1, 2) == 1) {
            sb.append(rangeChar(1, XING));
            sb.append(rangeChar(1, MING));
        } else {
            sb.append(rangeChar(1, XING));
            sb.append(rangeChar(1, MING));
            sb.append(rangeChar(1, MING));
        }

        return sb.toString();
    }


    /**
     * 获取范围随机整数：如 rangeInt(1,9)
     *
     * @param s 前数(包括)
     * @param e 后数(包括)
     * @return 范围随机整数
     */
    public static int rangeInt(int s, int e) {
        int max = Math.max(s, e);
        int min = Math.min(s, e) - 1;
        return (int) (min + Math.ceil(Math.random() * (max - min)));
    }

    /**
     * 将字符串仅首字母大写
     *
     * @param str 待处理字符串
     * @return 将字符串仅首字母大写
     */
    private static String upAChar(String str) {
        String a = str.substring(0, 1);
        String tail = str.substring(1);
        return a.toUpperCase() + tail;
    }

}

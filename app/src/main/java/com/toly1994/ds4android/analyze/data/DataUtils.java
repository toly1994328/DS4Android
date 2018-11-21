package com.toly1994.ds4android.analyze.data;

import android.content.Context;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 作者：张风捷特烈
 * 时间：2018/4/25:8:52
 * 邮箱：1981462002@qq.com
 * 说明：测试数据工具类
 */
public class DataUtils {

    /**
     * 返回姓名集合
     *
     * @param len 个数
     * @param cn  是否中文
     * @return 返回姓名集合
     */
    public static ArrayList<String> getRandomName(int len, boolean cn) {

        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            names.add(cn ? ZRandom.randomCnName() : ZRandom.randomEnName());
        }
        return names;
    }


    public static List<String> getStringList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }


    public static HashMap<String, String> getStringMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "a");
        map.put("b", "b");
        map.put("c", "c");
        return map;
    }

    public static int[] getIntArray() {
        int[] intArray = {1, 2, 3, 4, 5, 6};
        return intArray;
    }

    public static Integer[] getIntegerArray() {
        Integer[] intArray2 = {1, 2, 3, 4, 5, 6};
        return intArray2;
    }


    public static String[][][] getStringArray3() {
        String[][][] stringArray3 = {{{"1", "2"}, {"1", "2"}, {"1", "2"}, {"1", "2"}, {"1", "2"}},
                {{"1", "2"}, {"1", "2"}, {"1", "2"}, {"1", "2"}, {"1", "2"}}};
        return stringArray3;
    }

    public static String[][] getStringArray2() {
        String[][] stringArray2 = {{"1", "2"}, {"1", "2"}, {"1", "2"}, {"1", "2"}, {"1", "2"}};
        return stringArray2;
    }

    public static String[] getStringArray() {
        String[] stringArray = {"1", "2", "1", "2", "1", "2", "1", "2", "1", "2"};
        return stringArray;
    }

    /**
     * 获取json对象
     *
     * @return
     */

    public static String getJson() {
        String json = "{'a':'b','c':{'aa':234,'dd':{'az':12}}}";
        return json;
    }

    /**
     * 大文本
     *
     * @param context
     * @return
     */
    public static String getBigString(Context context) {
        try {
            InputStream is = context.getAssets().open("city.json");
            StringBuffer sb = new StringBuffer();
            int len = -1;
            byte[] buf = new byte[is.available()];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "utf-8"));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Pensons><Penson id=\"1\"><name>name</name><sex>男</sex><age>30</age></Penson><Penson id=\"2\"><name>name</name><sex>女</sex><age>27</age></Penson></Pensons>";
    }


    public static ArrayList<String> testData(int num) {

        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < num; i++) {

            list.add("测试数据" + i);
        }
        return list;
    }

    public static ArrayList<String> testData2() {
        ArrayList<String> list = new ArrayList<String>();
        // 虚拟数据
        list.add("捷特");
        list.add("巫缨");
        list.add("龙少");
        list.add("七皇钺·梦飞烟");
        list.add("梦小梦");
        list.add("林昔瑶");
        list.add("玉面·奕星龙");
        list.add("冰雪·先一");
        list.add("雪玉·宛如");
        list.add("雪玉·冰忆");
        list.add("无苍");
        list.add("捷伊斯·克洛雷塔·特莫里斯洛德");
        list.add("木艾奇");
        list.add("木时黎");
        list.add("奇");
        list.add("神迹天启·九方玄玉");
        list.add("七日·洪荒");
        list.add("问天道·语熙华");
        list.add("领主·初星");
        list.add("虎翼·穷奇");
        list.add("林兮");
        list.add("梦千");
        list.add("御木·凌风");
        list.add("梦辰");
        list.add("破迁");
        list.add("梦童");
        list.add("一骑·赦王");
        list.add("葛潇月");
        list.add("录名主·张风");
        list.add("医生·布衣人");
        list.add("唐宋·元明清");
        list.add("步落尘");
        list.add("神迹·画天");
        list.add("拓雷");
        list.add("刃王·奇刻");
        list.add("凯·信");
        list.add("漫世·匠心");
        list.add("叶若薇");
        list.add("艾隆");
        list.add("龙右");
        list.add("烈");
        list.add("薛剑儿");
        list.add("莫慈良");
        list.add("藏凯洋");
        list.add("武落英");
        list.add("武落英子");
        list.add("胡服玉龙");
        list.add("斩石·浪封");
        list.add("末剑·何解连");
        list.add("三千面·弗莱明");
        list.add("邓梓");
        list.add("古千缘");
        list.add("朱纱明慧");
        list.add("武文商");
        list.add("莫向阳");
        list.add("巫妻孋");
        list.add("士方");
        list.add("正构");
        list.add("棘廉");
        list.add("J·klist");
        list.add("Y·klist");
        list.add("莫子薇");
        list.add("奇雨欣");
        list.add("林天蕊");
        return list;

    }

    public static ArrayList<String> testData3() {
        ArrayList<String> list = new ArrayList<String>();
        // 虚拟数据
        for (String s : testData3) {
            list.add(s);
        }
        return list;
    }

    /**
     * 获取一个TextView
     *
     * @param str
     * @return
     */
    public static TextView getTextView(Context ctx, String str) {
        TextView view = new TextView(ctx);
        view.setText(str);
        return view;
    }

    public static final String[] testData3 = new String[]{"宋江", "卢俊义", "吴用", "公孙胜", "关胜", "林冲", "秦明", "呼延灼", "花荣", "柴进", "李应", "朱仝", "鲁智深", "武松", "董平", "张清", "杨志", "徐宁", "索超", "戴宗", "刘唐", "李逵", "史进", "穆弘", "雷横", "李俊", "阮小二", "张横", "阮小五", " 张顺", "阮小七", "杨雄", "石秀", "解珍", " 解宝", "燕青", "朱武", "黄信", "孙立", "宣赞", "郝思文", "韩滔", "彭玘", "单廷珪", "魏定国", "萧让", "裴宣", "欧鹏", "邓飞", " 燕顺", "杨林", "凌振", "蒋敬", "吕方", "郭 盛", "安道全", "皇甫端", "王英", "扈三娘", "鲍旭", "樊瑞", "孔明", "孔亮", "项充", "李衮", "金大坚", "马麟", "童威", "童猛", "孟康", "侯健", "陈达", "杨春", "郑天寿", "陶宗旺", "宋清", "乐和", "龚旺", "丁得孙", "穆春", "曹正", "宋万", "杜迁", "薛永", "施恩",};


}

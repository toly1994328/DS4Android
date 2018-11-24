package com.toly1994.ds4android;

import com.toly1994.ds4android.ds.impl.chart.LinkedChart;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        LinkedChart<String> lc = new LinkedChart<>();

        lc.add("toly");

        System.out.println(lc.get(0));

        String toly2 = lc.set(0, "toly2");

        System.out.println(lc.get(0));
        System.out.println(toly2);
    }
}
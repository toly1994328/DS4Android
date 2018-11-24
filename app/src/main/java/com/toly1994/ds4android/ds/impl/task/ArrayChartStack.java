package com.toly1994.ds4android.ds.impl.task;


import com.toly1994.ds4android.ds.impl.chart.ArrayChart;
import com.toly1994.ds4android.ds.itf.IStack;

/**
 * 作者：张风捷特烈
 * 时间：2018/8/17 0017:12:56
 * 邮箱：1981462002@qq.com
 * 说明：栈的数组表实现
 */
public class ArrayChartStack<T> implements IStack<T> {
    private ArrayChart<T> mArrayChart;//成员变量

    public ArrayChartStack(int capacity) {
        mArrayChart = new ArrayChart<>(capacity);
    }

    public ArrayChartStack() {
        mArrayChart = new ArrayChart<>();
    }


    @Override
    public int size() {
        return mArrayChart.size();
    }

    @Override
    public int capacity() {
        return mArrayChart.capacity();
    }


    @Override
    public boolean isEmpty() {
        return mArrayChart.isEmpty();
    }


    @Override
    public T pop() {
        return mArrayChart.remove();
    }


    @Override
    public void push(T el) {
        mArrayChart.add(el);
    }


    @Override
    public T peek() {
        return mArrayChart.get(size() - 1);
    }

}

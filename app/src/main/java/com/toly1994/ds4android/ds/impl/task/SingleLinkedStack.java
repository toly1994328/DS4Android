package com.toly1994.ds4android.ds.impl.task;


import com.toly1994.ds4android.ds.impl.chart.SingleLinkedChart;
import com.toly1994.ds4android.ds.itf.IStack;

/**
 * 作者：张风捷特烈
 * 时间：2018/11/23 0017:22:40
 * 邮箱：1981462002@qq.com
 * 说明：栈的链表式集合实现
 */
public class SingleLinkedStack<E> implements IStack<E> {

    private SingleLinkedChart<E> mSingleLinkedChart;

    public SingleLinkedStack() {
        mSingleLinkedChart = new SingleLinkedChart<>();
    }


    @Override
    public int size() {
        return mSingleLinkedChart.size();
    }

    @Override
    public int capacity() {
        return mSingleLinkedChart.size();
    }

    @Override
    public boolean isEmpty() {
        return mSingleLinkedChart.isEmpty();
    }

    @Override
    public void push(E el) {
        mSingleLinkedChart.add(el);
    }

    @Override
    public E pop() {
        return mSingleLinkedChart.remove();
    }

    @Override
    public E peek() {
        return mSingleLinkedChart.get(0);
    }
}

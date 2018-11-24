package com.toly1994.ds4android.ds.impl.queue;


import com.toly1994.ds4android.ds.impl.chart.ArrayChart;
import com.toly1994.ds4android.ds.itf.IQueue;

/**
 * 作者：张风捷特烈
 * 时间：2018/8/17 0017:15:57
 * 邮箱：1981462002@qq.com
 * 说明：普通队列的数组实现----性能非常差,后面用数组实现循环队列来优化
 */
public class ArrayChartQueue<E> implements IQueue<E> {

    /**
     * 成员变量
     */
    private ArrayChart<E> array;

    public ArrayChartQueue(int capacity) {
        this.array = new ArrayChart<>(capacity);
    }

    public ArrayChartQueue() {
        this.array = new ArrayChart<>();
    }

    @Override
    public void enqueue(E el) {
        array.add(el);
    }

    @Override
    public E dequeue() {
        return array.remove(0);
    }

    @Override
    public E front() {
        return array.get(0);
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    //----此函数为了视图显示辅助的需要---实际上可不用暴露
    public int capacity() {
        return array.capacity();
    }

}

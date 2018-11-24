package com.toly1994.ds4android.ds.impl.queue;


import com.toly1994.ds4android.ds.itf.IQueue;

/**
 * 作者：张风捷特烈
 * 时间：2018/8/17 0017:16:03
 * 邮箱：1981462002@qq.com
 * 说明：数组实现循环队列
 */
public class ArrayLoopQueue<T> implements IQueue<T> {

    private T[] data;// 队列数据
    private int head;//队首标示
    private int tail;//队尾标示
    private int size;//元素个数

    public ArrayLoopQueue() {//无参构造：默认8个容量
        this(8);
    }

    public ArrayLoopQueue(int capacity) {
        // 因为会有一个浪费,所以+1
        data = (T[]) new Object[capacity + 1];
        head = 0;
        tail = 0;
        size = 0;
    }

    @Override
    public void enqueue(T el) {
        if (isFull()) {//加入时满了---扩容
            grow(capacity() * 2);
        }
        data[tail] = el;//在队尾插入
        //插入数据时对尾标示进行维护-----
        tail = (tail + 1) % data.length;
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("MakeSure it's not an empty queue");
        }
        T ret = data[head];
        data[head] = null;//让队首移除
        //队首移除时对首标示进行维护-----
        head = (head + 1) % data.length;
        size--;
        //闲置太多---缩容
        if (size == capacity() / 4 && capacity() / 2 != 0 && size > 4) {
            grow(capacity() / 2);
        }
        return ret;
    }

    @Override
    public T front() {
        if (isEmpty()) {
            throw new IllegalArgumentException("MakeSure it's not an empty queue");
        }
        return data[head];
    }

    /**
     * 扩容/缩容
     *
     * @param newCapacity 新的容量
     */
    private void grow(int newCapacity) {
        T[] newData = (T[]) new Object[newCapacity + 1];
        for (int i = 0; i < size; i++) {
            // 此时在newData中队首对齐回来，data中就得有一个front的偏移量
            newData[i] = data[(i + head) % data.length];
        }
        data = newData;
        head = 0;
        tail = size;
    }

    /**
     * 获取容量
     *
     * @return 容量
     */
    public int capacity() {
        return data.length - 1;
    }

    /**
     * 队列元素个数
     *
     * @return 元素个数
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 是否为空
     *
     * @return 是否为空
     */
    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    /**
     * 队列是否满了
     *
     * @return 队列是否满了
     */
    private boolean isFull() {
        // tail的下一个位置等于head时
        return (tail + 1) % data.length == head;
    }
}

package com.toly1994.ds4android.ds.impl.queue;


import com.toly1994.ds4android.ds.itf.IQueue;

/**
 * 作者：张风捷特烈
 * 时间：2018/8/17 0017:22:50
 * 邮箱：1981462002@qq.com
 * 说明：单链表实现队列
 */
public class SingleLinkedQueue<T> implements IQueue<T> {

    private Node head;//头节点
    private Node tail;//尾节点

    private int size;//元素个数

    public SingleLinkedQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void enqueue(T el) {
        // 如果队尾为空，说明队列是空的。因为tail一直指向最后一个非空节点。
        if (tail == null) {
            tail = new Node(null, el);//初始化
            head = tail;
        } else {
            tail.next = new Node(null, el); // 新来的排到后面去
            tail = tail.next; //更新队尾
        }
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty())
            throw new IllegalArgumentException("MakeSure it's not an empty queue");
        Node targetNode = head;//我是老大
        head = head.next; // 我是老二，但我要篡位了...以后哥就是老大
        targetNode.next = null; //前任老大走了....
        if (head == null) {// 如果头结点为空
            tail = null;
        }
        size--;
        return targetNode.el;
    }

    @Override
    public T front() {
        if (isEmpty())
            throw new IllegalArgumentException("MakeSure it's not an empty queue");
        return head.el;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Node {
        private T el;//改节点上的元素
        private Node next; //下一节点
        /**
         * 两参构造
         *
         * @param next //下一节点
         * @param el   生成节点的元素值
         */
        private Node(Node next, T el) {
            this.el = el;
            this.next = next;
        }
    }
}

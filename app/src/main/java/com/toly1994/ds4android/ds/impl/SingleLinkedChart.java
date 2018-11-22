package com.toly1994.ds4android.ds.impl;

import com.toly1994.ds4android.ds.itf.IChart;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/22 0022:15:36<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class SingleLinkedChart<T> implements IChart<T> {

    /**
     * 虚拟头结点
     */
    private Node headNode;
    protected int size;

    public SingleLinkedChart() {
        clear();
    }


    @Override
    public void add(int index, T el) {
        // index可以取到size，在链表末尾空位置添加元素。
        if (index + 1 < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Illegal index");
        }
        addNode(index + 1, el);
    }

    @Override
    public void add(T el) {
        add(size, el);
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Remove failed. Illegal index");
        }
        return removeNode(index + 1);
    }

    @Override
    public T remove() {
        return remove(size - 1);
    }

    @Override
    public int removeEl(T el) {
        return 0;
    }

    @Override
    public boolean removeEls(T el) {
        return false;
    }

    @Override
    public void clear() {
        headNode = new Node(null, null);
        size = 0;
    }

    @Override
    public T set(int index, T el) {
        return null;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Get failed. Illegal index");
        }
        //消除headNode影响，所以+1
        return getNode(index + 1).el;
    }

    @Override
    public int[] getIndex(T el) {
        return new int[0];
    }

    @Override
    public boolean contains(T el) {
        return false;
    }

    @Override
    public IChart<T> contact(IChart<T> iChart) {
        return null;
    }

    @Override
    public IChart<T> contact(int index, IChart<T> iChart) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return size;
    }


    /**
     * 内部私有节点类
     */
    private class Node {
        /**
         * 节点数据元素
         */
        public T el;
        /**
         * 下一节点的引用
         */
        public Node next;

        public Node(Node next, T el) {
            this.el = el;
            this.next = next;
        }
    }

    ////////////////////------------节点操作

    /**
     * 在指定链表前添加节点
     *
     * @param index 索引
     * @param el    数据
     */
    private void addNode(int index, T el) {
        Node target = getNode(index - 1);
        //新建节点，同时下一节点指向target的下一节点
        Node tNode = new Node(target.next, el);
        //目标节点的next指向新节点
        target.next = tNode;
        size++;
    }

    /**
     * 移除指定索引的节点
     *
     * @param index 索引
     * @return 删除的元素
     */
    private T removeNode(int index) {
        //目标节点前一节点
        Node targetPrev = getNode(index - 1);
        //目标节点
        Node target = targetPrev.next;
        //目标节点前一节点的next指向目标节点下一节点
        targetPrev.next = target.next;
        target.next = null;
        size--;
        return target.el;
    }

    /**
     * 修改节点
     *
     * @param index 节点位置
     * @param el    数据
     * @return 修改后的节点
     */
    private Node setNode(int index, T el) {
        Node node = getNode(index);
        node.el = el;
        return node;
    }

    /**
     * 根据索引寻找节点
     *
     * @param index 索引
     * @return 节点
     */
    private Node getNode(int index) {
        //声明目标节点
        Node targetNode = headNode;
        for (int i = 0; i < index; i++) {
            //一个挨着一个找，直到index
            targetNode = targetNode.next;
        }
        return targetNode;
    }
}

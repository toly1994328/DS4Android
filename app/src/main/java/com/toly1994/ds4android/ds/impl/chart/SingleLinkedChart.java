package com.toly1994.ds4android.ds.impl.chart;

import com.toly1994.ds4android.ds.itf.IChart;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/22 0022:15:36<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：单链表实现表结构
 */
public class SingleLinkedChart<T> implements IChart<T> {

    /**
     * 虚拟头结点
     */
    private Node headNode;

    protected int size;

    public SingleLinkedChart() {
        headNode = new Node(null, null);
        size = 0;
    }


    @Override
    public void add(int index, T el) {
        // index可以取到size，在链表末尾空位置添加元素。
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Illegal index");
        }
        addNode(index + 1, el);
        //为了接口规范,计数从0开始,而链表没有索引概念，只是第几个,T0被认为是第一节车厢。
        //比如选中点2---说明是第三节车厢，所以index + 1 =2+1
    }

    @Override
    public void add(T el) {
        add(0, el);
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
        return remove(0);
    }

    @Override
    public int removeEl(T el) {
        int[] indexes = getIndex(el);
        int index = -1;
        if (indexes.length > 0) {
            index = indexes[0];
            remove(indexes[0]);
        }
        return index;
    }

    @Override
    public boolean removeEls(T el) {
        int[] indexArray = getIndex(el);
        if (indexArray.length != 0) {
            for (int i = 0; i < indexArray.length; i++) {
                remove(indexArray[i] - i); // 注意-i
            }
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        headNode = new Node(null, null);
        size = 0;
    }

    @Override
    public T set(int index, T el) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("ISet failed. Illegal index");
        }
        return setNode(index, el).el;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Get failed. Illegal index");
        }
        return getNode(index + 1).el;
    }

    @Override
    public int[] getIndex(T el) {
        int[] tempArray = new int[size];//临时数组
        int index = 0;//重复个数
        int count = 0;
        Node node = headNode.next;
        while (node != null) {
            if (el.equals(node.el)) {
                tempArray[index] = -1;
                count++;
            }
            index++;
            node = node.next;
        }
        int[] indexArray = new int[count];//将临时数组压缩
        int indexCount = 0;
        for (int i = 0; i < tempArray.length; i++) {
            if (tempArray[i] == -1) {
                indexArray[indexCount] = i;
                indexCount++;
            }
        }
        return indexArray;
    }

    @Override
    public boolean contains(T el) {
        Node target = headNode;
        while (target.next != null) {
            if (el.equals(target.next.el)) {
                return true;
            }
            target = target.next;
        }
        return false;
    }

    /////////////////只是实现一下，getHeadNode和getLastNode破坏了Node的封装性///////////////
    @Override
    public IChart<T> contact(IChart<T> iChart) {
        return contact(0, iChart);
    }

    @Override
    public IChart<T> contact(int index, IChart<T> iChart) {
        if (!(iChart instanceof SingleLinkedChart)) {
            return null;
        }
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Contact failed. Illegal index");
        }
        SingleLinkedChart<T> linkedGroup = (SingleLinkedChart<T>) iChart;
        Node firstNode = linkedGroup.getHeadNode().next;//接入链表 头结点
        Node lastNode = linkedGroup.getLastNode();//接入链表 尾结点
        Node target = getNode(index + 1);//获取目标节点
        Node targetNext = target.next;//获取目标节点的下一节点
        target.next = firstNode;//获取目标节点的next连到 接入链表 头结点
        lastNode.next = targetNext; //待接入链表 尾结点连到 目标节点的下一节点
        return this;
    }

    public Node getHeadNode() {
        return headNode;
    }

    public Node getLastNode() {
        return getNode(size);
    }

    ///////////////////////////////////////////////////////////////
    @Override
    public boolean isEmpty() {
        return size == 0;
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
        private T el;
        /**
         * 下一节点的引用
         */
        private Node next;

        private Node(Node next, T el) {
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
        Node preTarget = getNode(index - 1);//获取前一节车厢
        //新建节点,同时下一节点指向target的下一节点--
        //这里有点绕,分析一下:例子：2号车厢和1号车厢之间插入一节T4车厢
        //preTarget：1号车厢   preTarget.next：2号车厢
        //T4车厢:new Node(preTarget.next, el)---创建时就把链子拴在了：2号车厢（preTarget.next）
        Node tNode = new Node(preTarget.next, el);
        //preTarget的next指向tNode--- 1号车厢栓到T4车厢
        preTarget.next = tNode;
        size++;
    }

    /**
     * 移除指定索引的节点
     *
     * @param index 索引
     * @return 删除的元素
     */
    private T removeNode(int index) {
        Node preTarget = getNode(index - 1);//获取前一节车厢
        Node target = preTarget.next;//目标车厢
        //前一节车厢的next指向目标车厢下一节点
        preTarget.next = target.next;//T0和T2手拉手
        target.next = null;//T1把自己的链子拿掉,伤心地走开...
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
//        if (index == 0) {
//
//        }

        //声明目标节点
        Node targetNode = headNode;
        for (int i = 0; i < index; i++) {
            //一个挨着一个找，直到index
            targetNode = targetNode.next;
        }
        return targetNode;
    }
}

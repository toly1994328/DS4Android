package com.toly1994.ds4android.ds.impl.chart;

import com.toly1994.ds4android.ds.itf.IChart;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/22 0022:15:36<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：双链表实现表结构
 */
public class LinkedChart<T> implements IChart<T> {

    private Node headNode;//虚拟尾节点--相当于头部火车头
    private Node tailNode;//虚拟尾节点--相当于尾部火车头
    protected int size;

    public LinkedChart() {
        clearOrInitNode();
    }


    @Override
    public void add(int index, T el) {
        // index可以取到size，在链表末尾空位置添加元素。
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Illegal index");
        }
        addNode(getNode(index), el);
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
        return removeNode(getNode(index));
    }

    @Override
    public T remove() {
        return remove(size);
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
        clearOrInitNode();
    }

    @Override
    public T set(int index, T el) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("ISet failed. Illegal index");
        }

        return setNode(index, el);
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Get failed. Illegal index");
        }
        return getNode(index).el;
    }

    @Override
    public int[] getIndex(T el) {
        int[] tempArray = new int[size];//临时数组
        int index = 0;//重复个数
        int count = 0;
        Node node = headNode.next;
        while (node.next != null) {
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

//region ----------只是实现一下，getHeadNode和getLastNode破坏了Node的封装性---------------


    @Override
    public IChart<T> contact(IChart<T> iChart) {
        return contact(0, iChart);
    }

    @Override
    public IChart<T> contact(int index, IChart<T> iChart) {
        if (!(iChart instanceof LinkedChart)) {
            return null;
        }
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Contact failed. Illegal index");
        }
        LinkedChart linkedGroup = (LinkedChart) iChart;
        Node targetNode = getNode(index);
        Node targetNextNode = targetNode.next;
        //目标节点的next指向待接链表的第一个节点
        targetNode.next = linkedGroup.getHeadNode().next;
        //向待接链表的第一个节点的prev指向目标节点
        linkedGroup.getHeadNode().next.prev = targetNode;
        //目标节点的下一节点指的prev向待接链表的最后一个节点
        targetNextNode.prev = linkedGroup.getLastNode().prev;
        //向待接链表的最后一个节点的next指向目标节点的下一节点的
        linkedGroup.getLastNode().prev.next = targetNextNode;
        return this;
    }

    public Node getHeadNode() {
        return headNode;
    }

    public Node getLastNode() {
        return tailNode;
    }
    //endregion


//region -----------内部私有节点类及节点操作--------------


    private class Node {
        /**
         * 数据
         */
        private T el;
        /**
         * 前节点
         */
        private Node prev;
        /**
         * 后节点
         */
        private Node next;

        private Node(Node prev, Node next, T el) {
            this.el = el;
            this.prev = prev;
            this.next = next;
        }
    }

    ////////////////////------------节点操作

    /**
     * 根据目标节点插入新节点--目标节点之前
     *
     * @param target 目标节点
     * @param el     新节点数据
     */
    private void addNode(Node target, T el) {
        //新建一个node,将前、后指向分别指向目标前节点和目标节点
        Node newNode = new Node(target.prev, target, el);
        //目标前节点next指向新节点
        target.prev.next = newNode;
        //目标节点prev指向新节点
        target.prev = newNode;
        //链表长度+1
        size++;
    }

    /**
     * 移除目标节点
     *
     * @param target 目标节点
     * @return 目标节点数据
     */
    private T removeNode(Node target) {
        //目标前节点的next指向目标节点后节点
        target.prev.next = target.next;
        //目标后节点的prev指向目标节点前节点
        target.next.prev = target.prev;
        target.prev = null;//放开左手
        target.next = null;//放开右手---挥泪而去
        //链表长度-1
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
    private T setNode(int index, T el) {
        Node node = getNode(index);
        T tempNode = node.el;
        node.el = el;
        return tempNode;
    }

    /**
     * 根据索引寻找节点
     *
     * @param index 索引
     * @return 节点
     */
    private Node getNode(int index) {
        //索引越界处理
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        //声明目标节点
        Node targetNode;
        //如果索引在前半,前序查找
        if (index < size / 2) {
            targetNode = headNode.next;
            for (int i = 0; i < index; i++) {
                targetNode = targetNode.next;
            }
        } else {  //如果索引在后半,反序查找
            targetNode = tailNode;
            for (int i = size; i > index; i--) {
                targetNode = targetNode.prev;
            }
        }
        return targetNode;
    }

    /**
     * 清空所有节点
     */
    private void clearOrInitNode() {
        //实例化头结点
        headNode = new Node(null, null, null);
        //实例化尾节点，并将prev指向头
        tailNode = new Node(headNode, null, null);
        headNode.next = tailNode;
        //链表长度置零
        size = 0;
    }
//endregion
}

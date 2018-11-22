package com.toly1994.ds4android;

/**
 * 作者：张风捷特烈
 * 时间：2018/9/20 0020:9:30
 * 邮箱：1981462002@qq.com
 * 说明：单链表实现集合
 */
public class SingleLinkedChart<T> extends AbstractChart<T> {

    /**
     * 虚拟头结点
     */
    private Node headNode;

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
    public T remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Remove failed. Illegal index");
        }
        return removeNode(index + 1);
    }

    @Override
    public void clear() {
        headNode = new Node(null, null);
        size = 0;
    }

    public void addLast(T el) {
        add(size, el);
    }

    @Override
    public T set(int index, T el) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("ISet failed. Illegal index");
        }
        return setNode(index + 1, el).el;
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
        //临时数组
        int[] tempArray = new int[size];
        //重复个数
        int index = 0;
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
        //将临时数组压缩
        int[] indexArray = new int[count];
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
    public IChart<T> contact(int index, IChart<T> iChart) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Contact failed. Illegal index");
        }
        SingleLinkedChart<T> linkedGroup = (SingleLinkedChart<T>) iChart;
        //获取待接入链表 头结点
        Node firstNode = linkedGroup.getHeadNode().next;
        //获取待接入链表 尾结点
        Node lastNode = linkedGroup.getLastNode();
        //获取目标节点
        Node<T> target = getNode(index + 1);
        //获取目标节点的下一节点
        Node targetNext = target.next;
        //获取目标节点的next连到 接入链表 头结点
        target.next = firstNode;
        //待接入链表 尾结点连到 目标节点的下一节点
        lastNode.next = targetNext;
        return this;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("head: ");
        for (Node cur = headNode.next; cur != null; cur = cur.next) {
            res.append(cur.el + "->");
        }
        res.append("NULL");
        return res.toString();
    }

    public Node getHeadNode() {
        return headNode;
    }

    public Node getLastNode() {
        return getNode(size);
    }


    ////////////////////------------节点操作

    /**
     * 在指定链表前添加节点
     *
     * @param index 索引
     * @param el    数据
     */
    private void addNode(int index, T el) {
        Node<T> target = getNode(index - 1);
        //新建节点，同时下一节点指向target的下一节点
        Node<T> tNode = new Node<>(target.next, el);
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
        Node<T> targetPrev = getNode(index - 1);
        //目标节点
        Node<T> target = targetPrev.next;
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
    private Node<T> setNode(int index, T el) {
        Node<T> node = getNode(index);
        node.el = el;
        return node;
    }

    /**
     * 根据索引寻找节点
     *
     * @param index 索引
     * @return 节点
     */
    private Node<T> getNode(int index) {
        //声明目标节点
        Node<T> targetNode = headNode;
        for (int i = 0; i < index; i++) {
            //一个挨着一个找，直到index
            targetNode = targetNode.next;
        }
        return targetNode;
    }


    /**
     * 内部私有节点类
     *
     * @param <T>
     */
    private class Node<T> {
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
}

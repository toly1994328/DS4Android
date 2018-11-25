package com.toly1994.ds4android.ds.tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * 作者：张风捷特烈
 * 时间：2018/10/7 0007:7:36
 * 邮箱：1981462002@qq.com
 * 说明：
 */
public class BinarySearchTree<T extends Comparable<T>> {


    private Node root;//根节点
    private int size;//节点个数
    private boolean canSame;//是否允许重复节点

    public Node getRoot() {
        return root;
    }

    /**
     * 盛放重复元素的集合
     */
    private Map<T, Integer> mNodeCountMap;

    public BinarySearchTree() {
        this(false);
    }

    public BinarySearchTree(boolean canSame) {
        this.canSame = canSame;
        //允许有重复元素时才创建节点计数的Hash表
        if (canSame) {
            mNodeCountMap = new HashMap<>();
        }
    }

    /**
     * 添加节点
     *
     * @param el 节点元素
     */
    public void add(T el) {
        root = addNode(root, el);
    }


    /**
     * 移除节点
     *
     * @param el 节点元素
     */
    public void remove(T el) {
        root = removeNode(root, el);
    }

    /**
     * 是否包含某元素
     *
     * @param el 待查元素
     */
    public boolean contains(T el) {
        return containsNode(root, el);
    }

    /**
     * 获取最大值
     *
     * @return
     */
    public T getMax() {
        return getMaxNode(root).el;
    }

    /**
     * 获取最小值
     *
     * @return
     */
    public T getMin() {
        return getMinNode(root).el;
    }

    /**
     * 从二分搜索树中删除最大值所在节点
     *
     * @return
     */
    public T removeMax() {
        T ret = getMax();
        root = removeMaxNode(root);
        return ret;
    }

    /**
     * 从二分搜索树中删除最小值所在节点
     *
     * @return
     */
    public T removeMin() {
        T ret = getMin();
        root = removeMinNode(root);
        return ret;
    }


// region ---------二叉树遍历--------------

    /**
     * 二分搜索树的前序遍历（用户使用）
     */
    public void orderPer(List<T> els) {
        orderPerNode(root, els);
    }

    /**
     * 二分搜索树的中序遍历（用户使用）
     */
    public void orderIn(List<T> els) {
        orderNodeIn(root, els);
    }

    /**
     * 二分搜索树的后序遍历（用户使用）
     */
    public void orderPost(List<T> els) {
        orderNodePost(root, els);
    }


    /**
     * 二分搜索树的层序遍历，使用队列实现
     */
    public void orderLevel( List<T> els) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node cur = queue.remove();
            els.add(cur.el);
            //出队时将孩子入队
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
    }

    /**
     * 前序遍历以target为根的二分搜索树
     *
     * @param target 目标树根节点
     */
    private void orderPerNode(Node target, List<T> els) {
        if (target == null) {
            return;
        }
        els.add(target.el);
        orderPerNode(target.left, els);
        orderPerNode(target.right, els);
    }

    /**
     * 中序遍历以target为根的二分搜索树
     *
     * @param target 目标树根节点
     */
    private void orderNodeIn(Node target, List<T> els) {
        if (target == null) {
            return;
        }
        orderNodeIn(target.left, els);
        els.add(target.el);
        orderNodeIn(target.right, els);
    }



    /**
     * 后序遍历以target为根的二分搜索树
     *
     * @param target 目标树根节点
     */
    private void orderNodePost(Node target, List<T> els) {
        if (target == null) {
            return;
        }
        orderNodePost(target.left, els);
        orderNodePost(target.right, els);
        els.add(target.el);
    }




    /**
     * 二分搜索树的前序遍历非递归（用户使用）
     */
    private void orderPerNR() {
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            //当前要操作的节点
            Node cur = stack.pop();
            System.out.print(cur.el + " ");

            //如果有右节点，右节点入栈
            if (cur.right != null) {
                stack.push(cur.right);
            }
            //如果有左节点，左节点入栈
            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
    }

    /**
     * 二分搜索树的中序遍历非递归（用户使用）
     */
    private void orderInNR() {
        Stack<Node> stack = new Stack<>();
        Node cur = root;
        while (cur != null || !stack.empty()) {
            //先将根节点的所有左子线依次入栈
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            //栈顶为左子线尾部节点,弹出时打印
            cur = stack.pop();
            System.out.print(cur.el + " ");
            //!!弹出的同时将当前点设为弹出节点的右子
            cur = cur.right;
        }
    }

    /**
     * 二分搜索树的后序遍历非递归（用户使用）
     */
    private void orderPostNR() {
        Stack<Node> stack = new Stack<>();
        Node pre = null;
        Node cur = root;

        while (cur != null || !stack.empty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                if (cur.right == null || pre == cur.right) {
                    System.out.print(cur.el + " ");
                    pre = cur;
                    cur = null;
                } else {
                    stack.push(cur);
                    cur = cur.right;
                }
            }
        }
    }


// endregion


    /**
     * 查看以目标元素为根节点的二叉树是否包含某节点
     *
     * @param target 目标节点
     * @param el     待查元素
     */
    private boolean containsNode(Node target, T el) {
        if (target == null) {
            return false;
        }
        //判断是否小于目标元素
        boolean isSmallThan = el.compareTo(target.el) < 0;
        if (el.compareTo(target.el) == 0) {
            return true;
        }
        //如果小于，向左侧查找
        return containsNode(isSmallThan ? target.left : target.right, el);
    }

    /**
     * 返回插入新节点后的二分搜索树的根
     *
     * @param target 目标节点
     * @param el     插入元素
     * @return 插入新节点后的二分搜索树的根
     */
    private Node addNode(Node target, T el) {
        if (target == null) {
            size++;
            return new Node(null, null, el);
        }
        //节点相同，并且不允许重复时
        if (el.equals(target.el) && !canSame) {
            return target;//传入值与父节点值相同,并且不允许相同时
        }
        if (el.compareTo(target.el) <= 0) {
            target.left = addNode(target.left, el);
            target.left.deep = target.deep + 1;//!为方便视图绘制---维护deep
        } else if (el.compareTo(target.el) > 0) {
            target.right = addNode(target.right, el);
            target.right.deep = target.deep + 1;//!为方便视图绘制---维护deep
        }
        return target;
    }


    /**
     * 获取节点的个数
     * 在这采取层序遍历中移除节点时对节点进行统计
     * 并未在add总进行统计，原因：1+count)*count/2=mKey
     *
     * @return 节点的个数
     */
    public Map<T, Integer> getNodeCountMap() {
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            //当前要操作的节点
            Node cur = stack.pop();

            if (mNodeCountMap != null) {
                Integer key = mNodeCountMap.get(cur.el);
                if (key == null) {
                    key = 1;
                    mNodeCountMap.put(cur.el, key);
                } else {
                    key++;
                    mNodeCountMap.put(cur.el, key);
                }
            }

            //如果有右节点，右节点入栈
            if (cur.right != null) {
                stack.push(cur.right);
            }
            //如果有左节点，左节点入栈
            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
        return mNodeCountMap;
    }

    /**
     * 删除掉以target为根的二分搜索树中值为e的节点, 递归算法 返回删除节点后新的二分搜索树的根
     *
     * @param target
     * @param el
     * @return
     */
    private Node removeNode(Node target, T el) {
        if (target == null) {
            return null;
        }

        if (el.compareTo(target.el) < 0) {
            target.left = removeNode(target.left, el);
        } else if (el.compareTo(target.el) > 0) {
            target.right = removeNode(target.right, el);
            return target;
        } else {//相等时
            switch (target.getType()) {
                case LEFT_NULL://左残--
                case LEAF:
                    Node rightNode = target.right;
                    target.right = null;
                    size--;
                    return rightNode;
                case RIGHT_NULL:
                    Node leftNode = target.left;
                    target.left = null;
                    size--;
                    return leftNode;
                case FULL:
                    //找后继节点
                    Node successor = getMinNode(target.right);
                    successor.right = removeMinNode(target.right);
                    successor.left = target.left;
                    target.left = target.right = null;
                    return successor;
            }
        }
        return target;
    }


//region -------------最值操作------------

    /**
     * 返回最大值节点
     *
     * @param target 目标根节点
     * @return 最大值节点
     */
    private Node getMaxNode(Node target) {
        //右子不为空就一直拿右子
        return target.right == null ? target : getMaxNode(target.right);
    }

    /**
     * 返回最小值节点
     *
     * @param target 目标根节点
     * @return 最大值节点
     */
    private Node getMinNode(Node target) {
        //右子不为空就一直拿右子
        return target.left == null ? target : getMinNode(target.left);
    }

    /**
     * 删除掉以node为根的二分搜索树中的最大节点 返回删除节点后新的二分搜索树的根
     *
     * @param node
     * @return
     */
    private Node removeMaxNode(Node node) {

        if (node.right == null) {
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }

        node.right = removeMaxNode(node.right);
        return node;
    }

    /**
     * 删除掉以node为根的二分搜索树中的最小节点 返回删除节点后新的二分搜索树的根
     *
     * @param node
     * @return
     */
    private Node removeMinNode(Node node) {

        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }

        node.left = removeMinNode(node.left);
        return node;
    }
//endregion


    /**
     * 获取节点个数
     *
     * @return 节点个数
     */
    public int size() {
        return size;
    }

    /**
     * 二分搜索树是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    enum NodeType {
        FULL,//满树
        LEAF,//叶
        RIGHT_NULL,//右残
        LEFT_NULL;//左残
    }

    /**
     * 节点类----!为方便视图绘制---private 改为 public
     */
    public class Node {

        public T el;//储存的数据元素
        public Node left;//左子
        public Node right;//右子

        public int deep;//!为方便视图绘制---增加节点树深


        /**
         * 构造函数
         *
         * @param left  左子
         * @param right 右子
         * @param el    储存的数据元素
         */
        private Node(Node left, Node right, T el) {
            this.el = el;
            this.left = left;
            this.right = right;
        }


        public NodeType getType() {

            if (this.right == null) {
                if (this.left == null) {
                    return NodeType.LEAF;
                } else {
                    return NodeType.RIGHT_NULL;
                }
            }

            if (this.left == null) {
                return NodeType.LEFT_NULL;
            } else {
                return NodeType.FULL;
            }
        }
    }
}

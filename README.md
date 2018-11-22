#### 零、前言
>最近两个星期研究了一下Android的绘图，也可以说是自定义控件吧。  
但仅是如此吗？那些都是开胃菜，吾现在要放大招了，那就是让无数新手闻风丧胆的——数!据!结!构!  
没错，吾要和数据结构进行第三次大规模战斗，而这场战斗的战利品将是本篇和之后的n篇，以及我从中获得的经验与知识。  
本系列为了测试吾的能力(绘图能力，分析能力，表述能力，统筹能力)以及为大家换一个角度去看待数据结构。   
本系列将会成为吾编程生涯的一座`里程碑`和`个人标志`。[希望你可以和我在Github一同见证：DS4Android](https://github.com/toly1994328/DS4Android)  
`注：本系列的[捷文规范]定位在掘金，简书同步更新`

##### 0.本系列后续更新链接合集：(动态更新)
>[看得见的数据结构Android版之开篇前言](https://juejin.im/post/5bf52228e51d4542fc64d92f)  
>[看得见的数据结构Android版之表的数组实现(数据结构篇)](https://juejin.im/post/5bf626c5e51d450d5441721d)
看得见的数据结构Android版之表的数组实现(视图篇)
>看得见的数据结构Android版之单链表篇(待完成)
>看得见的数据结构Android版之双链表篇(待完成)
>看得见的数据结构Android版之栈（待完成）
>看得见的数据结构Android版之队列（待完成）
>看得见的数据结构Android版之二叉树篇(待完成)
>看得见的数据结构Android版之二分搜索树篇(待完成)
>看得见的数据结构Android版之AVL树篇(待完成)
>看得见的数据结构Android版之红黑树篇(待完成)
>更多数据结构---以后再说吧

##### 1.说说我对数据结构的理解吧：
>1---数据结构=数据+结构：
2---说到结构，我第一反应就是生物的骨架，而数据则是附着在骨架上的躯体。
3---躯体外显，骨架内隐，骨架的行为在躯体上表现。很符合数据与结构的关系。
4---简而言之，我认为结构是数据的载体，数据是结构行为的体现。
5---血肉大同小异，但骨骼千差万别，有的灵巧娇小，有的笨重硕大，但各有千秋。


##### 2.总结与展望：我与数据结构的两次大战

```
第一次接触数据结构是在学编程初期，可谓完败,内心倍受摧残，从而敬而远之
第二次接触数据结构是在前几个月，原因是感觉编程的境界提升很多，数据结构这个瓶颈早晚还是要过的
便决定潜心研究两个星期，感觉还不错，可以说平分秋色，收获颇多，虽然过深的知识我也只能浅尝辄止。

这次是第三次对数据结构的大战，基于Android的View来显示数据结构，让它的神秘无所遁藏。
就我刚写完数组篇来讲，确实对我的思维和分析有很大的考验，对于层次分解也更清晰，
常量的价值、监听的使用，以及接口的鬼斧神工还有泛型的使用(泛型包泛型我也是佩服自己)都理解得更深了
```

##### 3.总体的思路

![综述.png](https://upload-images.jianshu.io/upload_images/9414344-99a900ad5ffc4ac5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 4.下面是数组数据结构的视图：(有没有很期待)

>表结构的常规操作

![表结构的常规操作.gif](https://upload-images.jianshu.io/upload_images/9414344-ec41a4b8c46a722f.gif?imageMogr2/auto-orient/strip)

>数组的扩容与缩容

![数组的扩容与缩容](https://upload-images.jianshu.io/upload_images/9414344-cd9e34edd4e06e31.gif?imageMogr2/auto-orient/strip)


---

#### 一、本文是干嘛的(开场篇当然不能太low)

>既然决定对战数据结构，那擂台便必不可少，Android的View就是擂台。本篇解决擂台的问题。
本系列每种数据结构将分为两篇：`数据结构篇`和`视图篇`，如果你只偏爱其中一种，自选观看(数据结构接口与java基本保持一致)
本篇会解决数组、单链表、双链表、二叉树、红黑树的单体绘制，有什么视觉方面的想法欢迎留言。   
基本样子大概就是下图了：

![数据结构视图单体.png](https://upload-images.jianshu.io/upload_images/9414344-2c8c2dfd70fa6a3c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

---

#### 二、下面来绘制一下各种数据结构的单体

>以下是安卓原生绘图结果，也是本篇的重点：


##### 0.统一常量管理类

```
/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:14:21<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：统一常量管理类
 */
public class Cons {

    public static final int BOX_HEIGHT = 100;//一个数组盒子的高
    public static final int BOX_WIDTH = 200;//一个数组盒子的宽
    
    public static final int LINK_LEN = 100;//链表长度
    public static final int ARROW_DX = 20;//链表箭头
    public static final int BOX_RADIUS = 10;//盒子圆角
    public static final int FONT_SIZE = 40;//数组文字字号
    public static final int BST_RADIUS = 50;//二叉树文字字号
}
```


##### 1.数组单体的绘制
>控制点1,2重合了，你也可以用二次的贝塞尔曲线

![数组画法.png](https://upload-images.jianshu.io/upload_images/9414344-67a6a83348426e8b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
private void arrayView(Canvas canvas) {
    //画圆角矩形没什么好说的
    canvas.drawRoundRect(
            0, 0, Cons.BOX_WIDTH, Cons.BOX_HEIGHT,
            Cons.BOX_RADIUS, Cons.BOX_RADIUS, mPaint);
    //绘制贝塞尔弧线
    mPath.moveTo(0, 0);
    mPath.rCubicTo(
            Cons.BOX_WIDTH / 2, Cons.BOX_HEIGHT / 2,//控制点1
            Cons.BOX_WIDTH / 2, Cons.BOX_HEIGHT / 2,//控制点2
            Cons.BOX_WIDTH, 0);//终点
    canvas.drawPath(mPath, mPathPaint);
    //绘制文字没什么好说的
    canvas.drawText("0",Cons.BOX_WIDTH / 2, 3 * 10, mDataPaint);
    canvas.drawText("toly",
            0 + Cons.BOX_WIDTH / 2, 0 + Cons.BOX_HEIGHT / 2 + 3 * 10, mDataPaint);
}
```

---

##### 2.单链表单体的绘制：

![单链表画法.png](https://upload-images.jianshu.io/upload_images/9414344-fd47c99417f25d22.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
private void singleLink(Canvas canvas) {
    //画圆角矩形没什么好说的
    canvas.drawRoundRect(
            0, 0, Cons.BOX_WIDTH, Cons.BOX_HEIGHT,
            Cons.BOX_RADIUS, Cons.BOX_RADIUS, mPaint);
    //画路径
    mPath.rCubicTo(
            Cons.BOX_WIDTH / 2, Cons.BOX_HEIGHT / 2,
            Cons.BOX_WIDTH / 2, Cons.BOX_HEIGHT / 2,
            Cons.BOX_WIDTH, 0);
    mPath.rLineTo(0, Cons.BOX_HEIGHT / 2f);//往下画线走半高
    mPath.rLineTo(Cons.LINK_LEN, 0);//往左画线走线长
    mPath.rLineTo(-Cons.ARROW_DX, -Cons.ARROW_DX);//画箭头
    canvas.drawPath(mPath, mPathPaint);
    //绘制文字没什么好说的
    canvas.drawText("0", Cons.BOX_WIDTH / 2, 3 * 10, mDataPaint);
    canvas.drawText("toly",
            0 + Cons.BOX_WIDTH / 2, 0 + Cons.BOX_HEIGHT / 2 + 3 * 10, mDataPaint);
}
```

---


##### 3.双链表单体的绘制：
![双链表画法.png](https://upload-images.jianshu.io/upload_images/9414344-bf805ed411e62c4a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
private void DoubleLink(Canvas canvas) {
    //画圆角矩形没什么好说的
    canvas.drawRoundRect(
            0, 0, Cons.BOX_WIDTH, Cons.BOX_HEIGHT,
            Cons.BOX_RADIUS, Cons.BOX_RADIUS, mPaint);
    //画路径
    mPath.rCubicTo(
            Cons.BOX_WIDTH / 2, Cons.BOX_HEIGHT / 2,
            Cons.BOX_WIDTH / 2, Cons.BOX_HEIGHT / 2,
            Cons.BOX_WIDTH, 0);
    mPath.lineTo(Cons.BOX_WIDTH, Cons.BOX_HEIGHT / 2.2f);
    mPath.rLineTo(Cons.LINK_LEN, 0);
    mPath.rLineTo(-Cons.ARROW_DX, -Cons.ARROW_DX);
    mPath.moveTo(0, 0);
    mPath.rLineTo(0, Cons.BOX_HEIGHT / 1.2f);
    mPath.rLineTo(-Cons.LINK_LEN - Cons.ARROW_DX, 0);
    mPath.rLineTo(Cons.ARROW_DX, Cons.ARROW_DX);
    canvas.drawPath(mPath, mPathPaint);
    //绘制文字没什么好说的
    canvas.drawText("0",
            Cons.BOX_WIDTH / 2,
            3 * 10, mDataPaint);
    canvas.drawText("toly",
            0 + Cons.BOX_WIDTH / 2,
            0 + Cons.BOX_HEIGHT / 2 + 3 * 10, mDataPaint);
}
```


---
##### 4.二叉树单体的绘制

![二叉树画法.png](https://upload-images.jianshu.io/upload_images/9414344-95a505f7df3b4fcc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
private void BST(Canvas canvas) {
    //先画线,圆将先盖住
    mPath.moveTo(0 + Cons.BST_RADIUS / 2, 0 + Cons.BST_RADIUS / 2);
    mPath.rLineTo(150, 150);
    mPath.moveTo(0 - Cons.BST_RADIUS / 2, 0 + Cons.BST_RADIUS / 2);
    mPath.rLineTo(-150, 150);
    canvas.drawPath(mPath, mPathPaint);
    //父节点绘制
    canvas.drawCircle(0, 0, Cons.BST_RADIUS, mPaint);
    canvas.drawText("0", 0, 10, mDataPaint);
    //绘制子节点
    int offsetX = 150;//子圆心偏移X
    int offsetY = 150;//子圆心偏移Y
    canvas.drawCircle(0 - offsetX, 0 + offsetY, Cons.BST_RADIUS, mPaint);
    canvas.drawText("null", 0 - offsetX, 10 + offsetY, mDataPaint);
    canvas.drawCircle(offsetX, offsetY, Cons.BST_RADIUS, mPaint);
    canvas.drawText("null", offsetX, 10 + offsetY, mDataPaint);
}
```


---


##### 5.红黑树的画法

![红黑树树画法.png](https://upload-images.jianshu.io/upload_images/9414344-b0878409ba489ef2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


```
private void bAr(Canvas canvas) {
    mPath.moveTo(0 + Cons.BST_RADIUS / 2, 0 + Cons.BST_RADIUS / 2);
    mPath.rLineTo(150, 150);
    mPath.moveTo(0 - Cons.BST_RADIUS / 2, 0 + Cons.BST_RADIUS / 2);
    mPath.rLineTo(-150, 150);
    canvas.drawPath(mPath, mPathPaint);
    mPaint.setColor(Color.BLACK);
    canvas.drawCircle(0, 0, Cons.BST_RADIUS, mPaint);
    canvas.drawText("50", 0, 10, mDataPaint);
    //绘制子节点
    int offsetX = 150;//子圆心偏移X
    int offsetY = 150;//子圆心偏移Y
    mPaint.setColor(Color.RED);
    canvas.drawCircle(0 - offsetX, 0 + offsetY, Cons.BST_RADIUS, mPaint);
    canvas.drawText("35", 0 - offsetX, 10 + offsetY, mDataPaint);
    mPaint.setColor(Color.BLACK);
    canvas.drawCircle(offsetX, offsetY, Cons.BST_RADIUS, mPaint);
    canvas.drawText("78", offsetX, 10 + offsetY, mDataPaint);
}
```

---
##### 三、其他初始：

##### 1.可显示出来的基类
>所有数据结构绘制单体的父类

```
/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:8:50<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：可显示出来的基本条件
 */
public class Viewable {
    public float x;//单体的x坐标
    public float y;//单体的y坐标
    public int color = 0xff43A3FA;//单体的颜色

    public float vX;//单体的水平速度
    public float vY;//单体的数值速度

    public Viewable() {
    }

    public Viewable(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
```

##### 2.控操作接口

```
/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:10:17<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：控操作接口
 */
public interface OnCtrlClickListener<T> {

    void onAdd(T view);

    void onRemove(T view);

    void onSet(T view);

    void onFind(T view);
}

```

---
#### 后记：捷文规范
##### 1.本文成长记录及勘误表
[项目源码](https://github.com/toly1994328/DS4Android) | 日期|备注
---|---|---
[V0.1--github](https://github.com/toly1994328/DS4Android)|2018-11-21|[看得见的数据结构Android版之开篇前言](https://juejin.im/post/5bf52228e51d4542fc64d92f)


##### 2.更多关于我

笔名 | QQ|微信|爱好
---|---|---|---|
张风捷特烈 | 1981462002|zdl1994328|语言
 [我的github](https://github.com/toly1994328)|[我的简书](https://www.jianshu.com/u/e4e52c116681)|[我的掘金](https://juejin.im/user/5b42c0656fb9a04fe727eb37)|[个人网站](http://www.toly1994.com)

##### 3.声明
>1----本文由张风捷特烈原创,转载请注明  
2----欢迎广大编程爱好者共同交流  
3----个人能力有限，如有不正之处欢迎大家批评指证，必定虚心改正   
4----看到这里，我在此感谢你的喜欢与支持

---

![icon_wx_200.png](https://upload-images.jianshu.io/upload_images/9414344-8a0c95a090041a0d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
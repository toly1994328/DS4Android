#### 零、前言
>最近两个星期研究了一下Android的绘图，也可以说是自定义控件吧。  
但仅是如此吗？那些都是开胃菜，吾现在要放大招了，那就是让无数新手闻风丧胆的——数!据!结!构!  
没错，吾要和数据结构进行第三次大规模战斗，而这场战斗的战利品将是本篇和之后的n篇，以及我从中获得的经验与知识。  
本系列为了测试吾的能力(绘图能力，分析能力，表述能力，统筹能力)以及为大家换一个角度去看待数据结构。   
本系列将会成为吾编程生涯的一座`里程碑`和`个人标志`。[希望你可以和我在Github一同见证：DS4Android](https://github.com/toly1994328/DS4Android)  
`注：本系列的[捷文规范]定位在掘金，简书同步更新`


##### 0.本系列后续更新链接合集：(动态更新)
>[看得见的数据结构Android版之开篇前言](https://juejin.im/post/5bf52228e51d4542fc64d92f)    
[看得见的数据结构Android版之表的数组实现(数据结构篇)](https://juejin.im/post/5bf626c5e51d450d5441721d)    
看得见的数据结构Android版之表的数组实现(视图篇)    
看得见的数据结构Android版之单链表篇(待完成)    
看得见的数据结构Android版之双链表篇(待完成)     
看得见的数据结构Android版之栈（待完成）   
看得见的数据结构Android版之队列（待完成）   
看得见的数据结构Android版之二叉树篇(待完成)     
看得见的数据结构Android版之二分搜索树篇(待完成)     
看得见的数据结构Android版之AVL树篇(待完成)   
看得见的数据结构Android版之红黑树篇(待完成)   
更多数据结构---以后再说吧  


##### 1.下面是数组数据结构的视图：(有没有很期待)

>表结构的常规操作

![表结构的常规操作.gif](https://upload-images.jianshu.io/upload_images/9414344-ec41a4b8c46a722f.gif?imageMogr2/auto-orient/strip)

>数组的扩容与缩容

![数组的扩容与缩容](https://upload-images.jianshu.io/upload_images/9414344-cd9e34edd4e06e31.gif?imageMogr2/auto-orient/strip)

>单链表

![单链表.gif](https://upload-images.jianshu.io/upload_images/9414344-620fbc5eaa7c5cc7.gif?imageMogr2/auto-orient/strip)


##### 2.说说我对数据结构的理解吧：
>1---数据结构=数据+结构：
2---说到结构，我第一反应就是生物的骨架，而数据则是附着在骨架上的躯体。
3---躯体外显，骨架内隐，骨架的行为在躯体上表现。很符合数据与结构的关系。
4---简而言之，我认为结构是数据的载体，数据是结构行为的体现。
5---血肉大同小异，但骨骼千差万别，有的灵巧娇小，有的笨重硕大，但各有千秋。




##### 3.总体的思路

![综述.png](https://upload-images.jianshu.io/upload_images/9414344-99a900ad5ffc4ac5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

---

##### 1.更多关于我

笔名 | QQ|微信|爱好
---|---|---|---|
张风捷特烈 | 1981462002|zdl1994328|语言
 [我的github](https://github.com/toly1994328)|[我的简书](https://www.jianshu.com/u/e4e52c116681)|[我的掘金](https://juejin.im/user/5b42c0656fb9a04fe727eb37)|[个人网站](http://www.toly1994.com)

##### 2.声明
>1----本文由张风捷特烈原创,转载请注明  
2----欢迎广大编程爱好者共同交流  
3----个人能力有限，如有不正之处欢迎大家批评指证，必定虚心改正   
4----看到这里，我在此感谢你的喜欢与支持

---

![icon_wx_200.png](https://upload-images.jianshu.io/upload_images/9414344-8a0c95a090041a0d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

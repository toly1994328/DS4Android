package com.toly1994.ds4android.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.toly1994.ds4android.analyze.ColUtils;
import com.toly1994.ds4android.analyze.gold12.HelpDraw;
import com.toly1994.ds4android.analyze.gold12.JudgeMan;
import com.toly1994.ds4android.ds.impl.chart.ArrayChart;
import com.toly1994.ds4android.ds.itf.IChart;
import com.toly1994.ds4android.model.ArrayBox;
import com.toly1994.ds4android.view.other.Cons;
import com.toly1994.ds4android.view.other.OnCtrlClickListener;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:8:01<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：数组实现表结构---测试视图
 */
public class ArrayView<E> extends View {
    private Point mCoo = new Point(200, 150);//坐标系
    private Picture mCooPicture;//坐标系canvas元件
    private Picture mGridPicture;//网格canvas元件


    private Path mPath;//主路径
    private Paint mPaint;//主画笔
    private Paint mTxtPaint;//数字画笔
    private Paint mPathPaint;//路径画笔
    private Paint mCtrlPaint;//几个圆的画笔

    private IChart<ArrayBox<E>> mArrayBoxes = new ArrayChart<>();
    private OnCtrlClickListener<ArrayView<E>> mOnCtrlClickListener;
    private int selectIndex = -1;//当前选中的索引
    private ValueAnimator mAnimator;


    private static final int OFFSET_X = 10;//X空隙
    private static final int OFFSET_Y = 60;//Y空隙
    private static final int OFFSET_OF_TXT_Y = 10;//文字的偏移
    private static final int BOX_RADIUS = 10;//数组盒子的圆角

    private static final Point[] CTRL_POS = new Point[]{//控制按钮的点位
            new Point(-100, 100),//添加
            new Point(-100, 300),//更新
            new Point(-100, 500),//查看
            new Point(-100, 700),//删除

            new Point(700, -70),//定点添加
            new Point(700 + 300, -70),//定值查询
            new Point(700 + 300 * 2, -70),//定点删除
            new Point(700 + 300 * 3, -70),//清除

    };

    private static int[] CTRL_COLOR = new int[]{//控制按钮的颜色
            0xff1EF519,//添加
            0xff2992F2,//更新
            0xffB946F4,//添加
            0xffF50C0C,//删除

            0xff1EF519,//定点添加
            0xffB946F4,//定值查询
            0xffF50C0C,//定点删除
            0xffF46410,//清除
    };

    private static final String[] CTRL_TXT = new String[]{//控制按钮的文字
            "添加",//添加
            "更新",//更新
            "查寻",//添加
            "删除",//删除

            "定点+",//定点添加
            "值查",//定值查询
            "定点-",//定点删除
            "清空",//清除按键
    };

    private static final int CTRL_RADIUS = 50;//控制按钮的半径


    public void setOnCtrlClickListener(OnCtrlClickListener<ArrayView<E>> onCtrlClickListener) {
        mOnCtrlClickListener = onCtrlClickListener;
    }

    public ArrayView(Context context) {
        this(context, null);
    }

    public ArrayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();//初始化
    }

    private void init() {
        //初始化主画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(50);
        //初始化主路径
        mPath = new Path();
        //初始化文字画笔
        mTxtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTxtPaint.setColor(Color.WHITE);
        mTxtPaint.setTextAlign(Paint.Align.CENTER);
        mTxtPaint.setTextSize(40);
        //初始化路径画笔
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setColor(Color.GRAY);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mCooPicture = HelpDraw.getCoo(getContext(), mCoo, false);
        mGridPicture = HelpDraw.getGrid(getContext());
        //初始化圆球按钮画笔
        mCtrlPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCtrlPaint.setColor(Color.RED);
        mCtrlPaint.setTextAlign(Paint.Align.CENTER);
        mCtrlPaint.setTextSize(30);
        //初始化时间流ValueAnimator
        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setRepeatCount(-1);
        mAnimator.setDuration(2000);
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(animation -> {
            updateBall();//更新小球位置
            invalidate();
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        HelpDraw.draw(canvas, mGridPicture);
        canvas.save();
        canvas.translate(mCoo.x, mCoo.y);//画布移到坐标原点
        mTxtPaint.setColor(Color.WHITE);

        dataView(canvas);//核心操作展示
        ctrlView(canvas);//操作按钮
        helpView(canvas);//辅助视图
        canvas.restore();
        HelpDraw.draw(canvas, mCooPicture);
    }

    /**
     * 绘制表结构
     *
     * @param canvas
     */
    private void dataView(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPath.reset();
        for (int i = 0; i < mArrayBoxes.size(); i++) {
            ArrayBox box = mArrayBoxes.get(i);
            mPaint.setColor(box.color);
            canvas.drawRoundRect(
                    box.x, box.y, box.x + Cons.BOX_WIDTH, box.y + Cons.BOX_HEIGHT,
                    BOX_RADIUS, BOX_RADIUS, mPaint);

            mPath.moveTo(box.x, box.y);
            mPath.rCubicTo(Cons.BOX_WIDTH / 2, Cons.BOX_HEIGHT / 2,
                    Cons.BOX_WIDTH / 2, Cons.BOX_HEIGHT / 2, Cons.BOX_WIDTH, 0);

            canvas.drawPath(mPath, mPathPaint);
            canvas.drawText(box.data + "",
                    box.x + Cons.BOX_WIDTH / 2,
                    box.y + Cons.BOX_HEIGHT / 2 + 3 * OFFSET_OF_TXT_Y, mTxtPaint);
        }
    }

    /**
     * 绘制数组的长度个空白矩形
     *
     * @param canvas
     */
    private void helpView(Canvas canvas) {
        for (int i = 0; i < mArrayBoxes.capacity(); i++) {
            int y = i / 8;//行坐标
            int x = i % 8;//列坐标
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(0xff821AFA);
            canvas.drawRoundRect(
                    (Cons.BOX_WIDTH + OFFSET_X) * x, (Cons.BOX_HEIGHT + OFFSET_Y) * y,
                    (Cons.BOX_WIDTH + OFFSET_X) * x + Cons.BOX_WIDTH, (Cons.BOX_HEIGHT + OFFSET_Y) * y + Cons.BOX_HEIGHT,
                    BOX_RADIUS, BOX_RADIUS, mPaint);
            mTxtPaint.setColor(0xff821AFA);
            canvas.drawText(i + "",
                    (Cons.BOX_WIDTH + OFFSET_X) * x + Cons.BOX_WIDTH / 2,
                    (Cons.BOX_HEIGHT + OFFSET_Y) * y + 3 * OFFSET_OF_TXT_Y, mTxtPaint);
        }
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("线性表", -100, -50, mPaint);
        canvas.drawText("当前选中点：" + selectIndex, 250, -50, mPaint);
    }

    /**
     * 控制面板--圆球
     *
     * @param canvas 画布
     */
    private void ctrlView(Canvas canvas) {
        for (int i = 0; i < CTRL_POS.length; i++) {
            mCtrlPaint.setColor(CTRL_COLOR[i]);
            canvas.drawCircle(CTRL_POS[i].x, CTRL_POS[i].y, CTRL_RADIUS, mCtrlPaint);
            canvas.drawText(CTRL_TXT[i], CTRL_POS[i].x, CTRL_POS[i].y + OFFSET_OF_TXT_Y, mTxtPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float downX = event.getX() - mCoo.x;
                float downY = event.getY() - mCoo.y;

                for (int i = 0; i < CTRL_POS.length; i++) {
                    //区域判定
                    if (JudgeMan.judgeCircleArea(
                            CTRL_POS[i].x, CTRL_POS[i].y,
                            downX, downY, CTRL_RADIUS * 1.2f)) {
                        if (mOnCtrlClickListener != null) {
                            switch (i) {
                                case 0://插入尾部
                                    mOnCtrlClickListener.onAdd(this);
                                    if (selectIndex > 0) {
                                        mArrayBoxes.get(selectIndex).color = 0xff43A3FA;
                                        selectIndex = -1;
                                    }
                                    break;
                                case 1://更新
                                    mOnCtrlClickListener.onSet(this);
//                                    contactTest();
                                    break;
                                case 2://查找
                                    mOnCtrlClickListener.onFind(this);
                                    break;
                                case 3://删除尾部
                                    if (selectIndex > 0) {//如果有选中的颜色，先复原
                                        mArrayBoxes.get(selectIndex).color = 0xff43A3FA;
                                    }
                                    selectIndex = mArrayBoxes.size() - 1;
                                    mAnimator.start();
                                    break;
                                case 4://定点添加
                                    if (selectIndex > 0) {
                                        mArrayBoxes.get(selectIndex).color = 0xff43A3FA;
                                        mOnCtrlClickListener.onAddByIndex(this);
                                        selectIndex = -1;
                                    }
                                    break;
                                case 5://定值查询
                                    mOnCtrlClickListener.onFindByData(this);
                                    break;
                                case 6://定点移除
                                    mAnimator.start();
                                    break;
                                case 7://清空
                                    mOnCtrlClickListener.onClear(this);
                                    selectIndex = -1;
                                    break;
                            }
                            CTRL_COLOR[i] = 0xff54E1F8;//点击更换颜色
                        }
                    }
                }
                updateSelectIndex(downX, downY);//更新selectIndex
                break;
            case MotionEvent.ACTION_UP://还原颜色
                CTRL_COLOR[0] = 0xff1EF519;
                CTRL_COLOR[1] = 0xff2992F2;
                CTRL_COLOR[2] = 0xffB946F4;
                CTRL_COLOR[3] = 0xffF50C0C;
                CTRL_COLOR[4] = 0xff1EF519;
                CTRL_COLOR[5] = 0xffB946F4;
                CTRL_COLOR[6] = 0xffF50C0C;
                CTRL_COLOR[7] = 0xffF46410;
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 点击时动态更新选中值
     *
     * @param downX 按下点X
     * @param downY 按下点Y
     */
    private void updateSelectIndex(float downX, float downY) {
        float x = downX / (Cons.BOX_WIDTH + OFFSET_X) - 0.5f;
        float y = downY / (Cons.BOX_HEIGHT + OFFSET_Y) - 0.5f;

        if (x > -0.5 && y > -0.5) {
            int indexOfData = Math.round(y) * 8 + Math.round(x);//逆向求取点中的数据索引

            if (selectIndex != -1) {
                mArrayBoxes.get(selectIndex).color = 0xff43A3FA;//还原之前选中的颜色
            }

            if (indexOfData < mArrayBoxes.size()) {
                mArrayBoxes.get(indexOfData).color = ColUtils.randomRGB();
                selectIndex = indexOfData;
            }
        }
    }

    private void contactTest() {
        IChart<ArrayBox<E>> contactArr = new ArrayChart<>();
        contactArr.add(new ArrayBox<E>((E) "toly1"));
        contactArr.add(new ArrayBox<E>((E) "toly2"));
        contactArr.add(new ArrayBox<E>((E) "toly3"));
        contactData(selectIndex, contactArr);
    }

    private void contactData(int index, IChart<ArrayBox<E>> chart) {
        mArrayBoxes.contact(index, chart);
        updatePosOfData();
    }

    /**
     * 更新小球
     */
    private void updateBall() {
        if (mArrayBoxes.size() <= 0 && selectIndex < 0) {
            return;
        }
        ArrayBox ball = mArrayBoxes.get(selectIndex);
        ball.x += ball.vX;
        ball.y += ball.vY;

        if (ball.y > 600) {
            if (mOnCtrlClickListener != null) {
                mOnCtrlClickListener.onRemoveByIndex(this);//移除监听放在这里了!!
                mAnimator.pause();
            }
        }
    }


    /**
     * 视图的数据操作接口方法--添加
     *
     * @param data 数据
     */
    public void addData(E data) {
        ArrayBox<E> arrayBox = new ArrayBox<>(0, 0);
        arrayBox.data = data;
        mArrayBoxes.add(arrayBox);
        updatePosOfData();
    }

    /**
     * 视图的数据操作接口方法--根据索引添加
     *
     * @param index 索引
     * @param data  数据
     */
    public void addDataById(int index, E data) {
        if (mArrayBoxes.size() > 0 && index < mArrayBoxes.size() && index >= 0) {
            ArrayBox<E> arrayBox = new ArrayBox<>(0, 0);
            arrayBox.data = data;
            mArrayBoxes.add(index, arrayBox);
            updatePosOfData();
        }
    }

    /**
     * 视图的数据操作接口方法--根据id查询操作
     *
     * @param index 索引
     * @return
     */
    public E findData(int index) {
        if (mArrayBoxes.size() > 0 && index < mArrayBoxes.size() && index >= 0) {
            return mArrayBoxes.get(index).data;
        }
        return null;
    }


    /**
     * 视图的数据操作接口方法--根据数据查询操作
     *
     * @param data 数据
     * @return
     */
    public int[] findData(E data) {
        ArrayBox<E> arrayBox = new ArrayBox<>(0, 0);
        arrayBox.data = data;
        return mArrayBoxes.getIndex(arrayBox);
    }

    /**
     * 视图的数据操作接口方法--更新数据
     *
     * @param index 索引
     * @param data  数据
     */
    public void setData(int index, E data) {
        if (mArrayBoxes.size() > 0 && index < mArrayBoxes.size() && index >= 0) {
            mArrayBoxes.get(index).data = data;
        }
    }

    /**
     * 视图的数据操作接口方法--移除末尾
     */
    public void removeData() {
        if (mArrayBoxes.size() > 0) {
            mArrayBoxes.remove();
            updatePosOfData();
        }
    }

    /**
     * 视图的数据操作接口方法--定索引删除操作
     *
     * @param index 索引
     */
    public void removeData(int index) {
        if (mArrayBoxes.size() > 0 && index < mArrayBoxes.size() && index >= 0) {
            //更新后面的索引
            for (int i = index; i < mArrayBoxes.size(); i++) {
                mArrayBoxes.get(i).index -= 1;
            }
            mArrayBoxes.remove(index);
            selectIndex = -1;
            updatePosOfData();
        }
    }

    /**
     * 视图的数据操作接口方法--清空操作
     */
    public void clearData() {
        mArrayBoxes.clear();
    }

    /**
     * 更新绘制单体的点位
     */
    private void updatePosOfData() {
        for (int i = 0; i < mArrayBoxes.size(); i++) {
            int y = i / 8;//行坐标
            int x = i % 8;//列坐标

            ArrayBox box = mArrayBoxes.get(i);
            box.x = (Cons.BOX_WIDTH + OFFSET_X) * x;
            box.y = (Cons.BOX_HEIGHT + OFFSET_Y) * y;
            box.vY = 100;
            box.vX = 100;
        }
    }

    /**
     * 获取选中索引
     *
     * @return
     */
    public int getSelectIndex() {
        return selectIndex;
    }

    /**
     * 获取选中值
     *
     * @return
     */
    public E getSelectData() {
        if (selectIndex >= 0) {
            return mArrayBoxes.get(selectIndex).data;
        }
        return null;
    }


}
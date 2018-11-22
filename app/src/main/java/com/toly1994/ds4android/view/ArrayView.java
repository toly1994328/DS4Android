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
import com.toly1994.ds4android.analyze.HelpDraw;
import com.toly1994.ds4android.analyze.L;
import com.toly1994.ds4android.analyze.gold12.JudgeMan;
import com.toly1994.ds4android.ds.impl.ArrayChart;
import com.toly1994.ds4android.ds.itf.IChart;
import com.toly1994.ds4android.model.ArrayBox;

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


    private Paint mPaint;//主画笔
    private Path mPath;//主路径

    private IChart<ArrayBox<E>> mArrayBoxes = new ArrayChart<>();


    private static final int OFFSET_X = 10;//X空隙
    private static final int OFFSET_Y = 60;//Y空隙
    private static final int OFFSET_OF_INDEX_Y = 10;//索引文字的上方空隙
    private static final int BOX_RADIUS = 10;//数组盒子的圆角
    private Paint mDataPaint;
    private Paint mCtrlPaint;
    private OnCtrlClickListener<ArrayView<E>> mOnCtrlClickListener;

    private int selectIndex = -1;


    private static final Point[] CTRL_POS = new Point[]{
            new Point(-100, 100),//添加按键的点位
            new Point(-100, 300),//更新按键的点位
            new Point(-100, 500),//查看按键的点位
            new Point(-100, 700),//删除按键的点位

            new Point(700, -70),//定点添加的点位
            new Point(700 + 300, -70),//定值查询的点位
            new Point(700 + 300 * 2, -70),//定点删除按键的点位
            new Point(700 + 300 * 3, -70),//清除按键的点位

    };

    private static int[] CTRL_COLOR = new int[]{
            0xff1EF519,//添加按键的颜色
            0xff2992F2,//更新按键的颜色
            0xffB946F4,//添加按键的颜色
            0xffF50C0C,//删除按键的颜色

            0xff1EF519,//定点添加按键的颜色
            0xffB946F4,//定值查询按键的颜色
            0xffF50C0C,//定点删除按键的颜色
            0xffF46410,//清除按键的颜色
    };

    private static final String[] CTRL_TXT = new String[]{
            "添加",//添加按键的文字
            "更新",//更新按键的文字
            "查寻",//添加按键的文字
            "删除",//删除按键的文字

            "定点+",//定点添加的文字
            "值查",//定值查询按键的文字
            "定点-",//定点删除按键的文字
            "清空",//清除按键的文字
    };

    private static final int CTRL_RADIUS = 50;//控制圆半径
    private Paint mPathPaint;//路径画笔
    private ValueAnimator mAnimator;


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
        //初始化数据画笔
        mDataPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDataPaint.setColor(Color.WHITE);
        mDataPaint.setTextAlign(Paint.Align.CENTER);
        mDataPaint.setTextSize(40);
        //初始化数据画笔
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setColor(Color.GRAY);
        mPathPaint.setStyle(Paint.Style.STROKE);


        mCooPicture = HelpDraw.getCoo(getContext(), mCoo);
        mGridPicture = HelpDraw.getGrid(getContext());

        //初始化数据画笔
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
        canvas.translate(mCoo.x, mCoo.y);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);

        mDataPaint.setColor(Color.WHITE);

        canvas.drawText("线性表", -100, -50, mPaint);
        canvas.drawText("当前选中点：" + selectIndex, 250, -50, mPaint);
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

//            canvas.drawText(box.index + "",
//                    box.x + Cons.BOX_WIDTH / 2,
//                    box.y + 3 * OFFSET_OF_INDEX_Y, mDataPaint);

            canvas.drawText(box.data + "",
                    box.x + Cons.BOX_WIDTH / 2,
                    box.y + Cons.BOX_HEIGHT / 2 + 3 * OFFSET_OF_INDEX_Y, mDataPaint);

        }

        ctrlView(canvas);
        helpView(canvas);//辅助视图

        canvas.restore();
        HelpDraw.draw(canvas, mCooPicture);
    }

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
            mDataPaint.setColor(0xff821AFA);
            canvas.drawText(i + "",
                    (Cons.BOX_WIDTH + OFFSET_X) * x + Cons.BOX_WIDTH / 2,
                    (Cons.BOX_HEIGHT + OFFSET_Y) * y + 3 * OFFSET_OF_INDEX_Y, mDataPaint);
        }

    }

    /**
     * 控制面板--圆球
     *
     * @param canvas
     */
    private void ctrlView(Canvas canvas) {

        for (int i = 0; i < CTRL_POS.length; i++) {
            mCtrlPaint.setColor(CTRL_COLOR[i]);
            canvas.drawCircle(CTRL_POS[i].x, CTRL_POS[i].y, CTRL_RADIUS, mCtrlPaint);
            canvas.drawText(CTRL_TXT[i], CTRL_POS[i].x, CTRL_POS[i].y + OFFSET_OF_INDEX_Y, mDataPaint);
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
                    if (JudgeMan.judgeCircleArea(CTRL_POS[i].x, CTRL_POS[i].y, downX, downY, CTRL_RADIUS * 1.2f)) {
                        if (mOnCtrlClickListener != null) {
                            switch (i) {
                                case 0://插入尾部
                                    mOnCtrlClickListener.onAdd(this);
                                    break;
                                case 1://更新
//                                    mOnCtrlClickListener.onSet(this);

                                    IChart<ArrayBox<E>> contactArr = new ArrayChart<>();
                                    contactArr.add(new ArrayBox<E>((E) "toly1"));
                                    contactArr.add(new ArrayBox<E>((E) "toly2"));
                                    contactArr.add(new ArrayBox<E>((E) "toly3"));
                                    contactData(selectIndex, contactArr);

                                    break;
                                case 2://查找
                                    mOnCtrlClickListener.onFind(this);
                                    break;
                                case 3://删除尾部
                                    selectIndex = mArrayBoxes.size() - 1;
                                    mAnimator.start();
                                    break;
                                case 4://定点添加尾部
                                    mOnCtrlClickListener.onAddByIndex(this);
                                    break;
                                case 5://定值查询
                                    mOnCtrlClickListener.onFindByData(this);
                                    break;
                                case 6://定点移除
                                    mAnimator.start();
                                    break;
                                case 7://清空
                                    mOnCtrlClickListener.onClear(this);
                                    break;
                            }
                            CTRL_COLOR[i] = 0xff54E1F8;
                        }
                    }
                }


                float x = downX / (Cons.BOX_WIDTH + OFFSET_X) - 0.5f;
                float y = downY / (Cons.BOX_HEIGHT + OFFSET_Y) - 0.5f;

                if (x > -0.5 && y > -0.5) {
                    int indexOfData = Math.round(y) * 8 + Math.round(x);
                    if (indexOfData < mArrayBoxes.size()) {
                        mArrayBoxes.get(indexOfData).color = ColUtils.randomRGB();
                        selectIndex = indexOfData;
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
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

    private void contactData(int index, IChart<ArrayBox<E>> chart) {


        mArrayBoxes.contact(index, chart);
        updatePosOfData();
    }

    /**
     * 更新小球
     */
    private void updateBall() {
        if (mArrayBoxes.size() <= 0) {
            return;
        }
        L.d(selectIndex + L.l());
        ArrayBox ball = mArrayBoxes.get(selectIndex);
        ball.x += ball.vX;
        ball.y += ball.vY;

        if (ball.y > 600) {
            if (mOnCtrlClickListener != null) {
                mOnCtrlClickListener.onRemove(this);
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

    public void removeData(int index) {
        if (mArrayBoxes.size() > 0 && index < mArrayBoxes.size() && index > 0) {
            //更新后面的索引
            for (int i = index; i < mArrayBoxes.size(); i++) {
                mArrayBoxes.get(i).index -= 1;
            }
            mArrayBoxes.remove(index);
            selectIndex = -1;
            updatePosOfData();
        }
    }

    public void removeData() {
        if (mArrayBoxes.size() > 0) {
            mArrayBoxes.remove();
            updatePosOfData();
        }

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

    public int getSelectIndex() {
        return selectIndex;
    }

    public E getSelectData() {
        return mArrayBoxes.get(selectIndex).data;
    }

    public void setData(int index, E data) {
        if (mArrayBoxes.size() > 0 && index < mArrayBoxes.size() && index >= 0) {
            mArrayBoxes.get(index).data = data;
            updatePosOfData();
        }
    }

    public E findData(int index) {
        if (mArrayBoxes.size() > 0 && index < mArrayBoxes.size() && index >= 0) {
            return mArrayBoxes.get(index).data;
        }

        return null;
    }

    public int[] findData(E data) {
        ArrayBox<E> arrayBox = new ArrayBox<>(0, 0);
        arrayBox.data = data;
        return mArrayBoxes.getIndex(arrayBox);

    }

    public void addData(int index, E data) {
        if (mArrayBoxes != null && index < mArrayBoxes.size() && index >= 0) {
            ArrayBox<E> arrayBox = new ArrayBox<>(0, 0);
            arrayBox.data = data;
            mArrayBoxes.add(index, arrayBox);
            updatePosOfData();
        }
    }

    public void clearData() {
        mArrayBoxes.clear();
    }
}
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
import android.widget.Toast;

import com.toly1994.ds4android.analyze.ColUtils;
import com.toly1994.ds4android.analyze.gold12.HelpDraw;
import com.toly1994.ds4android.analyze.gold12.JudgeMan;
import com.toly1994.ds4android.ds.impl.task.SingleLinkedStack;
import com.toly1994.ds4android.ds.itf.IStack;
import com.toly1994.ds4android.model.StackBox;
import com.toly1994.ds4android.view.other.Cons;
import com.toly1994.ds4android.view.other.OnCtrlClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:8:01<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：栈结构---测试视图
 */
public class StackView<E> extends View {
    private Point mCoo = new Point(300, 200);//坐标系
    private Picture mGridPicture;//网格canvas元件

    private Path mPath;//主路径
    private Paint mPaint;//主画笔
    private Paint mTxtPaint;//数字画笔
    private Paint mBoderPaint;//路径画笔
    private Paint mCtrlPaint;//几个圆的画笔

    //    private IStack<StackBox<E>> mStackBoxes = new ArrayChartStack<>();//数组表栈
    private IStack<StackBox<E>> mStackBoxes = new SingleLinkedStack<>();//

    //用于绘制非栈顶元素(由于Stack无法获取这些元素，所以此集合辅助绘制)
    private List<StackBox<E>> mUnSeeStackItemBox = new ArrayList<>();
    private OnCtrlClickListener<StackView<E>> mOnCtrlClickListener;///点击监听
    private ValueAnimator mInAnimator;//入栈动画
    private ValueAnimator mOutAnimator;//出栈动画
    private boolean canAdd = true;//是否可添加---防止多次点击添加

    private static final int OFFSET_OF_TXT_Y = 10;//文字的偏移

    private static final Point[] CTRL_POS = new Point[]{//控制按钮的点位
            new Point(-120, 100),//添加
            new Point(-120, 300 + 50),//移除
            new Point(-120, 500 + 100),//查看栈顶
    };

    private static int[] CTRL_COLOR = new int[]{//控制按钮的颜色
            0xff1EF519,//添加
            0xffB946F4,//移除
            0xff2992F2,//查看栈顶
    };

    private static final String[] CTRL_TXT = new String[]{//控制按钮的文字
            "push",//添加
            "pop",//移除
            "peek",//查看栈顶

    };
    private static final int CTRL_RADIUS = 70;//控制按钮的半径

    public void setOnCtrlClickListener(OnCtrlClickListener<StackView<E>> onCtrlClickListener) {
        mOnCtrlClickListener = onCtrlClickListener;
    }

    public StackView(Context context) {
        this(context, null);
    }

    public StackView(Context context, @Nullable AttributeSet attrs) {
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
        mTxtPaint.setTextSize(50);
        //初始化路径画笔
        mBoderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoderPaint.setColor(Color.BLACK);
        mBoderPaint.setStrokeWidth(4);
        mBoderPaint.setStyle(Paint.Style.STROKE);
        mGridPicture = HelpDraw.getGrid(getContext());
        //初始化圆球按钮画笔
        mCtrlPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCtrlPaint.setColor(Color.RED);
        mCtrlPaint.setTextAlign(Paint.Align.CENTER);
        mCtrlPaint.setTextSize(30);

        //初始化时间流ValueAnimator
        mInAnimator = ValueAnimator.ofFloat(0, 1);
        mInAnimator.setRepeatCount(-1);
        mInAnimator.setDuration(2000);
        mInAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mInAnimator.setInterpolator(new LinearInterpolator());
        mInAnimator.addUpdateListener(animation -> {
            updateBall();//更新小球位置
            invalidate();
        });

        //初始化时间流ValueAnimator---移除
        mOutAnimator = ValueAnimator.ofFloat(0, 1);
        mOutAnimator.setRepeatCount(-1);
        mOutAnimator.setDuration(2000);
        mOutAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mOutAnimator.setInterpolator(new LinearInterpolator());
        mOutAnimator.addUpdateListener(animation -> {
            updateOutBall();//更新小球位置
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
    }

    private static final int BOTTOM_OF_STACK = 700;//控制按钮的半径
    private static final int WIDTH_OF_STACK = 300;//控制按钮的半径
    private static final int STACK_X = 400;//控制按钮的半径
    private static final int STACK_Y = 100;//控制按钮的半径

    private static final int LEN_ABOVE_STACK = 200;//控制按钮的半径

    private int mCurStackTopLine = BOTTOM_OF_STACK;//当前栈顶线

    /**
     * 绘制栈结构
     *
     * @param canvas
     */
    private void dataView(Canvas canvas) {
        mPath.moveTo(STACK_X, STACK_Y);
        mPath.rLineTo(0, BOTTOM_OF_STACK);
        mPath.rLineTo(WIDTH_OF_STACK, 0);
        mPath.rLineTo(0, -BOTTOM_OF_STACK);
        canvas.drawPath(mPath, mBoderPaint);


        mPaint.setColor(Color.GRAY);
        for (StackBox<E> box : mUnSeeStackItemBox) {
            canvas.drawRect(box.x, box.y,
                    box.x + WIDTH_OF_STACK, box.y + Cons.BOX_HEIGHT,
                    mPaint);


            canvas.drawRect(box.x, box.y,
                    box.x + WIDTH_OF_STACK, box.y + Cons.BOX_HEIGHT,
                    mBoderPaint);

            canvas.drawText((String) box.data, box.x + WIDTH_OF_STACK / 2,
                    box.y + Cons.BOX_HEIGHT / 2 + 5, mTxtPaint);
        }
        mPaint.setColor(Color.BLUE);

        if (mStackBoxes.size() > 0) {
            StackBox<E> peek = mStackBoxes.peek();
            canvas.drawRect(peek.x, peek.y,
                    peek.x + WIDTH_OF_STACK, peek.y + Cons.BOX_HEIGHT,
                    mPaint);

            canvas.drawText((String) peek.data, peek.x + WIDTH_OF_STACK / 2,
                    peek.y + Cons.BOX_HEIGHT / 2 + 5, mTxtPaint);
        }

    }

    /**
     * 绘制数组的长度个空白矩形
     *
     * @param canvas
     */
    private void helpView(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("栈", -100, -50, mPaint);
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
                                    break;
                                case 1://更新
                                    mOnCtrlClickListener.onRemove(this);
                                    break;
                                case 2://查找
                                    mOnCtrlClickListener.onFind(this);
                                    break;
                            }
                            CTRL_COLOR[i] = 0xff54E1F8;//点击更换颜色
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP://还原颜色
                CTRL_COLOR[0] = 0xff1EF519;
                CTRL_COLOR[1] = 0xff2992F2;
                CTRL_COLOR[2] = 0xffB946F4;
                break;
        }
        invalidate();
        return true;
    }


    /**
     * 入栈动画
     */
    private void updateBall() {
        if (mStackBoxes.size() > 0) {
            StackBox ball = mStackBoxes.peek();
            ball.x += ball.vX;
            ball.y += ball.vY;

            if (ball.y > mCurStackTopLine) {
                ball.y = mCurStackTopLine;
                mInAnimator.pause();
                mCurStackTopLine = BOTTOM_OF_STACK
                        - (mUnSeeStackItemBox.size()) * Cons.BOX_HEIGHT;//更新栈顶线
                canAdd = true;
            }
        }
    }

    /**
     * 出栈动画
     */
    private void updateOutBall() {
        if (mStackBoxes.size() > 0) {
            StackBox ball = mStackBoxes.peek();
            ball.x += ball.vX;
            ball.y -= ball.vY;
            if (ball.y < -Cons.BOX_HEIGHT) {
                mStackBoxes.pop();
                mUnSeeStackItemBox.remove(mUnSeeStackItemBox.size() - 1);
                mOutAnimator.pause();
                mCurStackTopLine += Cons.BOX_HEIGHT;
                canAdd = true;
            }
        }
    }

    /**
     * 入栈
     *
     * @param data 数据
     */
    public void addData(E data) {
        if (!canAdd) {
            return;
        }
        StackBox<E> stackBox = new StackBox<>(0, 0);
        stackBox.vY = 18;
        stackBox.data = data;

        stackBox.x = STACK_X;
        stackBox.color = ColUtils.randomRGB();
        stackBox.y = STACK_Y - BOTTOM_OF_STACK + Cons.BOX_HEIGHT * mStackBoxes.size();
        mStackBoxes.push(stackBox);
        mUnSeeStackItemBox.add(stackBox);

        StackBox box = mStackBoxes.peek();//更新栈顶点位
        box.x = STACK_X;
        box.y = STACK_Y - LEN_ABOVE_STACK;
        mInAnimator.start();
        canAdd = false;

    }


    /**
     * 查看栈顶元素
     */
    public E findData() {
        if (mStackBoxes.isEmpty()) {
            Toast.makeText(getContext(), "栈为空", Toast.LENGTH_SHORT).show();
        }
        if (mStackBoxes.size() > 0) {
            return mStackBoxes.peek().data;
        }
        return null;
    }

    /**
     * 弹栈
     */
    public void removeData() {
        if (mStackBoxes.isEmpty()) {
            Toast.makeText(getContext(), "栈为空", Toast.LENGTH_SHORT).show();
        }

        if (mStackBoxes.size() > 0) {
            mOutAnimator.start();
            canAdd = false;
        }
    }
}
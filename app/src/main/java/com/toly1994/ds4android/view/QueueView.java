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

import com.toly1994.ds4android.analyze.L;
import com.toly1994.ds4android.analyze.gold12.HelpDraw;
import com.toly1994.ds4android.analyze.gold12.JudgeMan;
import com.toly1994.ds4android.ds.impl.queue.ArrayChartQueue;
import com.toly1994.ds4android.ds.impl.queue.ArrayLoopQueue;
import com.toly1994.ds4android.ds.itf.IQueue;
import com.toly1994.ds4android.model.QueueBox;
import com.toly1994.ds4android.view.other.OnCtrlClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:8:01<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：队列结构---测试视图
 */
public class QueueView<E> extends View {
    private Point mCoo = new Point(300, 200);//坐标系
    private Picture mGridPicture;//网格canvas元件

    private Path mPath;//主路径
    private Paint mPaint;//主画笔
    private Paint mTxtPaint;//数字画笔
    private Paint mBoderPaint;//路径画笔
    private Paint mCtrlPaint;//几个圆的画笔

//    private IQueue<QueueBox<E>> mQueueBoxes = new ArrayChartQueue<>(4);//普通数组表队列
    private IQueue<QueueBox<E>> mQueueBoxes = new ArrayLoopQueue<>(4);//普通数组表队列

    //用于绘制非栈顶元素(由于Stack无法获取这些元素，所以此集合辅助绘制)
    private List<QueueBox<E>> mUnSeeStackItemBox = new ArrayList<>();
    private OnCtrlClickListener<QueueView<E>> mOnCtrlClickListener;///点击监听
    private ValueAnimator mInAnimator;//入栈动画
    private ValueAnimator mOutAnimator;//出栈动画
    private boolean canAdd = true;//是否可添加---防止多次点击添加

    private static final int OFFSET_OF_TXT_Y = 10;//文字的偏移

    private static final Point[] CTRL_POS = new Point[]{//控制按钮的点位
            new Point(-120, 100),//添加
            new Point(-120, 300 + 50),//移除
            new Point(-120, 500 + 100),//查看队首
    };

    private static int[] CTRL_COLOR = new int[]{//控制按钮的颜色
            0xff1EF519,//添加
            0xffB946F4,//移除
            0xff2992F2,//查看队首
    };

    private static final String[] CTRL_TXT = new String[]{//控制按钮的文字
            "入队",//添加
            "出队",//移除
            "查看",//查看队首

    };
    private static final int CTRL_RADIUS = 70;//控制按钮的半径

    public void setOnCtrlClickListener(OnCtrlClickListener<QueueView<E>> onCtrlClickListener) {
        mOnCtrlClickListener = onCtrlClickListener;
    }

    public QueueView(Context context) {
        this(context, null);
    }

    public QueueView(Context context, @Nullable AttributeSet attrs) {
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


    private static final int QUEUE_WIDTH = 1200;//队列宽
    private static final int QUEUE_HEIGHT = 300;//队列高

    private static final int HEIGHT_OF_ITEM = QUEUE_HEIGHT;//单体高
    private static final int WIDTH_OF_ITEM = 100;//单体宽

    private static final int QUEUE_X = 200;
    private static final int QUEUE_Y = 100;

    private static final int END_OF_MOVE_X = QUEUE_X + QUEUE_WIDTH;//空队列队首的X
    private static final int MAX_OF_MOVE_X = END_OF_MOVE_X + 300;//空队列队首的X

    private int mCurQueueLine = END_OF_MOVE_X - WIDTH_OF_ITEM;//当前队列前线

    /**
     * 绘制栈结构
     *
     * @param canvas
     */
    private void dataView(Canvas canvas) {
        mPath.moveTo(QUEUE_X, QUEUE_Y);
        mPath.rLineTo(QUEUE_WIDTH, 0);


        mPath.moveTo(QUEUE_X, QUEUE_Y + QUEUE_HEIGHT);
        mPath.rLineTo(QUEUE_WIDTH, 0);
        canvas.drawPath(mPath, mBoderPaint);

        //绘制数组显示区----------
        if (mQueueBoxes instanceof ArrayChartQueue) {
            int rightMax = QUEUE_X + QUEUE_WIDTH - WIDTH_OF_ITEM;
            mCtrlPaint.setColor(0x882BC7F8);
            for (int i = 0; i < ((ArrayChartQueue) mQueueBoxes).capacity(); i++) {
                canvas.drawRoundRect(rightMax - i * WIDTH_OF_ITEM, QUEUE_Y, rightMax - i * WIDTH_OF_ITEM + WIDTH_OF_ITEM, QUEUE_Y + HEIGHT_OF_ITEM,
                        10, 10, mCtrlPaint);
            }
            for (int i = 0; i < ((ArrayChartQueue) mQueueBoxes).capacity(); i++) {
                canvas.drawRoundRect(rightMax - i * WIDTH_OF_ITEM, QUEUE_Y, rightMax - i * WIDTH_OF_ITEM + WIDTH_OF_ITEM, QUEUE_Y + HEIGHT_OF_ITEM,
                        10, 10, mBoderPaint);
            }
        }
        //----------

        canvas.save();
        canvas.rotate(90, QUEUE_X + QUEUE_WIDTH, QUEUE_Y + QUEUE_HEIGHT / 2);
        canvas.drawText("前台招待处", QUEUE_X + QUEUE_WIDTH, QUEUE_Y + QUEUE_HEIGHT / 2 - 100, mPaint);
        canvas.restore();


        for (int i = 0; i < mUnSeeStackItemBox.size(); i++) {
            QueueBox<E> box = mUnSeeStackItemBox.get(i);
            if (i == 0) {
                mPaint.setColor(Color.BLUE);
            } else {
                mPaint.setColor(Color.GRAY);
            }
            canvas.drawRect(box.x, box.y,
                    box.x + WIDTH_OF_ITEM, box.y + HEIGHT_OF_ITEM,
                    mPaint);

            canvas.drawRect(box.x, box.y,
                    box.x + WIDTH_OF_ITEM, box.y + HEIGHT_OF_ITEM,
                    mBoderPaint);

            canvas.save();
            canvas.rotate(90, box.x + WIDTH_OF_ITEM / 2 - 10,
                    box.y + HEIGHT_OF_ITEM / 2 + 5);
            canvas.drawText((String) box.data, box.x + WIDTH_OF_ITEM / 2 - 10,
                    box.y + HEIGHT_OF_ITEM / 2 + 5, mTxtPaint);
            canvas.restore();
        }

    }

    /**
     * 绘制数组的长度个空白矩形
     *
     * @param canvas
     */
    private void helpView(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("队列", -100, -50, mPaint);


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
        if (mQueueBoxes.size() > 0) {
            QueueBox ball = mUnSeeStackItemBox.get(mQueueBoxes.size() - 1);//获取尾元素
            ball.x += ball.vX;
            ball.y += ball.vY;

            if (ball.x > mCurQueueLine) {
                ball.x = mCurQueueLine;
                mInAnimator.pause();
                mCurQueueLine -= WIDTH_OF_ITEM;//更新栈顶线
                canAdd = true;
            }
        }
    }

    /**
     * 出栈动画
     */
    private void updateOutBall() {
        if (mQueueBoxes.size() > 0) {
            QueueBox ball = mQueueBoxes.front();
            ball.x += ball.vX;
            ball.y -= ball.vY;
            if (ball.x > MAX_OF_MOVE_X) {
                mQueueBoxes.dequeue();
                mUnSeeStackItemBox.remove(0);
                mOutAnimator.pause();
                mCurQueueLine += WIDTH_OF_ITEM;
                for (QueueBox<E> unSeeStackItemBox : mUnSeeStackItemBox) {
                    unSeeStackItemBox.x += WIDTH_OF_ITEM;
                }
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
        QueueBox<E> queueBox = new QueueBox<>(0, 0);
        queueBox.vX = 18;
        queueBox.data = data;
        queueBox.x = END_OF_MOVE_X - WIDTH_OF_ITEM * mQueueBoxes.size();//元素x坐标
        queueBox.y = QUEUE_Y;
        mQueueBoxes.enqueue(queueBox);
        mUnSeeStackItemBox.add(queueBox);
        L.d(mQueueBoxes.size() + L.l());

//        QueueBox box = mQueueBoxes.front();//更新栈顶点位

        //获取尾元素--此处验证了队列的大小
        QueueBox box = mUnSeeStackItemBox.get(mQueueBoxes.size() - 1);
        box.x = QUEUE_X - 200;//队首元素更改x
        mInAnimator.start();
        canAdd = false;
    }


    /**
     * 查看栈顶元素
     */
    public E findData() {
        if (mQueueBoxes.isEmpty()) {
            Toast.makeText(getContext(), "栈为空", Toast.LENGTH_SHORT).show();
        }
        if (mQueueBoxes.size() > 0) {
            return mQueueBoxes.front().data;
        }
        return null;
    }

    /**
     * 弹栈
     */
    public void removeData() {
        if (mQueueBoxes.isEmpty()) {
            Toast.makeText(getContext(), "栈为空", Toast.LENGTH_SHORT).show();
        }

        if (mQueueBoxes.size() > 0) {
            mOutAnimator.start();
            canAdd = false;
        }
    }
}
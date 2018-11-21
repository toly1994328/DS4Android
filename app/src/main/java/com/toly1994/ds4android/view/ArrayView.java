package com.toly1994.ds4android.view;

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

import com.toly1994.ds4android.analyze.ColUtils;
import com.toly1994.ds4android.analyze.HelpDraw;
import com.toly1994.ds4android.analyze.gold12.JudgeMan;
import com.toly1994.ds4android.model.ArrayBox;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:8:01<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class ArrayView<E> extends View {
    private Point mCoo = new Point(200, 100);//坐标系
    private Picture mCooPicture;//坐标系canvas元件
    private Picture mGridPicture;//网格canvas元件
    private Paint mHelpPint;//辅助画笔

    private Paint mPaint;//主画笔
    private Path mPath;//主路径

    private List<ArrayBox<E>> mArrayBoxes = new ArrayList<>();


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
    };
    private static int[] CTRL_COLOR = new int[]{
            0xff1EF519,//添加按键的颜色
            0xff2992F2,//更新按键的颜色
            0xffB946F4,//添加按键的颜色
            0xffF50C0C,//删除按键的颜色
    };

    private static final String[] CTRL_TXT = new String[]{
            "添加",//添加按键的颜色
            "更新",//更新按键的颜色
            "查寻",//添加按键的颜色
            "删除",//删除按键的颜色
    };

    //    private static final Point CTRL_ADD_POS = new Point(-100, 100);//添加按键的点位
//    private static final Point CTRL_UPDATE_POS = new Point(-100, 300);//添加按键的点位
//    private static final Point CTRL_FIND_POS = new Point(-100, 500);//添加按键的点位
//    private static final Point CTRL_DELETE_POS = new Point(-100, 700);//添加按键的点位
    private static final int CTRL_RADIUS = 50;//控制圆半径
    private Paint mPathPaint;//路径画笔


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


        //初始化数据画笔
        mCtrlPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCtrlPaint.setColor(Color.RED);
        mCtrlPaint.setTextAlign(Paint.Align.CENTER);
        mCtrlPaint.setTextSize(30);

        //初始化辅助
        mHelpPint = HelpDraw.getHelpPint(Color.RED);
        mCooPicture = HelpDraw.getCoo(getContext(), mCoo);
        mGridPicture = HelpDraw.getGrid(getContext());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        HelpDraw.draw(canvas, mGridPicture);
        canvas.save();
        canvas.translate(mCoo.x, mCoo.y);
        canvas.drawText("线性表", -100, 0, mPaint);
        mPath.reset();
        for (int i = 0; i < mArrayBoxes.size(); i++) {
            ArrayBox box = mArrayBoxes.get(i);
            mPaint.setColor(box.color);
            canvas.drawRoundRect(
                    box.x, box.y, box.x + ArrayBox.BOX_WIDTH, box.y + ArrayBox.BOX_HEIGHT,
                    BOX_RADIUS, BOX_RADIUS, mPaint);

            mPath.moveTo(box.x, box.y);
            mPath.rCubicTo(ArrayBox.BOX_WIDTH / 2, ArrayBox.BOX_HEIGHT / 2,
                    ArrayBox.BOX_WIDTH / 2, ArrayBox.BOX_HEIGHT / 2, ArrayBox.BOX_WIDTH, 0);

            canvas.drawPath(mPath, mPathPaint);

            canvas.drawText(box.index + "",
                    box.x + ArrayBox.BOX_WIDTH / 2,
                    box.y + 3*OFFSET_OF_INDEX_Y, mDataPaint);

            canvas.drawText(box.data + "",
                    box.x + ArrayBox.BOX_WIDTH / 2,
                    box.y + ArrayBox.BOX_HEIGHT / 2 + 3*OFFSET_OF_INDEX_Y, mDataPaint);

        }
        ctrlView(canvas);
        canvas.restore();
        HelpDraw.draw(canvas, mCooPicture);
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


    private float downX;
    private float downY;

    @Override

    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX() - mCoo.x;
                downY = event.getY() - mCoo.y;
                //插入区域判定
                if (JudgeMan.judgeCircleArea(CTRL_POS[0].x, CTRL_POS[0].y, downX, downY, CTRL_RADIUS * 1.2f)) {
                    if (mOnCtrlClickListener != null) {
                        mOnCtrlClickListener.onAdd(this);
                        CTRL_COLOR[0] = 0xff54E1F8;

                    }
                }

                //更新区域判定
                if (JudgeMan.judgeCircleArea(CTRL_POS[1].x, CTRL_POS[1].y, downX, downY, CTRL_RADIUS * 1.2f)) {
                    if (mOnCtrlClickListener != null) {
                        mOnCtrlClickListener.onSet(this);
                        CTRL_COLOR[1] = 0xff54E1F8;
                    }
                }

                //查询区域判定
                if (JudgeMan.judgeCircleArea(CTRL_POS[2].x, CTRL_POS[2].y, downX, downY, CTRL_RADIUS * 1.2f)) {
                    if (mOnCtrlClickListener != null) {
                        mOnCtrlClickListener.onFind(this);
                        CTRL_COLOR[2] = 0xff54E1F8;

                    }
                }

                //删除区域判定
                if (JudgeMan.judgeCircleArea(CTRL_POS[3].x, CTRL_POS[3].y, downX, downY, CTRL_RADIUS * 1.2f)) {
                    if (mOnCtrlClickListener != null) {
                        mOnCtrlClickListener.onRemove(this);
                        CTRL_COLOR[3] = 0xff54E1F8;

                    }
                }


                float x = downX / (ArrayBox.BOX_WIDTH + OFFSET_X) - 0.5f;
                float y = downY / (ArrayBox.BOX_HEIGHT + OFFSET_Y) - 0.5f;

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
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 视图的数据操作接口方法--添加
     *
     * @param data 数据
     */
    public void addData(E data) {
        if (mArrayBoxes.isEmpty()) {
            ArrayBox<E> arrayBox = new ArrayBox<>(0, 0);
            arrayBox.index = 0;
            arrayBox.data = data;
            mArrayBoxes.add(arrayBox);
        } else {
            ArrayBox lastBox = mArrayBoxes.get(mArrayBoxes.size() - 1);
            ArrayBox<E> arrayBox = new ArrayBox<>(0, 0);
            arrayBox.index = lastBox.index + 1;
            arrayBox.data = data;
            mArrayBoxes.add(arrayBox);
        }
        updatePosOfData();
    }

    public void removeData(int index) {
        if (mArrayBoxes != null && index < mArrayBoxes.size() && index > 0) {
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
     * 更新绘制单体的点位
     */
    private void updatePosOfData() {

        for (int i = 0; i < mArrayBoxes.size(); i++) {
            int y = i / 8;//行坐标
            int x = i % 8;//列坐标

            ArrayBox box = mArrayBoxes.get(i);
            box.x = (ArrayBox.BOX_WIDTH + OFFSET_X) * x;
            box.y = (ArrayBox.BOX_HEIGHT + OFFSET_Y) * y;
        }

    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }


    public void setData(int index, E data) {
        if (mArrayBoxes != null && index < mArrayBoxes.size() && index > 0) {
            mArrayBoxes.get(index).data = data;
            updatePosOfData();
        }
    }

    public E findData(int index) {
        return mArrayBoxes.get(index).data;
    }
}
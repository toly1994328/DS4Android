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
import android.view.View;

import com.toly1994.ds4android.analyze.HelpDraw;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:14:03<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class SingleView4Test extends View {
    private Point mCoo = new Point(500, 500);//坐标系
    private Picture mCooPicture;//坐标系canvas元件
    private Picture mGridPicture;//网格canvas元件
    private Paint mHelpPint;//辅助画笔

    private Paint mPaint;//主画笔
    private Path mPath;//主路径
    private Paint mDataPaint;
    private Paint mPathPaint;

    public SingleView4Test(Context context) {
        this(context, null);
    }

    public SingleView4Test(Context context, @Nullable AttributeSet attrs) {
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
        mDataPaint.setTextSize(Cons.FONT_SIZE);
        //初始化数据画笔
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setColor(0xff0CEF1A);
        mPathPaint.setStrokeWidth(5);
        mPathPaint.setStyle(Paint.Style.STROKE);

        //初始化主路径
        mPath = new Path();

        //初始化辅助
        mHelpPint = HelpDraw.getHelpPint(Color.RED);
        mCooPicture = HelpDraw.getCoo(getContext(), mCoo);
        mGridPicture = HelpDraw.getGrid(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mCoo.x, mCoo.y);

//        arrayView(canvas);//数组
//        singleLink(canvas);//单链表
//        DoubleLink(canvas);//双链表
//        BST(canvas);//二叉树
        bAr(canvas);//红黑树


        canvas.restore();
//        HelpDraw.draw(canvas, mGridPicture, mCooPicture);
    }

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
        canvas.drawText("0",
                Cons.BOX_WIDTH / 2,
                3 * 10, mDataPaint);
        canvas.drawText("toly",
                0 + Cons.BOX_WIDTH / 2,
                0 + Cons.BOX_HEIGHT / 2 + 3 * 10, mDataPaint);
    }
}
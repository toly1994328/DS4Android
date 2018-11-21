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
import com.toly1994.ds4android.model.ArrayBox;

/**
 * 作者：张风捷特烈<br/>
 * 时间：2018/11/21 0021:14:03<br/>
 * 邮箱：1981462002@qq.com<br/>
 * 说明：
 */
public class SingleView extends View {
    private Point mCoo = new Point(500, 500);//坐标系
    private Picture mCooPicture;//坐标系canvas元件
    private Picture mGridPicture;//网格canvas元件
    private Paint mHelpPint;//辅助画笔

    private Paint mPaint;//主画笔
    private Path mPath;//主路径
    private Paint mDataPaint;
    private Paint mPathPaint;

    public SingleView(Context context) {
        this(context, null);
    }

    public SingleView(Context context, @Nullable AttributeSet attrs) {
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

//        arrayView(canvas);
//        singleLink(canvas);
//        DoubleLink(canvas);
//        BST(canvas);

        mPath.moveTo(0 + Cons.BST_RADIUS / 2, 0 + Cons.BST_RADIUS / 2);
        mPath.rLineTo(100, 100);
        mPath.moveTo(0 - Cons.BST_RADIUS / 2, 0 + Cons.BST_RADIUS / 2);
        mPath.rLineTo(-100, 100);

        canvas.drawPath(mPath, mPathPaint);

        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(0, 0, Cons.BST_RADIUS, mPaint);
        canvas.drawText("50", 0, 10, mDataPaint);

        int offsetLeftX =  100 + Cons.BST_RADIUS / 2;
        int offsetLeftY =  100 + Cons.BST_RADIUS / 2;

        mPaint.setColor(Color.RED);
        canvas.drawCircle(0 -offsetLeftX, 0+ offsetLeftY, Cons.BST_RADIUS, mPaint);
        canvas.drawText("35", 0-offsetLeftX, 10 +offsetLeftY, mDataPaint);

        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(offsetLeftX, offsetLeftY, Cons.BST_RADIUS, mPaint);
        canvas.drawText("78", offsetLeftX, 10 +offsetLeftY, mDataPaint);


        canvas.restore();
//        HelpDraw.draw(canvas, mGridPicture, mCooPicture);
    }

    private void BST(Canvas canvas) {
        mPath.moveTo(0 + Cons.BST_RADIUS / 2, 0 + Cons.BST_RADIUS / 2);
        mPath.rLineTo(100, 100);
        mPath.moveTo(0 - Cons.BST_RADIUS / 2, 0 + Cons.BST_RADIUS / 2);
        mPath.rLineTo(-100, 100);

        canvas.drawPath(mPath, mPathPaint);

        canvas.drawCircle(0, 0, Cons.BST_RADIUS, mPaint);
        canvas.drawText("0", 0, 10, mDataPaint);


//        mPath.moveTo(0 - Cons.BST_RADIUS, 0 + Cons.BST_RADIUS);
//        mPath.rLineTo(-100, 100);

        int offsetLeftX =  100 + Cons.BST_RADIUS / 2;
        int offsetLeftY =  100 + Cons.BST_RADIUS / 2;

        canvas.drawCircle(0 -offsetLeftX, 0+ offsetLeftY, Cons.BST_RADIUS, mPaint);
        canvas.drawText("null", 0-offsetLeftX, 10 +offsetLeftY, mDataPaint);

        canvas.drawCircle(offsetLeftX, offsetLeftY, Cons.BST_RADIUS, mPaint);
        canvas.drawText("null", offsetLeftX, 10 +offsetLeftY, mDataPaint);
    }

    private void DoubleLink(Canvas canvas) {
        canvas.drawRoundRect(
                0, 0, ArrayBox.BOX_WIDTH, ArrayBox.BOX_HEIGHT,
                Cons.BOX_RADIUS, Cons.BOX_RADIUS, mPaint);


        mPath.rCubicTo(ArrayBox.BOX_WIDTH / 2, ArrayBox.BOX_HEIGHT / 2,
                ArrayBox.BOX_WIDTH / 2, ArrayBox.BOX_HEIGHT / 2, ArrayBox.BOX_WIDTH, 0);

        mPath.lineTo(ArrayBox.BOX_WIDTH, ArrayBox.BOX_HEIGHT / 2.2f);
        mPath.rLineTo(Cons.LINK_LEN, 0);
        mPath.rLineTo(-Cons.ARROW_DX, -Cons.ARROW_DX);

        mPath.moveTo(0, 0);
        mPath.rLineTo(0, ArrayBox.BOX_HEIGHT / 1.2f);
        mPath.rLineTo(-Cons.LINK_LEN - Cons.ARROW_DX, 0);
        mPath.rLineTo(Cons.ARROW_DX, Cons.ARROW_DX);

        canvas.drawPath(mPath, mPathPaint);

        canvas.drawText("0",
                ArrayBox.BOX_WIDTH / 2,
                3 * 10, mDataPaint);

        canvas.drawText("toly",
                0 + ArrayBox.BOX_WIDTH / 2,
                0 + ArrayBox.BOX_HEIGHT / 2 + 3 * 10, mDataPaint);
    }

    private void singleLink(Canvas canvas) {
        canvas.drawRoundRect(
                0, 0, ArrayBox.BOX_WIDTH, ArrayBox.BOX_HEIGHT,
                Cons.BOX_RADIUS, Cons.BOX_RADIUS, mPaint);


        mPath.rCubicTo(ArrayBox.BOX_WIDTH / 2, ArrayBox.BOX_HEIGHT / 2,
                ArrayBox.BOX_WIDTH / 2, ArrayBox.BOX_HEIGHT / 2, ArrayBox.BOX_WIDTH, 0);

        mPath.lineTo(ArrayBox.BOX_WIDTH, ArrayBox.BOX_HEIGHT / 2f);
        mPath.rLineTo(Cons.LINK_LEN, 0);
        mPath.rLineTo(-Cons.ARROW_DX, -Cons.ARROW_DX);

//        mPath.moveTo(0, 0);
//        mPath.rLineTo(0,ArrayBox.BOX_HEIGHT / 1.2f);
//        mPath.rLineTo(-Cons.LINK_LEN-Cons.ARROW_DX, 0);
//        mPath.rLineTo(Cons.ARROW_DX, Cons.ARROW_DX);

        canvas.drawPath(mPath, mPathPaint);

        canvas.drawText("0",
                ArrayBox.BOX_WIDTH / 2,
                3 * 10, mDataPaint);


        canvas.drawText("toly",
                0 + ArrayBox.BOX_WIDTH / 2,
                0 + ArrayBox.BOX_HEIGHT / 2 + 3 * 10, mDataPaint);
    }

    private void arrayView(Canvas canvas) {
        canvas.drawRoundRect(
                0, 0, ArrayBox.BOX_WIDTH, ArrayBox.BOX_HEIGHT,
                10, 10, mPaint);

        mPath.moveTo(0, 0);
        mPath.rCubicTo(ArrayBox.BOX_WIDTH / 2, ArrayBox.BOX_HEIGHT / 2,
                ArrayBox.BOX_WIDTH / 2, ArrayBox.BOX_HEIGHT / 2, ArrayBox.BOX_WIDTH, 0);

        canvas.drawPath(mPath, mPathPaint);

        canvas.drawText("0",
                ArrayBox.BOX_WIDTH / 2,
                3 * 10, mDataPaint);


        canvas.drawText("toly",
                0 + ArrayBox.BOX_WIDTH / 2,
                0 + ArrayBox.BOX_HEIGHT / 2 + 3 * 10, mDataPaint);
    }
}
package com.yidao.platform.read.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xuhuanli.androidutils.density.DensityUtil;
import com.yidao.platform.app.utils.ScreenUtil;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }

    /*    @Override
    public ImageView createImageView(Context context) {
        //圆角
        return new RoundAngleImageView(context);
    }*/
    /*public class RoundImageView extends android.support.v7.widget.AppCompatImageView {

        private Context mContext;
        float width,height;
        private int mRadiusPx;
        private Path path;

        public RoundImageView(Context context) {
            this(context, null);
        }

        public RoundImageView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            mContext = context;
            mRadiusPx = ScreenUtil.dip2px(getContext(),10);
            path = new Path();
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            width = getWidth();
            height = getHeight();
        }

        @Override
        protected void onDraw(Canvas canvas) {

            //这里的目的是将画布设置成一个顶部边缘是圆角的矩形
            if (width > mRadiusPx && height > mRadiusPx) {
                path.moveTo(mRadiusPx, 0);
                path.lineTo(width - mRadiusPx, 0);
                path.quadTo(width, 0, width, mRadiusPx);
                path.lineTo(width, height - mRadiusPx);
                path.quadTo(width, height, width - mRadiusPx, height);
                path.lineTo(mRadiusPx, height);
                path.quadTo(0, height, 0, height - mRadiusPx);
                path.lineTo(0, mRadiusPx);
                path.quadTo(0, 0, mRadiusPx, 0);


                canvas.clipPath(path);
            }

            super.onDraw(canvas);
        }
    }*/

    public class RoundAngleImageView extends android.support.v7.widget.AppCompatImageView {

        private Paint paint;
        private int roundWidth = ScreenUtil.dip2px(getContext(),10);
        private int roundHeight = ScreenUtil.dip2px(getContext(),10);
        private Paint paint2;

        public RoundAngleImageView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init(context, attrs);
        }

        public RoundAngleImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context, attrs);
        }

        public RoundAngleImageView(Context context) {
            super(context);
            init(context, null);
        }

        private void init(Context context, AttributeSet attrs) {
            paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            paint2 = new Paint();
            paint2.setXfermode(null);
        }

        @Override
        public void draw(Canvas canvas) {
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(bitmap);
            super.draw(canvas2);
            drawLiftUp(canvas2);
            drawRightUp(canvas2);
            drawLiftDown(canvas2);
            drawRightDown(canvas2);
            canvas.drawBitmap(bitmap, 0, 0, paint2);
            bitmap.recycle();
        }

        private void drawLiftUp(Canvas canvas) {
            Path path = new Path();
            path.moveTo(0, roundHeight);
            path.lineTo(0, 0);
            path.lineTo(roundWidth, 0);
            path.arcTo(new RectF(
                            0,
                            0,
                            roundWidth*2,
                            roundHeight*2),
                    -90,
                    -90);
            path.close();
            canvas.drawPath(path, paint);
        }

        private void drawLiftDown(Canvas canvas) {
            Path path = new Path();
            path.moveTo(0, getHeight()-roundHeight);
            path.lineTo(0, getHeight());
            path.lineTo(roundWidth, getHeight());
            path.arcTo(new RectF(
                            0,
                            getHeight()-roundHeight*2,
                            0+roundWidth*2,
                            getHeight()),
                    90,
                    90);
            path.close();
            canvas.drawPath(path, paint);
        }

        private void drawRightDown(Canvas canvas) {
            Path path = new Path();
            path.moveTo(getWidth()-roundWidth, getHeight());
            path.lineTo(getWidth(), getHeight());
            path.lineTo(getWidth(), getHeight()-roundHeight);
            path.arcTo(new RectF(
                    getWidth()-roundWidth*2,
                    getHeight()-roundHeight*2,
                    getWidth(),
                    getHeight()), 0, 90);
            path.close();
            canvas.drawPath(path, paint);
        }

        private void drawRightUp(Canvas canvas) {
            Path path = new Path();
            path.moveTo(getWidth(), roundHeight);
            path.lineTo(getWidth(), 0);
            path.lineTo(getWidth()-roundWidth, 0);
            path.arcTo(new RectF(
                            getWidth()-roundWidth*2,
                            0,
                            getWidth(),
                            0+roundHeight*2),
                    -90,
                    90);
            path.close();
            canvas.drawPath(path, paint);
        }

    }
}

package syr.labs.jarvis.app.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public final class TimerKnobView extends View
{
    public static interface OnRotateListener
    {

        public abstract void onRotate(int i);
    }


    private static final String TAG = TimerKnobView.class.getSimpleName();
    Bitmap backgroundBitmap;
    Paint backgroundPaint;
    private float currentValue;
    Paint handPaint;
    Path handPath;
    Paint handScrewPaint;
    private OnRotateListener listener;
    private int mLogoID;
    private String mTitle;
    private float theta_old;

    public TimerKnobView(Context context)
    {
        super(context);
        currentValue = 0.0F;
        listener = null;
        theta_old = 0.0F;
        init(null);
    }

    public TimerKnobView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        currentValue = 0.0F;
        listener = null;
        theta_old = 0.0F;
        init(attributeset);
    }

    public TimerKnobView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        currentValue = 0.0F;
        listener = null;
        theta_old = 0.0F;
        init(attributeset);
    }

    private int chooseDimension(int i, int j)
    {
        if (i == MeasureSpec.EXACTLY || i == MeasureSpec.AT_MOST)
        {
            return j;
        } else
        {
            return getPreferredSize();
        }
    }

    private int getPreferredSize()
    {
        return 300;
    }

    private float getTheta(float f, float f1)
    {
        float f2 = f - (float)getWidth() / 2.0F;
        float f3 = f1 - (float)getHeight() / 2.0F;
        float f4 = (float)Math.sqrt(f2 * f2 + f3 * f3);
        float f5 = f2 / f4;
        float f6 = 57.29578F * (float)Math.atan2(f3 / f4, f5);
        if (f6 < 0.0F)
        {
            f6 += 360F;
        }
        return f6;
    }

    private void init(AttributeSet attributeset)
    {
        initDrawingTools();
        initAttrs(attributeset);
    }

    private void initAttrs(AttributeSet attributeset)
    {
        if (attributeset != null)
        {
            mTitle = attributeset.getAttributeValue("http://schemas.android.com/apk/res-auto", "title");
            if (mTitle == null)
            {
                mTitle = "";
            }
            mLogoID = attributeset.getAttributeResourceValue("http://schemas.android.com/apk/res-auto", "image", 0x7f020015);
        }
    }

    private void initDrawingTools()
    {
        backgroundPaint = new Paint();
        backgroundPaint.setFilterBitmap(true);
        handPaint = new Paint();
        handPaint.setAntiAlias(true);
        handPaint.setColor(0xff392f2c);
        handPaint.setShadowLayer(0.01F, -0.005F, -0.005F, 0x7f000000);
        handPaint.setStyle(Paint.Style.FILL);
        handPath = new Path();
        handPath.moveTo(0.5F, 0.7F);
        handPath.lineTo(0.49F, 0.69F);
        handPath.lineTo(0.498F, 0.22F);
        handPath.lineTo(0.502F, 0.22F);
        handPath.lineTo(0.51F, 0.69F);
        handPath.lineTo(0.5F, 0.7F);
        handPath.addCircle(0.5F, 0.5F, 0.025F, Path.Direction.CW);
        handScrewPaint = new Paint();
        handScrewPaint.setAntiAlias(true);
        handScrewPaint.setColor(0xff493f3c);
        handScrewPaint.setStyle(Paint.Style.FILL);
    }

    private void regenerateBackground()
    {
        float f = getWidth();
        float f1 = getHeight();
        try {
            backgroundBitmap = Bitmap.createBitmap((int) f, (int) f1, Bitmap.Config.ARGB_8888);
        }catch (Exception e)
        {
            Log.e(TAG,"regenrate background error---");
        }
        Canvas canvas = new Canvas(backgroundBitmap);
        float f2 = getWidth();
        canvas.save(1);
        canvas.scale(f2, f2);

        drawShadedCircle(canvas);
        /*drawScale(canvas);
        drawScale(canvas);
        drawScale(canvas);*/
       drawHand(canvas, 315.0f);
        canvas.restore();
    }

    void drawHand(Canvas canvas, float f)
    {
        Paint handPaint = new Paint();
        handPaint.setAntiAlias(true);
        handPaint.setColor(0xff392f2c);
        //handPaint.setShadowLayer(0.01f, -0.005f, -0.005f, 0x7f000000);
        handPaint.setStyle(Paint.Style.FILL);

        Path handPath = new Path();

        handPath.moveTo(0.5f, 0.3f + 0.2f);

        /*handPath.lineTo(0.5f,0.2f);
        handPath.lineTo(0.5f + 0.010f , 0.2f );
        handPath.lineTo(0.5f + 0.010f , 0.2f );
        handPath.lineTo(0.5f + 0.010f , 0.2f );
        handPath.lineTo(0.5f + 0.010f , 0.2f );*/

        /*handPath.lineTo(0.5f + 0.010f, 0.3f + 0.2f - 0.007f);
        handPath.lineTo(0.5f + 0.002f, 0.3f - 0.28f);
        handPath.lineTo(0.5f + 0.010f, 0.3f + 0.2f - 0.007f);
        handPath.lineTo(0.5f + 0.002f, 0.3f - 0.28f);
        handPath.lineTo(0.5f, 0.3f - 0.2f);
 */       handPath.addCircle(0.5f, 0.2f, 0.035f, Path.Direction.CW);

        Paint handScrewPaint = new Paint();
        handScrewPaint.setAntiAlias(true);
        handScrewPaint.setColor(0xff493f3c);
        handScrewPaint.setStyle(Paint.Style.FILL);

        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        // Exercise: the hand should be in the correct angle.
        //canvas.rotate(angle, 0.5f, 0.5f);
        canvas.drawPath(handPath, handPaint);
        //canvas.drawCircle(0.5f, 0.5f, 0.01f, handScrewPaint);
        canvas.restore();
    }


    void drawScale(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#F0715E"));
        paint.setStrokeWidth(0.005f);
        paint.setAntiAlias(true);
        paint.setTextSize(0.030f);
        paint.setTypeface(Typeface.SANS_SERIF);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setLinearText(true);

        int degreesPerNick = 6;
        //canvas.drawCircle(0.5f, 0.5f, 0.35f, paint);

        int total = 360/degreesPerNick;
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        for (int i = 0; i < total; ++i) {
            float y1 = 0.15f;
            float y2 = y1 + 0.020f;

            canvas.drawLine(0.5f, y1, 0.5f, y2, paint);

            if (i % 5 == 0) {
                int value = i*degreesPerNick/6;
                String valueString = Integer.toString(value);
                canvas.drawText(valueString, 0.5f, y2 + 0.025f, paint);
            }

            // Exercise: Replace the following statement with the correct code
            canvas.rotate(degreesPerNick, 0.5f, 0.5f);
        }

        canvas.restore();
    }

    void drawShadedCircle(Canvas canvas)
    {
        int width = canvas.getWidth();

        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(Color.parseColor("#323232"));
        canvas.drawCircle(0.5f, 0.5f, 0.23f, paint);

    }

    void drawTitle(Canvas canvas)
    {
        canvas.getWidth();
        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 102, 0));
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(0.05F);
        paint.setTextScaleX(0.8F);
        Path path = new Path();
        path.addArc(new RectF(0.3F, 0.3F, 0.7F, 0.7F), -180F, -180F);
        canvas.drawTextOnPath(mTitle, path, 0.0F, 0.0F, paint);
    }

    protected void onDraw(Canvas canvas)
    {
        int i = getWidth();
        int j = getHeight();
        if (backgroundBitmap != null)
        {
            Matrix matrix = new Matrix();
            matrix.setRotate(currentValue, i / 2, j / 2);
            canvas.drawBitmap(backgroundBitmap, matrix, backgroundPaint);
        }
 }

    protected void onMeasure(int i, int j)
    {
        int k = MeasureSpec.getMode(i);
        int l = MeasureSpec.getSize(i);
        int i1 = MeasureSpec.getMode(j);
        int j1 = MeasureSpec.getSize(j);
        int k1 = Math.min(chooseDimension(k, l), chooseDimension(i1, j1));
        setMeasuredDimension(k1, k1);
    }

    protected void onSizeChanged(int i, int j, int k, int l)
    {
        regenerateBackground();
    }

    public boolean onTouchEvent(MotionEvent motionevent) {
        float f = getTheta(motionevent.getX(), motionevent.getY());
        switch (MotionEvent.ACTION_MASK & motionevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                theta_old = f;
                break;
            case MotionEvent.ACTION_MOVE:
                float f1 = f - theta_old;
                theta_old = f;
                int i;
                if (f1 > 0.0F) {
                    i = 1;
                } else {
                    i = -1;
                }
                currentValue = currentValue + (float) (i * 3);
                if (currentValue < 0.0F) {
                    currentValue = 360F + currentValue;
                }
                currentValue = currentValue - (float) (360 * (int) (currentValue / 360F));
                invalidate();
                if (listener != null)
                    listener.onRotate((int) currentValue);
                break;
        }
        return true;
    }

    public void setAngle(float f)
    {
        currentValue = f;
        invalidate();
    }

    public void setOnRotateListener(OnRotateListener onrotatelistener)
    {
        listener = onrotatelistener;
    }

}




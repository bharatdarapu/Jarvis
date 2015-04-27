package syr.labs.jarvis.app.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class KnobView extends ImageView
{
    public static interface RotaryKnobListener
    {

        public abstract void onKnobChanged(int i);
    }


    private final int DEGREEPERLIGHT;
    private float angle;
    private Bitmap background;
    private Paint lightOffPaint;
    private Paint lightOnPaint;
    private RotaryKnobListener listener;
    private Paint mPaint;
    private float offAngle;
    private Paint rimCirclePaint;
    private Paint rimPaint;
    private RectF rimRect;
    private RectF rimRect_inner;
    private float startAngle;
    private float stopAngle;
    private float theta_old;
    //private String dialColor;

    public KnobView(Context context)
    {
        super(context);
        theta_old = 0.0F;
        startAngle = 60F;
        stopAngle = 300F;
        offAngle = startAngle - 15F;
        angle = offAngle;
        DEGREEPERLIGHT = 15;
        initialize();
    }

    public KnobView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        theta_old = 0.0F;
        startAngle = 60F;
        stopAngle = 300F;
        //dialColor = attributeset.getAttributeValue(5).toString();
        offAngle = startAngle - 15F;
        angle = offAngle;
        DEGREEPERLIGHT = 15;
        initialize();
    }

    public KnobView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        theta_old = 0.0F;
        startAngle = 60F;
        stopAngle = 300F;
        offAngle = startAngle - 15F;
        angle = offAngle;
        //dialColor = attributeset.getAttributeValue(5).toString();
        DEGREEPERLIGHT = 15;
        initialize();
    }

    private int chooseDimension(int i, int j, int k)
    {
        if (i == MeasureSpec.EXACTLY || i == MeasureSpec.AT_MOST)
        {
            k = j;
        }
        return k;
    }

    private void drawIndicator(Canvas canvas)
    {
        canvas.save(1);
        canvas.rotate(0.0F, 0.5F, 0.5F);
        canvas.drawLine(0.5F, 0.78F, 0.5F, 0.73F, lightOnPaint);
        canvas.restore();
    }

    void drawScale(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setStyle(android.graphics.Paint.Style.STROKE);
        paint.setColor(0x9f004d0f);
        paint.setStrokeWidth(0.003F);
        paint.setAntiAlias(true);
        paint.setTextSize(0.05F);
        paint.setTypeface(Typeface.SANS_SERIF);
        paint.setTextAlign(android.graphics.Paint.Align.CENTER);
        paint.setLinearText(true);
        canvas.drawCircle(0.5F, 0.5F, 0.35F, paint);
        int i = 60 / 3;
        canvas.save(1);
        for (int j = 0; j < i; j++)
        {
            float f = 0.15F + 0.02F;
            int _tmp = j * 3;
            canvas.drawLine(0.5F, 0.15F, 0.5F, f, paint);
            if (j % 5 == 0)
            {
                canvas.drawText(Integer.toString(j * 3), 0.5F, f + 0.025F, paint);
            }
            canvas.rotate(6, 0.5F, 0.5F);
        }

        canvas.restore();
    }



    private void drawLights(Canvas canvas)
    {
        canvas.save(1);
        int i = (int)(stopAngle - startAngle) / 15;
        canvas.rotate(startAngle, 0.5F, 0.5F);
        float f = startAngle;
        int j = 0;
        while (j <= i)
        {
            if (angle >= f)
            {
                canvas.drawLine(0.5F, 0.9F, 0.5F, 0.85F, lightOnPaint);
            } else
            {
                canvas.drawLine(0.5F, 0.9F, 0.5F, 0.85F, lightOffPaint);
            }
            canvas.rotate(15F, 0.5F, 0.5F);
            f += 15F;
            j++;
        }
        canvas.restore();
    }

    private void drawRim(Canvas canvas)
    {
        canvas.drawOval(rimRect, rimPaint);
        canvas.drawOval(rimRect_inner, rimCirclePaint);
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

    private void notifyListener(int i)
    {
        if (listener != null)
        {
            listener.onKnobChanged(i);
        }
    }

    private void regenerateBackground()
    {
        float f = getWidth();
        float f1 = getHeight();
        background = Bitmap.createBitmap((int)f, (int)f1, android.graphics.Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(background);
        float f2 = getWidth();
        canvas.scale(f2, f2);
        //canvas.drawBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.shape_green_rectangle), null, rimRect, new Paint());
        drawRim(canvas);
        drawIndicator(canvas);
        drawScale(canvas);
    }

    public void initialize()
    {
        rimRect = new RectF(0.2F, 0.2F, 0.8F, 0.8F);
        rimRect_inner = new RectF(0.2F + 0.02F, 0.2F + 0.02F, 0.8F - 0.02F, 0.8F - 0.02F);
        rimPaint = new Paint();
        rimPaint.setFlags(1);
        int ai[] = new int[9];
        ai[0] = Color.rgb(192, 197, 192);
        ai[1] = Color.rgb(240, 245, 240);
        ai[2] = Color.rgb(192, 197, 192);
        ai[3] = Color.rgb(64, 65, 64);
        ai[4] = Color.rgb(192, 197, 192);
        ai[5] = Color.rgb(240, 245, 240);
        ai[6] = Color.rgb(192, 197, 192);
        ai[7] = Color.rgb(64, 65, 64);
        ai[8] = Color.rgb(192, 197, 192);
        /*new float[] {
                0.0F, 0.125F, 0.25F, 0.375F, 0.5F, 0.625F, 0.75F, 0.875F, 1.0F
        };*/
        rimPaint.setShader(new LinearGradient(0.4F, 0.0F, 0.6F, 1.0F, Color.rgb(240, 245, 240), Color.rgb(48, 49, 48), android.graphics.Shader.TileMode.CLAMP));
        rimCirclePaint = new Paint();
        rimCirclePaint.setAntiAlias(true);
        rimCirclePaint.setFlags(1);
        int ai1[] = new int[9];
        ai1[0] = Color.rgb(176, 181, 176);
        ai1[1] = Color.rgb(224, 229, 224);
        ai1[2] = Color.rgb(176, 181, 176);
        ai1[3] = Color.rgb(64, 65, 64);
        ai1[4] = Color.rgb(176, 181, 176);
        ai1[5] = Color.rgb(224, 229, 224);
        ai1[6] = Color.rgb(176, 181, 176);
        ai1[7] = Color.rgb(64, 65, 64);
        ai1[8] = Color.rgb(176, 181, 176);
        float af[] = {
                0.0F, 0.125F, 0.25F, 0.375F, 0.5F, 0.625F, 0.75F, 0.875F, 1.0F
        };
        rimCirclePaint.setShader(new SweepGradient(0.5F, 0.5F, ai1, af));
        lightOnPaint = new Paint();
        lightOnPaint.setStyle(android.graphics.Paint.Style.STROKE);

        lightOnPaint.setColor(Color.parseColor("#ff7c00"));//Dial Color

        /*if(dialColor.equals("#FFFFFF"))
            lightOnPaint.setColor(Color.GREEN);//Dial Color
        else if(dialColor.equals("#ff7c00"))
            lightOnPaint.setColor(Color.parseColor("#ff7c00"));//Dial Color
        else
            lightOnPaint.setColor(Color.CYAN);//Dial Color*/

        lightOnPaint.setStrokeWidth(0.02F);
        lightOnPaint.setAntiAlias(true);
        lightOnPaint.setTextSize(0.025F);
        lightOnPaint.setTypeface(Typeface.SANS_SERIF);
        lightOnPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
        lightOffPaint = new Paint();
        lightOffPaint.setStyle(android.graphics.Paint.Style.STROKE);
        lightOffPaint.setColor(0xff888888);
        lightOffPaint.setStrokeWidth(0.02F);
        lightOffPaint.setAntiAlias(true);
        lightOffPaint.setTextSize(0.025F);
        lightOffPaint.setTypeface(Typeface.SANS_SERIF);
        lightOffPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(android.graphics.Paint.Style.FILL);
        mPaint.setColor(0xff000000);
        mPaint.setStrokeWidth(2.0F);
        mPaint.setTextSize(40F);
        mPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
        mPaint.setLinearText(true);
    }

    protected void onDraw(Canvas canvas)
    {
        int i = getWidth();
        int j = getHeight();
        mPaint.setTextSize(2.0F * (0.02F * (float)i));
        canvas.drawText("Off", 0.25F * (float)i, 0.78F * (float)j, mPaint);
        Matrix matrix = new Matrix();
        matrix.setRotate(angle, i / 2, j / 2);
        canvas.drawBitmap(background, matrix, new Paint());
        canvas.scale(i, i);
        drawLights(canvas);
        super.onDraw(canvas);
    }

    protected void onMeasure(int i, int j)
    {
        int k = android.view.View.MeasureSpec.getMode(i);
        int l = android.view.View.MeasureSpec.getSize(i);
        int i1 = android.view.View.MeasureSpec.getMode(j);
        int j1 = android.view.View.MeasureSpec.getSize(j);
        int k1 = Math.min(chooseDimension(k, l, 800), chooseDimension(i1, j1, 800));
        Log.d("size", Integer.toString(k1));
        setMeasuredDimension(k1, k1);
    }

    protected void onSizeChanged(int i, int j, int k, int l)
    {
        regenerateBackground();
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        float f = getTheta(motionevent.getX(), motionevent.getY());
        float f1;
        switch (0xff & motionevent.getAction())
        {
            case 1: // '\001'
            default:
                return true;

            case 0: // '\0'
                theta_old = f;
                return true;

            case 2: // '\002'
                f1 = f - theta_old;
                break;
        }
        theta_old = f;
        int i;
        if (f1 > 0.0F)
        {
            i = 1;
        } else
        {
            i = -1;
        }
        angle = angle + (float)(i * 3);
        if (angle < 0.0F)
        {
            angle = 360F + angle;
        }
        angle = angle - (float)(360 * (int)(angle / 360F));
        angle = Math.min(angle, stopAngle);
        angle = Math.max(angle, offAngle);
        Log.d("Angle", Float.toString(angle));
        invalidate();
        notifyListener((int)angle);
        return true;
    }

    public void setKnobListener(RotaryKnobListener rotaryknoblistener)
    {
        listener = rotaryknoblistener;
    }

    public void setAngle(float f)
    {
        angle = f;
        invalidate();
    }

}
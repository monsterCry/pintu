package picture.dream.timefluid.pintu;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

public class TestView extends SurfaceView implements SurfaceHolder.Callback ,Runnable{

    private SurfaceHolder holder;

    public TestView(Context context) {
        super(context);
        init();
    }

    Paint paint = new Paint();
    TestSpirite sky;
    void init() {
        holder = getHolder();
        holder.addCallback(this);
        setZOrderOnTop(true);
        sky =  new SkyLineAnim(20);
        TimerThread.getInstance()
                .addTask("back", this);
        holder.setFormat(PixelFormat.TRANSLUCENT);
    }


    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i("surfaceCreated", getWidth() +" 线程" + getHeight());
        ((SkyLineAnim)sky).init(getWidth(),getHeight());
        synchronized (this) {
            isDesroy = false;
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.i("surfaceDestroyed","线程");
        synchronized (this) {
            isDesroy = true;
        }

    }



    boolean isDesroy = false;
    private long preTime = System.currentTimeMillis();
    private long curTime;
    long det;

    @Override
    public void run() {
        synchronized (this) {
            if(isDesroy) {
                return;
            }
            curTime = System.currentTimeMillis();
            det = curTime - preTime;

            int fps = 0;
            if(det >0 ) {
                fps = (int)(1000 / det);
            }
            preTime = curTime;
            Canvas canvas = holder.lockCanvas();
            if(canvas != null) {
                try {
                    paint.setTextSize(40);
                    canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
                    paint.setColor(Color.rgb(50,50,50));
                    sky.Paint(canvas,paint);
                    paintGroop(canvas,paint);
                    canvas.drawText(fps + "/s",50,50,paint);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    holder.unlockCanvasAndPost(canvas);
                }
                sky.Update();
            } else {
                Log.d("mmmp","=====================================");
            }

        }
    }

    Path groop = new Path();
    float d1 = -500,d2 = 300, d3 = -200;
    void paintGroop(Canvas canvas,Paint paint) {
        float w = getWidth();
        float h = getHeight();
        groop.reset();
        groop.moveTo(-50,h);
        groop.cubicTo(w/4,h+d1,w/2 ,h+d2,w+50 ,h + d3);
        groop.lineTo(w+50,h);
        groop.lineTo(-50,h);
        groop.close();
        paint.setColor(Color.rgb(60,179,113));
        canvas.drawPath(groop,paint);
    }


}

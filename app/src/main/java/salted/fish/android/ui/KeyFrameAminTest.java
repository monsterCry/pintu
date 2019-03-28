package salted.fish.android.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import picture.dream.timefluid.pintu.TestSpirite;

public class KeyFrameAminTest extends TestSpirite {

    private float[][] keyPoints = {
            {10,10,0},
            {500,800,1},
            {800,80,3.5f},
            {800,1500,7.5f}
    };


    float x = 10;
    float y= 10;

    @Override
    public void Paint(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        canvas.drawCircle(keyPoints[0][0],keyPoints[0][1],10,paint);
        for (int i = 1; i < keyPoints.length ; i++){
            canvas.drawLine(keyPoints[i][0],keyPoints[i][1],keyPoints[i - 1][0],keyPoints[i - 1][1],paint);
            canvas.drawCircle(keyPoints[i][0],keyPoints[i][1],10,paint);
        }
        paint.setColor(Color.RED);
        canvas.drawCircle(x,y,30,paint);
    }

    long pre = System.currentTimeMillis();
    long cur = pre;
    int curFrame = 0;
    float animDet = 0;
    float fps = 1.0f / 60.0f;
    @Override
    public void Update() {
        cur = System.currentTimeMillis();
        float det = (cur - pre) / 1000.f;
        if(det < fps) {
            return;
        }
        animDet += det;
        pre = cur;

        if(curFrame == keyPoints.length - 1 ) {
            curFrame = 0;
            animDet = 0;
            x = 10;
            y = 10;
        }
        float detTime = keyPoints[curFrame + 1][2] - keyPoints[curFrame][2];
        float detX = keyPoints[curFrame + 1][0] - keyPoints[curFrame][0];
        float detY = keyPoints[curFrame + 1][1] - keyPoints[curFrame][1];
        x +=  (detX) * det / detTime;
        y +=   detY * det  / detTime;
        if(animDet > keyPoints[curFrame + 1][2]) {
            curFrame++;
            x = keyPoints[curFrame][0];
            y = keyPoints[curFrame][1];
        }
    }
}

package picture.dream.timefluid.pintu;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.Random;

public class SkyLineAnim extends TestSpirite{

    private int dotNum = 20;
    private ArrayList<MoveAbleDot> points;
    private float velocity = 12;
    private int width;
    private int height;
    private float maxDistance = 500;

    public SkyLineAnim(int dotNum) {
        this.dotNum = dotNum;
        points = new ArrayList<>();
    }

    public void Paint(Canvas canvas, Paint paint) {
        for (MoveAbleDot p : points) {
            canvas.drawCircle(p.x,p.y,p.radius,paint);
        }
        for (int i = 0; i < points.size(); i++) {
            for(int j = i + 1; j < points.size(); j++) {
                MoveAbleDot d1 = points.get(i);
                MoveAbleDot d2 = points.get(j);
                float xx = d1.x - d2.x;
                float yy = d1.y - d2.y;
                float dist = (float) Math.sqrt(xx*xx + yy*yy);
                if(dist < maxDistance) {
                    //paint.setAlpha(255);
                    paint.setStrokeWidth(3);
                    paint.setAlpha(255 - (int) (0.51*dist));
                    canvas.drawLine(d1.x,d1.y,d2.x,d2.y,paint);
                }
            }
        }
    }

    Path path = new Path();
    public void init(int w,int h) {
        if(points.size() == dotNum) {
            return;
        }
        width = w;
        height = h;
        for (int i = 0; i < dotNum; i++) {
            points.add(new MoveAbleDot(rom.nextInt(w/2) - w/4,
                    rom.nextInt(h/2) - h/4,
                    rom.nextFloat() - 0.5f,rom.nextFloat() - 0.5f));
        }
    }

    Random rom = new Random(System.currentTimeMillis());
    public void Update() {
        for (MoveAbleDot p : points) {
            p.move(velocity);
            if(p.x > width || p.x < 0 || p.y > height || p.y < 0) {
                p.set(rom.nextInt(height),rom.nextInt(height));
                p.setDir(rom.nextFloat() - 0.5f,rom.nextFloat() - 0.5f);
            }
        }

    }


}

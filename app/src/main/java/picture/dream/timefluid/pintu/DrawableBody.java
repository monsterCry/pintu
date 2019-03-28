package picture.dream.timefluid.pintu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import org.dyn4j.collision.Fixture;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Vector2;

public class DrawableBody extends Body {

    Path path = new Path();
    float bias = 20;
    public void paint(Canvas canvas, Paint paint) {
        canvas.save();
        paint.setColor(Color.RED);
        canvas.translate((float) (getTransform().getTranslationX() * bias),
                (float) (getTransform().getTranslationY() * bias));
        for(Fixture fix : getFixtures()) {
            Polygon convex = (Polygon) fix.getShape();
            Vector2[] vertice = convex.getVertices();
            path.reset();
            path.moveTo((float) vertice[0].x,(float) vertice[0].y);
            for(int  j = 1; j < vertice.length; j++) {
                path.lineTo((float)vertice[j].x,(float)vertice[j].y);
            }
            path.close();

            canvas.drawPath(path,paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            canvas.drawText("王", 0,0,paint);
            paint.setTextSize(50);
        }
        canvas.restore();
    }

}

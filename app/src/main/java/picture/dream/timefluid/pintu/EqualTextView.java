package picture.dream.timefluid.pintu;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class EqualTextView extends TextView {

    private Bitmap img;
    private int realIndex;


    public EqualTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public EqualTextView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

    Rect src = new Rect(0,0,136,136);
    Rect dst = new Rect(0,0,0,0);
    @Override
    public void onDraw(Canvas canvas) {
       super.onDraw(canvas);
       if(img != null){
           src.bottom = img.getHeight();
           src.right = img.getWidth();
           dst.right = getWidth();
           dst.bottom = getHeight();
           Paint paint = getPaint();
           paint.setAlpha(255);
           canvas.drawBitmap(img,src,dst, paint);
       }

    }

    public void setImg(Bitmap m) {
        this.img = m;
    }

    public Bitmap getImg() {
        return img;
    }

    public int getRealIndex(){
        return realIndex;
    }


    public void setRealIndex(int index) {
        realIndex = index;
    }
}


package picture.dream.timefluid.pintu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyImageView extends ImageView {

    private Bitmap backImg;

    public void setBackImg(Bitmap back) {
        backImg = back;
    }

    public Bitmap getBackImg() {
        return backImg;
    }

    public MyImageView(Context context) {
        super(context);
    }

    Paint paint = new Paint();
    Rect src = new Rect(0,0,0,0);
    Rect dst = new Rect(0,0,0,0);

    private int index = 0;
    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        if(backImg != null) {
            src.bottom = backImg.getHeight();
            src.right = backImg.getWidth();
            dst.right = getWidth();
            dst.bottom = getHeight();
            c.drawBitmap(backImg,src,dst,paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

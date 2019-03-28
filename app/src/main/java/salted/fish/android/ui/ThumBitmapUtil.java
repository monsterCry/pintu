package salted.fish.android.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ThumBitmapUtil {

    public static Bitmap getThumFromStream(InputStream inputStream, int dstW, int dstH) throws IOException {
        Bitmap res;
        //BufferedInputStream ins = new BufferedInputStream(inputStream);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        DataInputStream ins = new DataInputStream(inputStream);
        ins.mark(ins.available() + 9);
        Log.d("filesize","" + inputStream.available());
        BitmapFactory.decodeStream(ins, null, options);

        options.inSampleSize = calculateSampleSize(options,dstW,dstH);
        options.inJustDecodeBounds = false;
        ins.reset();
        res = BitmapFactory.decodeStream(ins, null, options);
        ins.close();
        return res;
    };

    public static int calculateSampleSize(BitmapFactory.Options options, int w, int h) {
        int width = options.outWidth;
        int height = options.outHeight;
        int sampleSIze = 1;
        if (height > h || width > w) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / sampleSIze) > h
                    && (halfWidth / sampleSIze) > w) {
                sampleSIze *= 2;
            }
        }
        return sampleSIze;
    }
}

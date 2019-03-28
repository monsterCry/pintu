package picture.dream.timefluid.pintu;

import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;

import salted.fish.android.ui.LoadingDialog;

public class Assets {

    public static SoundPool sundPoll =
            new SoundPool(2, AudioManager.STREAM_MUSIC,0);


    public static Typeface font;

    private static boolean isLoaded = false;

    public static void load(Context context) {
        if(!isLoaded) {
            loadAudio(context);
            font = Typeface.createFromAsset(
                    context.getAssets(), "fontawesome-webfont.ttf");
        }
    }

    private static void loadAudio(Context context) {
        sundPoll.load(context,R.raw.d0,1);
        sundPoll.load(context,R.raw.d1,1);
        final LoadingDialog dialog = new LoadingDialog(context);
        dialog.show();
        sundPoll.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                if(TimerThread.getInstance() == null) {
                    TimerThread th = new TimerThread();
                    th.setDaemon(true);
                    TimerThread.setInstance(th);
                    th.start();
                }
                dialog.dismiss();
            }
        });
    }

    public static void release() {
        font = null;
        sundPoll.release();
    }
}

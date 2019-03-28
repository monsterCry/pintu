package salted.fish.android.ui;

import android.util.Log;

public class ClickCheckUtils {

    private static long lastClickTime;
    private static long delyTime = 1000;

    public static boolean isLastClick() {
        long curClick = System.currentTimeMillis();
        long det = curClick - lastClickTime;
        Log.d("clicktime",det + "");
        lastClickTime = curClick;
        if(det < delyTime) {
            return true;
        }

        return false;
    }
}

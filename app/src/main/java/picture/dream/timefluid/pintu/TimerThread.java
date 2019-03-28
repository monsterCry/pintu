package picture.dream.timefluid.pintu;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class TimerThread extends Thread {

    private boolean isStop = true;
    private boolean isPause = false;
    private Object lock = new Object();

    private Map<String,Runnable> tasks = new HashMap<>();

    public TimerThread() {
        setName("TimerThread");
    }

    int fps = 16;

    public void run() {
        try {
            long lastTime;
            long curTime;

            long det;
            isStop = false;
            while (!isStop) {
                onPause();
               // Log.d("thread","runing" + tasks.size());
                curTime = System.currentTimeMillis();
                synchronized (tasks) {
                    for (Runnable r :tasks.values()) {
                        r.run();
                    }
                }
                lastTime = System.currentTimeMillis();
                det = curTime - lastTime;
                if(det < fps) {
                    Thread.sleep(fps - det);
                }

            }
            isStop = true;
            instance = null;
        } catch (Exception w) {
            w.printStackTrace();
        }

    }

    public void makeStop() {
        this.isStop = true;
    }

    public void addTask(String name,Runnable r) {
        synchronized (tasks) {
            tasks.put(name,r);
            Log.d("task Add", "线程");
       }
    }

    public void removeTask(String name) {
        synchronized (tasks) {
            if(tasks.containsKey(name)) {
                tasks.remove(name);
                Log.d("task Remove", "线程");
            }
        }
    }


    public boolean isStopted() {
        return isStop;
    }

    private static TimerThread instance;

    public static void setInstance(TimerThread th) {
        instance = th;
    }

    public static TimerThread getInstance() {
        return instance;
    }

    public void pause() {
        isPause = true;
        Log.d("thread-pause", ComonVariable.Showing_Activity_Count + "");
    }

    private void onPause() throws Exception {
        synchronized (lock) {
            while (isPause) {
                lock.wait();
            }
        }
    }

    public void reSume() {
        synchronized (lock) {
            isPause = false;
            lock.notifyAll();
        }
    }
}

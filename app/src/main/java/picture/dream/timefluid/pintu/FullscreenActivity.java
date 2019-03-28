package picture.dream.timefluid.pintu;


import android.app.Activity;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.GridView;
import android.widget.TextView;



/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        setUpButtons();
        try {
            Intent intent = this.getIntent();
            int size = Integer.parseInt(intent.getStringExtra("size"));
            int type = intent.getIntExtra("type", 1);
            String fileName = intent.getStringExtra("fileName");

            GridView gridView = this.findViewById(R.id.gridMain);
            gridView.setNumColumns(size);
            game = new Game(this,size,fileName,type);
            SimpleGridAdapter mProvinceAdapter = new SimpleGridAdapter(this, game.getSubPic(),size);
            gridView.setAdapter(mProvinceAdapter);

            TimerThread th = TimerThread.getInstance();

            final long preTime = System.currentTimeMillis();
            final Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(msg.what == 1) {
                        long curTime = System.currentTimeMillis();
                        long det = (curTime - preTime) / 1000;
                        int min = (int)(det / 60);
                        int mil = (int) (det - min * 60);
                        ((TextView)findViewById(R.id.moveTime))
                                .setText(min + ":" + mil);

                    }
                }
            };
            th.addTask("timer", new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(1);
                }
            });
        } catch (Exception e ){
            e.printStackTrace();
        }

    }


    private Game game;
    private void setUpButtons() {
        Button bt1 = findViewById(R.id.reset);
        bt1.setTypeface(Assets.font);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.reSet();
            }
        });

        Button bt2 = findViewById(R.id.backto);
        bt2.setTypeface(Assets.font);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        game.stop();
        Log.d("destory task","===");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("thread","full-start" + ComonVariable.Showing_Activity_Count);
        ComonVariable.Showing_Activity_Count++;
        if(ComonVariable.Showing_Activity_Count > 0) {
            TimerThread.getInstance().reSume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("thread","full-stop" + ComonVariable.Showing_Activity_Count);
        ComonVariable.Showing_Activity_Count--;
        if(ComonVariable.Showing_Activity_Count <= 0) {
            TimerThread.getInstance().pause();
        }
    }
}

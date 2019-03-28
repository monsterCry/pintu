package picture.dream.timefluid.pintu;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import salted.fish.android.ui.ClickCheckUtils;
import salted.fish.android.ui.LoadingDialog;
import salted.fish.android.ui.ThumBitmapUtil;

public class ChoosePictureActivity extends Activity {

    private ArrayList<MyImageView> buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_picture);
        Assets.load(this);
        loadImg();
    }

    ListAdapter adp = new ListAdapter(buttons);
    private void loadImg() {
        GridView layout = findViewById(R.id.gridPic);
        AssetManager mgr = getAssets();
        try {
            //新增按钮
            MyImageView bt = new MyImageView(this);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ClickCheckUtils.isLastClick()) {
                        return;
                    }
                    //打开相册
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                    intent.setType("image/*");
                    startActivityForResult(intent, 8);
                }
            });
            Bitmap addImg = BitmapFactory
                    .decodeResource(getResources(),R.drawable.add);
            bt.setBackImg(addImg);
            buttons.add(bt);


            //asset文件夹
            String[] files = mgr.list("img");
            setGridItem(files, null, ComonVariable.TYPE_ASSET);

            //data文件夹
            File fileDir = this.getFilesDir();
            files = fileDir.list();
            setGridItem(files,listener,ComonVariable.TYPE_FILE);

            //设置Adapter
            layout.setAdapter(adp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Uri imageUri = null ;
    private File dst = null;

    private  DeleteTouchListener listener = new DeleteTouchListener(this);

    //获取本地图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("================",data + "" + requestCode + "&&" + resultCode);
        if (resultCode == RESULT_OK) {
            try {
                if (requestCode == Crop.REQUEST_CROP) {
                    dealCropImg();
                }else {
                    dst = new File(getFilesDir().getPath() ,"JZ" + UUID.randomUUID()
                            .toString() + ".jpg");
                    imageUri = Uri.fromFile(dst);
                    Crop.of(data.getData(), imageUri)
                            .asSquare().start(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void dealCropImg() throws Exception{
        Bitmap image =  BitmapFactory.decodeStream(
                getContentResolver().openInputStream(imageUri));
        GridView layout = findViewById(R.id.gridPic);
        MyImageView bt = new MyImageView(this);
        bt.setBackImg(image);
        bt.setTag(dst.getName());
        bt.setOnLongClickListener(listener);
        buttons.add(bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDialog dialog = new SimpleDialog(view.getContext());
                dialog.setFileName(view.getTag() + "");
                dialog.setType(ComonVariable.TYPE_FILE);
                dialog.show();
            }
        });
        layout.setAdapter(adp);
        dst = null;
        imageUri = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(TimerThread.getInstance() != null){
            TimerThread.getInstance()
                    .makeStop();
            TimerThread.getInstance().removeTask("timer");
            TimerThread.getInstance().removeTask("back");

            TimerThread.setInstance(null);
        }

        Assets.release();

    }

    @Override
    protected void onStart() {
        Log.d("thread","choose-start" + ComonVariable.Showing_Activity_Count);
        super.onStart();
        ComonVariable.Showing_Activity_Count++;
        if(TimerThread.getInstance() != null && ComonVariable.Showing_Activity_Count > 0 && !TimerThread.getInstance().isStopted()) {
            TimerThread
                    .getInstance()
                    .reSume();
        }
    }

    @Override
    protected void onStop() {
        Log.d("thread","choose-stop" + ComonVariable.Showing_Activity_Count);
        super.onStop();
        ComonVariable.Showing_Activity_Count--;
        if(TimerThread.getInstance() !=null && ComonVariable.Showing_Activity_Count <= 0) {
            TimerThread.getInstance().pause();
        }
    }


    public void removeView(View view) {
        buttons.remove(view);
        GridView lay = (GridView) findViewById(R.id.gridPic);
        lay.setAdapter(adp);
    }

    //初始化网格
    private void setGridItem(String[] files,
                             View.OnLongClickListener l, final int type) throws Exception {
        AssetManager mgr = getAssets();
        for(String name:files) {
            if(type == ComonVariable.TYPE_FILE && !name.startsWith("JZ")) {
                continue;
            }
            MyImageView bt = new MyImageView(this);
            InputStream inputStream = null;
            if(type == ComonVariable.TYPE_ASSET) {
                inputStream = mgr
                        .open("img/" + name);
            } else {
                inputStream = this.openFileInput(name);
            }

            Bitmap bitmap = ThumBitmapUtil.getThumFromStream(inputStream,
                    ComonVariable.THUM_PIC_SIZE,ComonVariable.THUM_PIC_SIZE);
            bt.setBackImg(bitmap);
            bt.setTag(name);
            buttons.add(bt);
            if(l != null) {
                bt.setOnLongClickListener(l);
            }
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ClickCheckUtils.isLastClick()) {
                        return;
                    }
                    SimpleDialog dialog = new SimpleDialog(view.getContext());
                    dialog.setFileName(view.getTag() + "");
                    dialog.setType(type);
                    dialog.show();
                }
            });
            inputStream.close();
        }
    }
}

package picture.dream.timefluid.pintu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.TextView;


import org.dyn4j.collision.Bounds;
import org.dyn4j.collision.Collidable;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import java.io.InputStream;
import java.util.Random;

public class Game implements View.OnClickListener {

    private EqualTextView subPic[][] = null;
    private int moveCount = 0;
    private int size = 0;
    private int resourceType;
    private String fileName;
    private Activity context;

    private Bitmap textures[][] = null;
    private EqualTextView curText = null;



    public Game(Activity c, int size, String fileName,int type) {
        this.size = size;
        this.fileName = fileName;
        resourceType = type;
        context = c;

        init();
    }
    private void init() {
        subPic = new EqualTextView[size][size];
        textures = new Bitmap[size][size];
        loadImage();
        setUpGrid();
    }

    public void move(View view) {
        int index = (int)view.getTag();
        int x = index % size;
        int y = index / size;
        String text = "" + subPic[y][x].getText();
        Bitmap bitmap = subPic[y][x].getImg();
        int realIndex = subPic[y][x].getRealIndex();
        subPic[y][x].setText(curText.getText());
        subPic[y][x].setImg(curText.getImg());
        subPic[y][x].setRealIndex(curText.getRealIndex());
        curText.setText(text);
        curText.setImg(bitmap);
        curText.setRealIndex(realIndex);
        curText = subPic[y][x];
        moveCount++;
    }


    private void loadImage() {
        try {
            Bitmap img;
            InputStream inputStream = null;
            if(resourceType == ComonVariable.TYPE_FILE) {
                inputStream = context
                        .openFileInput(fileName);
            } else if( resourceType == ComonVariable.TYPE_ASSET) {
                inputStream = context.getAssets().open("img/" + fileName);
            }
            img = BitmapFactory.decodeStream(inputStream);
            int min = img.getWidth() > img.getHeight() ? img.getHeight() : img.getWidth();
            int avg = min / size;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    subPic[i][j] = new EqualTextView(context);
                    Bitmap texture = Bitmap
                            .createBitmap(img, j * avg , i * avg , avg, avg);
                    subPic[i][j].setImg(texture);
                    textures[i][j] = texture;
                }
            }
            img.recycle();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public boolean isFinished() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(!subPic[i][j].getTag().equals(subPic[i][j].getRealIndex())) {
                    return false;
                }
            }
        }
        return true;
    }

    void randomIndex(int size) {
        Random rom = new Random(System.currentTimeMillis());
        int indexArray[] = new int[size * size];
        for(int i = 0; i < indexArray.length; i++) {
            indexArray[i] = i;
        }
        for(int i = 0; i < indexArray.length; i++) {
            int romIndex = rom.nextInt(indexArray.length);
            int romIndex1 = rom.nextInt(indexArray.length);
            if(romIndex == indexArray.length - 1 || romIndex1 == indexArray.length - 1) {
                continue;
            }
            int tmp = indexArray[romIndex1];
            indexArray[romIndex1] = indexArray[romIndex];
            indexArray[romIndex] = tmp;
        }

        for (int i = 0; i < indexArray.length; i++) {
            int tagIndex = indexArray[i];
            int curIndex = (int) curText.getTag();
            int curx = curIndex % size, cury = curIndex / size;
            int x = tagIndex % size, y = tagIndex / size;
            while (true) {
                if(tagIndex == curIndex) {
                    break;
                }
                if(x < curx) {
                    move(subPic[--curx][cury]);
                }
                if(x > curx) {
                    move(subPic[++curx][cury]);
                }
                if(y > cury) {
                    move(subPic[curx][++cury]);
                }
                if(y < cury) {
                    move(subPic[curx][--cury]);
                }
                if(curx == x && cury == y) {
                    break;
                }
            }
        }

    }


    int[][] dirArray = new int[][]{
            {0,1},
            {1,0},
            {-1,0},
            {0,-1}
    };

    private int judge(View view) {
        int index = (int)view.getTag();
        int x = index % size;
        int y = index / size;
        if(curText == subPic[y][x]) {
            return -1;
        }
        for (int i = 0; i < 4; i++) {
            int tx = x + dirArray[i][0];
            int ty = y + dirArray[i][1];
            if(tx >=0 && tx < size && ty >=0 && ty < size && curText == subPic[ty][tx]) {
                return i;
            }
        }

        return -1;
    }

    public void reSet() {
        for(int i = 0; i < size; i++) {
            for(int j = 0 ; j < size ; j++) {
                EqualTextView item = subPic[i][j];
                int index = i * size + j;
                item.setTag(index);
                item.setText("" + index);
                item.setRealIndex(index);
                item.setImg(textures[i][j]);
                if( i == size - 1 && j == size - 1) {
                    curText = item;
                    item.setImg(null);
                }
            }
        }
        randomIndex(size);
        moveCount = 0;
    }

    private void setUpGrid() {
        for(int i = 0; i < size; i++) {
            for(int j = 0 ; j < size ; j++) {
                EqualTextView item = subPic[i][j];
                item.setOnClickListener(this);
                int index = i * size + j;
                item.setTag(index);
                item.setRealIndex(index);
                if( i == size - 1 && j == size - 1) {
                    curText = item;
                    item.setImg(null);
                    textures[i][j].recycle();
                    textures[i][j] = null;
                }
                subPic[i][j] = item;
            }
        }
        randomIndex(size);
        moveCount = 0;
    }


    int id1 = 1, id2 = 2;
    @Override
    public void onClick(View view) {
        int dir = judge(view);
        if(dir < 0) {
            Assets.sundPoll.play(id1, 1, 1, 1, 0, 1);
            return;
        }
        Assets.sundPoll
                .play(id2, 1, 1, 1, 0, 1);
        move(view);
        ((TextView)context.findViewById(R.id.moveCunt))
                .setText("" + moveCount);
        if(isFinished()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("你真厉害，完成了拼图!");
            builder.show();
            TimerThread.getInstance()
                    .removeTask("timer");
        }
    }

    public EqualTextView[][] getSubPic() {
        return subPic;
    }

    public void stop() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if( textures[i][j] != null) {
                    textures[i][j].recycle();
                    textures[i][j] = null;
                    subPic[i][j].setImg(null);
                }
            }
        }
    }
}

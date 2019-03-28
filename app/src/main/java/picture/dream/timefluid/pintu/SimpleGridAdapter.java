package picture.dream.timefluid.pintu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class SimpleGridAdapter extends BaseAdapter {

    private EqualTextView childrens[][];

    private Context context;

    private int size;
    public SimpleGridAdapter(Context c,EqualTextView l[][],int size){
        context = c;
        childrens = l;
        this.size = size;
    }

    @Override
    public int getCount() {
        return size * size;
    }

    @Override
    public Object getItem(int i) {
        int y = i / size;
        int x = i % size;
        return childrens[y][x];
    }

    @Override
    public long getItemId(int i) {
        int y = i / size;
        int x = i % size;
        return childrens[y][x].getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            int y = i / size;
            int x = i % size;
            view = childrens[y][x];
        }
        return view;
    }
}

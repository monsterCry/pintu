package picture.dream.timefluid.pintu;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private ArrayList<MyImageView> children;

    public ListAdapter(ArrayList<MyImageView> list){
        children = list;
    }

    @Override
    public int getCount() {
        return children.size();
    }

    @Override
    public Object getItem(int i) {
        return children.get(i);
    }

    @Override
    public long getItemId(int i) {
        return children.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = children.get(i);
        }
        return view;
    }
}

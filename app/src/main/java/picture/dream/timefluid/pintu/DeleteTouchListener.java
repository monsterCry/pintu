package picture.dream.timefluid.pintu;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import salted.fish.android.ui.ConfirmDialog;

public class DeleteTouchListener implements View.OnLongClickListener, View.OnClickListener {

    private String fileName = null;

    private ChoosePictureActivity activity;

    private View removeView;

    public DeleteTouchListener(ChoosePictureActivity ac) {
        activity = ac;
    }

    @Override
    public boolean onLongClick(View view) {
        ConfirmDialog builder = new ConfirmDialog(view.getContext());
        builder.setOkListener(this);

        fileName = "" + view.getTag();
        removeView = view;
        builder.show();
        return false;
    }

    @Override
    public void onClick(View view) {
        Log.d("===",fileName + "");
        Context context = view.getContext();
        if(!context.deleteFile(fileName)){
            Toast.makeText(context,"删除失败，请重试！",Toast.LENGTH_LONG);
        } else {
            activity.removeView(removeView);
        }
    }
}

package picture.dream.timefluid.pintu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import salted.fish.android.ui.ClickCheckUtils;

public class SimpleDialog extends Dialog {

    private Context parentContext;

    private String fileName;

    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setFileName(String str) {
        fileName = str;
    }

    public String getFileName() {
        return fileName;
    }

    public SimpleDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        parentContext = context;
    }

    public SimpleDialog(@NonNull Context context, int themeResId) {
        super(context, R.style.MyDialog);
        parentContext = context;
    }

    protected SimpleDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        parentContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_choose_dialog_layout);
        initEvent();
    }

    void initEvent() {
        LevelChooseListener listener = new LevelChooseListener(parentContext, this);
        findViewById(R.id.simple).setOnClickListener(listener);
        findViewById(R.id.normal_).setOnClickListener(listener);
        findViewById(R.id.complex).setOnClickListener(listener);
    }
}

class LevelChooseListener implements View.OnClickListener {

    private Context context;
    private SimpleDialog dialog;
    public LevelChooseListener(Context c,SimpleDialog dialog) {
        this.context = c;
        this.dialog = dialog;
    }

    @Override
    public void onClick(View view) {
        if(ClickCheckUtils.isLastClick()) {
            return;
        }
        Intent intent = new Intent(view.getContext(),FullscreenActivity.class);
        intent.putExtra("size",(String) view.getTag());
        intent.putExtra("fileName",dialog.getFileName());
        intent.putExtra("type",dialog.getType());
        view.getContext().startActivity(intent);
    }
}
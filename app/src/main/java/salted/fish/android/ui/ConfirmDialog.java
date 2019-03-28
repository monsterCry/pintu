package salted.fish.android.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import picture.dream.timefluid.pintu.R;

public class ConfirmDialog extends AlertDialog {

    private View.OnClickListener okListener;

    private View.OnClickListener cancelListener;

    public ConfirmDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confir_dialog_layout);
        findViewById(R.id.okbt)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(okListener != null) {
                            okListener.onClick(view);
                        }
                        dismiss();
                    }
                });
        findViewById(R.id.cancelbt)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(cancelListener != null) {
                            cancelListener.onClick(view);
                        }
                        dismiss();
                    }
                });
    }

    public void setOkListener(View.OnClickListener okListener) {
        this.okListener = okListener;
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }
}

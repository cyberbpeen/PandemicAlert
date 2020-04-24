package com.mahato.covid_19pandemic.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.mahato.covid_19pandemic.R;

public class CustomDialog {

    private Activity activity;
    private AlertDialog dialog;
    private ImageView image;
    private TextView title;
    private TextView desc;
    private TextView closeBtn;

    public CustomDialog(Activity activity) {
        this.activity = activity;
        initDialog();
        dismiss();
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_custom_alert, null);
        builder.setView(view);

        image = view.findViewById(R.id.alert_image);
        title = view.findViewById(R.id.alert_title);
        desc = view.findViewById(R.id.alert_content);
        closeBtn = view.findViewById(R.id.alert_close_btn);

        builder.setCancelable(true);
        dialog = builder.create();
    }

    public void showErrorDialog(){
        dialog.show();
        image.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_warning_icon));
        title.setText("Server Error");
        desc.setText("Please restart the application or wi-fi or cellular network. And if that won't contact Administration.");
    }

    public void dismiss() {
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void internetError(){
        dialog.show();
        image.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_signal_wifi_off_icon));
        title.setText("No Internet");
        desc.setText("Internet connection unavailable. Please connect to wi-fi or cellular network.");

    }
}

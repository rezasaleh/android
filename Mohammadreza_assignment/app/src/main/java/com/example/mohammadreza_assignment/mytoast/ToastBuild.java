package com.example.mohammadreza_assignment.mytoast;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastBuild {
    public static Toast displayImageToast(Context context, String text, int imageRes, int duration) {
        ImageToast toastCustom = new ImageToast(context);
        toastCustom.setText(text);
        toastCustom.getToast().setDuration(duration);
        toastCustom.getToast().setGravity(Gravity.CENTER, 0, 0);
        toastCustom.setPhoto(imageRes);
        return toastCustom.getToast();
    }
}

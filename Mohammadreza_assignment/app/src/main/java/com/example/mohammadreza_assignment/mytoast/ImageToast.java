package com.example.mohammadreza_assignment.mytoast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohammadreza_assignment.R;

public class ImageToast {
    Toast toast;

    private Context context;
    public View view;

    public ImageToast(Context context) {
        this.context = context;
        this.toast = new Toast(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = inflater.inflate(R.layout.mytoast, null);

        toast.setView(view);
    }

    public Toast getToast() {
        return toast;
    }

    public View getView() {
        return view;
    }

    public void setText(String text) {
        if (view == null)

            return;

        ((TextView) view.findViewById(R.id.toast_msg)).setText(text);
    }

    public void setPhoto(int imageResId) {
        if (view == null) return;

        ((ImageView) view.findViewById(R.id.toast_photo)).setImageResource(imageResId);
    }
    public void setIcon(int iconResId){
        if(view==null)
            return;
        ((ImageView) view.findViewById(R.id.toast_photo)).setImageResource(iconResId);
    }
}


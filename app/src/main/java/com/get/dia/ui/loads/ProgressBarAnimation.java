package com.get.dia.ui.loads;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.get.dia.MainActivity;

public class ProgressBarAnimation extends Animation {


    private Context context;
    private ProgressBar progressBar;
    private TextView textView;
    private float from,to;


    public ProgressBarAnimation(Context context, ProgressBar progressBar, TextView text, float from, float to ){
        this.context = context;
        this.from = from;
        this.progressBar = progressBar;
        this.textView = text;
        this.to = to;
    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to-from) * interpolatedTime;
        progressBar.setProgress((int)value);
        textView.setText((int)value+" %");
        if(value == to){
                context.startActivity(new Intent(context, MainActivity.class));
        }
    }

}

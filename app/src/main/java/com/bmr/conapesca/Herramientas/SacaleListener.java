package com.bmr.conapesca.Herramientas;

import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.bmr.conapesca.SelectFoto;

public class SacaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    ImageView imageView;
    private float scale =1f;
    public SacaleListener (ImageView imageView){
         this.imageView=imageView;
    }
    public boolean onScale(ScaleGestureDetector detector){
    scale *= detector.getScaleFactor();
    imageView.setScaleX(scale);
    imageView.setScaleY(scale);
    return  true;
    }
}

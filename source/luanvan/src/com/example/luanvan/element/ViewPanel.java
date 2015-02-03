package com.example.luanvan.element;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class ViewPanel extends SurfaceView implements SurfaceHolder.Callback {
	
	
	public ViewPanel(Context context){
		super(context);
		getHolder().addCallback(this);
	}
	public abstract void doDraw(Canvas c);
	
}


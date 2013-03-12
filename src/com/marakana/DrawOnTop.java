package com.marakana;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawOnTop extends View {

	      public DrawOnTop(Context context) {
	              super(context);
	              // TODO Auto-generated constructor stub
	      }

	      @Override
	      protected void onDraw(Canvas canvas) {
	              // TODO Auto-generated method stub

	              Paint paint = new Paint();
	              paint.setStyle(Paint.Style.FILL);
	              paint.setColor(Color.RED);
	              canvas.drawText("Test Text", 100, 100, paint); 

	              super.onDraw(canvas);
	      }
	  }


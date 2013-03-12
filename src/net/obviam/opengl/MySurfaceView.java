package net.obviam.opengl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class MySurfaceView extends GLSurfaceView {

	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	} 
	
	@Override
    protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            canvas.drawText("GL Surface View", 100, 100, paint);

            super.onDraw(canvas);
    }
}

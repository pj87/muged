package com.marakana;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface.OnKeyListener;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.MotionEvent; 
import android.view.KeyEvent;
/*
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import net.obviam.opengl.p03.GlRenderer;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
*/

@SuppressLint("NewApi")
public class CameraActivity extends Activity {
  private static final String TAG = "CameraDemo";
  Preview preview; // <1>
  Button buttonClick; // <2>

  /** The OpenGL view */
  //private GLSurfaceView glSurfaceView;
  
  GLSurfaceView glView; 
  GLArtificialWorldRenderer glArtificialWorldRenderer; 
  float x, y, z; 
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate( Bundle savedInstanceState ) {
	    super.onCreate( savedInstanceState ); 
	    
	    //setContentView(R.layout.main); 
	    // When working with the camera, it's useful to stick to one orientation.
	    setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );
	 
	    // Next, we disable the application's title bar...
	    requestWindowFeature( Window.FEATURE_NO_TITLE );
	    // ...and the notification bar. That way, we can use the full screen.
	    getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
	                            WindowManager.LayoutParams.FLAG_FULLSCREEN );
	 
	    // Now let's create an OpenGL surface.
	    glView = new GLSurfaceView( this );
	    // To see the camera preview, the OpenGL surface has to be created translucently.
	    // See link above.
	    glView.setEGLConfigChooser( 8, 8, 8, 8, 16, 0 );
	    glView.getHolder().setFormat( PixelFormat.TRANSLUCENT);
	    
	    
	    
	    // The renderer will be implemented in a separate class, GLView, which I'll show next.
	    //glView.setRenderer( new GLRotatingCubeRenderer()); 
	    
	    Button myButton = new Button(this); 
	    myButton.setText("OK");
	    
	    LinearLayout ll = new LinearLayout(this); 
	    LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT); 
	    
	    ll.addView(myButton, lp);  
	    
	    glArtificialWorldRenderer =  new GLArtificialWorldRenderer(this); 
	    
	    glView.setRenderer(glArtificialWorldRenderer); 
	    
	    // Now set this as the main view.
	    setContentView( glView );
	 
	    // Now also create a view which contains the camera preview...
	    Preview preview = new Preview( this);
	    // ...and add it, wrapping the full screen size.
	    
	    addContentView( preview, new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT ) );
	}

  /**
	 * Remember to resume the glSurface
	 */
  
	@Override

	protected void onResume() {
		super.onResume();
		glView.onResume();
	}
	/**
	 * Also pause the glSurface
	 */
  @Override
	protected void onPause() {
		super.onPause();
		glView.onPause();
	}
  
  //@Override
	protected void onStart() {
		super.onPause();
		glView.onStartTemporaryDetach(); 
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
    //public boolean dispatchGenericMotionEvent(MotionEvent event) { 
	  float x = event.getX();                              // get x/y coords of touch event
	  float y = event.getY();  
	  
	  int action = event.getActionMasked();          // get code for action
	  float pressure = event.getPressure();                // get pressure and size
	  float pointerSize = event.getSize();
	 
	  String touchEvent;
	switch (action) {                              // let us know which action code shows up
	  
	  case MotionEvent.ACTION_DOWN:
	    touchEvent = "DOWN";
	    
	    int buttonState = event.getButtonState(); 
	    if(buttonState == MotionEvent.BUTTON_PRIMARY) 
	    	this.z++; 
	    	 
	    else if(buttonState == MotionEvent.BUTTON_SECONDARY) 
	    	this.z--;  
	    
	    glArtificialWorldRenderer.onCameraMovementChange(0.0f, 0.0f, this.z);
	    
	    break;
	  case MotionEvent.ACTION_SCROLL: 
		  //x += event.getAxisValue(MotionEvent.AXIS_VSCROLL); 
		  //glArtificialWorldRenderer.onCameraAngleChange(x, 0.0f, 0.0f, 0.0f, 0.0f); 
		  break; 
	  case MotionEvent.ACTION_UP:
	    touchEvent = "UP";
	    pressure = pointerSize = 0.0f;                // when up, set pressure/size to 0 
	  case MotionEvent.ACTION_MOVE:
	    touchEvent = "MOVE";
	    int buttonState1 = event.getButtonState(); 
	    if(buttonState1 == MotionEvent.BUTTON_TERTIARY) 
	    	glArtificialWorldRenderer.onCameraAngleChange(x, y);
	    /*
	    if(buttonState1 == MotionEvent.BUTTON_SECONDARY) 
	    { 
	    	this.x+=0.1; 
	    	glArtificialWorldRenderer.onCameraChange(this.x, 0.0f, 0.0f, 0.0f, 0.0f); 
	    } 
	    else if(buttonState1 == MotionEvent.BUTTON_TERTIARY) 
	    { 
	    	this.x-=0.1; 
	    	glArtificialWorldRenderer.onCameraChange(this.x, 0.0f, 0.0f, 0.0f, 0.0f); 
	    } 
	    break;
	    */
	    
	  //case MotionEvent.AXIS_GAS: 
		  //glArtificialWorldRenderer.onCameraChange(0.0f, 0.0f, 0.0f, x, y); 
	  default:
	    touchEvent = "OTHER (CODE " + action + ")";  // default text on other event 
	  }
	 
	  Log.d(TAG, "Myszka'd " + "x: " + x + "y: " + y); 
	  //glArtificialWorldRenderer.onCameraChange(0.0f, 0.0f, 0.0f, x, y); 
	  
	  return super.dispatchTouchEvent(event);        // pass data along when done!
	} 
	
	/*
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
	if (e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
	    	x++; 
	    	y++; 
	    	z++; 
	    	
	    	glArtificialWorldRenderer.onCameraChange(0.0f, 0.0f, 0.0f, x, y); 
	    }
	    return super.dispatchKeyEvent(e);
	};
	*/
	/*
	public void OnGenericMotion(MotionEvent me) {
		if (me.getToolType(0) == MotionEvent.TOOL_TYPE_MOUSE) { 
			glArtificialWorldRenderer.onCameraChange(0.0f, 0.0f, 0.0f, me.getX(), me.getY()); 
		} 
	} */
  /*
  // Called when shutter is opened
  ShutterCallback shutterCallback = new ShutterCallback() { // <6>
    public void onShutter() {
      Log.d(TAG, "onShutter'd");
    }
  };

  // Handles data for raw picture
  PictureCallback rawCallback = new PictureCallback() { // <7>
    public void onPictureTaken(byte[] data, Camera camera) {
      Log.d(TAG, "onPictureTaken - raw");
    }
  };

  // Handles data for jpeg picture
  PictureCallback jpegCallback = new PictureCallback() { // <8>
    public void onPictureTaken(byte[] data, Camera camera) {
      FileOutputStream outStream = null;
      try {
        // Write to SD Card
        outStream = new FileOutputStream(String.format("/sdcard/%d.jpg",
            System.currentTimeMillis())); // <9>
        outStream.write(data);
        outStream.close();
        Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
      } catch (FileNotFoundException e) { // <10>
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
      }
      Log.d(TAG, "onPictureTaken - jpeg");
    }
  };*/

}

package com.marakana;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.obviam.opengl.MySurfaceView;
import net.obviam.opengl.p03.GlRenderer;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class CameraActivity extends Activity {
  private static final String TAG = "CameraDemo";
  Preview preview; // <1>
  Button buttonClick; // <2>

  /** The OpenGL view */
  //private GLSurfaceView glSurfaceView;
  
  private MySurfaceView glSurfaceView;
  GLSurfaceView glView; 
  /** Called when the activity is first created. */
  @Override
  public void onCreate( Bundle savedInstanceState ) {
	    super.onCreate( savedInstanceState );
	    
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
	    glView.setRenderer( new GLRotatingCubeRenderer()); 
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

package com.marakana;

import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.GLSurfaceView.Renderer; 
import android.opengl.GLU; 

import javax.microedition.khronos.egl.EGLConfig; 
import javax.microedition.khronos.opengles.GL10; 
import android.util.FloatMath; 
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
//TUTAJ PISZEMY 
@SuppressLint("NewApi")
public class GLArtificialWorldRenderer implements Renderer, CameraChangeListener {
	
	private Cube mCube = new Cube(); 
    private float mCubeRotation; 
    private float mCubeZ; 
    Rectangle mRectangle;  
    private int q = 0; 
    boolean isTouched = false; 
    
    //Camera coordinates 
    private float alpha, theta; 
    private float camX, camY, camZ; 

    private Text 		text;		// the square
    
    private Text 		alphaText;	
    private Text		thetaText; 
    private Text		camXText;
    
    private Context 	context;
    
    GLArtificialWorldRenderer(Context context) {
		this.context = context;
		
		// initialise the square
		this.text = new Text(new String("Hello"), 1.0f, 2.0f); 
		
		this.alphaText = new Text(new String("" + this.alpha), 1.0f, 2.0f); 
		this.thetaText = new Text(new String("" + this.theta), 1.0f, 2.0f);
		this.camXText = new Text(new String("" + this.camX), 1.0f, 2.0f); 
		
		mRectangle = new Rectangle();
	}
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    	
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
            
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                  GL10.GL_NICEST);
        
        text.loadGLTexture(gl, this.context);
        alphaText.loadGLTexture(gl, this.context);
        thetaText.loadGLTexture(gl, this.context);
        gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading        
    }

    @Override
    public void onDrawFrame(GL10 gl) { 
    	this.text = new Text(new String("" + mCubeZ), 1.0f, 2.0f); 
    	text.loadGLTexture(gl, this.context); 
    	this.alphaText = new Text(new String("" + this.alpha), 1.0f, 2.0f); 
    	alphaText.loadGLTexture(gl, context); 
    	this.thetaText = new Text(new String("" + this.theta), 1.0f, 2.0f); 
    	thetaText.loadGLTexture(gl, context); 
    	this.camXText = new Text(new String("" + this.camX), 1.0f, 2.0f); 
    	camXText.loadGLTexture(gl, context);
    	
    	
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);  
        gl.glLoadIdentity();
        
        //gl.glTranslatef(0.25f * mCubeZ * FloatMath.sin(mCubeZ), 0.25f * mCubeZ * FloatMath.cos(mCubeZ), mCubeZ);
        
        gl.glRotatef(this.alpha, 0.0f, 1.0f, 0.0f); 
		gl.glRotatef(this.theta, 1.0f, 0.0f, 0.0f);
        
        gl.glTranslatef(this.camX, this.camY, this.camZ); 
        
        gl.glPushMatrix(); 
        gl.glTranslatef(0.0f, 0.0f, -10.0f); 
        mCube.draw(gl); 
        gl.glPopMatrix(); 
        
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 10.0f); 
        mCube.draw(gl);
        gl.glPopMatrix(); 
        
        gl.glPushMatrix();
        gl.glTranslatef(-10.0f, 0.0f, 0.0f); 
        mCube.draw(gl);
        gl.glPopMatrix(); 
        
        gl.glPushMatrix();
        gl.glTranslatef(10.0f, 0.0f, 0.0f); 
        mCube.draw(gl); 
        gl.glPopMatrix(); 
        
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -10.0f, 0.0f); 
        mCube.draw(gl);
        gl.glPopMatrix(); 
        
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 10.0f, 0.0f); 
        mCube.draw(gl);
        gl.glPopMatrix(); 
        
        //gl.glLoadIdentity(); 
        
        //mCubeZ = -10.0f; 
        /*
        mCubeRotation -= 1.0f; 
        
        if(q == 0) 
        	mCubeZ-= 0.25; 
        else 
        	mCubeZ+= 0.25; 
        
        if (mCubeZ < -20.0) 
        	q = 1; 
        if(mCubeZ > -5.0f) 
        	q = 0; 
		*/
		// Reset the Modelview Matrix
		gl.glLoadIdentity();

		// Drawing
		gl.glTranslatef(0.0f, 0.0f, -5.0f);		// move 5 units INTO the screen 
        
		gl.glPushMatrix(); 
		gl.glTranslatef(-2.65f, 2.15f, 0.0f); 
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
		camXText.draw(gl);
        gl.glDisable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW ) 
        gl.glPopMatrix();
		
        gl.glPushMatrix(); 
		gl.glTranslatef(-2.65f, 2.35f, 0.0f); 
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
		thetaText.draw(gl);
        gl.glDisable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW ) 
        gl.glPopMatrix();

        gl.glPushMatrix(); 
		gl.glTranslatef(-2.65f, 2.55f, 0.0f); 
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
		alphaText.draw(gl);
        gl.glDisable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW ) 
        gl.glPopMatrix();
        
        gl.glPushMatrix(); 
		gl.glTranslatef(-2.65f, 2.75f, 0.0f); 
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
		text.draw(gl);
        gl.glDisable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW ) 
        gl.glPopMatrix();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {		
        gl.glViewport(0, 0, width, height); 
        gl.glMatrixMode(GL10.GL_PROJECTION); 
        gl.glLoadIdentity(); 
        GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);  
        gl.glViewport(0, 0, width, height); 

        gl.glMatrixMode(GL10.GL_MODELVIEW); 
        gl.glLoadIdentity(); 
        
        //Sprawdzić jak jest z tą kamerą!!!! 
        //GLU.gluLookAt(gl, camX, camY, camZ, centerX, centerY, centerZ, upX, upY, upZ)
    }

	@Override
	public void onCameraMovementChange(float x, float y, float z) {
		// TODO Auto-generated method stub
		this.camX = x; 
		this.camY = y; 
		this.camZ = z; 
	}

	@Override
	public void onCameraAngleChange(float alpha, float theta) {
		// TODO Auto-generated method stub 
		this.alpha = alpha; 
		this.theta = theta; 
	}
	
}

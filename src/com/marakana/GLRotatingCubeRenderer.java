package com.marakana;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer; 
import android.opengl.GLU; 

import javax.microedition.khronos.egl.EGLConfig; 
import javax.microedition.khronos.opengles.GL10; 
import android.util.FloatMath; 

public class GLRotatingCubeRenderer implements Renderer {
	
	private Cube mCube = new Cube(); 
    private float mCubeRotation; 
    private float mCubeZ; 
    private int q = 0; 
    
    //Camera coordinates 
    private float alpha, theta; 
    private float camX, camY, camZ; 

    private Text 		text;		// the square
    private Context 	context;
    
    GLRotatingCubeRenderer(Context context) {
		this.context = context;
		
		// initialise the square
		this.text = new Text(new String("Hello"), 1.0f, 2.0f);
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
        gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading        
        /*
        gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
        gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
    	*/
    	/*
    	// Load the texture for the square
    	text.loadGLTexture(gl, this.context);
    			
    	gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
    	gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
    	gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 	//Black Background
    	gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
    	gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
    	gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do
    			
    	//Really Nice Perspective Calculations
    	gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 
        */    
    }

    @Override
    public void onDrawFrame(GL10 gl) {
    	this.text = new Text(new String("" + mCubeZ), 1.0f, 2.0f); 
    	text.loadGLTexture(gl, this.context);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);  
        gl.glLoadIdentity();
        
        gl.glTranslatef(0.25f * mCubeZ * FloatMath.sin(mCubeZ), 0.25f * mCubeZ * FloatMath.cos(mCubeZ), mCubeZ);
        gl.glRotatef(mCubeRotation, 1.0f, 1.0f, 1.0f); 
        
        mCube.draw(gl); 
           
        gl.glLoadIdentity(); 
            
        mCubeRotation -= 1.0f; 
        
        if(q == 0) 
        	mCubeZ-= 0.25; 
        else 
        	mCubeZ+= 0.25; 
        
        if (mCubeZ < -20.0) 
        	q = 1; 
        if(mCubeZ > -5.0f) 
        	q = 0; 

		// Reset the Modelview Matrix
		gl.glLoadIdentity();

		// Drawing
		gl.glTranslatef(0.0f, 0.0f, -5.0f);		// move 5 units INTO the screen
    	
		gl.glPushMatrix(); 
		gl.glTranslatef(-2.65f, 2.75f, 0.0f); 
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
		text.draw(gl);
        gl.glDisable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW ) 
        gl.glPopMatrix(); 
        
        

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
    	/*
    	if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}

		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix

		//Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
		gl.glLoadIdentity(); 					//Reset The Modelview Matrix
    	*/
		
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
	
}

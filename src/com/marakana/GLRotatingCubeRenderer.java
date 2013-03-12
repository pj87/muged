package com.marakana;

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

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
            
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                  GL10.GL_NICEST);
            
    }

    @Override
    public void onDrawFrame(GL10 gl) {
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
    }
	
}

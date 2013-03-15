package com.marakana; 

public interface CameraChangeListener { 
	public abstract void onCameraMovementChange(float x, float y, float z); 
	public abstract void onCameraAngleChange(float alpha, float theta);
} 

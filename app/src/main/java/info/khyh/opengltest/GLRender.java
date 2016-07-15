package info.khyh.opengltest;

import info.khyh.opengltest.DrawLibrary.DrawMain;
import info.khyh.opengltest.Library.GLLoader.GLES;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class GLRender implements GLSurfaceView.Renderer {
	private DrawMain d;
	
	public GLRender(Context cxt, DrawMain d)
	{
		GLES.cxt = cxt;
		this.d = d;
	}
	
	public boolean OnTouchEvent(MotionEvent e)
	{return d.OnTouchEvent(e);}
	
	public boolean OnKey(int keyCode, KeyEvent event)
	{return d.OnKey(keyCode, event);}

	public void onPause()
	{d.onPause();}
	
	public void onResume()
	{}
	
	@Override
	public void onDrawFrame(GL10 gl) 
	{d.onDrawFrame((GL11)gl);}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) 
	{d.OnSurfaceChanged((GL11)gl, w, h);}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig eglconfig) 
	{d.onSurfaceCreated((GL11)gl, eglconfig);}

}

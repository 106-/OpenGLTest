package info.khyh.opengltest;

import info.khyh.opengltest.DrawLibrary.MyDrawMain;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;

public class MainActivity extends Activity {
	private GLSurfaceView glview;
	private GLRender glr;
	public final static String Tag = "OpenGLTest";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		glview = new GLSurfaceView(this);
		glr = new GLRender(this, new MyDrawMain(this));
		glview.setRenderer(glr);
		setContentView(glview);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		glview.onResume();
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		glview.onPause();
		glr.onPause();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return glr.OnTouchEvent(event);
	}
	
	private boolean OnKey(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{
			super.onBackPressed();
			return super.onKeyDown(keyCode, event);
		}
		glr.OnKey(keyCode, event);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{return OnKey(keyCode, event);}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{return OnKey(keyCode, event);}
	
}

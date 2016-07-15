package info.khyh.opengltest.DrawLibrary;

import java.nio.IntBuffer;

import info.khyh.opengltest.MainActivity;
import info.khyh.opengltest.Library.Graphics;
import info.khyh.opengltest.Library.Vector2;
import info.khyh.opengltest.Library.Vector3;
import info.khyh.opengltest.Library.GLLoader.GLES;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public abstract class DrawMain {
	//
	public float aspect;
	protected float width, height;
	public Handler handler;
	
	//��ʂ̑傫�� not �𑜓x 
	public final int WINDOW_W = 720,
					WINDOW_H = 1280;
	//public final int WINDOW_W = 540,
	//				WINDOW_H = 960;
	public int SCREEN_WIDTH,
				SCREEN_HEIGHT;
	
	//�^�b�`���W�����
	protected Vector2 TouchPos;
	
	//�J�����p�x�N�g��
	public Vector3 campos;
	public Vector3 upvec;
	public Vector3 focus;
	public Vector3 lightpos;
	
	//�`��p�e�N�X�`��
	public Graphics G;
	
	//�R���e�L�X�g
	protected Context cxt;
	
	private MainActivity act;
	
	public DrawMain(MainActivity act)
	{
		this.cxt = act.getApplicationContext();
		this.act = act;
		this.handler = new Handler();
	}
	
	public abstract boolean OnTouchEvent(MotionEvent e);
	public abstract boolean OnKey(int keyCode, KeyEvent event);	
	public abstract void onPause();
	public abstract void onResume();
	
	public void onDrawFrame(GL11 gl){}

	public void OnSurfaceChanged(GL11 gl, int w, int h)
	{
		this.SCREEN_HEIGHT = h;
		this.SCREEN_WIDTH = w;
		gl.glViewport(0, 0, w, h);
		aspect = (float)w/(float)h;
	}

	public void onSurfaceCreated(GL11 gl, EGLConfig eglconfig)
	{
		Log.v(MainActivity.Tag, "-----*状況開始*-----");
		int data[] = new int[3];
		gl.glGetIntegerv(GL11.GL_MAX_TEXTURE_SIZE, data, 0);
		Log.v(MainActivity.Tag, String.format("最大テクスチャサイズ:%s", data[0]));
		gl.glGetIntegerv(GL11.GL_MAX_VIEWPORT_DIMS, data, 1);
		Log.v(MainActivity.Tag, String.format("ビューポート最大幅:%s", data[1]));
		gl.glGetIntegerv(GL11.GL_MAX_TEXTURE_UNITS, data, 2);
		Log.v(MainActivity.Tag, String.format("最大テクスチャ数:%s", data[2]));
		//gl.glGetIntegerv(GL11.GL_MAX_VIEWPORT_DIMS, data, 0);
		//Log.v(MainActivity.Tag, String.format("最大テクスチャサイズ:%s", data[0]));
		
		GLES.gl = gl;
		//�f�v�X�e�X�g�ƌ����̗L��
		gl.glEnable(GL11.GL_DEPTH_TEST);
		gl.glEnable(GL11.GL_LIGHTING);
		gl.glEnable(GL11.GL_LIGHT0);
		
		//�����F�̎w��
		gl.glLightfv(GL11.GL_LIGHT0, GL11.GL_AMBIENT, 
				new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
		gl.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, 
				new float[]{0.9f, 0.9f, 0.9f, 1.0f}, 0);
		gl.glLightfv(GL11.GL_LIGHT0, GL11.GL_SPECULAR, 
				new float[]{0.5f, 0.5f, 0.5f, 1.0f}, 0);
		
		//�J�����p�x�N�g���̏���
		campos = new Vector3(50f, 100f, 0f);
		focus = new Vector3(0.0f, 0.0f, 0.0f);
		upvec = new Vector3(0.0f, 1.0f, 0.0f);
		lightpos = new Vector3(0,100,0);
		
		TouchPos = new Vector2();
	}
	
	public Vector2 GetTouchPos(){return TouchPos;}
	public void OnGameOver()
	{
		act.setResult(Activity.RESULT_CANCELED);
		act.finish();
	}
	public void OnGameOver(int score)
	{ 
        Intent intent = new Intent();  
        intent.putExtra("SCORE", score);
        Log.v(MainActivity.Tag, "#Activityを破棄します");
        Log.v(MainActivity.Tag, "-----*状況終了*-----");
        Log.v(MainActivity.Tag, "====それではごきげんよう====");
        act.setResult(Activity.RESULT_OK,intent); 
		act.finish();
	}
	public Context GetContext(){return cxt;}
	public Handler GetHandler(){return handler;}
}

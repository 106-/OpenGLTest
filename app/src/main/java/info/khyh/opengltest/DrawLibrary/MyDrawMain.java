package info.khyh.opengltest.DrawLibrary;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;

import info.khyh.opengltest.MainActivity;
import info.khyh.opengltest.GameClass.Stage.MyStage;
import info.khyh.opengltest.GameClass.Stage.Stage;
import info.khyh.opengltest.Library.Graphics;
import info.khyh.opengltest.Library.Quaternion;
import info.khyh.opengltest.Library.Vector3;
import info.khyh.opengltest.Library.VectorUtls;

public class MyDrawMain extends DrawMain{
	//�^�b�`�ʒu�ێ��p
	private float old_x,old_y;
	private float old_dist;
	
	//�X�e�[�W�N���X
	private Stage s;
	
	public MyDrawMain(MainActivity act)
	{
		super(act);
	}
	
	@Override
	public void onDrawFrame(GL11 GL11) 
	{
		super.onDrawFrame(GL11);
		s.Update();
		s.Draw(GL11);
	}
	
	@Override
	public void onPause()
	{s.OnPause();}
	
	@Override
	public void onResume()
	{s.OnResume();}

	@Override
	public boolean OnTouchEvent(MotionEvent e) {
		if(e==null)return false;
		if(s!=null)s.OnTouchEvent(e);
		//MoveCam(e);
		return true;
	}
	
	private void MoveCam(MotionEvent e)
	{
		if( e.getPointerCount() == 1 ) {
			float x = e.getX();
			float y = e.getY();
			TouchPos.x = x;
			TouchPos.y = y;
	 
			switch ( e.getAction() ) {
			case MotionEvent.ACTION_MOVE:				
				float dist_x = x-old_x;
				float dist_y = y-old_y;
				//�c���ւ̈ړ�
				Vector3 v = VectorUtls.cross(campos, new Vector3(0,1,0));
				campos = Quaternion.Quat_Rota(campos, v, Math.toRadians(dist_y));
				//�����ւ̈ړ�
				campos = Quaternion.Quat_Rota(campos, new Vector3(0,1,0), Math.toRadians(dist_x));
				break;
			}
			  
			old_x = x;
			old_y = y;
		}
		else 
		{
			int p1 = e.findPointerIndex(e.getPointerId(0));
			int p2 = e.findPointerIndex(e.getPointerId(1));
	 
			float x = e.getX(p2) - e.getX(p1);
			float y = e.getY(p2) - e.getY(p1);
			
			//�O�t���[���̋����Ƃ̍������߂�
			float distance = (float) Math.sqrt(x * x + y * y);
			float dist_sub = distance - old_dist;
	 
			//��Ƃ�
			if(Math.abs(dist_sub)>10 )
			{
				switch ( e.getAction() ) {
				case MotionEvent.ACTION_MOVE:
					if(dist_sub>0)
					{
						Vector3 v = VectorUtls.add(campos, VectorUtls.multiple(campos.normed(), -1.5f));
						if(v.length()>1.5)
							campos = v;
					}
					else
					{
						campos = VectorUtls.add(campos, VectorUtls.multiple(campos.normed(), 1.5f));
					}
				}
				old_dist = distance;
			}
		}
	}

	@Override
	public void OnSurfaceChanged(GL11 GL11, int w, int h) {
		super.OnSurfaceChanged(GL11, w, h);
		G = new Graphics(WINDOW_W,WINDOW_H);
	}

	@Override
	public void onSurfaceCreated(GL11 GL11, EGLConfig eglconfig) {
		super.onSurfaceCreated(GL11, eglconfig);
		s = new MyStage(this,cxt);
	}
	
	@Override
	public boolean OnKey(int keyCode, KeyEvent event)
	{
		s.OnKey(keyCode, event);
		return false;
	}

}

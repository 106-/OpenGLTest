package info.khyh.opengltest.GameClass.Stage;

import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import info.khyh.opengltest.MainActivity;
import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.DrawLibrary.DrawMain;
import info.khyh.opengltest.GameClass.HitCheck;
import info.khyh.opengltest.GameClass.LoadResource;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Score;
import info.khyh.opengltest.GameClass.BackGround.BackGroundCommander;
import info.khyh.opengltest.GameClass.BackGround.BackGroundManage;
import info.khyh.opengltest.GameClass.BackGround.Concretes.MyBackGroundCommander;
import info.khyh.opengltest.GameClass.Commander.MyCommander;
import info.khyh.opengltest.GameClass.Enemy.EnemyManage;
import info.khyh.opengltest.GameClass.EnemyBullet.EnemyBulletManage;
import info.khyh.opengltest.GameClass.Ornament.OrnamentManage;
import info.khyh.opengltest.GameClass.Player.PlayerManage;
import info.khyh.opengltest.GameClass.PlayerBullet.PlayerBulletManage;
import info.khyh.opengltest.Library.GLLoader.GLES;

public class MyStage extends Stage {
	private MyCommander cm;
	private BackGroundCommander bgc;
	private HitCheck hc;
	private LoadResource ld;

	public MyStage(DrawMain dm, Context cxt) {
		super(dm,cxt);
		GLES.cxt = cxt;
		pm = new PlayerManage();
		em = new EnemyManage();
		pbm = new PlayerBulletManage();
		ebm = new EnemyBulletManage();
		om = new OrnamentManage();
		ld = new LoadResource(cxt);
		bagm = new BackGroundManage();
		mit = new Mediator(pm, pbm, em, ebm, om, bagm, ld, dm, this);
		cm = new MyCommander(mit);
		bgc = new MyBackGroundCommander(mit);
		hc = new HitCheck(pm, pbm, em, ebm);
		s = new Score(5);
		GAMEOVER = false;
		PAUSE = false;
	}

	@Override
	public void Update() {
		fps();
		//System.gc();
		bgc.OnTime(cnt);
		cm.OnTime(cnt);
		bagm.Update();
		pm.Update();
		em.Update();
		pbm.Update();
		ebm.Update();
		om.Update();
		//long start = System.currentTimeMillis();
		hc.Check();
		//long end = System.currentTimeMillis();
		///Log.v(MainActivity.Tag,String.format("HitCheck:%s",end-start));
		cnt++;
	}
	
	@Override
	public void Draw(GL11 gl) {	
		/*
		int error = gl.glGetError();
		if(error!=0)
		{
		+
			Log.e(MainActivity.Tag,"OPENGL ERROR:"+error);
		}
		*/
		DrawFuncs.ClearColor(gl, 0.1f, 0.1f, 0.3f);
		bagm.Draw(gl, mit.GetDrawMain().aspect, mit.GetDrawMain().campos, mit.GetDrawMain().focus, mit.GetDrawMain().upvec, mit.GetDrawMain().lightpos);
		DrawFuncs.Start2DDraw(gl);
		pbm.Draw(gl);
		em.Draw(gl);
		pm.Draw(gl);
		ebm.Draw(gl);
		om.Draw(gl);
		DrawFuncs.End2DDraw(gl);
	}

	@Override
	public void OnTouchEvent(MotionEvent e) {
		pm.OnTouchEvent(e);
	}

	@Override
	public void OnKey(int keyCode, KeyEvent event) {
		pm.OnKey(keyCode, event);
	}

	@Override
	public void OnPause()
	{
		if(mit.GetLoadResource().STAGEBGM.isPlaying())
			{mit.GetLoadResource().STAGEBGM.pause();}
		if(mit.GetLoadResource().BOSSBGM.isPlaying())
			{mit.GetLoadResource().BOSSBGM.pause();}
	}

	@Override
	public void OnResume() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
}

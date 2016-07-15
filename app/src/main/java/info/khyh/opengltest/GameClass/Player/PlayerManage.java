package info.khyh.opengltest.GameClass.Player;

import javax.microedition.khronos.opengles.GL11;

import android.view.KeyEvent;
import android.view.MotionEvent;

import info.khyh.opengltest.GameClass.Hitable;
import info.khyh.opengltest.GameClass.Manage;
import info.khyh.opengltest.Library.Vector2;

public class PlayerManage extends Manage{
	private Player plist[];
	public final int PLAYERMAX = 2;
	
	public PlayerManage() {
		plist = new Player[PLAYERMAX];
	}

	@Override
	public void Update() {
		for(int i=0;i<PLAYERMAX;i++)
			if(plist[i]!=null)
			{
				plist[i].Update();
				if(plist[i].IsLeaveScreen())plist[i]=null;
			}
	}

	@Override
	public void Draw(GL11 gl) {
		for(int i=0;i<PLAYERMAX;i++)
			if(plist[i]!=null)
				plist[i].Draw(gl);
	}

	@Override
	public void Add(Hitable h) {
		int i=CheckList();
		if(i!=-1)
			plist[i]=(Player)h;
	}

	@Override
	protected int CheckList() {
		for(int i=0;i<PLAYERMAX;i++)
			if(plist[i]==null)
				return i;
		return -1;
	}
	
	public void OnTouchEvent(MotionEvent e)
	{
		for(int i=0;i<PLAYERMAX;i++)
		{
			if(plist[i]!=null)
				plist[i].OnTouchEvent(e);
		}
	}
	
	public void OnKey(int keyCode, KeyEvent event)
	{
		for(int i=0;i<PLAYERMAX;i++)
		{
			if(plist[i]!=null)
				plist[i].OnKey(keyCode, event);
		}
	}

	@Override
	public Hitable[] GetList() {
		return plist;
	}
	
	public Vector2 GetPlayerPos()
	{
		for(int i=0;i<plist.length;i++)
			if(plist[i]!=null)
				return new Vector2(plist[i].x,plist[i].y);
		return null;
	}

}

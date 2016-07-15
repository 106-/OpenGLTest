package info.khyh.opengltest.GameClass.PlayerBullet;

import info.khyh.opengltest.GameClass.Hitable;
import info.khyh.opengltest.GameClass.Manage;

import javax.microedition.khronos.opengles.GL11;

public class PlayerBulletManage extends Manage{
	private PlayerBullet pblist[];
	public final int PLAYERBULLETMAX = 300;
	
	public PlayerBulletManage()
	{
		pblist = new PlayerBullet[PLAYERBULLETMAX];
	}

	@Override
	public void Update() {
		for(int i=0;i<PLAYERBULLETMAX;i++)
			if(pblist[i]!=null)
			{
				pblist[i].Update();
				if(pblist[i].IsLeaveScreen())pblist[i]=null;
			}
	}

	@Override
	public void Draw(GL11 gl) {
		for(int i=0;i<PLAYERBULLETMAX;i++)
			if(pblist[i]!=null)
				pblist[i].Draw(gl);
	}

	@Override
	public void Add(Hitable h) {
		int i=CheckList();
		if(i!=-1)
			pblist[i]=(PlayerBullet) h;
	}

	@Override
	protected int CheckList() {
		for(int i=0;i<PLAYERBULLETMAX;i++)
			if(pblist[i]==null)
				return i;
		return -1;
	}

	@Override
	public Hitable[] GetList() {
		return pblist;
	}
	
}

package info.khyh.opengltest.GameClass.Enemy;

import info.khyh.opengltest.GameClass.Hitable;
import info.khyh.opengltest.GameClass.Manage;
import javax.microedition.khronos.opengles.GL11;

public class EnemyManage extends Manage{
	private Enemy[] elist;
	public final int ENEMYMAX = 100;
	
	public EnemyManage()
	{
		elist = new Enemy[ENEMYMAX];
	}

	@Override
	public void Update() {
		for(int i=0;i<ENEMYMAX;i++)
			if(elist[i]!=null)
			{
				elist[i].Update();
				if(elist[i].IsLeaveScreen())elist[i]=null;
			}
	}

	@Override
	public void Draw(GL11 gl) {
		for(int i=0;i<ENEMYMAX;i++)
			if(elist[i]!=null)
				elist[i].Draw(gl);
	}

	@Override
	public void Add(Hitable h) {
		int i=CheckList();
		if(i!=-1)
			elist[i]=(Enemy) h;
	}

	@Override
	protected int CheckList() {
		for(int i=0;i<ENEMYMAX;i++)
			if(elist[i]==null)
				return i;
		return -1;
	}

	@Override
	public Hitable[] GetList() {
		return elist;
	}
}

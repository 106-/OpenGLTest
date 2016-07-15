package info.khyh.opengltest.GameClass.EnemyBullet;

import info.khyh.opengltest.GameClass.Hitable;
import info.khyh.opengltest.GameClass.Manage;
import javax.microedition.khronos.opengles.GL11;

public class EnemyBulletManage extends Manage {
	private EnemyBullet emlist[];
	public final int ENEMYBULLETMAX = 100;
	
	public EnemyBulletManage()
	{
		emlist = new EnemyBullet[ENEMYBULLETMAX];
	}

	@Override
	public void Update() {
		for(int i=0;i<ENEMYBULLETMAX;i++)
			if(emlist[i]!=null)
			{
				emlist[i].Update();
				if(emlist[i].IsLeaveScreen())
					emlist[i]=null;
			}
	}

	@Override
	public void Draw(GL11 gl) {
		for(int i=0;i<ENEMYBULLETMAX;i++)
			if(emlist[i]!=null)
				emlist[i].Draw(gl);
	}

	@Override
	public void Add(Hitable h) {
		int i=CheckList();
		if(i!=-1)
			emlist[i]=(EnemyBullet) h;
	}

	@Override
	protected int CheckList() {
		for(int i=0;i<ENEMYBULLETMAX;i++)
			if(emlist[i]==null)
				return i;
		return -1;
	}

	@Override
	public Hitable[] GetList() {
		return emlist;
	}
	
}

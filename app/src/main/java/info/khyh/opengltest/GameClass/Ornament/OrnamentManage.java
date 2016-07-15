package info.khyh.opengltest.GameClass.Ornament;

import javax.microedition.khronos.opengles.GL11;

import info.khyh.opengltest.GameClass.Hitable;
import info.khyh.opengltest.GameClass.Manage;

public class OrnamentManage extends Manage{
	private final int ORN_MAX = 200;
	private Ornament olist[];

	public OrnamentManage()
	{
		this.olist = new Ornament[ORN_MAX];
	}
	
	@Override
	public void Update() {
		for(int i=0;i<ORN_MAX;i++)
		{
			if(olist[i]==null)continue;
			olist[i].Update();
			if(olist[i].IsOver())olist[i]=null;
		}
	}

	@Override
	public void Draw(GL11 gl) {
		for(int i=0;i<ORN_MAX;i++)
		{
			if(olist[i]==null)continue;
			olist[i].Draw(gl);
		}
	}

	@Override
	public void Add(Hitable h) {}
	public void Add(Ornament or)
	{
		int i = CheckList();
		if(i!=-1)
			olist[i]=or;
	}

	@Override
	protected int CheckList() {
		for(int i=0;i<ORN_MAX;i++)
			if(olist[i]==null)
				return i;
		return -1;
	}

	@Override
	public Hitable[] GetList() {
		return null;
	}

}

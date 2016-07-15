package info.khyh.opengltest.GameClass;

import javax.microedition.khronos.opengles.GL11;

public abstract class Manage {
	public abstract void Update();
	public abstract void Draw(GL11 gl);
	public abstract void Add(Hitable h);
	protected abstract int CheckList();
	public abstract Hitable[] GetList();
}

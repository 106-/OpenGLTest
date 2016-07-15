package info.khyh.opengltest.GameClass.Ornament;

import javax.microedition.khronos.opengles.GL11;

public abstract class Ornament {
	protected float x,y;
	protected int cnt;
	
	public Ornament(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public abstract void Update();
	public abstract void Draw(GL11 gl);
	public abstract boolean IsOver();
	
}

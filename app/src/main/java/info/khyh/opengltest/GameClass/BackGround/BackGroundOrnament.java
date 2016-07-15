package info.khyh.opengltest.GameClass.BackGround;

import javax.microedition.khronos.opengles.GL11;

public abstract class BackGroundOrnament {
	public abstract void Update();
	public abstract void Draw(GL11 gl);
	public abstract boolean RemoveOK();
}

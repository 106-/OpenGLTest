package info.khyh.opengltest.GameClass.BackGround.Concretes;

import javax.microedition.khronos.opengles.GL11;

import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.BackGround.BackGround3DOrnament;
import info.khyh.opengltest.Library.Vector3;

public class MiniClowd extends BackGround3DOrnament{
	private Mediator mit;
	private Vector3 scale;
	
	public MiniClowd(Mediator mit, Vector3 v, Vector3 s)
	{
		this.pos = v;
		this.mit = mit;
		this.scale = s;
	}
	
	@Override
	public void Update() {
		pos.x+=5;
	}

	@Override
	public void Draw(GL11 gl) {
		gl.glEnable(GL11.GL_BLEND);
		gl.glDisable(GL11.GL_DEPTH_TEST);
		gl.glDisable(GL11.GL_LIGHTING);
		gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		mit.GetLoadResource().MINI_CLOWD.scale = scale;
		mit.GetLoadResource().MINI_CLOWD.position = pos;
		mit.GetLoadResource().MINI_CLOWD.draw(gl);
		gl.glEnable(GL11.GL_DEPTH_TEST);
		gl.glEnable(GL11.GL_LIGHTING);
		gl.glDisable(GL11.GL_BLEND);
	}

	@Override
	public boolean RemoveOK() {
		return this.pos.x>400;
	}

}

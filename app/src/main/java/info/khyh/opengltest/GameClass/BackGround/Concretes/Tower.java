package info.khyh.opengltest.GameClass.BackGround.Concretes;

import javax.microedition.khronos.opengles.GL11;

import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.BackGround.BackGround3DOrnament;
import info.khyh.opengltest.Library.Vector3;

public class Tower extends BackGround3DOrnament {
	private Mediator mit;
	
	public Tower(Mediator mit, Vector3 v)
	{
		this.pos = v;
		this.mit = mit;
	}
	
	@Override
	public void Update() {
		this.pos.x++;
	}

	@Override
	public void Draw(GL11 gl) {
		gl.glDisable(GL11.GL_LIGHTING);
		gl.glDisable(GL11.GL_BLEND);
		mit.GetLoadResource().TOWER.scale.set(1, 100, 1);
		mit.GetLoadResource().TOWER.position = pos;
		mit.GetLoadResource().TOWER.draw(gl);
		gl.glEnable(GL11.GL_BLEND);
		gl.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public boolean RemoveOK() {
		if(this.pos.x>250)
		{
			this.pos.x = -250;
		}
		return false;
	}

}

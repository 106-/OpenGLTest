package info.khyh.opengltest.GameClass.BackGround.Concretes;

import javax.microedition.khronos.opengles.GL11;

import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.BackGround.BackGround3DOrnament;
import info.khyh.opengltest.Library.Vector3;

public class BackCloud extends BackGround3DOrnament{
	private Mediator mit;
	private float angle;
	private int cnt;
	
	public BackCloud(Vector3 v, Mediator mit)
	{
		this.pos = v;
		this.mit = mit;
		angle = 0;
	}
	
	@Override
	public void Update() {
		cnt++;
	}

	@Override
	public void Draw(GL11 gl) {
		gl.glPushMatrix();
		gl.glMatrixMode(GL11.GL_TEXTURE);
		gl.glLoadIdentity();
		//gl.glDisable(GL11.GL_BLEND);
		gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
		gl.glTranslatef((float)(cnt*-0.01), (float)(cnt*-0.02), 0);
		mit.GetLoadResource().CLOUD.rotate.set(0, angle, 0);
		mit.GetLoadResource().CLOUD.scale.set(100, 1, 100);
		mit.GetLoadResource().CLOUD.position = pos;
		gl.glMatrixMode(GL11.GL_MODELVIEW);
		mit.GetLoadResource().CLOUD.draw(gl);
		gl.glMatrixMode(GL11.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glMatrixMode(GL11.GL_MODELVIEW);
		gl.glLoadIdentity();
		//gl.glEnable(GL11.GL_BLEND);
		gl.glPopMatrix();
	}

	@Override
	public boolean RemoveOK() {
		return false;
	}

}

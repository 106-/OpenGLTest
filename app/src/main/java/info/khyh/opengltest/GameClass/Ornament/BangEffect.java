package info.khyh.opengltest.GameClass.Ornament;

import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;

import javax.microedition.khronos.opengles.GL11;

public class BangEffect extends Ornament {
	protected int cnt;
	protected final int FRAMEMAX = 8,
						WIDTH=120, HEIGHT=120;
	protected Mediator mit;
	protected OnEndDrawListener oed;
	
	public BangEffect(float x, float y, Mediator mit, OnEndDrawListener oed) {
		super(x, y);
		cnt=0;
		this.oed = oed;
		this.mit = mit;
	}

	@Override
	public void Update() {
		cnt++;
	}

	@Override
	public void Draw(GL11 gl) {
		if(this.cnt < FRAMEMAX*2)
		mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, 1.0f, 
				(int)(x-WIDTH*2), (int)(y-HEIGHT*2), WIDTH*4, HEIGHT*4, WIDTH*((cnt-1)/2), 30, WIDTH, HEIGHT, 1,1,1,1, 0.0f, BLENDTYPE.ADD);
	}

	@Override
	public boolean IsOver() {
		if(this.cnt > FRAMEMAX*2)
		{
			if(oed!=null)
				oed.Execute();
			return true;
		}
		return false;
	}

}

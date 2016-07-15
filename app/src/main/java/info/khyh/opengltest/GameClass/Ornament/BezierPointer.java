package info.khyh.opengltest.GameClass.Ornament;

import info.khyh.opengltest.MainActivity;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;

import javax.microedition.khronos.opengles.GL11;

import android.util.Log;

public class BezierPointer extends Ornament{
	private Mediator mit;
	private final int UV_X = 320,
						UV_Y = 150,
						UV_WIDTH = 100,
						UV_HEIGHT = 100;

	private double range;
	private int cnt;
	
	public BezierPointer(float x, float y, Mediator mit) {
		super(x, y);
		this.mit = mit;
	}

	public BezierPointer(double x, double y, Mediator mit) {
		super((float)x, (float)y);
		this.mit = mit;
	}
	
	public float GetX(){return x;}
	public float GetY(){return y;}
	
	@Override
	public void Update() {
		cnt++;
		if(cnt<20)
			range = 150 + (20-cnt)*(20-cnt);
		else
			range = 150;
	}

	@Override
	public void Draw(GL11 gl) {
		//mit.GetGraphics().drawBox(x, y, 30, 30, 1, 1, 1, 1, BLENDTYPE.ALPHA);
		mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect,
				(int)(x-range/2), (int)(y-range/2), (int)range, (int)range, UV_X, UV_Y, UV_WIDTH, UV_HEIGHT, 
				1, 1, 1, 1f+(float)Math.sin(Math.toRadians(cnt*10))*0.5f, (float)cnt*5, BLENDTYPE.ALPHA);
	}

	@Override
	public boolean IsOver() {
		return false;
	}

}

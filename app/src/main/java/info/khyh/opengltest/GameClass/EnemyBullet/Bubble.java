package info.khyh.opengltest.GameClass.EnemyBullet;

import javax.microedition.khronos.opengles.GL11;
import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;
import info.khyh.opengltest.Library.Vector2;

public class Bubble extends EnemyBullet{
	private double angle,spd,width,height;
	private final int LEAVE_RANGE = 64;
	private int cnt;

	public Bubble(double x, double y, double angle, double spd, double width, double height, Mediator mit) {
		super(x, y, mit);
		this.angle = angle;
		this.spd = spd;
		this.rangetype = HIT_RANGE_TYPE.SQUARE;
		this.width = width;
		this.height = height;
		this.pos = new Vector2[4];
		for(int i=0;i<4;i++)
		{
			this.pos[i]=new Vector2();
		}
	}

	@Override
	public void Update() {
		this.x += Math.cos(angle)*spd;
		this.y -= Math.sin(angle)*spd;
		cnt++;
		pos[0].set((float)x-(width/2),(float)y-(height/2));
		pos[1].set((float)x+(width/2),(float)y-(height/2));
		pos[2].set((float)x+(width/2),(float)y+(height/2));
		pos[3].set((float)x-(width/2),(float)y+(height/2));
		pos = DrawFuncs.RotatePoints(pos, new Vector2(this.x, this.y), (float)(-angle+Math.toRadians(90.0)));
	}

	@Override
	public void Draw(GL11 gl) {
		if(cnt%10>5)
		mit.GetGraphics().drawImage(mit.GetLoadResource().bubble, mit.GetDrawMain().aspect, 
				(int)(x-width/2), (int)(y-height/2), (int)width, (int)height, 
				0, 0, 128, 128, 
				1.0f, 1.0f, 1.0f, 1.0f, 
				(float)Math.toDegrees(-angle)+90, BLENDTYPE.ALPHA);
		else
		mit.GetGraphics().drawImage(mit.GetLoadResource().bubble, mit.GetDrawMain().aspect, 
				(int)(x-width/2), (int)(y-height/2), (int)width, (int)height, 
				0, 0, 128, 128, 
				1.0f, 1.0f, 1.0f, 1.0f, 
				(float)Math.toDegrees(-angle)+90, BLENDTYPE.XOR);
		
//		DrawFuncs.DrawModiTexture(mit.GetGraphics(), mit.GetLoadResource().FILTER, 
//		pos[0].x, pos[0].y, 
//		pos[1].x, pos[1].y, 
//		pos[2].x, pos[2].y, 
//		pos[3].x, pos[3].y);
	}

	@Override
	public void Hitted(HIT_TYPE type, float damage) {
	}

	@Override
	public boolean IsLeaveScreen() {
		if(x<0-LEAVE_RANGE || mit.WINDOW_W+LEAVE_RANGE<x || y<0-LEAVE_RANGE || mit.WINDOW_H+LEAVE_RANGE<y)
			return true;
		return false;
	}

	@Override
	public boolean IsEnable() {
		return true;
	}

	@Override
	public float GetDamage() {
		return 0;
	}
	
	

}

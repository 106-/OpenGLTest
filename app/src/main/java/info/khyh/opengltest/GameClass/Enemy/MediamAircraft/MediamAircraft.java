package info.khyh.opengltest.GameClass.Enemy.MediamAircraft;

import javax.microedition.khronos.opengles.GL11;

import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Enemy.Enemy;
import info.khyh.opengltest.GameClass.Ornament.BangEffect;
import info.khyh.opengltest.GameClass.Ornament.DrawMiniNum;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;

public abstract class MediamAircraft extends Enemy{
	//中型機
	protected final int WIDTH=120,
			HEIGHT=57,
			UV_X = 0,
			UV_Y = 165,
			LEAVE_RANGE = 60;

	private int HP_MAX = 200;
	private int hp;
	private boolean HITTED;
	
	public MediamAircraft(double x, double y, Mediator mit) {
		super(x, y, mit);
		this.hp = HP_MAX;
		HITTED = false;
	}

	@Override
	public void Draw(GL11 gl) {
		if(!HITTED)
			mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
				(int)(x-WIDTH), (int)(y-HEIGHT), WIDTH*2, HEIGHT*2, UV_X, UV_Y, WIDTH, HEIGHT, (float)Math.toDegrees(angle)+90);
		else
			mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
					(int)(x-WIDTH), (int)(y-HEIGHT), WIDTH*2, HEIGHT*2, UV_X, UV_Y, WIDTH, HEIGHT, 1.0f, 1.0f, 1.0f, 1.0f, (float)Math.toDegrees(angle)+90, BLENDTYPE.XOR);
		HITTED = false;
		//DrawFuncs.DrawModiTexture(mit.GetGraphics(), mit.GetLoadResource().FILTER, 
		//pos[0].x, pos[0].y, 
		//pos[1].x, pos[1].y, 
		//pos[2].x, pos[2].y, 
		//pos[3].x, pos[3].y);
	}

	@Override
	public void Hitted(HIT_TYPE type, float damage) {
		switch(type)
		{
		case PLAYER_BULLET:
			if(!Remove_OK)
			{
				if(!HITTED)mit.GetLoadResource().PlaySound(mit.GetLoadResource().KASURI);
				HITTED = true;
				this.hp -= damage;
				mit.SetEnemyDamageRatio((float)hp/(float)HP_MAX);
				if(this.hp<0)
				{
					mit.AddScore(10);
					mit.AddDestroyedEnemyCnt();
					mit.AddOrnament(new BangEffect((float)x, (float)y, mit, null));
					mit.AddOrnament(new DrawMiniNum((float)x, (float)y, mit, mit.GetDestroyedEnemyCnt()));
					mit.GetLoadResource().PlaySound(mit.GetLoadResource().EXPLODE_2);
					Remove_OK = true;
				}
			}
			break;
		}
	}
	
	@Override
	public boolean IsLeaveScreen() {
		if(x<0-LEAVE_RANGE || mit.WINDOW_W+LEAVE_RANGE<x || y<0-LEAVE_RANGE || mit.WINDOW_H+LEAVE_RANGE<y)
			return true;
		return Remove_OK;
	}

}

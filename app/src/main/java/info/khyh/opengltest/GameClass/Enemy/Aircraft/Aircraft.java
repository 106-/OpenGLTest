package info.khyh.opengltest.GameClass.Enemy.Aircraft;

import javax.microedition.khronos.opengles.GL11;

import android.util.Log;

import info.khyh.opengltest.MainActivity;
import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Enemy.Enemy;
import info.khyh.opengltest.GameClass.Hitable.HIT_TYPE;
import info.khyh.opengltest.GameClass.Ornament.BangEffect;
import info.khyh.opengltest.GameClass.Ornament.DrawMiniNum;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;

public abstract class Aircraft extends Enemy{
	//小型ザコ
	protected final int UV_X = 0,
						UV_Y = 230,
						WIDTH = 45,
						HEIGHT = 48,
						LEAVE_RANGE = 400,
						HP_MAX = 100;

	public int hp;
	private boolean HITTED;
	
	public Aircraft(double x, double y, Mediator mit) {
		super(x, y, mit);
		this.hp = HP_MAX;
		HITTED = false;
	}

	@Override
	public void Draw(GL11 gl) {
		if(!HITTED)
			mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
					(int)(x-WIDTH), (int)(y-HEIGHT), WIDTH*2, HEIGHT*2, UV_X, UV_Y, WIDTH, HEIGHT, (float)Math.toDegrees(angle)+270);
		else
			mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
					(int)(x-WIDTH), (int)(y-HEIGHT), WIDTH*2, HEIGHT*2, UV_X, UV_Y, WIDTH, HEIGHT, 1.0f, 1.0f, 1.0f, 1.0f, (float)Math.toDegrees(angle)+270, BLENDTYPE.XOR);
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
					mit.AddScore(5);
					mit.AddDestroyedEnemyCnt();
					mit.AddOrnament(new BangEffect((float)x, (float)y, mit, null));
					mit.AddOrnament(new DrawMiniNum((float)x, (float)y, mit, mit.GetDestroyedEnemyCnt()));
					mit.GetLoadResource().PlaySound(mit.GetLoadResource().EXPLODE_1);
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

package info.khyh.opengltest.GameClass.PlayerBullet;

import javax.microedition.khronos.opengles.GL11;

import android.util.Log;

import info.khyh.opengltest.MainActivity;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Ornament.DrawMiniNum;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;

public class Wave extends PlayerBullet{
	protected final int RANGE = 100,
						UV_X=120,
						UV_Y=150,
						CYCLE=5,
						TAMEMAX=150;
	protected int cnt,zerocnt;
	
	public Wave(double x, double y, int cnt, Mediator mit) {
		super(x, y, mit);
		this.rangetype = HIT_RANGE_TYPE.CIRCLE;
		this.cnt = cnt;
		this.zerocnt = cnt;
		Remove_OK = false;
	}

	@Override
	public void Update() {
		cnt--;
		// f(x) = 0.1(x-20)^3+2(x-20)^2+x
		if(cnt<30 && zerocnt==TAMEMAX)
		{
			range = 0.1*Math.pow((cnt-20),3)+4.3*Math.pow((cnt-20),2)+cnt;
			if(cnt==29)
				mit.GetLoadResource().PlaySound(mit.GetLoadResource().WAVEBREAK);
		}
		else if(zerocnt-cnt<40)
			range = 240-zerocnt+cnt;
		else
			range = 200;
		if(mit.GetPlayerPos() != null)
		{
			this.x = mit.GetPlayerPos().x;
			this.y = mit.GetPlayerPos().y;
		}
		else
			this.Remove_OK = true;
		
		if(cnt==0)
		{
			int sco = mit.GetDestroyedEnemyCnt()*100;
			mit.AddScore(sco);
			mit.AddOrnament(new DrawMiniNum((float)x, (float)y, mit, sco));
			mit.ResetDestroyedEnemyCnt();
		}
	}

	@Override
	public void Draw(GL11 gl) {
		if(cnt<30 && zerocnt>125)
			mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
					(int)(x-range), (int)(y-range), (int)range*2, (int)range*2, 
					UV_X, UV_Y, RANGE*2, RANGE*2, 
					1,1,1, 1f, (float)-cnt, BLENDTYPE.ADD);
		else if(zerocnt-cnt<10)
		{
			mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
					(int)(x-range), (int)(y-range), (int)range*2, (int)range*2, UV_X, UV_Y, RANGE*2, RANGE*2, 1, 1, 1, (float)(zerocnt-cnt)/10,(float)-cnt, BLENDTYPE.ALPHA);
		}
		else if(cnt<10)
		{
			mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
					(int)(x-range), (int)(y-range), (int)range*2, (int)range*2, UV_X, UV_Y, RANGE*2, RANGE*2, 1, 1, 1, (float)(cnt)/10.0f,(float)-cnt, BLENDTYPE.ALPHA);	
		}
		else
		{
			mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
					(int)(x-range), (int)(y-range), (int)range*2, (int)range*2, UV_X, UV_Y, RANGE*2, RANGE*2, (float)-cnt);
		}
	}

	@Override
	public void Hitted(HIT_TYPE type, float damage) {
	}

	@Override
	public boolean IsLeaveScreen() {
		return (cnt<0 || Remove_OK);
	}

	@Override
	public boolean IsEnable() {
		return (cnt<30 || cnt%CYCLE==0);
	}

	@Override
	public float GetDamage() {
		if(cnt<30 && zerocnt>125 && cnt%CYCLE==0)
			return 100;
		if(cnt%CYCLE==0)
			return 20;
		return 0;
	}

}

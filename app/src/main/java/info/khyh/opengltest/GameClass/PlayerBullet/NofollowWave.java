package info.khyh.opengltest.GameClass.PlayerBullet;

import javax.microedition.khronos.opengles.GL11;

import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Ornament.DrawMiniNum;

public class NofollowWave extends Wave{

	public NofollowWave(double x, double y, int cnt, Mediator mit) {
		super(x, y, cnt, mit);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void Update()
	{
		cnt--;
		range = Math.sin(Math.toRadians(cnt)*5)*50+50;
		y++;
		if(cnt==0)
		{
			mit.AddScore(100*mit.GetDestroyedEnemyCnt());
			mit.AddOrnament(new DrawMiniNum((float)x, (float)y, mit, 100*mit.GetDestroyedEnemyCnt()));
			mit.ResetDestroyedEnemyCnt();
		}
	}
	
	@Override
	public void Draw(GL11 gl) {
		mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
				(int)(x-range), (int)(y-range), (int)range*2, (int)range*2, UV_X, UV_Y, RANGE*2, RANGE*2, (float)-cnt);
	}
	
	@Override
	public float GetDamage() {
		if(cnt%CYCLE==0)
			return 5;
		return 0;
	}
	
}

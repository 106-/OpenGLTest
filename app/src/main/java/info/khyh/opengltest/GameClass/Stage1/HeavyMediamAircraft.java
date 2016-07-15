package info.khyh.opengltest.GameClass.Stage1;

import java.util.ArrayList;
import java.util.List;

import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Enemy.MediamAircraft.SinMediamAircraft;
import info.khyh.opengltest.GameClass.EnemyBullet.Bubble;
import info.khyh.opengltest.Library.Vector2;

public class HeavyMediamAircraft extends SinMediamAircraft{
	
	public HeavyMediamAircraft(double x, double y, Mediator mit) {
		super(x, y, mit, 40, null);
		this.angle = Math.toRadians(90);
		this.poslst = new ArrayList<Vector2>();
		this.poslst.add(new Vector2(x,y)); //40
		this.poslst.add(new Vector2(x,y-400));
		this.poslst.add(new Vector2(x,y-400));
		this.poslst.add(new Vector2(x,y-400));
		this.poslst.add(new Vector2(x,y-400));
		this.poslst.add(new Vector2(x,y-400)); //240
		this.poslst.add(new Vector2(x,y+100));
	}
	
	@Override
	public void Update()
	{
		super.Update();
		shot();
	}
	
	private void shot()
	{
		if(40<=cnt && cnt<=100 && cnt%2==0)
		{
			double n = cnt-40;
			double ratio = n/20;
			mit.AddEnemyBullet(new Bubble(x+50, y, angle-Math.toRadians(180*ratio), 8f, 10, 200, mit));
			mit.AddEnemyBullet(new Bubble(x-50, y, angle+Math.toRadians(180*ratio), 8f, 10, 200, mit));
		}
		if(cnt==220 || cnt==150)
		{
			mit.AddEnemyBullet(new Bubble(x, y, mit.GetPlayerAngle((int)y, (int)x), 13.0, 50, 50, mit));
			mit.AddEnemyBullet(new Bubble(x, y, mit.GetPlayerAngle((int)y, (int)x), 13.5, 50, 50, mit));
			mit.AddEnemyBullet(new Bubble(x, y, mit.GetPlayerAngle((int)y, (int)x), 14.0, 50, 50, mit));
			mit.AddEnemyBullet(new Bubble(x, y, mit.GetPlayerAngle((int)y, (int)x), 14.5, 50, 50, mit));
			mit.AddEnemyBullet(new Bubble(x, y, mit.GetPlayerAngle((int)y, (int)x), 15.0, 50, 50, mit));
			mit.AddEnemyBullet(new Bubble(x, y, mit.GetPlayerAngle((int)y, (int)x), 15.5, 50, 50, mit));
		}
	}
	
}

package info.khyh.opengltest.GameClass.Stage1;

import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Enemy.Aircraft.ConstantAircraft;
import info.khyh.opengltest.GameClass.EnemyBullet.Bubble;

public class ConstantMissileAircraft extends ConstantAircraft{
	private int cnt;
	
	public ConstantMissileAircraft(double x, double y, Mediator mit,
			double angle, double spd) {
		super(x, y, mit, angle, spd);
	}
	
	@Override
	public void Update()
	{
		super.Update();
		shot();
		cnt++;
	}
	
	private void shot()
	{
		if(mit.GetCnt()%30==0)
		{
			mit.AddEnemyBullet(new Bubble(x, y, -angle, 20f, 10, 80, mit));
		}
	}

}

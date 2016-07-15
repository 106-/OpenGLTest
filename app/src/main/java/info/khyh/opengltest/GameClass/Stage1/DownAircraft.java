package info.khyh.opengltest.GameClass.Stage1;

import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Enemy.Aircraft.ConstantAircraft;
import info.khyh.opengltest.GameClass.EnemyBullet.Bubble;

public class DownAircraft extends ConstantAircraft{

	public DownAircraft(double x, double y, Mediator mit, double spd) {
		super(x, y, mit, Math.toRadians(-90), spd);
	}
	
	@Override
	public void Update()
	{
		super.Update();
		shot();
	}
	
	private void shot()
	{
		if(cnt==60)
		{
			for(int i=3;i<5;i++)
			{
				mit.AddEnemyBullet(new Bubble(x, y, mit.GetPlayerAngle((int)y, (int)x), 3+2*i, 50, 50, mit));
			}
		}
	}
	
}

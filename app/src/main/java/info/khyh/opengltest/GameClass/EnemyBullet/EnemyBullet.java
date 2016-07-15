package info.khyh.opengltest.GameClass.EnemyBullet;

import info.khyh.opengltest.GameClass.Hitable;
import info.khyh.opengltest.GameClass.Mediator;

public abstract class EnemyBullet extends Hitable{
	
	public EnemyBullet(double x, double y, Mediator mit)
	{
		super(x, y, mit);
		this.hittype = HIT_TYPE.ENEMY_BULLET;
	}
}

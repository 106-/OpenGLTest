package info.khyh.opengltest.GameClass.Enemy;

import java.util.List;

import info.khyh.opengltest.GameClass.Hitable;
import info.khyh.opengltest.GameClass.Mediator;

public abstract class Enemy extends Hitable{
	public Enemy(double x, double y, Mediator mit) {
		super(x, y, mit);
		this.hittype = HIT_TYPE.ENEMY;
	}
	
}

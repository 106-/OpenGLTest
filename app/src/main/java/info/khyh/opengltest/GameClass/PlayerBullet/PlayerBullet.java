package info.khyh.opengltest.GameClass.PlayerBullet;

import info.khyh.opengltest.GameClass.Hitable;
import info.khyh.opengltest.GameClass.Mediator;

public abstract class PlayerBullet extends Hitable {

	public PlayerBullet(double x, double y, Mediator mit) {
		super(x, y, mit);
		this.hittype = HIT_TYPE.PLAYER_BULLET;
	}
}

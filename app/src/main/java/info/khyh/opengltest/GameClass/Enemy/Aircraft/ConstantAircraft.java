package info.khyh.opengltest.GameClass.Enemy.Aircraft;

import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Hitable.HIT_RANGE_TYPE;
import info.khyh.opengltest.GameClass.EnemyBullet.Bubble;
import info.khyh.opengltest.Library.Vector2;

public class ConstantAircraft extends Aircraft{
	//小型ザコ等速
	
	private double spd;
	protected int cnt;
	
	public ConstantAircraft(double x, double y, Mediator mit, double angle, double spd) {
		super(x, y, mit);
		this.spd = spd;
		this.angle = angle;
		this.rangetype = HIT_RANGE_TYPE.SQUARE;
	}

	@Override
	public void Update() {
		this.x += Math.cos(angle)*spd;
		this.y += Math.sin(angle)*spd;
		pos = new Vector2[]
				{
				new Vector2((float)x-(WIDTH),(float)y-(HEIGHT)),
				new Vector2((float)x+(WIDTH),(float)y-(HEIGHT)),
				new Vector2((float)x-(WIDTH),(float)y+(HEIGHT)),
				new Vector2((float)x+(WIDTH),(float)y+(HEIGHT))
				};
		pos = DrawFuncs.RotatePoints(pos, new Vector2(this.x, this.y), (float)(angle+Math.toRadians(180.0)));
		cnt++;
	}

	@Override
	public boolean IsEnable() {
		return true;
	}

	@Override
	public float GetDamage() {
		return 0;
	}

}

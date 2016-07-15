package info.khyh.opengltest.GameClass.Enemy.MediamAircraft;

import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.Library.Vector2;

public class ConstantMediamAircraft extends MediamAircraft {
	//中型機の等速
	
	private double spd;
	private int cnt;
	
	public ConstantMediamAircraft(double x, double y, Mediator mit, float angle, float spd) {
		super(x, y, mit);
		this.rangetype = rangetype.SQUARE;
		this.angle = angle;
		this.spd = spd;
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

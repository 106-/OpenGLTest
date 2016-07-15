package info.khyh.opengltest.GameClass.Enemy.Aircraft;

import info.khyh.opengltest.DrawLibrary.Utls;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.Library.Vector2;

import java.util.List;

public class SinAircraft extends LinerAircraft{

	public SinAircraft(double x, double y, Mediator mit, int div,List<Vector2> poslst) {
		super(x, y, mit, div, poslst);
	}

	@Override
	protected void PosUpdate()
	{
		Vector2 v = Utls.GetSinMove(StartVector, EndVector, cnt%div, div);
		double old_x = this.x, old_y = this.y;
		this.x = v.x;
		this.y = v.y;
		this.angle = Math.atan2(y-old_y, x-old_x);
	}
	
}

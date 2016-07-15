package info.khyh.opengltest.GameClass.Enemy.MediamAircraft;

import java.util.List;

import info.khyh.opengltest.DrawLibrary.Utls;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.Library.Vector2;

public class SinMediamAircraft extends LinerMediamAircraft {

	public SinMediamAircraft(double x, double y, Mediator mit, int div,
			List<Vector2> poslst) {
		super(x, y, mit, div, poslst);
	}
	
	@Override
	protected void PosUpdate()
	{
		Vector2 v = Utls.GetSinMove(StartVector, EndVector, cnt%div, div);
		this.x = v.x;
		this.y = v.y;
	}
}

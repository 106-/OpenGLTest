package info.khyh.opengltest.GameClass.BackGround;

import info.khyh.opengltest.GameClass.Mediator;

public abstract class BackGroundCommander {
	protected Mediator mit;
	public BackGroundCommander(Mediator mit)
	{
		this.mit = mit;
	}
	public abstract void OnTime(int t);
}

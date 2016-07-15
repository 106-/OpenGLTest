package info.khyh.opengltest.GameClass.Commander;

import info.khyh.opengltest.GameClass.Mediator;

public abstract class Commander {
	protected Mediator mit;
	
	public Commander(Mediator mit)
	{
		this.mit = mit;
	}
	
	public abstract void OnTime(int n);
	public abstract void OnResume(int n);
}

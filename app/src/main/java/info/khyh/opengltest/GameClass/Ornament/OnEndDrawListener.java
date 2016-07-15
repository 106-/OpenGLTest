package info.khyh.opengltest.GameClass.Ornament;

import info.khyh.opengltest.GameClass.Mediator;

public abstract class OnEndDrawListener {
	protected Mediator mit;
	
	public OnEndDrawListener(Mediator mit)
	{
		this.mit = mit;
	}
	
	public abstract void Execute();
}

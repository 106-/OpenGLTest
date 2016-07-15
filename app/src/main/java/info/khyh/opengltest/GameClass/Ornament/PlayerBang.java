package info.khyh.opengltest.GameClass.Ornament;

import info.khyh.opengltest.GameClass.Mediator;

public class PlayerBang extends BangEffect {
	private final int WAITTIME = 50;

	public PlayerBang(float x, float y, Mediator mit, OnEndDrawListener oed) {
		super(x, y, mit, oed);
	}

	@Override
	public boolean IsOver() {
		if(this.cnt > FRAMEMAX*2 + WAITTIME)
		{
			if(oed!=null)
				oed.Execute();
			return true;
		}
		return false;
	}
	
}

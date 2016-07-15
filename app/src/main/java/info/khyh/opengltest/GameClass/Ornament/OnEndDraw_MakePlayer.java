package info.khyh.opengltest.GameClass.Ornament;

import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Player.Player1;

public class OnEndDraw_MakePlayer extends OnEndDrawListener{
	private float x,y;
	
	public OnEndDraw_MakePlayer(Mediator mit, float x, float y) {
		super(mit);
		this.mit = mit;
		this.x = x;
		this.y = y;
	}

	@Override
	public void Execute() {
		mit.AddPlayer(new Player1(x, y, mit));
	}

}

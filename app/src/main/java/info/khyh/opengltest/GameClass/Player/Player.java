package info.khyh.opengltest.GameClass.Player;

import android.view.KeyEvent;
import android.view.MotionEvent;

import info.khyh.opengltest.GameClass.Hitable;
import info.khyh.opengltest.GameClass.Mediator;

public abstract class Player extends Hitable {
	protected boolean Remove_OK;
	
	public Player(double x, double y, Mediator mit) {
		super(x, y, mit);
		Remove_OK = false;
		this.hittype = HIT_TYPE.PLAYER;
	}
	
	public abstract void OnTouchEvent(MotionEvent e);
	public abstract void OnKey(int keyCode, KeyEvent event);
}

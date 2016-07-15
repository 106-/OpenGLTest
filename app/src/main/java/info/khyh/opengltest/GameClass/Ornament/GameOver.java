package info.khyh.opengltest.GameClass.Ornament;

import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.DrawLibrary.DrawFuncs.StrType;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;

import javax.microedition.khronos.opengles.GL11;

public class GameOver extends Ornament {
	private int cnt;
	private Mediator mit;
	private final int CNTMAX=100;
	
	public GameOver(Mediator mit) {
		super(0,0);
		this.mit = mit;
	}	

	@Override
	public void Update() {
		cnt++;
	}

	@Override
	public void Draw(GL11 gl) {
		mit.GetGraphics().drawBox(0, 0, mit.WINDOW_W, mit.WINDOW_H, 0.0f, 0.0f, 0.0f, (float)cnt/(float)CNTMAX, BLENDTYPE.ALPHA);
		DrawFuncs.DrawString(mit.GetGraphics(), mit.GetDrawMain().aspect, mit.GetLoadResource().FONT, 
				"YOU CAN NO LONGER ENDURE THE PAIN", StrType.BLACK, 0, mit.WINDOW_H/2, mit.WINDOW_W, 100);
	}

	@Override
	public boolean IsOver() {
		if(cnt==300)
		{
			//mit.GetLoadResource().ReleaseResourse();
			mit.GetDrawMain().OnGameOver();
		}
		return false;
	}

}

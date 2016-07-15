package info.khyh.opengltest.GameClass.Ornament;

import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.DrawLibrary.DrawFuncs.StrType;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;

import javax.microedition.khronos.opengles.GL11;

public class ScoreBoard extends Ornament{
	private Mediator mit;
	private String score,remain;
	private final int HEIGHT=50;
	
	public ScoreBoard(Mediator mit) {
		super(0,0);
		this.mit = mit;
	}

	@Override
	public void Update() {}

	@Override
	public void Draw(GL11 gl) {
		if(mit.GetRemainPlayer()>-1)
			remain = String.format("REMAIN        %01d", mit.GetRemainPlayer());
		score = String.format("SCORE  %08d", mit.GetScore()>99999999? 99999999:mit.GetScore());
		mit.GetGraphics().drawBox(0, 0, mit.WINDOW_W, HEIGHT, 0.5f, 0.5f, 0.5f, 0.5f, BLENDTYPE.ALPHA);
		DrawFuncs.DrawString(mit.GetGraphics(), mit.GetDrawMain().aspect, mit.GetLoadResource().FONT, 
				score, StrType.BLUE, 0, 0, score.length()*20, HEIGHT/2);
		DrawFuncs.DrawString(mit.GetGraphics(), mit.GetDrawMain().aspect, mit.GetLoadResource().FONT, 
				remain, StrType.BLUE, 0, HEIGHT/2, remain.length()*20, HEIGHT/2);
		
		mit.GetGraphics().drawBox(310, 10, mit.WINDOW_W-320, 30, 0.8f, 0.8f, 0.5f, 0.6f, BLENDTYPE.ALPHA);
		mit.GetGraphics().drawBox(310, 10, (mit.WINDOW_W-320)*mit.GetChargeCnt()/mit.CHAGECNTMAX, 30, 1.0f, 0.1f, 0.1f, 0.6f, BLENDTYPE.ALPHA);
		if(mit.GetChargeCnt()==mit.CHAGECNTMAX)
		{
			DrawFuncs.DrawString(mit.GetGraphics(), mit.GetDrawMain().aspect, mit.GetLoadResource().FONT, 
					"EBULLIENT", StrType.BLUE, 310, 10, "EBULLIENT".length()*20, HEIGHT/2);
		}
		mit.GetGraphics().drawBox(0, mit.WINDOW_H-60, mit.WINDOW_W, 30, 1f, 1f, 1f, 0.8f, BLENDTYPE.ALPHA);
		mit.GetGraphics().drawBox(0, mit.WINDOW_H-60, (float)(mit.WINDOW_W*mit.GetEnemyDamageRatio()), 30, 1.0f, 0.1f, 0.1f, 0.8f, BLENDTYPE.ALPHA);
	}

	@Override
	public boolean IsOver() {
		return false;
	}

}

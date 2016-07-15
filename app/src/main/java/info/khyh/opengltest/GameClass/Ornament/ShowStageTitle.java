package info.khyh.opengltest.GameClass.Ornament;

import javax.microedition.khronos.opengles.GL11;

import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.DrawLibrary.DrawFuncs.StrType;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;

public class ShowStageTitle extends Ornament {
	private int cnt;
	private String StageName,Description;
	private Mediator mit;
	private final int HEIGHT=100
						,STR_X = 150
						,MOMENT_HIDE = 20
						,CNT_MAX = 300;
	
	public ShowStageTitle(String sn, String desc, Mediator mit) {
		super(0, 0);
		this.cnt = CNT_MAX;
		this.StageName = sn;
		this.Description = desc;
		this.mit = mit;
	}
	
	@Override
	public void Update() {
		cnt--;
	}
	
	@Override
	public void Draw(GL11 gl) {
		if(cnt>CNT_MAX-MOMENT_HIDE)
		{
			mit.GetGraphics().drawBox(0, mit.WINDOW_H-HEIGHT-STR_X + HEIGHT/2-HEIGHT/2*(CNT_MAX-cnt)/MOMENT_HIDE, mit.WINDOW_W, HEIGHT*(CNT_MAX-cnt)/MOMENT_HIDE, 0.5f, 0.5f, 0.5f, 0.5f, BLENDTYPE.REVERSE);
			mit.GetGraphics().drawBox(0, STR_X + HEIGHT/2-HEIGHT/2*(CNT_MAX-cnt)/MOMENT_HIDE, mit.WINDOW_W, HEIGHT*(CNT_MAX-cnt)/MOMENT_HIDE, 0.5f, 0.5f, 0.5f, 0.5f, BLENDTYPE.REVERSE);
		}
		else if(cnt>MOMENT_HIDE)
		{
			mit.GetGraphics().drawBox(0, mit.WINDOW_H-HEIGHT-STR_X, mit.WINDOW_W, HEIGHT, 0.5f, 0.5f, 0.5f, 0.5f, BLENDTYPE.REVERSE);
			mit.GetGraphics().drawBox(0, STR_X, mit.WINDOW_W, HEIGHT, 0.5f, 0.5f, 0.5f, 0.5f, BLENDTYPE.REVERSE);
			DrawFuncs.DrawString(mit.GetGraphics(), mit.GetDrawMain().aspect, mit.GetLoadResource().FONT, StageName, StrType.BLACK, 0, mit.WINDOW_H-HEIGHT-STR_X-2, 35*StageName.length(), HEIGHT);
			DrawFuncs.DrawString(mit.GetGraphics(), mit.GetDrawMain().aspect, mit.GetLoadResource().FONT, Description, StrType.BLACK, mit.WINDOW_W-35*Description.length(), STR_X-2, 35*Description.length(), HEIGHT);
		}
		else if(cnt>0)
		{
			mit.GetGraphics().drawBox(0, mit.WINDOW_H-HEIGHT-STR_X + HEIGHT/2-HEIGHT/2*cnt/MOMENT_HIDE, mit.WINDOW_W, HEIGHT*cnt/MOMENT_HIDE, 0.5f, 0.5f, 0.5f, 0.5f, BLENDTYPE.REVERSE);
			mit.GetGraphics().drawBox(0, STR_X + HEIGHT/2-HEIGHT/2*cnt/MOMENT_HIDE, mit.WINDOW_W, HEIGHT*cnt/MOMENT_HIDE, 0.5f, 0.5f, 0.5f, 0.5f, BLENDTYPE.REVERSE);
		}
	}
	
	@Override
	public boolean IsOver() {
		return cnt<0;
	}
	
}

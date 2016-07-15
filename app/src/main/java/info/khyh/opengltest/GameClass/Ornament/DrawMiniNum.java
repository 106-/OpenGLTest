package info.khyh.opengltest.GameClass.Ornament;

import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.DrawLibrary.DrawFuncs.StrType;
import info.khyh.opengltest.GameClass.Mediator;

import javax.microedition.khronos.opengles.GL11;

public class DrawMiniNum extends Ornament {
	private Mediator mit;
	private int cnt,num;
	private final int CNTMAX = 50,
						CHANGECOLORCNT = 20;
	
	public DrawMiniNum(float x, float y,Mediator mit, int n) {
		super(x,y);
		this.mit = mit;
		this.num = n;
	}

	@Override
	public void Update() {
		cnt++;
	}

	@Override
	public void Draw(GL11 gl) {
		if(cnt<CHANGECOLORCNT)
			DrawFuncs.DrawString(mit.GetGraphics(), mit.GetDrawMain().aspect, mit.GetLoadResource().FONT,
				String.valueOf(num), StrType.BLACK, x, y, String.valueOf(num).length()*30.0f*(float)cnt/(float)CHANGECOLORCNT, 30.0f, (float)cnt/(float)CHANGECOLORCNT);
		else if(CHANGECOLORCNT<cnt)
			DrawFuncs.DrawString(mit.GetGraphics(), mit.GetDrawMain().aspect, mit.GetLoadResource().FONT,
					String.valueOf(num), StrType.BLACK, x, y, String.valueOf(num).length()*30.0f, 30.0f);
	}

	@Override
	public boolean IsOver() {
		return (cnt>100);
	}

}

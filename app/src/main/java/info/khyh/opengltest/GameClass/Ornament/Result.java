package info.khyh.opengltest.GameClass.Ornament;

import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.DrawLibrary.DrawFuncs.StrType;
import info.khyh.opengltest.DrawLibrary.Utls;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;

import javax.microedition.khronos.opengles.GL11;

public class Result extends Ornament{
	private Mediator mit;
	private int remain,score,cnt;
	private String result, scorestr, remainstr, remaindesc;
	private boolean RemoveOK;
	
	public Result(Mediator mit) {
		super(0,0);
		this.mit = mit;
		remain = mit.GetRemainPlayer();
		mit.AddScore(remain*1000);
		score = mit.GetScore();
		scorestr = String.format("%08d", score);
		remainstr = String.format("1000 X %s %s", remain, remain*1000);
		RemoveOK = false;
	}
	
	private void DrawCenter(String str, int y)
	{
		DrawFuncs.DrawString(mit.GetGraphics(), 
				mit.GetDrawMain().aspect, 
				mit.GetLoadResource().FONT, 
				str, 
				StrType.BLACK, 
				mit.WINDOW_W/2-DrawFuncs.CHARACTER_W*str.length()/2, 
				y, 
				DrawFuncs.CHARACTER_W*str.length(), 
				DrawFuncs.CHARACTER_H);
	}
	
	@Override
	public void Update() {
		cnt++;
	}

	@Override
	public void Draw(GL11 gl) {
		if(300>cnt && cnt>60)
		{
			mit.GetGraphics().drawBox(100, 100, mit.WINDOW_W-200, mit.WINDOW_H-200, 0, 0, 0, 0.5f, BLENDTYPE.ALPHA);
			DrawCenter("R E S U L T", mit.WINDOW_H*3/4);
			DrawCenter("REMAIN BONUS",mit.WINDOW_H*3/4-200);
			DrawCenter(remainstr,mit.WINDOW_H*3/4-200-DrawFuncs.CHARACTER_H-10);
			DrawCenter("TOTAL", mit.WINDOW_H/2);
			DrawCenter(scorestr, mit.WINDOW_H/2-DrawFuncs.CHARACTER_H-10);
		}
		if(cnt==300)
			RemoveOK = true;
	}

	@Override
	public boolean IsOver() {
		if(RemoveOK)
		{
			//スコアを投稿する
			//String name = Utls.ShowDialog(mit);
			//Utls.POSTscore(name, mit.GetScore());
			//リソースの開放
			//mit.GetLoadResource().ReleaseResourse();
			//Activityの終了
			mit.GetDrawMain().OnGameOver(score);
			return true;
		}
		return false;
	}

}

package info.khyh.opengltest.GameClass.BackGround.Concretes;

import javax.microedition.khronos.opengles.GL11;

import android.util.Log;

import info.khyh.opengltest.MainActivity;
import info.khyh.opengltest.DrawLibrary.Utls;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.BackGround.BackGround2DOrnament;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;
import info.khyh.opengltest.Library.Vector2;

public class WARNING extends BackGround2DOrnament{
	private int cnt;
	private Vector2 pos;
	private float angle;
	private final int UV_X = 0,
						UV_Y = 245,
						HEIGHT = 60,
						WIDTH = 512,
						OPENTIME = 60,
						DOWNTIME = 120;
	private Mediator mit;
	private Vector2 startv,endv;
	
	public WARNING(Mediator mit)
	{
		this.mit = mit;
		pos = new Vector2(0,0);
		startv = new Vector2(mit.WINDOW_W*3/4, mit.WINDOW_H*3/4);
		endv =  new Vector2(mit.WINDOW_W*3/4, mit.WINDOW_H/2);
		mit.Warnisalive = true;
	}
	
	@Override
	public void Update() {
		cnt++;
		if(OPENTIME<cnt)
		{
			pos = Utls.GetSinMove(startv, endv, cnt-OPENTIME, DOWNTIME);
		}
		if(cnt%40==0)
			mit.GetLoadResource().PlaySound(mit.GetLoadResource().WARN);
	}

	@Override
	public void Draw(GL11 gl) {
		if(cnt<=OPENTIME)
		{
			double ratio = (float)Math.sin(((float)cnt/(float)OPENTIME)*Math.PI/2);
			mit.GetGraphics().drawBox((float)(mit.WINDOW_W/2-ratio*mit.WINDOW_W/2),
					0f, 
					(float)(ratio*mit.WINDOW_W), 
					(float)mit.WINDOW_H, 
					1f, 
					1f, 
					1f,
					0.5f,
					BLENDTYPE.XOR);
		}
		else if(OPENTIME<cnt)
		{
			double ratio = (double)(cnt-OPENTIME)/(double)DOWNTIME;
			mit.GetGraphics().drawBox(0f, 0f, (float)mit.WINDOW_W, (float)mit.WINDOW_H, 1f, 1f, 1f, 0.5f, BLENDTYPE.XOR);
			mit.GetGraphics().drawImage(mit.GetLoadResource().FONT, mit.GetDrawMain().aspect, 
					(int)(pos.x-WIDTH*2.5/2), (int)(pos.y-HEIGHT*2.5/2), (int)(WIDTH*2.5), (int)(HEIGHT*2.5), UV_X, UV_Y, WIDTH, HEIGHT, 1, 1, 1, (float)Math.sin(ratio*Math.PI/2), 270, BLENDTYPE.ALPHA);
		}
	}

	@Override
	public boolean RemoveOK() {
		if(cnt>OPENTIME+DOWNTIME*2)
		{
			mit.Warnisalive = false;
			return true;
		}
		return false;
	}

}

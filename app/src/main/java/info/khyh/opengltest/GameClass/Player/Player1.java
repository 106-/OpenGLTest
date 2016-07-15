package info.khyh.opengltest.GameClass.Player;

import javax.microedition.khronos.opengles.GL11;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import info.khyh.opengltest.MainActivity;
import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Ornament.BezierPointer;
import info.khyh.opengltest.GameClass.Ornament.GameOver;
import info.khyh.opengltest.GameClass.Ornament.OnEndDraw_MakePlayer;
import info.khyh.opengltest.GameClass.Ornament.PlayerBang;
import info.khyh.opengltest.GameClass.PlayerBullet.ChargeShot;
import info.khyh.opengltest.GameClass.PlayerBullet.Wave;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;
import info.khyh.opengltest.Library.Vector2;

public class Player1 extends Player{
	private final int WIDTH = 20,
						HEIGHT = 28,
						HIT_DIST = 5,
						ZURE = 15,
						TAMEMAX = 150,
						COST = 300;
	private int uvpos,cnt,tame,muteki_cnt,muteki_zero;
	private float old_x,old_y;
	private boolean tame_flg,muteki,HITTED;
	private BezierPointer bp;
	
	public Player1(double x, double y, Mediator mit) {
		super(x, y, mit);
		uvpos = 0;
		old_x=(float)x;
		old_y=(float)y;
		hittype = HIT_TYPE.PLAYER;
		rangetype = HIT_RANGE_TYPE.SQUARE;
		tame = 0;
		tame_flg = false;
		muteki_cnt=muteki_zero=100;
		muteki = true;
		HITTED = false;
		this.pos = new Vector2[4];
		for(int i=0;i<4;i++)
		{
			this.pos[i]=new Vector2();
		}
	}

	@Override
	public void Update() {
		if(tame==0)uvpos=0;
		if(tame_flg && tame<TAMEMAX)
			tame+=2;
		if(!tame_flg && tame!=0)
		{
			tame--;
			uvpos=(uvpos==7)?0:uvpos+1;
		}
		if(muteki)
		{
			muteki_cnt--;
			if(muteki_cnt==0)
				muteki = false;
			//uvpos = 0;
		}
		pos[0].set((float)x-HIT_DIST, (float)y-HIT_DIST);
		pos[1].set((float)x+HIT_DIST, (float)y-HIT_DIST);
		pos[2].set((float)x+HIT_DIST, (float)y+HIT_DIST);
		pos[3].set((float)x-HIT_DIST, (float)y+HIT_DIST);
		mit.AddChargeCnt();
		if(bp!=null)bp.Update();
		cnt++;
	}

	@Override
	public void Draw(GL11 gl) {
		mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
									(int)(x-WIDTH*2), (int)(y-HEIGHT*2+ZURE), WIDTH*4, HEIGHT*4, 
									uvpos*WIDTH, 0, WIDTH, HEIGHT, 0.0f);
		mit.GetGraphics().drawBox((float)x-150, (float)y-50, 300f, 10f, 0.5f, 0.5f, 0.5f, 0.5f, BLENDTYPE.ALPHA);
		mit.GetGraphics().drawBox((float)x-150, (float)y-50, 300f*tame/TAMEMAX, 10f, 1f, 1f, 1f, 0.5f, BLENDTYPE.ALPHA);
		mit.GetGraphics().drawBox((float)x-114, (float)y-50, 5, 10f, 1f, 0f, 1f, 0.5f, BLENDTYPE.ALPHA);
		mit.GetGraphics().drawBox((float)(x-150+300), (float)y-50, 5, 10f, 1f, 0f, 1f, 0.5f, BLENDTYPE.ALPHA);
		if(muteki && !mit.Warnisalive)
		{
			float p = (float)muteki_cnt/(float)muteki_zero;
			mit.GetGraphics().drawBox((float)(mit.WINDOW_W/2*(1.0-p)), 0f, (float)mit.WINDOW_W*p, (float)mit.WINDOW_H, 
									0.5f, (float)Math.sin(muteki_cnt/10.0), 0f, 0.5f, BLENDTYPE.XOR);
		}
		if(bp!=null)bp.Draw(gl);
		
//		DrawFuncs.DrawModiTexture(mit.GetGraphics(), mit.GetLoadResource().FILTER, 
//		pos[0].x, pos[0].y, 
//		pos[1].x, pos[1].y, 
//		pos[2].x, pos[2].y, 
//		pos[3].x, pos[3].y);
}

	@Override
	public void Hitted(HIT_TYPE type, float damage) {
		switch(type)
		{
		case ENEMY_BULLET:
			if(muteki || HITTED)break;
			HITTED = true;
			Remove_OK = true;
			mit.GetLoadResource().PlaySound(mit.GetLoadResource().EXPLODE_2);
			mit.PlayerDestroyed();
			if(mit.GetRemainPlayer()<0)
			{
				Log.v(MainActivity.Tag,"GAMEOVER!!!");
				mit.AddOrnament(new PlayerBang((float)(x-WIDTH/2), (float)(y-HEIGHT/2), mit, null));
				mit.AddOrnament(new GameOver(mit));
			}
			else
			{
				mit.AddOrnament(new PlayerBang((float)(x-WIDTH/2), (float)(y-HEIGHT/2), mit,
						new OnEndDraw_MakePlayer(mit,(float)x,(float)y)));
			}
			break;
		}
	}

	@Override
	public boolean IsLeaveScreen() {
		return Remove_OK;
	}

	@Override
	public void OnTouchEvent(MotionEvent e) {
		if(e.getPointerCount()!=1)return;
		else
		{
			float x = e.getX();
			float y = e.getY();
			switch ( e.getAction() ) {
			case MotionEvent.ACTION_DOWN:
				if(tame==0)
					tame_flg = true;
				break;
			case MotionEvent.ACTION_MOVE:
				this.x -= ((old_x - x)/mit.GetDrawMain().SCREEN_WIDTH)*mit.GetDrawMain().WINDOW_W;
				this.y += ((old_y - y)/mit.GetDrawMain().SCREEN_HEIGHT)*mit.GetDrawMain().WINDOW_H;
				if(tame_flg)
				{
					uvpos=0;
					if(old_x-x >0)uvpos = 7;
					else if(0>old_x-x)uvpos = 1;
				}
				
				if(tame == 0 && !tame_flg)
					tame_flg = true;
				break;
			case MotionEvent.ACTION_UP:
				if(tame>20 && tame_flg)
				{
					tame_flg = false;
					muteki_cnt += tame;
					muteki = true;
					mit.AddPlayerBullet(new Wave(x, y, tame, mit));
					mit.GetLoadResource().PlaySound(mit.GetLoadResource().BARIA);
				}
				else if(tame<20 && tame_flg)
				{
					tame=0;
					tame_flg=false;
				}
				break;
			}
			  
			old_x = x;
			old_y = y;
		
			if(this.x<0)this.x=0;
			if(this.y<0)this.y=0;
			if(mit.GetDrawMain().WINDOW_W<this.x)this.x = mit.GetDrawMain().WINDOW_W;
			if(mit.GetDrawMain().WINDOW_H<this.y)this.y = mit.GetDrawMain().WINDOW_H;
		}
	}
	
	@Override
	public void OnKey(int keyCode, KeyEvent event) {
		switch(event.getAction())
		{
		case KeyEvent.ACTION_DOWN:
			switch(keyCode)
			{
			case KeyEvent.KEYCODE_SPACE:
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				if(mit.GetChargeCnt()>=COST)
					if(bp==null)
						bp=new BezierPointer(x,y,mit);
				break;
			case KeyEvent.KEYCODE_VOLUME_UP:
				Log.v(MainActivity.Tag, "x:"+String.valueOf(mit.GetDrawMain().campos.x));
				Log.v(MainActivity.Tag, "y:"+String.valueOf(mit.GetDrawMain().campos.y));
				Log.v(MainActivity.Tag, "z:"+String.valueOf(mit.GetDrawMain().campos.z));
				Log.v(MainActivity.Tag, "cnt:" +String.valueOf(mit.GetCnt()));
				break;
			}
			break;
		case KeyEvent.ACTION_UP:
			if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_SPACE)
			{
				if(mit.GetChargeCnt()>=COST)
				{
					mit.GetLoadResource().PlaySound(mit.GetLoadResource().LAZER);
					if(bp==null)return;
					mit.SubChargeCnt(COST);
					double r = Math.atan2(y-bp.GetY(), x-bp.GetX());
					for(int i=1;i<4;i++)
					{mit.AddPlayerBullet(new ChargeShot(0, 0, bp.GetX(), bp.GetY(), x+700*Math.cos(r-Math.PI/2+Math.PI/6*i), y+500*Math.sin(r-6/Math.PI), mit));}
					for(int i=1;i<4;i++)
					{mit.AddPlayerBullet(new ChargeShot(0, 0, bp.GetX(), bp.GetY(), x+700*Math.cos(r+Math.PI/2+Math.PI/6*i), y+500*Math.sin(r+6/Math.PI), mit));}
					bp = null;
				}
			}
			break;
		}
	}

	@Override
	public boolean IsEnable() {
		return !muteki;
	}

	@Override
	public float GetDamage() {
		return 0;
	}
}

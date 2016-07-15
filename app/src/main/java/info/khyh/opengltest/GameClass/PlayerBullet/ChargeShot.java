package info.khyh.opengltest.GameClass.PlayerBullet;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL11;

import android.util.Log;

import info.khyh.opengltest.MainActivity;
import info.khyh.opengltest.DrawLibrary.Utls;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Hitable.HIT_RANGE_TYPE;
import info.khyh.opengltest.GameClass.Ornament.DrawMiniNum;
import info.khyh.opengltest.Library.Graphics;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;
import info.khyh.opengltest.Library.Vector2;

public class ChargeShot extends PlayerBullet{
	private int DIV = 60,
				LENGTH = 30,
				UV_X = 170,
				UV_Y = 10,
				UV_WIDTH = 300,
				UV_HEIGHT = 20,
				BEZIER_WIDTH = 5,
				RANGE = 70;
	private Vector2 cp;
	private BezierLine bezier_pos[];
	private Vector2 bezier_centerpos[];
	private FloatBuffer fb[];
	private int cnt;
	private boolean REMOVEOK;
	
	public ChargeShot(double x, double y, double ex, double ey, double cx, double cy, Mediator mit) {
		super(x, y, mit);
		cp = new Vector2(cx,cy);
		bezier_pos = new BezierLine[DIV];
		bezier_centerpos = new Vector2[DIV];
		fb = new FloatBuffer[DIV];
		
		for(int i=0;i<DIV;i++)
		{bezier_pos[i] = new BezierLine();}
		this.rangetype = HIT_RANGE_TYPE.SQUARE;
		
		Vector2 v,
		s=new Vector2(mit.GetPlayerPos().x,mit.GetPlayerPos().y), 
		e=new Vector2(ex, ey);
		for(int i=0;i<DIV;i++)
		{bezier_centerpos[i] = Utls.GetBezier(s, e, cp, DIV, i);}
		for(int i=0;i<DIV;i++)
		{
			double r;
			if(i==DIV-1)
				r = Math.atan2(e.y - bezier_centerpos[i].y, e.x - bezier_centerpos[i].x);
			else
				r = Math.atan2(bezier_centerpos[i+1].y - bezier_centerpos[i].y, bezier_centerpos[i+1].x - bezier_centerpos[i].x);
			bezier_pos[i].Right.x = (float) (bezier_centerpos[i].x + BEZIER_WIDTH * Math.cos(r+Math.PI/2));
			bezier_pos[i].Right.y = (float) (bezier_centerpos[i].y + BEZIER_WIDTH * Math.sin(r+Math.PI/2));
			bezier_pos[i].Left.x = (float) (bezier_centerpos[i].x + BEZIER_WIDTH * Math.cos(r-Math.PI/2));
			bezier_pos[i].Left.y = (float) (bezier_centerpos[i].y + BEZIER_WIDTH * Math.sin(r-Math.PI/2));
		}
		for(int i=0;i<DIV;i++)
			if(i+1<DIV)
				fb[i] = Graphics.makeFloatBufferFromDisplay(new float[]{
					bezier_pos[i+1].Left.x, bezier_pos[i+1].Left.y, 
					bezier_pos[i+1].Right.x, bezier_pos[i+1].Right.y, 
					bezier_pos[i].Left.x, bezier_pos[i].Left.y, 
					bezier_pos[i].Right.x, bezier_pos[i].Right.y
					});
		REMOVEOK = false;
	}

	private class BezierLine
	{
		public Vector2 Right;
		public Vector2 Left;
		public BezierLine()
		{
			this.Right = new Vector2();
			this.Left = new Vector2();
		}
	}
	
	@Override
	public void Update() {
		cnt+=3;
		if(cnt+LENGTH<DIV)
		{
			this.x = bezier_centerpos[cnt+LENGTH].x;
			this.y = bezier_centerpos[cnt+LENGTH].y;
		}
		else
		{
			this.x = bezier_centerpos[DIV-1].x;
			this.y = bezier_centerpos[DIV-1].y;	
		}
		this.pos = new Vector2[]
				{
				new Vector2(this.x-RANGE,this.y-RANGE),
				new Vector2(this.x-RANGE,this.y+RANGE),
				new Vector2(this.x+RANGE,this.y+RANGE),
				new Vector2(this.x+RANGE,this.y-RANGE)
				};
		if(cnt>DIV+LENGTH+1 && !REMOVEOK){
			if(mit.GetDestroyedEnemyCnt()!=0)
			{
				mit.AddScore(100*mit.GetDestroyedEnemyCnt());
				Log.v(MainActivity.Tag,String.format("AddedScore:%s",100*mit.GetDestroyedEnemyCnt()));
				Log.v(MainActivity.Tag,String.format("cnt:%s",cnt));
				if(mit.GetPlayerPos()!=null)
					mit.AddOrnament(new DrawMiniNum((float)mit.GetPlayerPos().x, (float)mit.GetPlayerPos().y, mit, 100*mit.GetDestroyedEnemyCnt()));
				mit.ResetDestroyedEnemyCnt();
			}
			mit.AddPlayerBullet(new NofollowWave(x, y, 100, mit));
			REMOVEOK = true;
		}
	}

	@Override
	public void Draw(GL11 gl) {
		for(int i=0;i<LENGTH;i++)
		{
			if(cnt+i<DIV-1)
			{
				mit.GetGraphics().drawImageFromPoints(mit.GetLoadResource().CHARACTER, 
						BLENDTYPE.ADD,
						UV_X+i*UV_WIDTH/LENGTH, UV_Y,
						UV_WIDTH/LENGTH, UV_HEIGHT,
						fb[cnt+i]);
			}
		}
	}

	@Override
	public void Hitted(HIT_TYPE type, float damage) {
		
	}

	@Override
	public boolean IsLeaveScreen() {
		return REMOVEOK;
	}

	@Override
	public boolean IsEnable() {
		return true;
	}

	@Override
	public float GetDamage() {
		if(cnt%30==0)
			return 10;
		else
			return 0;
	}

}

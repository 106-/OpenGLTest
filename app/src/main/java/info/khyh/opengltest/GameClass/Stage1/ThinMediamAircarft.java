package info.khyh.opengltest.GameClass.Stage1;

import java.util.ArrayList;
import java.util.List;

import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.DrawLibrary.Utls;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Enemy.MediamAircraft.SinMediamAircraft;
import info.khyh.opengltest.GameClass.EnemyBullet.Bubble;
import info.khyh.opengltest.Library.Vector2;

public class ThinMediamAircarft extends SinMediamAircraft{
	private int cnt;
	private double plang;
	
	public ThinMediamAircarft(double x, double y, Mediator mit, int div,
			Vector2 snipepoint) {
		super(0, 0, mit, div, null);
		this.poslst = new ArrayList<Vector2>();
		poslst.add(new Vector2(x,y));
		poslst.add(snipepoint);
		poslst.add(snipepoint);
		poslst.add(snipepoint);
		poslst.add(snipepoint);
		poslst.add(new Vector2(x,0));
	}

	@Override
	public void Update()
	{
		super.Update();
		shot();
		if(cnt==div*1)
			{plang = mit.GetPlayerAngle((int)y, (int)x);}
		cnt++;
	}
	
	@Override
	protected void PosUpdate()
	{
		Vector2 v = Utls.GetSinMove(StartVector, EndVector, cnt%div, div);
		double old_x = this.x, old_y = this.y;
		this.x = v.x;
		this.y = v.y;
		if(div*1<cnt && cnt<div*4)
		{
			this.angle = plang*-1;
		}
		else
		{
			this.angle = mit.GetPlayerAngle((int)y, (int)x)*-1;
		}
	}
	
	private void shot()
	{
		if(div*1<cnt && cnt<div*3 && cnt%5==0)
		{
			Vector2 shotpos[] = new Vector2[]
			{
				new Vector2(x+50,y),
				new Vector2(x-50,y)
			};
			shotpos = DrawFuncs.RotatePoints(shotpos, new Vector2(x,y), (float)(angle+Math.PI/2));
			for(Vector2 v:shotpos)
				mit.AddEnemyBullet(new Bubble(v.x, v.y, plang, 10f, 30, 50, mit));
		}
	}
	
}

package info.khyh.opengltest.GameClass.Stage1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import info.khyh.opengltest.DrawLibrary.Utls;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Enemy.Aircraft.Aircraft;
import info.khyh.opengltest.GameClass.Enemy.Aircraft.SinAircraft;
import info.khyh.opengltest.GameClass.EnemyBullet.Bubble;
import info.khyh.opengltest.Library.Vector2;

public class SinSnipeAircraft extends SinAircraft {
	private int cnt;
	private double playerangle;
	
	public SinSnipeAircraft(Mediator mit, int div, Vector2 snipepoint) {
		super(0,0, mit, div, null);
		this.poslst = new ArrayList<Vector2>();
		Random rnd = new Random();
		poslst.add(new Vector2(rnd.nextInt(mit.WINDOW_W), rnd.nextInt(mit.WINDOW_H)));
		poslst.add(snipepoint);
		poslst.add(snipepoint);
		poslst.add(new Vector2(rnd.nextInt(mit.WINDOW_W), mit.WINDOW_H));
	}

	@Override
	public void Update()
	{
		super.Update();
		shot();
		if(cnt==div*1)
			playerangle = mit.GetPlayerAngle((int)y, (int)x);
		cnt++;
	}
	
	@Override
	protected void PosUpdate()
	{
		Vector2 v = Utls.GetSinMove(StartVector, EndVector, cnt%div, div);
		double old_x = this.x, old_y = this.y;
		this.x = v.x;
		this.y = v.y;
		if(div*1<cnt && cnt<div*2)
			{this.angle = playerangle*-1;}
		else
			{this.angle = mit.GetPlayerAngle((int)y, (int)x)*-1;}
	}
	
	private void shot()
	{
		if(div*1<cnt && cnt<div*2 && cnt%5==0)
		{
			mit.AddEnemyBullet(new Bubble(x,y, playerangle, 10.0f, 50, 50, mit));
		}
	}
	
}

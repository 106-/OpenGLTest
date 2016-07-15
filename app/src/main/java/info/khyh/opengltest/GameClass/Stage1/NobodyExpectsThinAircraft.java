package info.khyh.opengltest.GameClass.Stage1;

import java.util.ArrayList;
import java.util.List;

import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Enemy.Aircraft.SinAircraft;
import info.khyh.opengltest.GameClass.EnemyBullet.Bubble;
import info.khyh.opengltest.Library.Vector2;

public class NobodyExpectsThinAircraft extends SinAircraft {
	private int cnt;
	
	public NobodyExpectsThinAircraft(double x, double y, Mediator mit, int div) {
		super(x, y, mit, div, null);
		poslst = new ArrayList<Vector2>();
		poslst.add(new Vector2(x,y));
		poslst.add(new Vector2(x,y+500));
		poslst.add(new Vector2(x,y+750));
		poslst.add(new Vector2(x,mit.WINDOW_H+100));
	}

	@Override
	public void Update()
	{
		super.Update();
		shot();
		cnt++;
	}
	
	private void shot()
	{
		if(div*1<cnt && cnt<div*2 && cnt%2==0)
		{
			double ang = Math.toRadians(Math.sin(Math.toRadians((double)cnt/((double)div/(double)3)*360))*40);
			//mit.AddEnemyBullet(new Bubble(x, y, Math.toRadians(90), 10.5f, 100, 100, mit));
			mit.AddEnemyBullet(new Bubble(x+WIDTH/2, y-20-HEIGHT/2, Math.toRadians(70)+ang, 10.5f, 25, 25, mit));
			mit.AddEnemyBullet(new Bubble(x-WIDTH/2, y-20-HEIGHT/2, Math.toRadians(110)-ang, 10.5f, 25, 25, mit));
		}
	}
	
}

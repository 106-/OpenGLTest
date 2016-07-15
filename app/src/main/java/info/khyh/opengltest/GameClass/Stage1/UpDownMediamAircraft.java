package info.khyh.opengltest.GameClass.Stage1;

import java.util.ArrayList;
import java.util.List;

import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Enemy.MediamAircraft.SinMediamAircraft;
import info.khyh.opengltest.GameClass.EnemyBullet.Bubble;
import info.khyh.opengltest.Library.Vector2;

public class UpDownMediamAircraft extends SinMediamAircraft{
	//上から降りてきて撃った後に上に引っ込む
	
	public UpDownMediamAircraft(double x, double y, Mediator mit) {
		super(x, y, mit, 60, null);
		this.poslst = new ArrayList<Vector2>();
		poslst.add(new Vector2(x,y));
		poslst.add(new Vector2(x,y-500));
		poslst.add(new Vector2(x,y-500));
		poslst.add(new Vector2(x,y-500));
		poslst.add(new Vector2(x,y-500));
		poslst.add(new Vector2(x,y-500));
		poslst.add(new Vector2(x,mit.WINDOW_H+100));
		angle = Math.toRadians(90);
	}
	
	@Override
	public void Update()
	{
		super.Update();
		shot();
	}
	
	private void shot()
	{
		if(60<cnt&&cnt<120&&cnt%10==0)
		{
			mit.AddEnemyBullet(new Bubble(x,y,angle,15f,50,50,mit));
		}
		if(120<cnt && cnt<300 && cnt%50==0)
		{
			mit.AddEnemyBullet(new Bubble(x,y,mit.GetPlayerAngle((int)y, (int)x),9.0f,50,50,mit));
			mit.AddEnemyBullet(new Bubble(x,y,mit.GetPlayerAngle((int)y, (int)x),9.5f,50,50,mit));
			mit.AddEnemyBullet(new Bubble(x,y,mit.GetPlayerAngle((int)y, (int)x),10.0f,50,50,mit));
			mit.AddEnemyBullet(new Bubble(x,y,mit.GetPlayerAngle((int)y, (int)x),10.5f,50,50,mit));
			mit.AddEnemyBullet(new Bubble(x,y,mit.GetPlayerAngle((int)y, (int)x),11.0f,50,50,mit));
		}
		if(300<cnt && cnt%15==0)
		{
			mit.AddEnemyBullet(new Bubble(x,y,angle+Math.toRadians(60),17f,10,100,mit));
			mit.AddEnemyBullet(new Bubble(x,y,angle-Math.toRadians(60),17f,10,100,mit));
		}
	}
	
}

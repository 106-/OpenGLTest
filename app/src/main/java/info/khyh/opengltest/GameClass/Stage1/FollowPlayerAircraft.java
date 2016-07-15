package info.khyh.opengltest.GameClass.Stage1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import info.khyh.opengltest.DrawLibrary.Utls;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Enemy.Aircraft.LinerAircraft;
import info.khyh.opengltest.GameClass.Enemy.Aircraft.SinAircraft;
import info.khyh.opengltest.GameClass.EnemyBullet.Bubble;
import info.khyh.opengltest.Library.Vector2;

public class FollowPlayerAircraft extends SinAircraft{
	private Vector2 endpoint;

	public FollowPlayerAircraft(double x, double y, Mediator mit, int div, Vector2 staypoint, Vector2 endpoint) {
		super(x, y, mit, div, null);
		this.poslst = new ArrayList<Vector2>();
		this.poslst.add(new Vector2(x,y));
		this.poslst.add(staypoint);
		this.poslst.add(staypoint);
		this.endpoint = endpoint;
	}
	
	@Override
	public void Update()
	{
		if(cnt < this.div*3)
		{
			this.angle = mit.GetPlayerAngle((int)y, (int)x)*-1;	
		}
		super.Update();
		if(cnt == this.div*2-1)
		{
			Random rnd = new Random();
			this.poslst.add(new Vector2(rnd.nextInt(mit.WINDOW_W), mit.WINDOW_H));
			this.poslst.add(endpoint);
		}
	}
	
	@Override
	protected void PosUpdate()
	{
		Vector2 v = Utls.GetSinMove(StartVector, EndVector, cnt%div, div);
		double old_x = this.x, old_y = this.y;
		this.x = v.x;
		this.y = v.y;
		if(!(cnt < this.div*3))
		{
			this.angle = Math.atan2(y-old_y, x-old_x);
		}
		shot();
	}
	
	private void shot()
	{
		if(cnt == this.div*3)
		{
			double ang = mit.GetPlayerAngle((int)y, (int)x);
			mit.AddEnemyBullet(new Bubble(x, y, ang, 10.0f, 50, 50, mit));
			mit.AddEnemyBullet(new Bubble(x, y, ang, 10.5f, 50, 50, mit));
		}
	}

}

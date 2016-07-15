package info.khyh.opengltest.GameClass.Stage1;

import java.util.List;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL11;

import info.khyh.opengltest.DrawLibrary.Utls;
import info.khyh.opengltest.GameClass.Hitable;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Enemy.EnemyGroup;
import info.khyh.opengltest.GameClass.Enemy.Aircraft.Aircraft;
import info.khyh.opengltest.Library.Vector2;

public class FastfowardAircraft extends EnemyGroup{
	
	private int diedcnt;
	
	public FastfowardAircraft(Mediator mit, int diedcnt) {
		super(0, 0, mit);
		this.diedcnt = diedcnt;
		
		Vector2 startvec=new Vector2(),
				endvec=new Vector2();
		
//		Utls.GetLinerMove(new Vector2(mit.WINDOW_W/2,100), new Vector2(50,mit.WINDOW_H*3/4), i, 4);
//		Utls.GetLinerMove(new Vector2(mit.WINDOW_W/2,100), new Vector2(mit.WINDOW_W-50,mit.WINDOW_H*3/4), i, 4);
//		Utls.GetLinerMove(new Vector2(mit.WINDOW_W/2,mit.WINDOW_H-100), new Vector2(50,mit.WINDOW_H*1/4), i, 4);
//		Utls.GetLinerMove(new Vector2(mit.WINDOW_W/2,mit.WINDOW_H-100), new Vector2(mit.WINDOW_W-50,mit.WINDOW_H*1/4), i, 4);
		
		switch(diedcnt%4)
		{
		case 0:
			startvec = new Vector2(mit.WINDOW_W/2,100);
			endvec =  new Vector2(50,mit.WINDOW_H*3/4);
			break;
		case 1:
			startvec = new Vector2(mit.WINDOW_W/2,100);
			endvec =  new Vector2(mit.WINDOW_W-50,mit.WINDOW_H*3/4);
			break;
		case 2:
			startvec = new Vector2(mit.WINDOW_W/2,mit.WINDOW_H-100);
			endvec =  new Vector2(50,mit.WINDOW_H*1/4);
			break;
		case 3:
			startvec = new Vector2(mit.WINDOW_W/2,mit.WINDOW_H-100);
			endvec =  new Vector2(mit.WINDOW_W-50,mit.WINDOW_H*1/4);
			break;
			
		}
		
		childs = new Hitable[5];
		for(int i=0;i<5;i++)
		{
			Vector2 pos = Utls.GetLinerMove(startvec, endvec, i, 4);
			childs[i]=new SinSnipeAircraft(mit, 50, pos);
		}
		for(Hitable h:childs)
		{
			Aircraft e=(Aircraft)h;
			e.Update();
			e.hp = 1;
		}
	}
	
	@Override
	public boolean IsLeaveScreen() {
		if(AllDestroyed)
		{
			if(mit.GetCnt()<5500)
				mit.AddEnemy(new FastfowardAircraft(mit, diedcnt+1));
			return true;
		}
		return false;
	}
	
}

package info.khyh.opengltest.GameClass.Commander;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.DrawLibrary.Utls;
import info.khyh.opengltest.DrawLibrary.DrawFuncs.StrType;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Enemy.BOSS;
import info.khyh.opengltest.GameClass.Enemy.BOSSOption;
import info.khyh.opengltest.GameClass.Enemy.Aircraft.ConstantAircraft;
import info.khyh.opengltest.GameClass.Enemy.Aircraft.LinerAircraft;
import info.khyh.opengltest.GameClass.Enemy.Aircraft.SinAircraft;
import info.khyh.opengltest.GameClass.Enemy.MediamAircraft.ConstantMediamAircraft;
import info.khyh.opengltest.GameClass.Enemy.MediamAircraft.LinerMediamAircraft;
import info.khyh.opengltest.GameClass.Stage1.BOSSGroup;
import info.khyh.opengltest.GameClass.Stage1.ConstantMissileAircraft;
import info.khyh.opengltest.GameClass.Stage1.DownAircraft;
import info.khyh.opengltest.GameClass.Stage1.FastfowardAircraft;
import info.khyh.opengltest.GameClass.Stage1.FollowPlayerAircraft;
import info.khyh.opengltest.GameClass.Stage1.HeavyMediamAircraft;
import info.khyh.opengltest.GameClass.Stage1.NobodyExpectsThinAircraft;
import info.khyh.opengltest.GameClass.Stage1.SinSnipeAircraft;
import info.khyh.opengltest.GameClass.Stage1.ThinMediamAircarft;
import info.khyh.opengltest.GameClass.Stage1.UpDownMediamAircraft;
import info.khyh.opengltest.GameClass.Ornament.Result;
import info.khyh.opengltest.GameClass.Ornament.ScoreBoard;
import info.khyh.opengltest.GameClass.Ornament.ShowStageTitle;
import info.khyh.opengltest.GameClass.Player.Player1;
import info.khyh.opengltest.GameClass.Stage1.UpDownMediamAircraft;
import info.khyh.opengltest.Library.Vector2;

public class MyCommander extends Commander {
	private Random rnd;

	public MyCommander(Mediator mit)
	{
		super(mit);
		rnd = new Random();
	}
	
	@Override
	public void OnTime(int n) {
		//自機作ったり
		if(n==0)
		{
			mit.AddPlayer(new Player1(mit.GetDrawMain().WINDOW_W/2,
											mit.GetDrawMain().WINDOW_H*1/4, mit));
			mit.AddOrnament(new ScoreBoard(mit));
			mit.GetLoadResource().STAGEBGM.start();
			//mit.AddOrnament(new Result(mit));
		}
		
		//ステージテロップの表示
		if(n==160)mit.AddOrnament(new ShowStageTitle("THE DAWN", "REGAIN LIBERTY", mit));
		
		//中型機1小型機3を左右
		if(n==260)
		{
			mit.AddEnemy(new UpDownMediamAircraft((double)(mit.WINDOW_W*0.25), (double)mit.WINDOW_H, mit));
			mit.AddEnemy(new DownAircraft(mit.WINDOW_W/2+50, mit.WINDOW_H, mit, 8));
			mit.AddEnemy(new DownAircraft(mit.WINDOW_W/2+100, mit.WINDOW_H+30, mit, 10));
			mit.AddEnemy(new DownAircraft(mit.WINDOW_W/2+150, mit.WINDOW_H+60, mit, 12));
		}
		if(n==470)
		{
			mit.AddEnemy(new UpDownMediamAircraft((double)(mit.WINDOW_W*0.75), (double)mit.WINDOW_H, mit));
			mit.AddEnemy(new DownAircraft(mit.WINDOW_W/2-50, mit.WINDOW_H, mit, 8));
			mit.AddEnemy(new DownAircraft(mit.WINDOW_W/2-100, mit.WINDOW_H+30, mit, 10));
			mit.AddEnemy(new DownAircraft(mit.WINDOW_W/2-150, mit.WINDOW_H+60, mit, 12));
		}
		
		//針をばらまく中型機
		if(n==700)
		{
			mit.AddEnemy(new HeavyMediamAircraft(mit.WINDOW_W/2, mit.WINDOW_H, mit));
		}
		
		//中型機の群
		if(n==950)
		{
			mit.AddEnemy(new UpDownMediamAircraft((double)(mit.WINDOW_W*0.25), (double)mit.WINDOW_H, mit));
			mit.AddEnemy(new UpDownMediamAircraft((double)(mit.WINDOW_W*0.75), (double)mit.WINDOW_H, mit));
		}
		if(n==970)
		{
			mit.AddEnemy(new UpDownMediamAircraft((double)(mit.WINDOW_W*0.50), (double)mit.WINDOW_H+25, mit));
		}
		if(n==1000)
		{
			mit.AddEnemy(new UpDownMediamAircraft((double)(mit.WINDOW_W*0.15), (double)mit.WINDOW_H+50, mit));
			mit.AddEnemy(new UpDownMediamAircraft((double)(mit.WINDOW_W*0.85), (double)mit.WINDOW_H+50, mit));
		}
		
		//上で自機に狙い撃ち
		if(1200<n && n<1700 && n%10==0)
		{
			mit.AddEnemy(new FollowPlayerAircraft(0, 0, mit, 40, new Vector2(rnd.nextInt(mit.WINDOW_W), rnd.nextInt(mit.WINDOW_H/2)), new Vector2(mit.WINDOW_W/2, mit.WINDOW_H)));
		}
		
		//編隊を組んだ小型機左右中
		if(n==1960)
		{
			double angle = Math.toRadians(225);
			Vector2 center = new Vector2(-100, 300);
			Vector2 positions[] = new Vector2[]
					{
					new Vector2(0,-50),
					new Vector2(80,0),
					new Vector2(-80,0),
					new Vector2(160,50),
					new Vector2(-160,50),
					new Vector2(240,100),
					new Vector2(-240,100)
					};
			positions = DrawFuncs.RotatePoints(positions, new Vector2(0,0), (float)Math.toRadians(-45));
			for(Vector2 i: positions)
			{
				mit.AddEnemy(new ConstantMissileAircraft(center.x+i.x, center.y+i.y, mit, angle+Math.toRadians(180), 10.0f));
			}
		}
		if(n==2160)
		{
			double angle = Math.toRadians(315);
			Vector2 center = new Vector2(mit.WINDOW_W+100, 300);
			Vector2 positions[] = new Vector2[]
					{
					new Vector2(0,-50),
					new Vector2(80,0),
					new Vector2(-80,0),
					new Vector2(160,50),
					new Vector2(-160,50),
					new Vector2(240,100),
					new Vector2(-240,100)
					};
			positions = DrawFuncs.RotatePoints(positions, new Vector2(0,0), (float)Math.toRadians(45));
			for(Vector2 i: positions)
			{
				mit.AddEnemy(new ConstantMissileAircraft(center.x+i.x, center.y+i.y, mit, angle+Math.toRadians(180), 10.0f));
			}
		}
		if(n==2360)
		{
			Vector2 center = new Vector2(mit.WINDOW_W/2, -100);
			Vector2 positions[] = new Vector2[]
					{
					new Vector2(0,-50),
					new Vector2(80,0),
					new Vector2(-80,0),
					new Vector2(160,50),
					new Vector2(-160,50),
					new Vector2(240,100),
					new Vector2(-240,100)
					};
			positions = DrawFuncs.RotatePoints(positions, new Vector2(0,0), (float)Math.toRadians(0));
			for(Vector2 i: positions)
			{
				mit.AddEnemy(new ConstantMissileAircraft(center.x+i.x, center.y+i.y, mit, Math.toRadians(90), 12.5f));
			}
		}
		
		//Sin波で弾をばらまく
		if(n==2530)
		{mit.AddEnemy(new NobodyExpectsThinAircraft(mit.WINDOW_W*3/4, 0, mit, 80));}
		if(n==2630)
		{mit.AddEnemy(new NobodyExpectsThinAircraft(mit.WINDOW_W*1/4, 0, mit, 80));}
		if(n==2730)
		{mit.AddEnemy(new NobodyExpectsThinAircraft(mit.WINDOW_W*3/4, 0, mit, 80));}
		if(n==2830)
		{mit.AddEnemy(new NobodyExpectsThinAircraft(mit.WINDOW_W*1/4, 0, mit, 80));}
		
		//どっかからあらわれて自機狙い
		//左下
		if(n==3000)
		{
			for(int i=0;i<5;i++)
			{
				Vector2 pos = Utls.GetLinerMove(new Vector2(mit.WINDOW_W/2,100), new Vector2(50,mit.WINDOW_H*3/4), i, 4);
				mit.AddEnemy(new SinSnipeAircraft(mit, 60, pos));
			}
		}
		//右下
		if(n==3200)
		{
			for(int i=0;i<5;i++)
			{
				Vector2 pos = Utls.GetLinerMove(new Vector2(mit.WINDOW_W/2,100), new Vector2(mit.WINDOW_W-50,mit.WINDOW_H*3/4), i, 4);
				mit.AddEnemy(new SinSnipeAircraft(mit, 60, pos));
			}
		}
		//中心でベジェ
		if(n==3400)
		{
			for(int i=0;i<5;i++)
			{
				Vector2 pos = Utls.GetBezier(new Vector2(0,mit.WINDOW_H/2), 
						new Vector2(mit.WINDOW_W,mit.WINDOW_H/2), 
						new Vector2(mit.WINDOW_W/2,0), 
						4, i);
				mit.AddEnemy(new SinSnipeAircraft(mit, 80, pos));
			}
		}
		//左上
		if(n==3600)
		{
			for(int i=0;i<5;i++)
			{
				Vector2 pos = Utls.GetLinerMove(new Vector2(mit.WINDOW_W/2,mit.WINDOW_H-100), new Vector2(50,mit.WINDOW_H*1/4), i, 4);
				mit.AddEnemy(new SinSnipeAircraft(mit, 60, pos));
			}
		}
		//右上
		if(n==3800)
		{
			for(int i=0;i<5;i++)
			{
				Vector2 pos = Utls.GetLinerMove(new Vector2(mit.WINDOW_W/2,mit.WINDOW_H-100), new Vector2(mit.WINDOW_W-50,mit.WINDOW_H*1/4), i, 4);
				mit.AddEnemy(new SinSnipeAircraft(mit, 60, pos));
			}
		}
		//中心でベジェ上から
		if(n==4000)
		{
			for(int i=0;i<5;i++)
			{
				Vector2 pos = Utls.GetBezier(new Vector2(0,mit.WINDOW_H/2), 
						new Vector2(mit.WINDOW_W,mit.WINDOW_H/2), 
						new Vector2(mit.WINDOW_W/2,mit.WINDOW_H), 
						4, i);
				mit.AddEnemy(new SinSnipeAircraft(mit, 80, pos));
			}
		}
		
		if(n==4200)
		{
			for(int i=0;i<5;i++)
			{
				Vector2 pos = Utls.GetBezier(new Vector2(0,mit.WINDOW_H*3/4), 
						new Vector2(mit.WINDOW_W,mit.WINDOW_H*3/4), 
						new Vector2(mit.WINDOW_W/2,mit.WINDOW_H*3/4), 
						4, i);
				mit.AddEnemy(new ThinMediamAircarft(pos.x, mit.WINDOW_H, mit, 40, pos));
			}
		}
		
		if(n==4500)
		{
			for(int i=0;i<5;i++)
			{
				Vector2 pos = Utls.GetBezier(new Vector2(0,mit.WINDOW_H*3/4), 
						new Vector2(mit.WINDOW_W,mit.WINDOW_H*3/4), 
						new Vector2(mit.WINDOW_W/2,mit.WINDOW_H*3/4), 
						4, i);
				mit.AddEnemy(new ThinMediamAircarft(pos.x, mit.WINDOW_H, mit, 40, pos));
			}
		}
		
		if(n==4800)mit.AddEnemy(new FastfowardAircraft(mit,0));
		if(n==5500)
		{
			mit.GetLoadResource().STAGEBGM.stop();
		}
		if(n==5800)
		{
			mit.AddEnemy(new BOSSGroup(mit.WINDOW_W/2, mit.WINDOW_H+10, mit));
			mit.GetLoadResource().BOSSBGM.start();
		}
	}

	@Override
	public void OnResume(int n) {
		//WARNINGの前か後か
		if(5500>n)
		{
			mit.GetLoadResource().STAGEBGM.start();
		}
		else
		{
			mit.GetLoadResource().BOSSBGM.start();
		}
	}

}

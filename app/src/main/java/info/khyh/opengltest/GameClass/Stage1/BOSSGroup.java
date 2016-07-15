package info.khyh.opengltest.GameClass.Stage1;

import javax.microedition.khronos.opengles.GL11;

import info.khyh.opengltest.GameClass.Hitable;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Enemy.BOSS;
import info.khyh.opengltest.GameClass.Enemy.BOSSOption;
import info.khyh.opengltest.GameClass.Enemy.EnemyGroup;
import info.khyh.opengltest.GameClass.Ornament.DrawMiniNum;
import info.khyh.opengltest.GameClass.Ornament.Result;
import info.khyh.opengltest.GameClass.Hitable.HIT_TYPE;
import info.khyh.opengltest.Library.Vector2;

public class BOSSGroup extends EnemyGroup {
	private BOSS boss;
	private BOSSOption bossop1, bossop2;
	private BOSSSTATE state;
	private int breakcnt;
	private int cnt;

	public enum BOSSSTATE
	{
		APPEAR,			//登場
		RELEASE_OPTION, //オプションを放つ
		SIN_FIRE,		//sin波攻撃
		OPTION_FIRE,	//オプションの自機狙い
		MOUNT_OPTION,	//オプションの回収
		BOSS_FIRE,		//本体自機狙い
		BREAKUP,		//破壊後
	}
	
	public BOSSGroup(double x, double y, Mediator mit) {
		super(x, y, mit);
		boss = new BOSS(x,y,mit,this);
		bossop1 = new BOSSOption(200, -100, new Vector2(mit.WINDOW_W*1/5, mit.WINDOW_H*1/4), mit, boss);
		bossop2 = new BOSSOption(-200, -100, new Vector2(mit.WINDOW_W*4/5, mit.WINDOW_H*1/4), mit, boss);
		state = BOSSSTATE.APPEAR;
		boss.setState(state);
		bossop1.setState(state);
		bossop2.setState(state);
		childs = new Hitable[]{boss, bossop1, bossop2};
	}

	public void setBossState(BOSSSTATE state)
	{
		if(state==BOSSSTATE.BREAKUP)
			breakcnt = cnt;
		this.state = state;
		boss.setState(state);
		bossop1.setState(state);
		bossop2.setState(state);
	}
	
	public void ResetLoop()
	{
		boss.ResetLoop();
		bossop1.ResetLoop();
		bossop2.ResetLoop();
	}
	
	@Override
	public void Update()
	{
		super.Update();
		if(state==BOSSSTATE.BREAKUP && cnt==breakcnt+60)
			Remove_OK = true;
		cnt++;
	}
	
	@Override
	public boolean IsLeaveScreen() {
		if(Remove_OK)
		{
			mit.AddOrnament(new Result(mit));
			int sco = (cnt>3600?0:3600-cnt)*100;
			mit.AddScore(sco);
			mit.AddOrnament(new DrawMiniNum((float)x, (float)y, mit, sco));
			return true;
		}
		return false;
	}

}

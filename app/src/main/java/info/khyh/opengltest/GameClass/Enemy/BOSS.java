package info.khyh.opengltest.GameClass.Enemy;

import javax.microedition.khronos.opengles.GL11;

import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.DrawLibrary.Utls;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.EnemyBullet.Bubble;
import info.khyh.opengltest.GameClass.Ornament.BangEffect;
import info.khyh.opengltest.GameClass.Ornament.DrawMiniNum;
import info.khyh.opengltest.GameClass.Stage1.BOSSGroup;
import info.khyh.opengltest.GameClass.Stage1.BOSSGroup.BOSSSTATE;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;
import info.khyh.opengltest.Library.Vector2;

public class BOSS extends Enemy{
	private final int UV_X = 0,
						UV_Y = 290,
						WIDTH = 100,
						HEIGHT = 95,
						HPMAX = 5000;
	
	private boolean HITTED;
	private int cnt,hp,tmpcnt;
	private float angle;
	private BOSSSTATE bossstate;
	private BOSSGroup bossgroup;
	private Vector2 tmpvec;

	public BOSS(double x, double y, Mediator mit, BOSSGroup bossgroup) {
		super(x, y, mit);
		HITTED = false;
		hp = HPMAX;
		//hp = 1;
		pos = new Vector2[4];
		this.bossgroup = bossgroup;
		this.rangetype = HIT_RANGE_TYPE.SQUARE;
		for(int i=0;i<pos.length;i++)
		{
			pos[i] = new Vector2();
		}
	}

	@Override
	public void Update() {
		cnt++;
		pos[0].set((float)x-(WIDTH*3/2),(float)y-(HEIGHT*3/2));
		pos[1].set((float)x+(WIDTH*3/2),(float)y-(HEIGHT*3/2));
		pos[2].set((float)x+(WIDTH*3/2),(float)y+(HEIGHT*3/2));
		pos[3].set((float)x-(WIDTH*3/2),(float)y+(HEIGHT*3/2));
		pos = DrawFuncs.RotatePoints(pos, new Vector2(this.x, this.y), (float)(angle+Math.toRadians(180.0)));
		switch(bossstate)
		{
		case APPEAR:
			tmpvec = Utls.GetSinMove(new Vector2(mit.WINDOW_W/2, mit.WINDOW_H+100), 
					new Vector2(mit.WINDOW_W/2, mit.WINDOW_H*3/4), cnt, 100);
			angle = (float)Math.PI;
			x = tmpvec.x;
			y = tmpvec.y;
			if(cnt>100)
				bossgroup.setBossState(BOSSSTATE.RELEASE_OPTION);
			break;
		//100
		case RELEASE_OPTION:
			if(cnt>160)
				bossgroup.setBossState(BOSSSTATE.OPTION_FIRE);
			break;
		//160
		case OPTION_FIRE:
			if(cnt>500)
				bossgroup.setBossState(BOSSSTATE.SIN_FIRE);
			break;
		case SIN_FIRE:
			x = mit.WINDOW_W/2 + Math.sin(Math.toRadians((cnt-tmpcnt)*3))*mit.WINDOW_W*1/4;
			if(cnt%5==0)
			{
				mit.AddEnemyBullet(new Bubble(x+90, y-30, Math.toRadians(90), 14f, 10, 100, mit));
				mit.AddEnemyBullet(new Bubble(x-90, y-30, Math.toRadians(90), 14f, 10, 100, mit));
			}
			if(cnt>800)
				bossgroup.setBossState(BOSSSTATE.MOUNT_OPTION);
			break;
		case MOUNT_OPTION:
			if(cnt>870)
				bossgroup.setBossState(BOSSSTATE.BOSS_FIRE);
			break;
		case BOSS_FIRE:
			if(900<cnt && cnt<1200 && cnt%2==0)
			{
				mit.AddEnemyBullet(new Bubble(x+90, y-100, Math.toRadians(45+Math.sin((cnt-900)*3)*40), 13, 30, 30, mit));
				mit.AddEnemyBullet(new Bubble(x-90, y-100, Math.toRadians(135+Math.sin((cnt-900-180)*3)*40), 13, 30, 30, mit));
			}
			if(cnt>1300)
			{
				bossgroup.setBossState(BOSSSTATE.RELEASE_OPTION);
				bossgroup.ResetLoop();
			}
			break;
		case BREAKUP:
			break;
		default:
			break;
		}
	}

	@Override
	public void Draw(GL11 gl) {
		if(HITTED)
		{
			mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
					(int)(x-WIDTH*3/2), (int)(y-HEIGHT*3/2), WIDTH*3, HEIGHT*3, UV_X, UV_Y, WIDTH, HEIGHT, 1f, 1f, 1f, 1f, (float)Math.toDegrees(angle), BLENDTYPE.XOR);
		}
		else
		{
			mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
					(int)(x-WIDTH*3/2), (int)(y-HEIGHT*3/2), WIDTH*3, HEIGHT*3, UV_X, UV_Y, WIDTH, HEIGHT, 1f, 1f, 1f, 1f, (float)Math.toDegrees(angle), BLENDTYPE.ALPHA);
		}
		HITTED = false;
	}

	public void setState(BOSSSTATE bossstate)
	{
		if(bossstate==BOSSSTATE.SIN_FIRE)
		{
			tmpcnt = cnt;
		}
		this.bossstate = bossstate;
	}
	
	public void ResetLoop()
	{
		cnt=100;
	}
	
	@Override
	public void Hitted(HIT_TYPE type, float damage) {
		switch(type)
		{
		case PLAYER_BULLET:
			if(!Remove_OK)
			{
				if(!HITTED)mit.GetLoadResource().PlaySound(mit.GetLoadResource().KASURI);
				HITTED = true;
				this.hp -= damage;
				mit.SetEnemyDamageRatio((float)hp/(float)HPMAX);
				if(this.hp<0)
				{
					mit.AddScore(1000);
					mit.AddDestroyedEnemyCnt();
					mit.AddOrnament(new BangEffect((float)x, (float)y, mit, null));
					mit.AddOrnament(new DrawMiniNum((float)x, (float)y, mit, mit.GetDestroyedEnemyCnt()));
					mit.GetLoadResource().PlaySound(mit.GetLoadResource().EXPLODE_1);
					Remove_OK = true;
					bossgroup.setBossState(BOSSSTATE.BREAKUP);
				}
			}
			break;
		}
	}

	@Override
	public boolean IsLeaveScreen() {
		return Remove_OK;
	}

	@Override
	public boolean IsEnable() {return !Remove_OK;}
	@Override
	public float GetDamage() {return 0;}

}

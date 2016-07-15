package info.khyh.opengltest.GameClass.Enemy;

import javax.microedition.khronos.opengles.GL11;

import info.khyh.opengltest.DrawLibrary.Utls;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.EnemyBullet.Bubble;
import info.khyh.opengltest.GameClass.Ornament.BangEffect;
import info.khyh.opengltest.GameClass.Stage1.BOSSGroup.BOSSSTATE;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;
import info.khyh.opengltest.Library.Vector2;

public class BOSSOption extends Enemy{
	private final int UV_X = 11,
					  UV_Y = 394,
					  WIDTH = 48,
					  HEIGHT = 45;
	private int cnt,breakcnt,departcnt;
	private double distx, disty;
	private BOSSSTATE bossstate;
	private BOSS boss;
	private float playerangle;
	private Vector2 tmpvec, releasepoint, departpoint, breakpoint;

	public BOSSOption(double distx, double disty, Vector2 releasepoint, Mediator mit, BOSS boss) {
		super(boss.x+distx, boss.y+disty, mit);
		this.boss = boss;
		this.distx = distx;
		this.disty = disty;
		this.releasepoint = releasepoint;
	}

	@Override
	public void Update() {
		cnt++;
		switch(bossstate)
		{
		case APPEAR:
			x = boss.x + distx;
			y = boss.y + disty;
			break;
		case RELEASE_OPTION:
			tmpvec = Utls.GetSinMove(departpoint, releasepoint, cnt-100, 60);
			x = tmpvec.x;
			y = tmpvec.y;
			break;
		case OPTION_FIRE:
			if(cnt==170)
				mit.AddEnemyBullet(new Bubble(x, y, mit.GetPlayerAngle((int)y, (int)x), 10.0f, 10, 200, mit));
			if(cnt==220)
				mit.AddEnemyBullet(new Bubble(x, y, mit.GetPlayerAngle((int)y, (int)x), 10.0f, 10, 200, mit));
			if(cnt==270)
				mit.AddEnemyBullet(new Bubble(x, y, mit.GetPlayerAngle((int)y, (int)x), 10.0f, 10, 200, mit));
			if(cnt==290)
				playerangle = (float) mit.GetPlayerAngle((int)y, (int)x);
			if(290<cnt && cnt<350 && cnt%5==0)
				mit.AddEnemyBullet(new Bubble(x,y, playerangle, 10.0f, 50, 50, mit));
			if(cnt==380)
				playerangle = (float) mit.GetPlayerAngle((int)y, (int)x);
			if(380<cnt && cnt<480 && cnt%5==0)
				mit.AddEnemyBullet(new Bubble(x,y, playerangle, 10.0f, 50, 50, mit));
			break;
		case MOUNT_OPTION:
			tmpvec = Utls.GetSinMove(departpoint, new Vector2(boss.x+distx,boss.y+disty), cnt-departcnt, 60);
			x = tmpvec.x;
			y = tmpvec.y;
			break;
		case SIN_FIRE:
			if(cnt%50==0)
			{mit.AddEnemyBullet(new Bubble(x, y, mit.GetPlayerAngle((int)y, (int)x), 10.0f, 10, 200, mit));}
			break;
		case BREAKUP:
			if(mit.GetPlayerPos()!=null)
			{
				tmpvec = Utls.GetSinMove(breakpoint, mit.GetPlayerPos(), cnt-breakcnt, 60);
				x = tmpvec.x;
				y = tmpvec.y;
				if(cnt-breakcnt==60)
				{
					mit.AddOrnament(new BangEffect((float)x, (float)y, mit, null));
					Remove_OK = true;
					mit.GetLoadResource().PlaySound(mit.GetLoadResource().EXPLODE_2);
				}
			}
			break;
		case BOSS_FIRE:
			if(900<cnt && cnt<1200 && cnt%50==0)
			{
				for(int i=0;i<10;i++)
				{
					mit.AddEnemyBullet(new Bubble(x, y, mit.GetPlayerAngle((int)y, (int)x), 13+0.8*i, 30, 30, mit));
				}
			}
			break;
		default:
			break;
		}
	}
	
	public void ResetLoop()
	{
		cnt=100;
	}
	
	public void setState(BOSSSTATE b)
	{
		if(b==BOSSSTATE.RELEASE_OPTION)
		{
			departpoint = new Vector2(x,y);
		}
		if(b==BOSSSTATE.BREAKUP)
		{
			breakpoint = new Vector2(x,y);
			breakcnt = cnt;
		}
		if(b==BOSSSTATE.MOUNT_OPTION)
		{
			departpoint = new Vector2(x,y);
			departcnt = cnt;
		}
		this.bossstate = b;
	}

	@Override
	public void Draw(GL11 gl) {
		int phase = 15*cnt%120;
		int phase2 = 15*(cnt+60)%120;
		mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
				(int)(x-WIDTH), (int)(y-HEIGHT), WIDTH*2, HEIGHT*2, UV_X, UV_Y, WIDTH, HEIGHT, 0);
		mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
				(int)(x-(WIDTH*2+phase)/2), (int)(y-(HEIGHT*2+phase)/2), WIDTH*2+phase, HEIGHT*2+phase, UV_X, UV_Y, WIDTH, HEIGHT, 1f, 1f, 1f, 0.5f, 0, BLENDTYPE.ALPHA);
		mit.GetGraphics().drawImage(mit.GetLoadResource().CHARACTER, mit.GetDrawMain().aspect, 
				(int)(x-(WIDTH*2+phase2)/2), (int)(y-(HEIGHT*2+phase2)/2), WIDTH*2+phase2, HEIGHT*2+phase2, UV_X, UV_Y, WIDTH, HEIGHT, 1f, 1f, 1f, 0.5f, 0, BLENDTYPE.ALPHA);
	}

	@Override
	public void Hitted(HIT_TYPE type, float damage) {}
	@Override
	public boolean IsLeaveScreen() {return Remove_OK;}
	@Override
	public boolean IsEnable() {return false;}
	@Override
	public float GetDamage() {return 0;}

}

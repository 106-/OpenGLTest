package info.khyh.opengltest.GameClass;

import info.khyh.opengltest.DrawLibrary.DrawMain;
import info.khyh.opengltest.GameClass.BackGround.BackGround2DOrnament;
import info.khyh.opengltest.GameClass.BackGround.BackGround3DOrnament;
import info.khyh.opengltest.GameClass.BackGround.BackGroundManage;
import info.khyh.opengltest.GameClass.Enemy.Enemy;
import info.khyh.opengltest.GameClass.Enemy.EnemyManage;
import info.khyh.opengltest.GameClass.EnemyBullet.EnemyBullet;
import info.khyh.opengltest.GameClass.EnemyBullet.EnemyBulletManage;
import info.khyh.opengltest.GameClass.Ornament.Ornament;
import info.khyh.opengltest.GameClass.Ornament.OrnamentManage;
import info.khyh.opengltest.GameClass.Player.Player;
import info.khyh.opengltest.GameClass.Player.PlayerManage;
import info.khyh.opengltest.GameClass.PlayerBullet.PlayerBullet;
import info.khyh.opengltest.GameClass.PlayerBullet.PlayerBulletManage;
import info.khyh.opengltest.GameClass.Stage.Stage;
import info.khyh.opengltest.Library.Graphics;
import info.khyh.opengltest.Library.Vector2;

public class Mediator {
	private PlayerManage pm;
	private PlayerBulletManage pbm;
	private EnemyManage em;
	private EnemyBulletManage ebm;
	private LoadResource ld;
	private DrawMain dm;
	private OrnamentManage om;
	private BackGroundManage bagm;
	private Stage s;
	
	public boolean Warnisalive;
	
	public final int WINDOW_W,WINDOW_H;
	public final int CHAGECNTMAX = 300;
	
	private Vector2 old_v;
	
	public Mediator(PlayerManage pm,
					PlayerBulletManage pbm,
					EnemyManage em,
					EnemyBulletManage ebm,
					OrnamentManage om,
					BackGroundManage bagm,
					LoadResource ld,
					DrawMain dm,
					Stage s)
	{
		this.pm = pm;
		this.pbm = pbm;
		this.em = em;
		this.ebm = ebm;
		this.om = om;
		this.ld = ld;
		this.dm = dm;
		this.bagm = bagm;
		this.WINDOW_W = dm.WINDOW_W;
		this.WINDOW_H = dm.WINDOW_H;
		this.s = s;
		old_v = new Vector2();
		Warnisalive = false;
	}
	
	//Add系はすべてマネージャにインスタンスを追加する
	public void AddPlayer(Player p){pm.Add(p);}
	public void AddEnemy(Enemy e){em.Add(e);}
	public void AddPlayerBullet(PlayerBullet pb){pbm.Add(pb);}
	public void AddEnemyBullet(EnemyBullet eb){ebm.Add(eb);}
	public void AddOrnament(Ornament or){om.Add(or);}
	public void AddBackGroundOrnament(BackGround2DOrnament bg2do){bagm.Add(bg2do);}
	public void AddBackGroundOrnament(BackGround3DOrnament bg3do){bagm.Add(bg3do);}
	
	//Get系
	public LoadResource GetLoadResource(){return ld;}
	public Graphics GetGraphics(){return dm.G;}
	public DrawMain GetDrawMain(){return dm;}
	public Vector2 GetTouchPos(){return dm.GetTouchPos();}
	public Vector2 GetPlayerPos(){return pm.GetPlayerPos();}
	public int GetCnt(){return s.GetCnt();}
	public int GetScore(){return s.s.GetScore();}
	public int GetRemainPlayer(){return s.s.GetRemainPlayer();}
	
	public void PlayerDestroyed(){s.s.PlayerDestroyed();}
	public void GameHasOver(){s.GAMEOVER = true;}
	public void AddScore(int n){s.s.AddScore(n);}
	public void AddDestroyedEnemyCnt(){s.DestoryedEnemyCnt++;}
	public void ResetDestroyedEnemyCnt(){s.DestoryedEnemyCnt=0;}
	public int GetDestroyedEnemyCnt(){return s.DestoryedEnemyCnt;}
	public int GetChargeCnt(){return s.Chargecnt;}
	public void AddChargeCnt(){s.Chargecnt++;if(s.Chargecnt>CHAGECNTMAX)s.Chargecnt=CHAGECNTMAX;}
	public void SubChargeCnt(int n){s.Chargecnt-=n;}
	public void ChargeCntReset(){s.Chargecnt=0;}
	public double GetEnemyDamageRatio(){return s.EnemyDamageRatio;}
	public void SetEnemyDamageRatio(double ratio){s.EnemyDamageRatio=ratio;}
	
	//キャラへの角度を返す
	public double GetPlayerAngle(int y, int x)
	{
		Vector2 v = pm.GetPlayerPos();
		if(v!=null)
		{
			old_v = v;
			return Math.atan2(y-v.y, x-v.x)*-1+Math.PI;
		}
		return Math.atan2(y-old_v.y, x-old_v.x)*-1+Math.PI;
	}
}

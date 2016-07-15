package info.khyh.opengltest.GameClass.Stage;

import javax.microedition.khronos.opengles.GL11;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import info.khyh.opengltest.MainActivity;
import info.khyh.opengltest.DrawLibrary.DrawMain;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Score;
import info.khyh.opengltest.GameClass.BackGround.BackGroundManage;
import info.khyh.opengltest.GameClass.Enemy.EnemyManage;
import info.khyh.opengltest.GameClass.EnemyBullet.EnemyBulletManage;
import info.khyh.opengltest.GameClass.Ornament.OrnamentManage;
import info.khyh.opengltest.GameClass.Player.PlayerManage;
import info.khyh.opengltest.GameClass.PlayerBullet.PlayerBulletManage;

public abstract class Stage {
	protected DrawMain dm;
	protected Mediator mit;
	
	protected PlayerBulletManage pbm;
	protected PlayerManage pm;
	protected EnemyBulletManage ebm;
	protected EnemyManage em;
	protected OrnamentManage om;
	protected BackGroundManage bagm;
	
	protected Context cxt;
	
	public Score s;
	public boolean GAMEOVER;
	public boolean PAUSE;
	
	//���̍U���łǂꂾ���̓G��|�����̂��̕ϐ�
	public int DestoryedEnemyCnt;
	
	//�����Ɏg����i�͂��j�̗��߃J�E���g
	public int Chargecnt;
	
	//攻撃している敵の残りHPの割合
	public double EnemyDamageRatio;
	
	public Stage(DrawMain dm, Context cxt)
	{
		this.dm = dm;
		this.cxt = cxt;
		cnt=0;
		f = new long[60];
	}
	
	public abstract void Draw(GL11 gl);
	public abstract void Update();
	public abstract void OnTouchEvent(MotionEvent e);
	public abstract void OnKey(int keyCode, KeyEvent event);
	public abstract void OnPause();
	public abstract void OnResume();
	public int GetCnt(){return cnt;}
	public void OnGameOver(){dm.OnGameOver();}
	
	private long f[];
	private long old_t,Time,Now_fps=0;
	protected int cnt;
    protected void fps() {
        int i;
        int ave = 0;
        f[cnt % 60] = System.currentTimeMillis() - old_t;
        old_t = System.currentTimeMillis();
        if (cnt % 60 == 59)
        {
            ave = 0;
            for (i = 0; i < 60; i++)
                ave += f[i];
            ave /= 60;
            Log.v(MainActivity.Tag, String.valueOf(1000.0/ave));
        }
    }
}

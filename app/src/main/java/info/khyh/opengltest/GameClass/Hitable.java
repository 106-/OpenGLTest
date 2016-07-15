package info.khyh.opengltest.GameClass;

import info.khyh.opengltest.Library.Vector2;

import javax.microedition.khronos.opengles.GL11;

public abstract class Hitable {
	public double x,y,range,angle;
	protected Vector2[] pos;
	protected Mediator mit;
	protected HIT_TYPE hittype;
	protected HIT_RANGE_TYPE rangetype;
	protected boolean Remove_OK;
	
	public enum HIT_TYPE
	{
		PLAYER,
		PLAYER_BULLET,
		ENEMY,
		ENEMY_BULLET
	}
	
	public enum HIT_RANGE_TYPE
	{
		CIRCLE,
		SQUARE,
		NONE,
	}
	
	public Hitable(double x, double y, Mediator mit)
	{
		this.x = x;
		this.y = y;
		this.mit = mit;
		this.Remove_OK = false;
	}
	
	//位置等の更新
	public abstract void Update();
	//描画
	public abstract void Draw(GL11 gl); 
	//ヒット時に呼ばれるハンドラ
	public abstract void Hitted(HIT_TYPE type, float damage);
	//画面から離れたか or 無効になったか
	public abstract boolean IsLeaveScreen();
	//物体が有効かどうか
	public abstract boolean IsEnable();
	//その物体がほかの物体に与える影響
	public abstract float GetDamage();
	
}

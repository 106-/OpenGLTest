package info.khyh.opengltest.GameClass;

import android.util.Log;
import info.khyh.opengltest.MainActivity;
import info.khyh.opengltest.GameClass.Hitable.HIT_RANGE_TYPE;
import info.khyh.opengltest.GameClass.Enemy.EnemyGroup;
import info.khyh.opengltest.GameClass.Enemy.EnemyManage;
import info.khyh.opengltest.GameClass.EnemyBullet.EnemyBulletManage;
import info.khyh.opengltest.GameClass.Player.PlayerManage;
import info.khyh.opengltest.GameClass.PlayerBullet.PlayerBulletManage;
import info.khyh.opengltest.Library.Vector2;
import info.khyh.opengltest.Library.VectorUtls;

public class HitCheck {
	private PlayerManage pm;
	private PlayerBulletManage pbm;
	private EnemyManage em;
	private EnemyBulletManage ebm;
	
	public HitCheck(PlayerManage pm, 
					PlayerBulletManage pbm, 
					EnemyManage em,
					EnemyBulletManage ebm)
	{
		this.pm = pm;
		this.pbm = pbm;
		this.em = em;
		this.ebm = ebm;
	}
	
	public void Check()
	{
		//敵弾と自機との当たり判定
		for(int i=0;i<pm.GetList().length;i++)
		{
			if(pm.GetList()[i]==null || !pm.GetList()[i].IsEnable())continue;
			for(int n=0;n<ebm.GetList().length;n++)
			{
				if(ebm.GetList()[n]==null || !ebm.GetList()[n].IsEnable())continue;
				if(CollisionChk(pm.GetList()[i], ebm.GetList()[n]))
				{
					pm.GetList()[i].Hitted(ebm.GetList()[n].hittype, ebm.GetList()[n].GetDamage());
					ebm.GetList()[n].Hitted(pm.GetList()[i].hittype, pm.GetList()[i].GetDamage());
				}
			}
		}
		//自弾と敵との当たり判定
		for(int i=0;i<pbm.GetList().length;i++)
		{
			if(pbm.GetList()[i]==null || !pbm.GetList()[i].IsEnable())continue;
			for(int n=0;n<em.GetList().length;n++)
			{
				if(em.GetList()[n]==null || !em.GetList()[n].IsEnable())continue;
				if(em.GetList()[n] instanceof EnemyGroup)
				{
					for(Hitable h:((EnemyGroup)em.GetList()[n]).childs)
					{
						if(h==null)continue;
						if(CollisionChk(h, pbm.GetList()[i]))
						{
							pbm.GetList()[i].Hitted(h.hittype, h.GetDamage());
							h.Hitted(pbm.GetList()[i].hittype, pbm.GetList()[i].GetDamage());
						}
					}
					continue;
				}
				if(CollisionChk(pbm.GetList()[i], em.GetList()[n]))
				{
					pbm.GetList()[i].Hitted(em.GetList()[n].hittype, em.GetList()[n].GetDamage());
					em.GetList()[n].Hitted(pbm.GetList()[i].hittype, pbm.GetList()[i].GetDamage());
				}
			}
		}
	}
	
	private boolean CollisionChk(Hitable a, Hitable b)
	{
		if(a.rangetype == HIT_RANGE_TYPE.SQUARE)
		{
			if(b.rangetype == HIT_RANGE_TYPE.SQUARE)
				return Collision_S_to_S(a, b);
			else if(b.rangetype == HIT_RANGE_TYPE.CIRCLE)
				return Collision_S_to_C(a, b);
		}
		if(a.rangetype == HIT_RANGE_TYPE.CIRCLE)
		{
			if(b.rangetype == HIT_RANGE_TYPE.SQUARE)
				return Collision_S_to_C(b, a);
			else if(b.rangetype == HIT_RANGE_TYPE.CIRCLE)
				return Collision_C_to_C(a, b);
		}
		return false;
	}
	
	//四角形と円の当たり判定
	private boolean Collision_S_to_C(Hitable square, Hitable circle)
	{
		//円の中に四角形の頂点がある場合
		for(int i=0;i<4;i++)
		{
			double xv = square.pos[i].x - circle.x,
					yv = square.pos[i].y - circle.y,
					r = circle.range;
			if(xv*xv + yv*yv < r*r)
			{
				//Log.v(MainActivity.Tag,"円の中に四角形の頂点がある場合");
				return true;
			}
		}
		//四角形の中に円の中心がある場合
		if(Hit_Rect_Point(square.pos, new Vector2(circle.x, circle.y)))
		{
			//Log.v(MainActivity.Tag,"四角形の中に円の中心がある場合");
			return true;
		}
		//四角形の辺と円が接触している場合
		for(int i=0;i<4;i++)
		{
			int s = i;
			int e = (i==3)? 0 : i+1;
			Vector2 startv = new Vector2(square.pos[s].x, square.pos[s].y);
			Vector2 endv = new Vector2(square.pos[e].x, square.pos[e].y);
			if(Distanse_Point_Vector2(startv, endv, new Vector2(circle.x,circle.y), circle.range))
			{
				//Log.v(MainActivity.Tag, "四角形の辺と円が接触している場合");
				return true;
			}
		}
		return false;
	}
	
	//円同士の当たり判定
	private boolean Collision_C_to_C(Hitable a, Hitable b)
	{
		return ((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y) < (a.range+b.range)*(a.range*b.range));
	}
	
	//四角形同士の当たり判定
	private boolean Collision_S_to_S(Hitable a, Hitable b)
	{
		if(a.pos == null || b.pos == null){return false;}
		for(int i=0;i<4;i++)
		{
			if(Hit_Rect_Point(a.pos, b.pos[i]))return true;
			if(Hit_Rect_Point(b.pos, a.pos[i]))return true;
		}
		return false;
	}
	
	//四角形と点の当たり判定
	private boolean Hit_Rect_Point(Vector2 rect[], Vector2 point) {
		int cnt = 0;
		for (int i = 0; i < 4; i++) {
			float x1 = rect[(i==3)?0:i+1].x - rect[i].x;
			float y1 = rect[(i==3)?0:i+1].y - rect[i].y;
			float x2 = point.x - rect[i].x;
			float y2 = point.y - rect[i].y;
			if (x1 * y2 - x2 * y1 < 0) {
				cnt++;
			} else {
				cnt--;
			}
		}
		return (cnt == 4 || cnt == -4);
	}
	
	//辺と点との距離を測る
	private boolean Distanse_Point_Vector2(Vector2 startv, Vector2 endv,  Vector2 point, double circle_range)
	{
		Vector2 A = new Vector2(point.x - startv.x, point.y - startv.y);
		Vector2 S = new Vector2(endv.x - startv.x, endv.y - startv.y);
		Vector2 B = new Vector2(point.x - endv.x, point.y - endv.y);
		float d = VectorUtls.cross(S, A) / S.length();
		if(Math.abs(d)<circle_range)
		{
			if(VectorUtls.dot(A, S)*VectorUtls.dot(B, S)<0)
				return true;
		}
		return false;
	}
	
}

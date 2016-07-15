package info.khyh.opengltest.GameClass.Enemy;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL11;

import info.khyh.opengltest.GameClass.Hitable;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.Hitable.HIT_TYPE;

public abstract class EnemyGroup extends Enemy{
	protected boolean AllDestroyed;
	protected int cnt;
	public Hitable childs[];
	
	public EnemyGroup(double x, double y, Mediator mit) {
		super(x, y, mit);
		childs = null;
		AllDestroyed=false;
		this.rangetype = HIT_RANGE_TYPE.NONE;
	}

	@Override
	public void Update() {
		for(int i=0;i<childs.length;i++)
		{
			if(childs[i]==null)continue;
			childs[i].Update();
			if(childs[i].IsLeaveScreen())
			{
				childs[i]=null;
			}
		}
		int nullcnt=0;
		for(int i=0;i<childs.length;i++)
			if(childs[i]==null)
				nullcnt++;
		if(nullcnt==childs.length)
			AllDestroyed=true;
		cnt++;
	}

	@Override
	public void Draw(GL11 gl) {
		for(Hitable h:childs)
		{
			if(h==null)continue;
			h.Draw(gl);
		}
	}
	
	@Override
	public boolean IsEnable() {
		return true;
	}

	@Override
	public float GetDamage() {
		return 0;
	}
	
	@Override
	public void Hitted(HIT_TYPE type, float damage) {
		//意味無
	}
}

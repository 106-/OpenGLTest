package info.khyh.opengltest.GameClass.Enemy.Aircraft;

import java.util.List;

import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.DrawLibrary.Utls;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.Library.Vector2;

public class LinerAircraft extends Aircraft {
	//線形移動な小型機

	protected int cnt,poscnt,div;
	protected List<Vector2> poslst;
	protected Vector2 StartVector,EndVector;
	
	public LinerAircraft(double x, double y, Mediator mit, int div, List<Vector2> poslst) {
		super(x, y, mit);
		this.poslst = poslst;
		this.div = div;
		this.rangetype = rangetype.SQUARE;
		StartVector = new Vector2();
		EndVector = new Vector2();
		PosUpdate();
	}

	private void NodeUpdate()
	{
		if(poscnt==poslst.size()-1)
		{
			Remove_OK = true;
		}
		else
		{
			StartVector = poslst.get(poscnt);
			EndVector = poslst.get(poscnt+1);
			poscnt++;
		}
	}
	
	protected void PosUpdate()
	{
		Vector2 v = Utls.GetLinerMove(StartVector, EndVector, cnt%div, div);
		double old_x = this.x, old_y = this.y;
		this.x = v.x;
		this.y = v.y;
		this.angle = Math.atan2(y-old_y, x-old_x);
	}
	
	@Override
	public void Update() {
		pos = new Vector2[]
				{
				new Vector2((float)x-(WIDTH),(float)y-(HEIGHT)),
				new Vector2((float)x+(WIDTH),(float)y-(HEIGHT)),
				new Vector2((float)x-(WIDTH),(float)y+(HEIGHT)),
				new Vector2((float)x+(WIDTH),(float)y+(HEIGHT))
				};
		pos = DrawFuncs.RotatePoints(pos, new Vector2(this.x, this.y), (float)(angle+Math.toRadians(180.0)));
		if(cnt%div==0)
			NodeUpdate();
		PosUpdate();
		cnt++;
	}

	@Override
	public boolean IsEnable() {
		return true;
	}

	@Override
	public float GetDamage() {
		return 0;
	}

}

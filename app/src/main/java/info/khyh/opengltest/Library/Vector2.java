package info.khyh.opengltest.Library;

public class Vector2 {
	public float x = 0.0f;
	public float y = 0.0f;
	
	public Vector2(){}
	
	public Vector2(float x, float y){set(x,y);}
	public Vector2(double x, double y){set((float)x,(float)y);}
	
	public void set(Vector2 orig)
	{
		x = orig.x;
		y = orig.y;
	}
	
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(double x, double y)
	{
		set((float)x, (float)y);
	}
	
	public Vector2 ScalarMultiply(double multi)
	{
		return new Vector2(this.x*multi, this.y*multi);
	}
	
	//�x�N�g���̒���
	public float length()
	{return (float)Math.sqrt((double)(x*x+y*y));}
	
	//�x�N�g���̐��K��
	public Vector2 normed()
	{
		float len = this.length();
		return new Vector2(this.x/len, this.y/len);
	}
	
	//��r
	@Override
	public boolean equals(Object o)
	{
		Vector3 v = (Vector3)o;
		return (v.x==x && v.y==y);
	}
}

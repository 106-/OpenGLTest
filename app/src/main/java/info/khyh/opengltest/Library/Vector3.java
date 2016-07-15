package info.khyh.opengltest.Library;

public class Vector3 {
	public float x = 0.0f;
	public float y = 0.0f;
	public float z = 0.0f;
	
	public Vector3(){}
	
	public Vector3(float x, float y, float z){set(x,y,z);}
	
	public void set(Vector3 orig)
	{
		x = orig.x;
		y = orig.y;
		z = orig.z;
	}
	
	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	//ベクトルの長さ
	public float length()
	{return (float)Math.sqrt((double)(x*x+y*y+z*z));}
	
	//ベクトルの正規化
	public Vector3 normed()
	{
		float len = this.length();
		return new Vector3(this.x/len, this.y/len, this.z/len);
	}
	
	//比較
	@Override
	public boolean equals(Object o)
	{
		Vector3 v = (Vector3)o;
		return (v.x==x && v.y==y && v.z==z);
	}
	
}

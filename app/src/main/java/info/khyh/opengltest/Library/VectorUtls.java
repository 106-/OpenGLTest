package info.khyh.opengltest.Library;

public class VectorUtls {
	//�x�N�g���̉��Z������N���X
	
	//�X�J���[�{
	public static Vector3 multiple(Vector3 v, float times)
	{return new Vector3(v.x * times, v.y * times, v.z * times);}
	public static Vector2 multiple(Vector2 v, float times)
	{return new Vector2(v.x * times, v.y * times);}
	
	//����
	public static float dot(Vector3 v0, Vector3 v1)
	{return (v0.x*v1.x)+(v0.y*v1.y)+(v0.z*v1.z);}
	public static float dot(Vector2 v0, Vector2 v1)
	{return v0.x*v1.x + v0.y*v1.y;}
	
	//�O��
	public static Vector3 cross(Vector3 v0, Vector3 v1)
	{
		return new Vector3((v0.y*v1.z)-(v0.z*v1.y), 
							(v0.z*v1.x)-(v0.x*v1.z), 
							(v0.x*v1.y)-(v0.y*v1.x));
	}
	public static float cross(Vector2 v0, Vector2 v1)
	{return v0.x * v1.y - v0.y * v1.x;}

	//�a
	public static Vector3 add(Vector3 v0, Vector3 v1)
	{return  new Vector3(v0.x + v1.x, v0.y + v1.y, v0.z + v1.z);}
	public static Vector2 add(Vector2 v0, Vector2 v1)
	{return new Vector2(v0.x+v1.x,v0.y+v1.y);}
	
	//��
	public static Vector3 sub(Vector3 v0, Vector3 v1)
	{return new Vector3(v0.x - v1.x,v0.y - v1.y,v0.z - v1.z);}
	public static Vector2 sub(Vector2 v0, Vector2 v1)
	{return new Vector2(v0.x-v1.x,v0.y-v1.y);}
	
}

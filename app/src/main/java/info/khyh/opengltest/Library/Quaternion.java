package info.khyh.opengltest.Library;

public class Quaternion {
    public float t;
    public Vector3 vec;
    
	public Quaternion(Vector3 v, float t)
	{
		this.t = t;
		this.vec = v;
	}
	
	public Quaternion(Vector3 v)
	{
		this.t = 0;
		this.vec = v;			
	}
	
	//クォータニオンの任意軸回転
	//Point : 回転の中心軸
	//v		: 回転する位置ベクトル	のはず
	public static Vector3 Quat_Rota(Vector3 Point, Vector3 v, double ang)
    {
        Vector3 ans;
        Vector3 tmp_v = new Vector3();
        Quaternion R, P, Q, buf;
        double co = Math.cos(ang / 2.0), si = Math.sin(ang / 2);
        
        //正規化
        v=v.normed();
        
        //クォータニオンに変換
        P = new Quaternion(Point);
        
        tmp_v = new Vector3((float)(v.x * si), (float)(v.y * si), (float)(v.z * si));
        Q = new Quaternion(tmp_v,(float)co);

        tmp_v = new Vector3((float)(-v.x * si), (float)(-v.y * si), (float)(-v.z * si));
        R = new Quaternion(tmp_v,(float)co);

        buf = quat_mul(quat_mul(R, P), Q);
        ans = buf.vec;

        return ans;
    }
	
	public static Vector2 Quat_Rota(Vector2 Point, double ang)
	{
		Vector3 rotav = Quat_Rota(new Vector3(Point.x,Point.y,0), new Vector3(0,0,1), ang);
		return new Vector2(rotav.x,rotav.y);
	}
	
	//クォータニオンの掛け算
	public static Quaternion quat_mul(Quaternion q1, Quaternion q2)
    {
        float t = q1.t * q2.t - VectorUtls.dot(q1.vec, q2.vec);
        Vector3 v = VectorUtls.add(VectorUtls.add(VectorUtls.multiple(q2.vec, q1.t), 
        		VectorUtls.multiple(q1.vec, q2.t)), VectorUtls.cross(q1.vec, q2.vec));
        return new Quaternion(v, t);
    }
}

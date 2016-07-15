package info.khyh.opengltest.DrawLibrary;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import info.khyh.opengltest.MainActivity;
import info.khyh.opengltest.GameClass.Mediator;
import android.widget.EditText;
import info.khyh.opengltest.Library.Vector2;
import info.khyh.opengltest.Library.Vector3;

public class Utls {
	private Utls(){}
	private static String name;
	public static final String DESC_URL = "http://damned.y.ribbon.to/desc.html",
								RANKING_URL = "https://powerful-dawn-9158.herokuapp.com/show/1",
								RANKING_POST_URL = "https://powerful-dawn-9158.herokuapp.com/add/",
								RANKING_ID = "1";
	
	//�x�W�F�Ȑ�̌v�Z
	static public Vector2 GetBezier(Vector2 start, Vector2 end, Vector2 ctrl, float div, int cnt)
	{
		float i = (1.0f/div)*cnt;
		float px = (1.0f-i)*start.x+i*ctrl.x;
		float py = (1.0f-i)*start.y+i*ctrl.y;
		float mx = (1.0f-i)*ctrl.x+i*end.x;
		float my = (1.0f-i)*ctrl.y+i*end.y;
		return new Vector2((1.0f-i)*px+i*mx, (1.0f-i)*py+i*my);
	}
	
	//����ړ���������Čv�Z
	static public Vector2 GetLinerMove(Vector2 start, Vector2 end, int param, int div)
	{
		float ratio = (float)param / (float)div;
		float vecx = end.x - start.x;
		float vecy = end.y - start.y;
		float velx = start.x+vecx*ratio;
		float vely = start.y+vecy*ratio;
		//Log.v(MainActivity.Tag,String.format("ratio:%s vecx:%s vecy:%s velx:%s vely:%s",ratio, vecx, vecy, velx, vely));
		return new Vector2(velx, vely);
	}
	
	static public Vector3 GetLinerMove(Vector3 start, Vector3 end, int param, int div)
	{
		float ratio = (float)param / (float)div;
		float vecx = end.x - start.x;
		float vecy = end.y - start.y;
		float vecz = end.z - start.z;
		float velx = start.x+vecx*ratio;
		float vely = start.y+vecy*ratio;
		float velz = start.z+vecz*ratio;
		//Log.v(MainActivity.Tag,String.format("ratio:%s vecx:%s vecy:%s velx:%s vely:%s",ratio, vecx, vecy, velx, vely));
		return new Vector3(velx, vely, velz);
	}
	
	static public Vector2 GetSinMove(Vector2 start, Vector2 end, int param, int div)
	{
		float ratio = (float)param / (float)div;
		float vecx = end.x - start.x;
		float vecy = end.y - start.y;
		//�ʑ�
		float phase = (float)(Math.sin(Math.PI/2 * ratio));
		return new Vector2(start.x+vecx*phase, start.y+vecy*phase);
	}
	
	static public Vector3 GetSinMove(Vector3 start, Vector3 end, int param, int div)
	{
		float ratio = (float)param / (float)div;
		float vecx = end.x - start.x;
		float vecy = end.y - start.y;
		float vecz = end.z - start.z;
		//�ʑ�
		float phase = (float)(Math.sin(Math.PI/2 * ratio));
		return new Vector3(start.x+vecx*phase, start.y+vecy*phase, start.z+vecz*phase);
	}
	
	static public Vector2 VerifyPos(Vector2 vec, Mediator mit)
	{
		if(vec.x<0)
			vec.x=0;
		if(mit.WINDOW_W<vec.x)
			vec.x=mit.WINDOW_W;
		if(vec.y<0)
			vec.y=0;
		if(mit.WINDOW_H<vec.y)
			vec.y=mit.WINDOW_H;
		return vec;
	}
	
	static public void POSTscore(String name, int score, Context cxt)
	{
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(cxt);
		boolean useproxy = sp.getBoolean("proxycheckbox", false);
		String proxystring = sp.getString("proxystring", "");
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		if(useproxy)
		{
			String part[] = proxystring.split(":");
			HttpHost proxy = new HttpHost(part[0], Integer.valueOf(part[1]), "http");
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		HttpPost post = new HttpPost(RANKING_POST_URL);
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("score", String.valueOf(score)));
		params.add(new BasicNameValuePair("ranking_id", RANKING_ID));
		
		try {
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			Log.v(MainActivity.Tag, "SCOREをPOST");
			final HttpResponse response = httpclient.execute(post);
			if(response.getStatusLine().getStatusCode() != 200 )
				Log.v(MainActivity.Tag, "StatusCodeが異常☆:"+response.getStatusLine().getStatusCode());
			HttpEntity entity = response.getEntity();
            Log.v(MainActivity.Tag,EntityUtils.toString(entity));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			httpclient.getConnectionManager().shutdown();
		}

	}
	
}

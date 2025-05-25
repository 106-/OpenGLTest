package info.khyh.opengltest.DrawLibrary;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import info.khyh.opengltest.MainActivity;
import info.khyh.opengltest.GameClass.Mediator;
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

	static public Vector2 GetLinerMove(Vector2 start, Vector2 end, int param, int div)
	{
		float ratio = (float)param / (float)div;
		float vecx = end.x - start.x;
		float vecy = end.y - start.y;
		float velx = start.x+vecx*ratio;
		float vely = start.y+vecy*ratio;
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

	static public void POSTscore(String name, int score, Context cxt) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(cxt);
		boolean useProxy   = sp.getBoolean("proxycheckbox", false);
		String  proxyStr   = sp.getString("proxystring", "");        // "host:port" 形式

		/* ---------- OkHttpClient 構築 ---------- */
		OkHttpClient.Builder builder = new OkHttpClient.Builder();

		// ★ デバッグ用の HTTP ログ（任意）
		HttpLoggingInterceptor logInt = new HttpLoggingInterceptor(
				msg -> Log.v(MainActivity.Tag, msg));
		logInt.setLevel(HttpLoggingInterceptor.Level.BASIC);
		builder.addInterceptor(logInt);

		// ★ プロキシ設定（必要な場合のみ）
		if (useProxy) {
			String[] part = proxyStr.split(":");
			builder.proxy(new Proxy(
					Proxy.Type.HTTP,
					new InetSocketAddress(part[0], Integer.parseInt(part[1]))));
		}

		OkHttpClient client = builder.build();

		/* ---------- フォームデータ ---------- */
		FormBody body = new FormBody.Builder()
				.add("name",        name)
				.add("score",       String.valueOf(score))
				.add("ranking_id",  RANKING_ID)
				.build();

		/* ---------- POST リクエスト ---------- */
		Request request = new Request.Builder()
				.url(RANKING_POST_URL)
				.post(body)
				.build();

		/* ---------- 同期実行（呼び出し元がバックグラウンドスレッドの場合） ---------- */
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				Log.w(MainActivity.Tag,
						"Unexpected code " + response.code());
			}
			Log.v(MainActivity.Tag,
					"Response: " + response.body().string());
		} catch (IOException e) {
			Log.e(MainActivity.Tag, "POST failed", e);
		}
	}
	
}

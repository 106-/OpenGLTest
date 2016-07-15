package info.khyh.opengltest.GameClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.preference.PreferenceManager;
import android.util.Log;
import info.khyh.opengltest.MainActivity;
import info.khyh.opengltest.Library.Object3D;
import info.khyh.opengltest.Library.GLLoader.ObjLoader;
import info.khyh.opengltest.Library.GLObjects.Texture;

public class LoadResource {
	public Texture bubble,FILTER,FONT,CHARACTER;
	public Object3D CLOUD,MINI_CLOWD,TOWER;
	public MediaPlayer STAGEBGM,BOSSBGM;
	public int KASURI,EXPLODE_1,EXPLODE_2,BARIA,YARARETA,LAZER,WAVEBREAK,WARN;
	private int volume;
	private float volumerate;
	public SoundPool soundpool;
	
	public LoadResource(Context cxt)
	{
		Log.v(MainActivity.Tag, "読み込み開始");
		CLOUD = new Object3D();
		MINI_CLOWD = new Object3D();
		TOWER = new Object3D();
		STAGEBGM = new MediaPlayer();
		BOSSBGM = new MediaPlayer();
		
		AssetManager am = cxt.getAssets();
		soundpool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		try
		{
			bubble = Texture.createTextureFromAsset("img/bul.png");
			FILTER = Texture.createTextureFromAsset("img/sample.png");
			FONT = Texture.createTextureFromAsset("img/font2.png");
			CHARACTER = Texture.createTextureFromAsset("img/chara.png");
			CLOUD.figure = ObjLoader.load("model/cloud.obj");
			MINI_CLOWD.figure = ObjLoader.load("model/miniclowd.obj");
			TOWER.figure = ObjLoader.load("model/tower.obj");
			KASURI = soundpool.load(am.openFd("snd/kasuri.wav"), 1);
			EXPLODE_1 = soundpool.load(am.openFd("snd/explode.wav"), 1);
			EXPLODE_2 = soundpool.load(am.openFd("snd/explode2.wav"), 1);
			BARIA = soundpool.load(am.openFd("snd/baria2.wav"), 1);
			YARARETA = soundpool.load(am.openFd("snd/yarareta.wav"), 1);
			LAZER = soundpool.load(am.openFd("snd/lazer.wav"), 1);
			WAVEBREAK = soundpool.load(am.openFd("snd/wavebreak.wav"), 1);
			WARN = soundpool.load(am.openFd("snd/WARN.wav"), 1);
			
			AssetFileDescriptor afd = am.openFd("music/stage.wav");
			STAGEBGM.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
			afd = am.openFd("music/boss.wav");
			BOSSBGM.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
			STAGEBGM.setLooping(true);
			STAGEBGM.prepare();
			BOSSBGM.setLooping(true);
			BOSSBGM.prepare();
			AudioInit(cxt);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			Log.v(MainActivity.Tag, ex.toString());
		}
		Log.v(MainActivity.Tag, "おわり");
	}
	
	private void AudioInit(Context cxt)
	{
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(cxt);
		
		AudioManager manager = (AudioManager)cxt.getSystemService(Context.AUDIO_SERVICE);
		volume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
		int max_vol = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volumerate = ((float)volume/(float)max_vol)*0.4f;
		Log.v(MainActivity.Tag, "volumerate:"+volumerate);
		manager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
		STAGEBGM.setAudioStreamType(AudioManager.STREAM_MUSIC);
	}
	
	public void PlaySound(int id)
	{
		soundpool.play(id, volumerate, volumerate, 0, 0, 1);
	}
	
	public void ReleaseResourse()
	{
		bubble.dispose();
		FILTER.dispose();
		FONT.dispose();
		CHARACTER.dispose();
		soundpool.release();
		if(STAGEBGM!=null)
		{
			if(STAGEBGM.isPlaying())
				STAGEBGM.stop();
			STAGEBGM.release();
			STAGEBGM = null;
		}
		if(BOSSBGM!=null)
		{
			if(BOSSBGM.isPlaying())
				BOSSBGM.stop();
			BOSSBGM.release();
			BOSSBGM = null;	
		}
	}
	
}

package info.khyh.opengltest;

import info.khyh.opengltest.R;
import info.khyh.opengltest.R.layout;
import info.khyh.opengltest.R.menu;
import info.khyh.opengltest.DrawLibrary.Utls;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class TitleActivity extends Activity {
	private ImageButton startbtn, expbtn, quitbtn, settingbtn, rankbtn;
	private final int GAMEACTIVITY = 1,
						SETTINGS = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title);

		startbtn = (ImageButton)findViewById(R.id.start);
		startbtn.setOnClickListener(new startsituation());
		expbtn = (ImageButton)findViewById(R.id.about);
		expbtn.setOnClickListener(new startexpression());
		quitbtn = (ImageButton)findViewById(R.id.quit);
		quitbtn.setOnClickListener(new quitsituation());
		settingbtn = (ImageButton)findViewById(R.id.setting);
		settingbtn.setOnClickListener(new settingbtn(this));
		rankbtn = (ImageButton)findViewById(R.id.ranking);
		rankbtn.setOnClickListener(new rankbtrn(this));
	}

	private class startsituation implements View.OnClickListener
	{
		@Override
		public void onClick(View v) {
			Intent intent=new Intent();
	        intent.setClassName("info.khyh.opengltest","info.khyh.opengltest.MainActivity");
	        startActivityForResult(intent, GAMEACTIVITY);
		}
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {  
        super.onActivityResult(requestCode, resultCode, intent);  
        int score=0;
        if (requestCode == GAMEACTIVITY)
        {  
            if (resultCode == RESULT_OK)
            {  
                Bundle extras = intent.getExtras();  
                if (extras != null)
                {  
                	score = extras.getInt("SCORE");
                    Log.v(MainActivity.Tag, "SCORE:"+score);
                }
            }
        }  
        if(score!=0)
        {
            nameDialog(this, score);
        }
    } 
	
	private class startexpression implements View.OnClickListener
	{
		@Override
		public void onClick(View v) {
			Intent intent = null;
			intent = new Intent(Intent.ACTION_VIEW,Uri.parse(Utls.DESC_URL));
			startActivity(intent);
		}
	}
	
	private class quitsituation implements View.OnClickListener
	{
		@Override
		public void onClick(View v) {
			finish();
		}
	}

	private class settingbtn implements View.OnClickListener
	{
		private Activity act;
		
		public settingbtn(Activity act)
		{
			this.act = act;
		}
		
		@Override
		public void onClick(View v) {
	        Intent i = new Intent();
	        i.setClass(act, SettingsActivity.class);
	        startActivity(i);
		}
	}
	
	private class rankbtrn implements View.OnClickListener
	{
		private Context cxt;
		public rankbtrn(Context cxt)
		{
			this.cxt = cxt;
		}
		
		@Override
		public void onClick(View v) {
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(cxt);
			String url = Utls.RANKING_URL;
			Intent intent = null;
			intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
			startActivity(intent);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	private class ScoreUpload extends AsyncTask<Void, Void, Void>
	{
		private Activity act;
		private int score;
		private String name;
		private Context cxt;
		
		public ScoreUpload(String name, int score, Context cxt)
		{
			this.act = act;
			this.name = name;
			this.score = score;
			this.cxt = cxt;
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			Log.v(MainActivity.Tag, "NAME:"+name);
			Utls.POSTscore(name, score, cxt);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void a) {
			Toast.makeText(cxt, "スコアをアップロードしました☆", Toast.LENGTH_LONG).show();
		}
	}
	
	
	private String name;
	private void nameDialog(final Activity act, final int score)
	{
		name="UNNAMED";
	    final EditText editView = new EditText(act);
	    new android.app.AlertDialog.Builder(act)
	        .setIcon(android.R.drawable.ic_dialog_info)
	        .setTitle("名前を入力してください")
	        .setView(editView)
	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            	name = editView.getText().toString();
	            	PostScore(name,score);
	            }
	        })
	        .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            	name = "UNNAMED";
	            	PostScore(name,score);
	            }
	        })
	        .show();
	}
	
	private void PostScore(String name, int score)
	{
		ScoreUpload su = new ScoreUpload(name, score, this);
        su.execute();
	}
	
}

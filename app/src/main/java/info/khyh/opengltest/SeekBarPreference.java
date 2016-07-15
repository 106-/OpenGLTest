package info.khyh.opengltest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekBarPreference extends DialogPreference{
	private int value;
	private SeekBar sb;
	private Context cxt;

	public SeekBarPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		cxt = context;
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(cxt);
		value = sp.getInt("se_volume", 10);
		setDialogLayoutResource(R.layout.seekbar_pref);
	}
	
	@Override
	protected void onDialogClosed(boolean res) 
	{
		super.onDialogClosed(res);
		if(res)
		{
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(cxt);
			Editor e = sp.edit();
			e.putInt("se_volume", value);
			e.commit();
		}
	}
	
	private class SBchange implements SeekBar.OnSeekBarChangeListener
	{	
		@Override
		public void onProgressChanged(SeekBar sb, int val, boolean fromUser) {
			value = val;
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {}
	}
	
	@Override
	protected void onBindDialogView(View v)
	{
		sb = (SeekBar)v.findViewById(R.id.seekBar1);
		sb.setOnSeekBarChangeListener(new SBchange());
		sb.setMax(100);
		sb.setProgress(value);
	}

}

package info.khyh.opengltest.GameClass;

import android.util.Log;
import info.khyh.opengltest.MainActivity;

public class Score {
	private int score,remain_player;
	private final int SCORE_MAX = 99999999;
	public Score(int remain_player)
	{
		score = 0;
		this.remain_player = remain_player;
	}
	
	public void AddScore(int n)
	{
		if(0<n)
		{
			score+=n;
			if(score>SCORE_MAX)score=SCORE_MAX;
		}
	}
	
	public void ExtendPlayer()
	{remain_player++;}
	
	public void PlayerDestroyed()
	{remain_player--;}
	
	public int GetScore()
	{return score;}
	
	public int GetRemainPlayer()
	{return remain_player;}
	
}

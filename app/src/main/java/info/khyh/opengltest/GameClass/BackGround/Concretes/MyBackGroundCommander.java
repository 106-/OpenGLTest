package info.khyh.opengltest.GameClass.BackGround.Concretes;

import java.util.Random;

import info.khyh.opengltest.DrawLibrary.Utls;
import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.GameClass.BackGround.BackGroundCommander;
import info.khyh.opengltest.GameClass.Ornament.Result;
import info.khyh.opengltest.Library.Vector2;
import info.khyh.opengltest.Library.Vector3;

public class MyBackGroundCommander extends BackGroundCommander{
	private Random rnd;
	private Vector3 camtmppos,focustmppos,uptmpvec,tmpvec;
	
	public MyBackGroundCommander(Mediator mit) {
		super(mit);
		this.rnd = new Random();
	}

	@Override
	public void OnTime(int t) {
		if(t==0)
		{
			mit.AddBackGroundOrnament(new Tower(mit, new Vector3(-250, 0, 10)));
			mit.AddBackGroundOrnament(new Tower(mit, new Vector3(-200, 0, 10)));
			mit.AddBackGroundOrnament(new Tower(mit, new Vector3(-150, 0, 10)));
			mit.AddBackGroundOrnament(new Tower(mit, new Vector3(-100, 0, 10)));
			mit.AddBackGroundOrnament(new Tower(mit, new Vector3(-50, 0, 10)));
			mit.AddBackGroundOrnament(new Tower(mit, new Vector3(0, 0, 10)));
			mit.AddBackGroundOrnament(new Tower(mit, new Vector3(50, 0, 10)));
			mit.AddBackGroundOrnament(new Tower(mit, new Vector3(100, 0, 10)));
			mit.AddBackGroundOrnament(new Tower(mit, new Vector3(150, 0, 10)));
			mit.AddBackGroundOrnament(new Tower(mit, new Vector3(200, 0, 10)));
			mit.AddBackGroundOrnament(new Tower(mit, new Vector3(250, 0, 10)));
			
			mit.AddBackGroundOrnament(new BackCloud(new Vector3(0,0,0), mit));
			mit.AddBackGroundOrnament(new BackCloud(new Vector3(200,0,0), mit));
			mit.AddBackGroundOrnament(new BackCloud(new Vector3(-200,0,0), mit));
			mit.AddBackGroundOrnament(new BackCloud(new Vector3(0,0,200), mit));
			mit.AddBackGroundOrnament(new BackCloud(new Vector3(200,0,200), mit));
			mit.AddBackGroundOrnament(new BackCloud(new Vector3(-200,0,200), mit));
			mit.AddBackGroundOrnament(new BackCloud(new Vector3(0,0,-200), mit));
			mit.AddBackGroundOrnament(new BackCloud(new Vector3(200,0,-200), mit));
			mit.AddBackGroundOrnament(new BackCloud(new Vector3(-200,0,-200), mit));
		}
		if(t%10==0)
			mit.AddBackGroundOrnament(new MiniClowd(mit, new Vector3(-500,rnd.nextInt(20)+2,-50+rnd.nextInt(200)
															), new Vector3(70+rnd.nextInt(50),1,10+rnd.nextInt(10))));
		
		MoveCam(t);
		if(t==5500)
		//if(t==200)
			mit.AddBackGroundOrnament(new WARNING(mit));
	}
	
	private void MoveCam(int t)
	{
		if(t==0)
		{mit.GetDrawMain().campos.set(200, 800, 0);
		 mit.GetDrawMain().focus.set(0,0,0);}
		if(t<130)
		{
			//800→300
			double ratio = (double)t/130;
			mit.GetDrawMain().campos.set(200, 800-(float)(Math.sin(Math.PI/2*ratio)*500), 0);
			mit.GetDrawMain().lightpos.x = 200;
			mit.GetDrawMain().lightpos.y = 800-(float)(Math.sin(Math.PI/2*ratio)*500);
			mit.GetDrawMain().lightpos.z = 0;
		}
		if(t==200)
		{
			camtmppos = mit.GetDrawMain().campos;
			focustmppos = mit.GetDrawMain().focus;
		}
		if(200<t && t<=300)
		{
			mit.GetDrawMain().campos = Utls.GetSinMove(camtmppos, new Vector3(200,50,0), t-200, 200);
			mit.GetDrawMain().focus = Utls.GetSinMove(focustmppos, new Vector3(0,15,0), t-200, 200);
		}
		//campos = (200,123,0)
		//focus = (0,15,0)
		//upvec = (0,1,0)
		if(t == 900){
			focustmppos = mit.GetDrawMain().focus;
			uptmpvec = mit.GetDrawMain().upvec;
		}
		if(900<t && t <=970)
		{
			mit.GetDrawMain().focus = Utls.GetSinMove(focustmppos, new Vector3(-200,30,50), t-900, 70);
			mit.GetDrawMain().upvec = Utls.GetSinMove(uptmpvec, new Vector3(0,1f,1f), t-900, 70);
		}
		//campos = (200,50,0)
		//focus = (-200,30,50)
		//upvec = (0,1f,1f)
		if(t == 1800){
			camtmppos = mit.GetDrawMain().campos;
			focustmppos = mit.GetDrawMain().focus;
			uptmpvec = mit.GetDrawMain().upvec;
		}
		if(1800<t && t <=1900)
		{
			mit.GetDrawMain().campos = Utls.GetSinMove(camtmppos, new Vector3(10,20,0), t-1800, 100);
			mit.GetDrawMain().focus = Utls.GetSinMove(focustmppos, new Vector3(0,15,0), t-1800, 100);
			mit.GetDrawMain().upvec = Utls.GetSinMove(uptmpvec, new Vector3(0,1f,0), t-1800, 100);
		}
		//campos = (10,20,0)
		//focus = (0,15,0)
		//upvec = (0,1,0)
		
		if(t == 2400){
			focustmppos = mit.GetDrawMain().focus;
			uptmpvec = mit.GetDrawMain().upvec;
		}
		if(2400<t && t <=2600)
		{
			mit.GetDrawMain().focus = Utls.GetSinMove(focustmppos, new Vector3(0,22,0), t-2400, 200);
			mit.GetDrawMain().upvec = Utls.GetSinMove(uptmpvec, new Vector3(0f,1f,0), t-2400, 200);
		}
		//campos = (10,20,0)
		//focus = (0,22,0)
		//upvec = (0,1,0)
		
		if(t == 3300){
			camtmppos = mit.GetDrawMain().campos;
			focustmppos = mit.GetDrawMain().focus;
			uptmpvec = mit.GetDrawMain().upvec;
		}
		if(3300<t && t <=3800)
		{
			mit.GetDrawMain().campos = Utls.GetSinMove(camtmppos, new Vector3(200,200,-200), t-3300, 500);
			mit.GetDrawMain().focus = Utls.GetSinMove(focustmppos, new Vector3(80,0,100), t-3300, 500);
			mit.GetDrawMain().upvec = Utls.GetSinMove(uptmpvec, new Vector3(1f,0,0), t-3300, 500);
		}
		
		
		if(t == 5600){
			camtmppos = mit.GetDrawMain().campos;
			focustmppos = mit.GetDrawMain().focus;
			uptmpvec = mit.GetDrawMain().upvec;
		}
		if(5600<t && t <=5800)
		{
			mit.GetDrawMain().campos = Utls.GetSinMove(camtmppos, new Vector3(200,50,-100), t-5600, 200);
			mit.GetDrawMain().focus = Utls.GetSinMove(focustmppos, new Vector3(0,15,0), t-5600, 200);
			mit.GetDrawMain().upvec = Utls.GetSinMove(uptmpvec, new Vector3(0,1f,0), t-5600, 200);
		}
		//campos = (200,50,0)
		//focus = (0,15,0)
		//upvec = (0,1,0)
		mit.GetDrawMain().lightpos = mit.GetDrawMain().campos;
	}

}

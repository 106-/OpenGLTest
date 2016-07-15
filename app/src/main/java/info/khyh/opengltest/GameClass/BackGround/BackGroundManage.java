package info.khyh.opengltest.GameClass.BackGround;

import info.khyh.opengltest.DrawLibrary.DrawFuncs;
import info.khyh.opengltest.Library.Vector3;

import javax.microedition.khronos.opengles.GL11;

public class BackGroundManage {
	private BackGround2DOrnament bgo2list[];
	private BackGround3DOrnament bgo3list[];
	private final int MAX2D = 100,
						MAX3D = 500;
	
	public BackGroundManage()
	{
		this.bgo2list = new BackGround2DOrnament[MAX2D];
		this.bgo3list = new BackGround3DOrnament[MAX3D];
	}
	
	public void Update()
	{
		for(int i=0;i<bgo3list.length;i++)
		{
			if(bgo3list[i]==null)continue;
			bgo3list[i].Update();
			if(bgo3list[i].RemoveOK())bgo3list[i]=null;
		}
		for(int i=0;i<bgo2list.length;i++)
		{
			if(bgo2list[i]==null)continue;
			bgo2list[i].Update();
			if(bgo2list[i].RemoveOK())bgo2list[i]=null;
		}
	}
	
	public void Draw(GL11 gl, float aspect, Vector3 campos, Vector3 focus, Vector3 upvec, Vector3 lightpos)
	{
		DrawFuncs.Start3DDraw(gl, aspect, campos, focus, upvec, lightpos);
		for(int i=0;i<bgo3list.length;i++)
		{
			if(bgo3list[i]==null)continue;
			bgo3list[i].Draw(gl);
		}
		DrawFuncs.End3DDraw(gl);
		DrawFuncs.Start2DDraw(gl);
		for(int i=0;i<bgo2list.length;i++)
		{
			if(bgo2list[i]==null)continue;
			bgo2list[i].Draw(gl);
		}
		DrawFuncs.End2DDraw(gl);
	}
	
	public void Add(BackGround2DOrnament bgo)
	{
		int i=Check2DList();
		if(i!=-1)
			bgo2list[i]=bgo;
	}
	
	public void Add(BackGround3DOrnament bgo)
	{
		int i=Check3DList();
		if(i!=-1)
			bgo3list[i]=bgo;
	}
	
	private int Check2DList()
	{
		for(int i=0;i<bgo2list.length;i++)
			if(bgo2list[i]==null)
				return i;
		return -1;
	}
	
	private int Check3DList()
	{
		for(int i=0;i<bgo3list.length;i++)
			if(bgo3list[i]==null)
				return i;
		return -1;
	}
	
}

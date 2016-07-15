package info.khyh.opengltest.Library.GLLoader;

import java.util.ArrayList;
import java.util.HashMap;

import info.khyh.opengltest.Library.GLObjects.*;;

public class Figure {
	public HashMap<String,Material> materials;
	public ArrayList<Mesh>			meshs;
	
	public void draw()
	{
		for(int i=0;i<meshs.size(); i++)
		{
			meshs.get(i).draw();
		}
		//for(Mesh mesh:meshs)mesh.draw();
	}
}

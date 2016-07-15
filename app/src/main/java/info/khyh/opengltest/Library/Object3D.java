package info.khyh.opengltest.Library;

import java.util.ArrayList;

import info.khyh.opengltest.Library.GLLoader.Figure;
import javax.microedition.khronos.opengles.GL11;

public class Object3D {
	public Figure figure;
	public Vector3 position = new Vector3();
	public Vector3 rotate	= new Vector3();
	public Vector3 scale	= new Vector3(1,1,1);
	public ArrayList<Object3D> childs = new ArrayList<Object3D>();
	
	//�`��
	public void draw(GL11 gl)
	{
		gl.glPushMatrix();
		gl.glTranslatef(position.x, position.y, position.z);
		gl.glRotatef(rotate.z, 0, 0, 1);
		gl.glRotatef(rotate.y, 0, 1, 0);
		gl.glRotatef(rotate.x, 1, 0, 0);
		gl.glScalef(scale.x, scale.y, scale.z);
		figure.draw();
		for(int i=0; i<childs.size(); i++){childs.get(i).draw(gl);}
		gl.glPopMatrix();
	}
	
}

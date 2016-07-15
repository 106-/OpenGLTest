package info.khyh.opengltest.Library.GLObjects;

import info.khyh.opengltest.Library.GLLoader.GLES;

import javax.microedition.khronos.opengles.GL11;

public class Material extends GLObject{
	public Texture texture;		//テクスチャ
	public float[] ambient =	new float[4];//環境光
	public float[] diffuse =	new float[4];//拡散光
	public float[] speculer =	new float[4];//鏡面光
	public float[] shininess =	new float[1];//鏡面反射角度
	
	@Override
	public void bind() {
		GL11 gl = GLES.gl;
		gl.glMaterialfv(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT, ambient, 0);
		gl.glMaterialfv(GL11.GL_FRONT_AND_BACK, GL11.GL_DIFFUSE, diffuse, 0);
		gl.glMaterialfv(GL11.GL_FRONT_AND_BACK, GL11.GL_SPECULAR, speculer, 0);
		gl.glMaterialfv(GL11.GL_FRONT_AND_BACK, GL11.GL_SHININESS, shininess, 0);
		//gl.glMaterialfv(GL11.GL_FRONT_AND_BACK, GL11.GL_EMISSION, new float[]{1,1,1,1}, 0);
		if(texture != null)texture.bind();
	}

	@Override
	public void unbind() {
		if(texture != null)texture.unbind();
	}

	@Override
	public void dispose() {
		if(texture != null)
		{
			texture.dispose();
			texture=null;
		}
	}

}

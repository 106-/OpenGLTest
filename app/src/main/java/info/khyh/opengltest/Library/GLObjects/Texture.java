package info.khyh.opengltest.Library.GLObjects;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL11;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import info.khyh.opengltest.Library.GLLoader.GLES;

public class Texture extends GLObject{
	public int textureId;
	public int width, height;
	
	@Override
	public void bind() {
		GL11 gl = GLES.gl;
		gl.glEnable(GL11.GL_TEXTURE_2D);
		gl.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
	}

	@Override
	public void unbind() {
		GL11 gl = GLES.gl;
		gl.glDisable(GL11.GL_TEXTURE_2D);
		gl.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	@Override
	public void dispose() {
		if(textureId != 0)
		{
			GLES.gl.glDeleteTextures(0, new int[]{textureId}, 0);
			textureId=0;
		}
	}

	public static Texture createInstance(Bitmap bmp)
	{
		Texture result = new Texture();
		GL11 gl = GLES.gl;
		int[] bufferIds = new int[1];
		gl.glGenTextures(1, bufferIds, 0);
		result.textureId = bufferIds[0];
		result.width = bmp.getWidth();
		result.height = bmp.getHeight();
		gl.glEnable(GL11.GL_TEXTURE_2D);
		gl.glBindTexture(GL11.GL_TEXTURE_2D, result.textureId);
		GLUtils.texImage2D(GL11.GL_TEXTURE_2D, 0, bmp, 0);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		//Log.v(MainActivity.Tag , String.valueOf(result.textureId));
		gl.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return result;
	}
	
	public static Texture createTextureFromAsset(String assetFileName) throws IOException
	{
		//Log.v(MainActivity.Tag , assetFileName);
		Context cxt = GLES.cxt;
		InputStream in = cxt.getAssets().open(assetFileName);
		Bitmap bmp = BitmapFactory.decodeStream(in);
		Texture result = Texture.createInstance(bmp);
		bmp.recycle();
		in.close();
		return result;
	}
	
}

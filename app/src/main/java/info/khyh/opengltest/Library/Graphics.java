package info.khyh.opengltest.Library;

import info.khyh.opengltest.Library.GLLoader.GLES;
import info.khyh.opengltest.Library.GLObjects.Texture;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class Graphics {
	public static int	screenW;
	public static int	screenH;
	private FloatBuffer vertexBuffer;
	private FloatBuffer uvBuffer;
	private FloatBuffer tmpbuf;
	private FloatBuffer uctmpbuf;
	
	public Graphics(int w, int h)
	{
		this.screenW = w;
		this.screenH = h;
		
		//���_�o�b�t�@�̍쐬
		float[] vertexs = {
			-1.0f,  1.0f, 0.0f,
			-1.0f, -1.0f, 0.0f,
			 1.0f,  1.0f, 0.0f,
			 1.0f, -1.0f, 0.0f
		};
		vertexBuffer = makeFloatBuffer(vertexs);
		
		//UV�o�b�t�@�̍쐬
		float[] uvs = {
			0.0f,0.0f,
			0.0f,1.0f,
			1.0f,0.0f,
			1.0f,1.0f
		};
		uvBuffer = makeFloatBuffer(uvs);
	}
	
	public void dispose()
	{
		GL11 gl = GLES.gl;
	}
	
	public enum BLENDTYPE
	{
		ALPHA,
		ADD,
		MULTIPLY,
		XOR,
		REVERSE
	}
	
	private void setBlend(GL11 gl, BLENDTYPE type)
	{
		switch(type)
		{
		case ADD:
			gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
			break;
		case ALPHA:
			//gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
			gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			break;
		case MULTIPLY:
			gl.glBlendFunc(GL10.GL_ZERO, GL10.GL_SRC_COLOR);
			break;
		case REVERSE:
			gl.glBlendFunc(GL10.GL_ONE_MINUS_DST_COLOR, GL10.GL_ZERO);
			break;
		case XOR:
			gl.glBlendFunc(GL10.GL_ONE_MINUS_DST_COLOR, GL10.GL_ONE_MINUS_SRC_COLOR);
			break;		
		}
	}
	
	//�`��
	public void drawImage(Texture texture, float aspect, int x, int y, float angle)
	{drawImage(texture, aspect, x, y, texture.width, texture.height, angle);}
	
	public void drawImage(Texture texture, float aspect, int x, int y, int w, int h, float angle)
	{drawImage(texture, aspect, x, y, w, h, 0, 0, texture.width, texture.height, angle);}
	
	public void drawImage(Texture texture, float aspect, int dx, int dy, int dw, int dh, int sx, int sy, int sw, int sh, float angle)
	{drawImage(texture, aspect, dx, dy, dw, dh, sx, sy, sw, sh, 1f, 1f, 1f, 1f, angle, BLENDTYPE.ALPHA);}
	
	public void drawImage(Texture texture, float aspect, int dx, int dy, int dw, int dh, int sx, int sy, int sw, int sh, float r, float g, float b, float alpha, float angle, BLENDTYPE type)
	{
		GL11 gl = GLES.gl;
		
		gl.glPushMatrix();
		//�u�����h�̐ݒ�
		gl.glEnable(GL11.GL_BLEND);
		setBlend(gl, type);
		gl.glColor4f(r, g, b, alpha);
		
		gl.glTexCoordPointer(2, GL11.GL_FLOAT, 0, uvBuffer);
		
		//�E�B���h�E���W�n�𐳋K���f�o�C�X���W�ɕϊ�
		float tw = (float)sw/(float)texture.width;
		float th = (float)sh/(float)texture.height;
		float tx = (float)sx/(float)texture.width;
		float ty = (float)sy/(float)texture.height;
		
		//�O����
		texture.bind();
		
		//�e�N�X�`���s��̈ړ��E�g�k
		gl.glMatrixMode(GL11.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glTranslatef(tx, ty, 0.0f);
		gl.glScalef(tw, th, 1.0f);
		
		//�E�B���h�E���W�𐳋K���f�o�C�X���W�ɕϊ�
		float mx = ((float)dx/(float)screenW)*2.0f-1.0f;
		float my = ((float)dy/(float)screenH)*2.0f-1.0f;
		float mw = ((float)dw/(float)screenW);
		float mh = ((float)dh/(float)screenH);
		
		//���f���r���[�s��̈ړ��E�g��k��
		gl.glMatrixMode(GL11.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(mx+mw, my+mh, 0.0f);
		gl.glScalef(1.0f / aspect, 1.0f, 1.0f);
		gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
		gl.glScalef(mw*aspect, mh, 1.0f);
		
		//�l�p�`�̕`��
		gl.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		
		//�㏈��
		texture.unbind();
		gl.glDisable(GL10.GL_BLEND);
		gl.glColor4f(1, 1, 1, 1);
		
		gl.glPopMatrix();
	}
	
	public void drawBox(float x, float y, float w, float h, float r, float g, float b, float alpha, BLENDTYPE type)
	{
		GL11 gl = GLES.gl;
		
		gl.glPushMatrix();
		//�u�����h�̐ݒ�
		gl.glEnable(GL11.GL_BLEND);
		setBlend(gl, type);
		
		gl.glColor4f(r,g,b,alpha);
		
		//�E�B���h�E���W�𐳋K���f�o�C�X���W�ɕϊ�
		float mx = ((float)x/(float)screenW)*2.0f-1.0f;
		float my = ((float)y/(float)screenH)*2.0f-1.0f;
		float mw = ((float)w/(float)screenW);
		float mh = ((float)h/(float)screenH);
		
		//���f���r���[�s��̈ړ��E�g��k��
		gl.glMatrixMode(GL11.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(mx+mw, my+mh, 0.0f);
		gl.glScalef(1.0f, 1.0f, 1.0f);
		gl.glRotatef(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glScalef(mw, mh, 1.0f);
		
		//�l�p�`�̕`��
		gl.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glColor4f(1, 1, 1, 1);
		
		gl.glPopMatrix();
	}
	
	//�l�_����`��
	public void drawImageFromPoints(Texture texture, 	BLENDTYPE type,
														float sx, float sy,
														float sw, float sh,
														float x1, float y1,
														float x2, float y2,
														float x3, float y3,
														float x4, float y4)
	{
		float f[] = new float[]
				{(x1/screenW)*2.0f-1.0f, (y1/screenH)*2.0f-1.0f, 0.0f, 
				(x2/screenW)*2.0f-1.0f, (y2/screenH)*2.0f-1.0f, 0.0f, 
				(x3/screenW)*2.0f-1.0f, (y3/screenH)*2.0f-1.0f, 0.0f, 
				(x4/screenW)*2.0f-1.0f, (y4/screenH)*2.0f-1.0f, 0.0f};
		FloatBuffer fb = makeFloatBuffer(f);
		drawImageFromPoints(texture, type, sx, sy, sw, sh, fb);
	}
	
	public void drawImageFromPoints(Texture texture, 	BLENDTYPE type,
									float sx, float sy,
									float sw, float sh,
									FloatBuffer fb)
	{
		GL11 gl = GLES.gl;

		gl.glPushMatrix();
		//�u�����h�̐ݒ�
		gl.glEnable(GL11.GL_BLEND);
		setBlend(gl, type);
		
		gl.glTexCoordPointer(2, GL11.GL_FLOAT, 0, uvBuffer);
		
		//�E�B���h�E���W�n�𐳋K���f�o�C�X���W�ɕϊ�
		float tw = (float)sw/(float)texture.width;
		float th = (float)sh/(float)texture.height;
		float tx = (float)sx/(float)texture.width;
		float ty = (float)sy/(float)texture.height;
		
		//�O����
		texture.bind();
		
		//�e�N�X�`���s��̈ړ��E�g�k
		gl.glMatrixMode(GL11.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glTranslatef(tx, ty, 0.0f);
		gl.glScalef(tw, th, 1.0f);
		
		//���f���r���[�s��̈ړ��E�g��k��
		gl.glMatrixMode(GL11.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		//�l�p�`�̕`��
		gl.glVertexPointer(3, GL11.GL_FLOAT, 0, fb);
		gl.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		
		//�㏈��
		texture.unbind();
		
		gl.glPopMatrix();
	}
	
	public static FloatBuffer makeFloatBufferFromDisplay(float[] array)
	{
		float f[] = new float[]
				{(array[0]/screenW)*2.0f-1.0f, (array[1]/screenH)*2.0f-1.0f, 0.0f, 
				(array[2]/screenW)*2.0f-1.0f, (array[3]/screenH)*2.0f-1.0f, 0.0f, 
				(array[4]/screenW)*2.0f-1.0f, (array[5]/screenH)*2.0f-1.0f, 0.0f, 
				(array[6]/screenW)*2.0f-1.0f, (array[7]/screenH)*2.0f-1.0f, 0.0f};
		return makeFloatBuffer(f);
	}
	
	//float�z���FloatBuffer�ɕϊ� ���@��ł̈Ⴂ���z���炵��
	public static FloatBuffer makeFloatBuffer(float[] array)
	{
		FloatBuffer fb = ByteBuffer.allocateDirect(array.length*4)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		fb.put(array).position(0);
		return fb;
	}
}

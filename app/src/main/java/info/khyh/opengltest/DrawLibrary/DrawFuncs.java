package info.khyh.opengltest.DrawLibrary;

import java.util.Locale;

import info.khyh.opengltest.GameClass.Mediator;
import info.khyh.opengltest.Library.Graphics;
import info.khyh.opengltest.Library.Graphics.BLENDTYPE;
import info.khyh.opengltest.Library.Vector2;
import info.khyh.opengltest.Library.Vector3;
import info.khyh.opengltest.Library.VectorUtls;
import info.khyh.opengltest.Library.GLObjects.Texture;

import javax.microedition.khronos.opengles.GL11;

import android.opengl.GLU;

public class DrawFuncs {
	private static Vector2 v2d;
	private static float[] fogcolor;
	
	static
	{
		v2d = new Vector2();
		fogcolor = new float[]{0.1f, 0.1f, 0.3f, 0.5f};
	}
	
	//��ʂ̏���
	public static void ClearColor(GL11 gl)
	{
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	//��ʂ̏��� �F�w��
	public static void ClearColor(GL11 gl, float r, float g, float b)
	{
		gl.glClearColor(r, g, b, 1.0f);
		gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	//2D�`��̑O�ɌĂ�
	public static void Start2DDraw(GL11 gl)
	{
		//���_�z��̗L��
		gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		
		//�f�v�X�e�X�g�ƌ����̖���
		gl.glDisable(GL11.GL_DEPTH_TEST);
		gl.glDisable(GL11.GL_LIGHTING);
		gl.glDisable(GL11.GL_LIGHT0);
		gl.glDisable(GL11.GL_FOG);
		
		//�ˉe�ϊ�
		gl.glMatrixMode(GL11.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	//2D�`��̌�ɌĂ�
	public static void End2DDraw(GL11 gl)
	{
		//���_�z��̖���
		gl.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		
		//�f�v�X�e�X�g�ƌ����̗L��
		gl.glEnable(GL11.GL_DEPTH_TEST);
		gl.glEnable(GL11.GL_LIGHTING);
		gl.glEnable(GL11.GL_LIGHT0);
	}
	
	//3D�`��̑O�ɌĂ�
	public static void Start3DDraw(GL11 gl, float aspect, Vector3 campos, Vector3 focus, Vector3 upvec, Vector3 lightpos)
	{
		//gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		//�J�����O�̖���
		//gl.glDisable(GL11.GL_CULL_FACE);
		
		//�e�N�X�`���s��̏���
		gl.glMatrixMode(GL11.GL_TEXTURE);
		gl.glLoadIdentity();
		
		//�f�v�X�e�X�g�ƌ����̗L��
		gl.glEnable(GL11.GL_DEPTH_TEST);
		gl.glEnable(GL11.GL_LIGHTING);
		gl.glEnable(GL11.GL_LIGHT0);
		gl.glEnable(GL11.GL_FOG);
		
		//fog
		gl.glFogfv(GL11.GL_FOG_COLOR, fogcolor, 0);
		gl.glFogf(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
		gl.glFogf(GL11.GL_FOG_START, 150.0f);
		gl.glFogf(GL11.GL_FOG_END, 450.0f);
		gl.glHint(GL11.GL_FOG_HINT, GL11.GL_NICEST);
		
		//�ˉe�ϊ�
		gl.glMatrixMode(GL11.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 
				45.0f,	//Y���̉�p 
				aspect,	//�A�X�y�N�g��
				1f,	//�j�A�N���b�v
				1000.0f);//�t�@�[�N���b�v
		
		//�����ʒu�̎w��
		gl.glMatrixMode(GL11.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION,
				new float[]{lightpos.x, lightpos.y, lightpos.z, 1.0f}, 0);
		
		//�r���[�ϊ�
		GLU.gluLookAt(gl, 
				campos.x, campos.y, campos.z, //�J�����̎��_
				focus.x, focus.y, focus.z, //�J�����̏œ_
				upvec.x, upvec.y, upvec.z);//�J�����̏���
	}
	
	public static void Start3DDraw(GL11 gl, Mediator mit)
	{
		Start3DDraw(gl, mit.GetDrawMain().aspect, 
				mit.GetDrawMain().campos, 
				mit.GetDrawMain().focus, 
				mit.GetDrawMain().upvec,
				mit.GetDrawMain().lightpos);
	}
	
	//3D�`��̌�ɌĂ�
	public static void End3DDraw(GL11 gl)
	{}
	
	//���S���W���w�肵�ĕ`��
	public static void DrawTexturePointf(Graphics g, Texture t, float x, float y)
	{g.drawImage(t, 1.0f, (int)x-t.width/2, (int)y-t.height/2, 0.0f);}
	
	//���S���W����X���ĕ`��
	//�p�x�͂ӂ��̂�Ŏw��
	public static void RotaDrawTexturePointf(Graphics g, float aspect, Texture t, float x, float y, float rad_angle)
	{g.drawImage(t, aspect, (int)x-t.width/2, (int)y-t.height/2, rad_angle);}
	
	//��_���w�肵�ĕ`��
	public static void DrawTextureTwoPointf(Graphics g, float aspect, Texture t, float x, float y, float x2, float y2)
	{g.drawImage(t, aspect, (int)x, (int)y, (int)(x2-x), (int)(y2-y), 0.0f);}
	
	//�p�x�Ɠ�_���w�肵�ĕ`��
	public static void DrawTextureTwoPointf(Graphics g, float aspect, Texture t, float x, float y, float x2, float y2, float rad_angle)
	{g.drawImage(t, aspect, (int)x, (int)y, (int)(x2-x), (int)(y2-y), rad_angle);}
	
	//�l�V���w�肵�ĕ`��
	public static void DrawModiTexture(Graphics g, Texture t, float x1, float y1,float x2, float y2,float x3, float y3,float x4, float y4)
	{g.drawImageFromPoints(t, BLENDTYPE.ALPHA, 0, 0, t.width, t.height, x1, y1, x2, y2, x3, y3, x4, y4);}
	public static void DrawModiTexture(Graphics g, Texture t, double x1, double y1,double x2, double y2,double x3, double y3,double x4, double y4)
	{DrawModiTexture(g, t, (float)x1, (float)y1, (float)x2, (float)y2, (float)x3, (float)y3, (float)x4, (float)y4);}
	
	//�_����]���č��W��Ԃ�
	public static Vector2 RotateVector2(Vector2 v, float rad_ang)
	{
		v2d.set((float)(v.x*Math.cos(rad_ang)+v.y*Math.sin(rad_ang)), 
						(float)-(-v.x*Math.sin(rad_ang)+v.y*Math.cos(rad_ang)));
		return v2d;
	}
	
	//�l�_����]���ĕԂ�
	public static Vector2[] RotatePoints(Vector2 v[], Vector2 center, float rad_ang)
	{
		for(int i=0;i<v.length;i++)
			v[i] = VectorUtls.add(center, RotateVector2(VectorUtls.sub(v[i],center), (float)rad_ang));
		return v;
	}
	
	//������`��
	public final static int CHARACTER_W=20;
	public final static int CHARACTER_H=40;
	public enum StrType
	{
		BLACK,
		BLUE
	}
	public static void DrawCharacter(Graphics g, Float aspect, Texture t, char c, StrType strtype, float x, float y, float w, float h, float alpha)
	{
		//��͂��ꂽ������ASCII�I�Ɍ��ăp�[�X������
		if(c<48 || 91<c)return;
		int cx=0, cy=0;
		if(47 < c && c < 58)
		{
			cx = c - 48;
			cy = 2;
		}
		else if(64 < c && c < 78)
		{
			cx = c - 65;
			cy = 0;
		}
		else if(77 < c && c < 91)
		{
			cx = c - 78;
			cy = 1;
		}
		if(strtype == StrType.BLUE)cy+=3;
		g.drawImage(t, aspect, (int)x, (int)y, (int)w, (int)h, 
						cx*CHARACTER_W, cy*CHARACTER_H, (int)CHARACTER_W, (int)CHARACTER_H, 1.0f, 1.0f, 1.0f, alpha, 0, BLENDTYPE.ALPHA);
//		g.drawImage(t, aspect, (int)x, (int)y, (int)w, (int)h, 
//						cx*CHARACTER_W, cy*CHARACTER_H, (int)CHARACTER_W, (int)CHARACTER_H, 0.0f);
	}
	public static void DrawString(Graphics g, Float aspect, Texture t, String str, StrType strtype, float x, float y, float w, float h, float alpha)
	{
		for(int i=0;i<str.length();i++)
		{
			DrawCharacter(g, aspect, t, str.toUpperCase(Locale.ENGLISH).charAt(i), strtype, 
							x+w/str.length()*i,
							y,
							w/str.length(),h
							,alpha);
		}
	}
	public static void DrawString(Graphics g, Float aspect, Texture t, String str, StrType strtype, float x, float y, float w, float h)
	{DrawString(g, aspect, t, str, strtype, x, y, w, h, 1.0f);}
	
}

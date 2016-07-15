package info.khyh.opengltest.Library.GLObjects;

public abstract class GLObject {
	
	//破棄時
	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		dispose();
	}
	
	//バインド
	public abstract void bind();
	//アンバインド
	public abstract void unbind();
	//解放
	public abstract void dispose();
	
}

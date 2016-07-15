package info.khyh.opengltest.Library.GLLoader;

import info.khyh.opengltest.Library.GLObjects.*;

public class Mesh {
	public VertexBuffer vertexBuffer;
	public IndexBuffer indexBuffer;
	public Material material;
	
	public void draw()
	{
		material.bind();
		vertexBuffer.bind();
		indexBuffer.draw();
		vertexBuffer.unbind();
		material.unbind();
	}
}

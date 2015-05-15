package graph;

public class Graph
{
	private Vertex[] vertices;

	public Graph(int size)
	{
		vertices = new Vertex[size];
	}

	public Vertex getVertex(int i)
	{
		return vertices[i];
	}

	public void setVertex(Vertex vertex, int i)
	{
		vertices[i] = vertex;
	}

	public int getSize()
	{
		return vertices.length;
	}
}

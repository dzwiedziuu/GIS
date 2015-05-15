package graph;

public class Graph
{
	private Vertex[] vertexes;

	public Graph(int size)
	{
		vertexes = new Vertex[size];
	}

	public Vertex getVertex(int i)
	{
		return vertexes[i];
	}

	public void setVertex(Vertex vertex, int i)
	{
		vertexes[i] = vertex;
	}

	public int getSize()
	{
		return vertexes.length;
	}
}

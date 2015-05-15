package graph;

public class Vertex
{
	private Boolean[] neighbours;

	public Vertex(int size)
	{
		neighbours = new Boolean[size];
		for (int i = 0; i < neighbours.length; i++)
			neighbours[i] = false;
	}

	public void setAsNeighbour(int i)
	{
		neighbours[i] = true;
	}

	public int getSize()
	{
		return neighbours.length;
	}

	public Boolean getNeighbour(int i)
	{
		return neighbours[i];
	}
}

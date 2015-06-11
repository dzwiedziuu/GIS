package domain;

public class Edge
{
	private Vertex begin;
	private Vertex end;

	public Edge(Vertex first, Vertex second)
	{
		this.begin = first;
		this.end = second;
	}

	public boolean isCorrect()
	{
		return !(begin.getColor() == end.getColor());
	}

	public Vertex getAnother(int id)
	{
		return id == begin.getId() ? end : begin;
	}
}

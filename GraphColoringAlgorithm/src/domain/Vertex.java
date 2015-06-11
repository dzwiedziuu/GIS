package domain;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Vertex
{
	private int id; // id
	private int color; // color
	private List<Edge> edges;

	Vertex(int id, int c)
	{
		this.id = id;
		this.color = c;
	}

	List<Edge> getIncorrectEdges()
	{
		List<Edge> result = new LinkedList<Edge>();
		for (Edge e : edges)
			if (e.isCorrect())
				result.add(e);
		return result;
	}

	int getId()
	{
		return id;
	}

	int getColor()
	{
		return color;
	}

	void setColor(Integer color)
	{
		this.color = color;
	}

	List<Edge> getEdges()
	{
		return edges;
	}

	boolean isIncorrect()
	{
		for (Edge edge : edges)
			if (!edge.isCorrect())
				return true;
		return false;
	}

	@Override
	public String toString()
	{
		return "ID: " + getId() + ", color: " + color + " neighbors: " + Arrays.toString(edges.stream().map(new Function<Edge, String>()
		{
			@Override
			public String apply(Edge t)
			{
				return "" + t.getAnother(getId()).getId();
			}
		}).toArray());
	}

	Vertex copy()
	{
		return new Vertex(this.id, this.color);
	}

	public void setEdges(List<Edge> edges2)
	{
		this.edges = edges2;
	}
}

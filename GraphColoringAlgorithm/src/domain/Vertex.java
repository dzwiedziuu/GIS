package domain;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Vertex
{
	int id; // id
	int color; // color
	List<Vertex> neighbors;

	public Vertex(int id, int c)
	{
		this.id = id;
		this.color = c;
		this.neighbors = new LinkedList<Vertex>();
	}

	public int getId()
	{
		return id;
	}

	public int getColor()
	{
		return color;
	}

	public void setColor(Integer color)
	{
		this.color = color;
	}

	public List<Vertex> getNeighbors()
	{
		return neighbors;
	}

	public boolean isIncorrect()
	{
		for (Vertex neighbor : neighbors)
			if (neighbor.color == color)
				return true;
		return false;
	}

	@Override
	public String toString()
	{
		return "ID: " + getId() + ", color: " + color + " neighbors: "
				+ Arrays.toString(neighbors.stream().map(new Function<Vertex, String>() {
					@Override
					public String apply(Vertex t)
					{
						return "" + t.id;
					}
				}).toArray());
	}
}

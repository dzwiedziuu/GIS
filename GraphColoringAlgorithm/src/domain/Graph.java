package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Graph
{
	public Vertex[] vertices;

	public Graph(int size, Random random)
	{
		vertices = new Vertex[size];
		for (int i = 0; i < size; i++)
			vertices[i] = new Vertex(i, random.nextInt(size));
	}

	public Vertex getVertex(int i)
	{
		return vertices[i];
	}

	public int getColorNumber()
	{
		return getAllColors().size();
	}

	public Set<Integer> getAllColors()
	{
		Set<Integer> set = new HashSet<Integer>();
		for (Vertex v : vertices)
			set.add(v.color);
		return set;
	}

	public int getNumberOfIncorrectPairs()
	{
		int result = 0;
		for (Vertex v : vertices)
			for (Vertex neighbor : v.getNeighbors())
				if (neighbor.id > v.id && neighbor.color == v.color)
					result++;
		return result;
	}

	public List<Vertex> getListOfIncorrectVertices()
	{
		List<Vertex> result = new ArrayList<Vertex>();
		for (Vertex v : vertices)
			if (v.isIncorrect())
				result.add(v);
		return result;
	}

	public List<Vertex> getListOfAllVertices()
	{
		return Arrays.asList(vertices);
	}

	public int size()
	{
		return vertices.length;
	}

	public Graph copy()
	{
		try
		{
			return (Graph) this.clone();
		} catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

package domain;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Graph
{
	public Vertex[] vertices;
	private static final Random random = new Random();
	private static int nextGraphId = 0;

	private int graphId;

	// color : vertex cnt
	private Map<Integer, Integer> colorCntMap = new LinkedHashMap<Integer, Integer>();
	// color : edge cnt
	private Map<Integer, Integer> incorrectEdgesCntMap = new LinkedHashMap<Integer, Integer>();

	private int numberOfIncorrectEdges;

	private List<Vertex> incorrectVertices = new LinkedList<Vertex>();

	private Edge[][] edges;

	public Graph(int size)
	{
		graphId = nextGraphId++;
		vertices = new Vertex[size];
		edges = new Edge[vertices.length][vertices.length];
	}

	public Graph init(Integer initialColorNumber)
	{
		if (initialColorNumber == null)
			initialColorNumber = vertices.length;
		for (int i = 0; i < vertices.length; i++)
		{
			int newColor = random.nextInt(initialColorNumber);
			vertices[i] = new Vertex(i, newColor);
		}
		return this;
	}

	public void setVertexColor(int vertexIdx, Integer currentNewColor)
	{
		int oldColor = vertices[vertexIdx].getColor();
		putNewOrAddToMap(colorCntMap, oldColor, 0, -1);
		List<Edge> oldIncorrectEdges = vertices[vertexIdx].getIncorrectEdges();
		for (Edge e : oldIncorrectEdges)
		{
			incorrectVertices.remove(e.getAnother(vertexIdx));
			putNewOrAddToMap(incorrectEdgesCntMap, oldColor, 0, -oldIncorrectEdges.size());
		}
		numberOfIncorrectEdges -= oldIncorrectEdges.size();

		vertices[vertexIdx].setColor(currentNewColor);

		putNewOrAddToMap(colorCntMap, currentNewColor, 1, 1);
		List<Edge> newIncorrectEdges = vertices[vertexIdx].getIncorrectEdges();
		for (Edge e : oldIncorrectEdges)
		{
			incorrectVertices.add(e.getAnother(vertexIdx));
			putNewOrAddToMap(incorrectEdgesCntMap, currentNewColor, oldIncorrectEdges.size(), oldIncorrectEdges.size());
		}
		numberOfIncorrectEdges += newIncorrectEdges.size();
	}

	public void setVertexNeighbours(Integer id, List<Integer> neighbours)
	{
		List<Edge> list = new LinkedList<Edge>();
		for (Integer i : neighbours)
		{
			int id2 = Math.max(i, id);
			int id1 = Math.min(i, id);
			Edge e = edges[id1][id2];
			if (e == null)
			{
				e = new Edge(vertices[id1], vertices[id2]);
				edges[id1][id2] = e;
			}
			list.add(e);
		}
		vertices[id].setEdges(list);
	}

	public void refreshGraphStats()
	{
		for (Vertex v : vertices)
		{
			putNewOrAddToMap(colorCntMap, v.getColor(), 1, 1);
			List<Edge> incorrectEdges = v.getIncorrectEdges();
			if (!incorrectEdges.isEmpty())
				incorrectVertices.add(v);
			int incorrectEdgesCnt = incorrectVertices.size();
			for (Edge e : incorrectEdges)
			{
				putNewOrAddToMap(incorrectEdgesCntMap, v.getColor(), incorrectEdgesCnt, incorrectEdgesCnt);
			}
			numberOfIncorrectEdges += incorrectEdgesCnt;
		}
		// normalize
		numberOfIncorrectEdges = numberOfIncorrectEdges / 2;
		Map<Integer, Integer> normalilzedIncorrectEdgesMap = new LinkedHashMap<Integer, Integer>();
		for (Integer color : incorrectEdgesCntMap.keySet())
			normalilzedIncorrectEdgesMap.put(color, incorrectEdgesCntMap.get(color) / 2);
	}

	// //

	private void putNewOrAddToMap(Map<Integer, Integer> map, Integer key, Integer defaultValue, Integer toAdd)
	{
		Integer value = map.get(key);
		if (value == null)
			value = defaultValue;
		else
			value += toAdd;
		map.put(key, value);
	}

	public Integer getVertexColor(int i)
	{
		return vertices[i].getColor();
	}

	public int getColorNumber()
	{
		return colorCntMap.size();
	}

	public int getNumberOfIncorrectPairs()
	{
		return numberOfIncorrectEdges;
	}

	public List<Vertex> getListOfIncorrectVertices()
	{
		return incorrectVertices;
	}

	public int size()
	{
		return vertices.length;
	}

	public Graph copy()
	{
		Graph g = new Graph(this.vertices.length);
		for (int i = 0; i < g.vertices.length; i++)
			g.vertices[i] = this.vertices[i].copy();
		for (int i = 0; i < g.vertices.length; i++)
			g.vertices[i].setEdges(this.vertices[i].getEdges());
		g.refreshGraphStats();
		return g;
	}

	public String getCurrentColoring()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("graphID: " + graphId + " - ");
		for (Vertex v : vertices)
			sb.append(v.getId() + ": " + v.getColor()).append(", ");
		sb.setLength(sb.length() - 2);
		return sb.toString();
	}

	public int getGraphID()
	{
		return graphId;
	}

	public boolean isLegal()
	{
		return getNumberOfIncorrectPairs() == 0;
	}

	public Set<Integer> getAllColors()
	{
		return new LinkedHashSet<Integer>(colorCntMap.keySet());
	}

	public Integer getIncorrectEdgeCnt(Integer color)
	{
		return incorrectEdgesCntMap.get(color);
	}

	public int getColorCnt(Integer color)
	{
		return colorCntMap.get(color);
	}
}

package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class Graph {
	public Vertex[] vertices;
	private static final Random random = new Random();
	private static int nextGraphId = 0;
	
	private int graphId;
	
	public Graph(int size) {
		graphId = nextGraphId++;
		vertices = new Vertex[size];
	}

	public Graph init() {
		for (int i = 0; i < vertices.length; i++)
			vertices[i] = new Vertex(i, random.nextInt(vertices.length));
		return this;
	}

	public Vertex getVertex(int i) {
		return vertices[i];
	}

	public int getColorNumber() {
		return getAllColors().size();
	}

	public Set<Integer> getAllColors() {
		Set<Integer> set = new HashSet<Integer>();
		for (Vertex v : vertices)
			set.add(v.color);
		return set;
	}

	public int getNumberOfIncorrectPairs() {
		int result = 0;
		for (Vertex v : vertices)
			for (Vertex neighbor : v.getNeighbors())
				if (neighbor.id > v.id && neighbor.color == v.color)
					result++;
		return result;
	}

	public List<Vertex> getListOfIncorrectVertices() {
		List<Vertex> result = new ArrayList<Vertex>();
		for (Vertex v : vertices)
			if (v.isIncorrect())
				result.add(v);
		return result;
	}

	public List<Vertex> getListOfAllVertices() {
		return Arrays.asList(vertices);
	}

	public int size() {
		return vertices.length;
	}

	public Graph copy() {
		Graph g = new Graph(this.vertices.length);
		for (int i = 0; i < g.vertices.length; i++)
			g.vertices[i] = this.vertices[i].copy();
		for (int i = 0; i < g.vertices.length; i++)
			g.vertices[i].neighbors = copyNeighbors(i, g);
		return g;
	}

	private List<Vertex> copyNeighbors(int i, Graph g) {
		List<Vertex> list = new ArrayList<Vertex>();
		for (Vertex v : this.vertices[i].neighbors)
			list.add(g.vertices[v.id]);
		return list;
	}

	public String getCurrentColoring() {
		StringBuffer sb = new StringBuffer();
		sb.append("graphID: " + graphId + " - ");
		for (Vertex v : vertices)
			sb.append(v.id + ": " + v.color).append(", ");
		sb.setLength(sb.length() - 2);
		return sb.toString();
	}

	public int getGraphID() {

		return graphId;
	}
}

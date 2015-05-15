package graph.generation;

import graph.Graph;
import graph.Vertex;

import java.util.Random;

public class DefaultGenerationStrategy implements GenerationStrategy
{
	private Random random = new Random();

	@Override
	public Graph generate(int vertexNumber, double probability)
	{
		Graph graph = new Graph(vertexNumber);
		for (int i = 0; i < vertexNumber; i++)
			graph.setVertex(new Vertex(vertexNumber), i);
		for (int i = 0; i < graph.getSize(); i++)
			for (int j = i + 1; j < graph.getSize(); j++)
			{
				if (checkIfIsNeighbour(probability))
				{
					graph.getVertex(i).setAsNeighbour(j);
					graph.getVertex(j).setAsNeighbour(i);
				}
			}

		return graph;
	}

	private boolean checkIfIsNeighbour(double probability)
	{
		return random.nextDouble() < probability ? true : false;
	}
}

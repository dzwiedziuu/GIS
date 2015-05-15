package algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Random;

import domain.Graph;
import domain.Vertex;

public class GraphColoringAlgorithm
{
	private Random random = new Random();
	private ColoringStrategy coloringStrategy;

	public Graph readGraph(String inputFile) throws IOException
	{
		Reader reader = null;
		if (inputFile != null)
			reader = new FileReader(new File(inputFile));
		else
			reader = new InputStreamReader(System.in);
		return readGraph(new BufferedReader(reader));
	}

	private Graph readGraph(BufferedReader reader) throws IOException
	{
		String line = reader.readLine();
		if (line == null)
			return null;
		Graph graph = new Graph(line.length() - 2, random);
		do
		{
			String[] parts = line.split(":");
			Integer id = Integer.parseInt(parts[0]);
			Vertex vertex = graph.getVertex(id);
			for (int i = 0; i < parts[1].length(); i++)
				if (parts[1].charAt(i) == '1')
					vertex.getNeighbors().add(graph.getVertex(i));
			line = reader.readLine();
		} while (line != null);
		return graph;
	}

	public Graph colorGraph(Graph graph, Long initialTemp, Long minTemp, Double alfa)
	{
		return coloringStrategy.colorGraph(graph, initialTemp, minTemp, alfa);
	}
}

package graph;

import graph.generation.DefaultGenerationStrategy;
import graph.generation.GenerationStrategy;
import graph.store.DefaultStoreStrategy;
import graph.store.StoreStrategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class GraphGenerator
{
	private GenerationStrategy generatorStrategy = new DefaultGenerationStrategy();
	// private StoreStrategy storeStrategy = new PrettyStoreStrategy();
	private StoreStrategy storeStrategy = new DefaultStoreStrategy();
	private Graph graph;

	public void generateGraph(int vertexNumber, double probability)
	{
		graph = generatorStrategy.generate(vertexNumber, probability);
	}

	public void storeGraph(File file)
	{
		try
		{
			Writer writer = new BufferedWriter(new FileWriter(file));
			// Writer writer = new BufferedWriter(new OutputStreamWriter(System.out));
			storeStrategy.store(writer, graph);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

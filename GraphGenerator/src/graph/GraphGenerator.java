package graph;

import graph.generation.DefaultGenerationStrategy;
import graph.generation.GenerationStrategy;
import graph.store.DefaultStoreStrategy;
import graph.store.StoreStrategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

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

	public void storeGraph(File file, boolean printConsole)
	{
		try
		{
			if (printConsole)
				storeStrategy.store(new BufferedWriter(new OutputStreamWriter(System.out)), graph);
			if (file != null)
				storeStrategy.store(new BufferedWriter(new FileWriter(file)), graph);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

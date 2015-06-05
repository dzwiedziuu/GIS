package algorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Graph;
import domain.Vertex;

public class GraphColoringAlgorithm
{
	private static final Logger logger = LoggerFactory.getLogger(GraphColoringAlgorithm.class);

	private ColoringStrategy coloringStrategy = new DefaultColoringStrategy();
	private PrintingStrategy printingStrategy = new DefaultPrintingStrategy();

	public Graph readGraph(File inputFile) throws IOException
	{
		Reader reader = null;
		if (inputFile != null)
			reader = new FileReader(inputFile);
		else
			reader = new InputStreamReader(System.in);
		return readGraph(new BufferedReader(reader));
	}

	private Graph readGraph(BufferedReader reader) throws IOException
	{
		String line = reader.readLine();
		if (line == null)
			return null;
		Graph graph = new Graph(line.length() - 2).init();
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

	public Graph colorGraph(Graph graph, Double initialTemp, Double minTemp, Double alfa, Long k)
	{
		Graph g = coloringStrategy.colorGraph(graph, initialTemp, minTemp, alfa, k);
		logger.info("Colored graph: " + g.getCurrentColoring());
		logger.info("Stats: colors: " + g.getColorNumber() + ", number of incorrectPairs: " + g.getNumberOfIncorrectPairs());
		return g;
	}

	public AlgorithmResult printResult(Graph g, String fileToSave, boolean printAtConsole)
	{
		AlgorithmResult algorithmResult = new AlgorithmResult();
		algorithmResult.colorNumber = g.getColorNumber();
		algorithmResult.totalSteps = coloringStrategy.getAlgorithmSteps();
		algorithmResult.worseSteps = coloringStrategy.getWorseNextSteps();
		if (printAtConsole)
			printingStrategy.printGraph(algorithmResult, new BufferedWriter(new OutputStreamWriter(System.out)));
		if (fileToSave != null)
			try
			{
				printingStrategy.printGraph(algorithmResult, new BufferedWriter(new FileWriter(fileToSave)));
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return algorithmResult;
	}
}

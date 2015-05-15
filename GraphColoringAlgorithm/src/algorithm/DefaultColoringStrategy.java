package algorithm;

import java.util.List;
import java.util.Random;
import java.util.Set;

import domain.Graph;
import domain.Vertex;

public class DefaultColoringStrategy implements ColoringStrategy
{
	private Graph currentBestGraph;
	private Double currentTemp;
	private Integer lastObjectiveFunctionValue;
	private Random random = new Random();
	private int nextNewColor;

	@Override
	public Graph colorGraph(Graph graph, Long initialTemp, Long minTemp, Double alfa)
	{
		nextNewColor = graph.size();
		lastObjectiveFunctionValue = getObjectiveFunction(graph);
		for (currentTemp = initialTemp * 1.0; currentTemp > minTemp; currentTemp = currentTemp * alfa)
		{
			Graph nextGraph = graph.copy();
			List<Vertex> listOfVertices = nextGraph.getListOfIncorrectVertices();
			Set<Integer> colors = nextGraph.getAllColors();
			Vertex currentVertex;
			if (listOfVertices.isEmpty())
			{
				currentVertex = nextGraph.getVertex(random.nextInt(nextGraph.size()));
				colors.add(nextNewColor++);
			} else
				currentVertex = listOfVertices.get(random.nextInt(listOfVertices.size()));
			Integer[] colorArray = colors.toArray(new Integer[colors.size()]);
			Integer currentNewColor = colorArray[random.nextInt(colorArray.length)];
			currentVertex.setColor(currentNewColor);
			Integer newObjectFunctionValue = getObjectiveFunction(nextGraph);
			Double probability = getProbability(newObjectFunctionValue);
			if (probability > random.nextDouble())
			{
				if (newObjectFunctionValue == nextGraph.getColorNumber())
					currentBestGraph = nextGraph;
				graph = nextGraph;
				lastObjectiveFunctionValue = newObjectFunctionValue;
			}
		}
		return currentBestGraph;
	}

	private Double getProbability(Integer newValue)
	{
		if (newValue < lastObjectiveFunctionValue)
			return 1.0;
		else
			return Math.pow(Math.E, -(newValue - lastObjectiveFunctionValue) / currentTemp);
	}

	private Integer getObjectiveFunction(Graph graph)
	{
		int a = graph.getNumberOfIncorrectPairs();
		int b = a > 0 ? 1 : 0;
		int c = graph.getColorNumber();
		return a + b + c;
	}

}

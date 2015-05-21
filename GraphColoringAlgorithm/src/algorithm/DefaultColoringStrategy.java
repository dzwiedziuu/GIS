package algorithm;

import java.util.List;
import java.util.Random;
import java.util.Set;

import main.Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Graph;
import domain.Vertex;

public class DefaultColoringStrategy implements ColoringStrategy
{
	private static final Logger logger = LoggerFactory.getLogger(DefaultColoringStrategy.class);
	
	private Graph currentBestGraph;
	private Double currentTemp;
	private Integer lastObjectiveFunctionValue;
	private Random random = new Random();
	private int nextNewColor;

	@Override
	public Graph colorGraph(Graph graph, Long initialTemp, Long minTemp, Double alfa)
	{
		logger.trace(graph.getCurrentColoring());
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
			colors.remove(currentVertex.getColor());
			Integer[] colorArray = colors.toArray(new Integer[colors.size()]);
			Integer currentNewColor = colorArray[random.nextInt(colorArray.length)];
			logger.trace("Vertex ID: " + currentVertex.getId() + ", old color: " + currentVertex.getColor() + ", new color: " + currentNewColor);
			currentVertex.setColor(currentNewColor);
			logger.trace(nextGraph.getCurrentColoring());
			Integer newObjectFunctionValue = getObjectiveFunction(nextGraph);
			Double probability = getProbability(newObjectFunctionValue);
			if (probability > random.nextDouble())
			{
				if (newObjectFunctionValue == nextGraph.getColorNumber())
					currentBestGraph = nextGraph;
				graph = nextGraph;
				lastObjectiveFunctionValue = newObjectFunctionValue;
			}
			logger.trace("Graph switched? " + (probability > random.nextDouble() ? "YES!" : "NO..."));
		}
		return currentBestGraph;
	}

	private Double getProbability(Integer newValue)
	{
		double result = 0;
		if (newValue < lastObjectiveFunctionValue)
			result= 1.0;
		else
			result = Math.pow(Math.E, -(newValue - lastObjectiveFunctionValue) / currentTemp);
		logger.trace("probability: " + result);
		return result;
	}

	private Integer getObjectiveFunction(Graph graph)
	{
		int a = graph.getNumberOfIncorrectPairs();
		int b = a > 0 ? 1 : 0;
		int c = graph.getColorNumber();
		int result = a + b + c;
		logger.trace("GraphID " + graph.getGraphID() + " - objectiveFunction: " + result + ", colors: " + c + ", incorrectPairs: " + a);
		return result;
	}

}
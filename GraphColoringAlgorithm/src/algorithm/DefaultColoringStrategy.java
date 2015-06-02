package algorithm;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.Graph;
import domain.Vertex;

public class DefaultColoringStrategy implements ColoringStrategy
{
	private static final double MAX_PROBABILITY = 1.0;

	private static final Logger logger = LoggerFactory.getLogger(DefaultColoringStrategy.class);

	private Graph currentBestGraph;
	private Double currentTemp;
	private Integer lastObjectiveFunctionValue;
	private Random random = new Random();
	private int nextNewColor;
	private int worseNextSteps;
	private long algorithmSteps;

	@Override
	public Graph colorGraph(Graph graph, Double initialTemp, Double minTemp, Double alfa, Long k)
	{
		worseNextSteps = 0;
		logger.trace(graph.getCurrentColoring());
		nextNewColor = graph.size();
		lastObjectiveFunctionValue = getObjectiveFunction(graph);
		algorithmSteps = 0;
		for (currentTemp = initialTemp * 1.0; currentTemp > minTemp; currentTemp = currentTemp * alfa)
		{
			for (long epochLeft = k; epochLeft > 0; epochLeft--)
			{
				algorithmSteps++;
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
				boolean switched = false;
				if (probability > random.nextDouble())
				{
					if (newObjectFunctionValue == nextGraph.getColorNumber()
							&& (currentBestGraph == null || currentBestGraph.getColorNumber() > nextGraph.getColorNumber()))
						currentBestGraph = nextGraph;
					graph = nextGraph;
					lastObjectiveFunctionValue = newObjectFunctionValue;
					if (probability != MAX_PROBABILITY)
						worseNextSteps++;
					switched = true;
				}
				logger.trace("Graph switched? " + (switched ? "YES!" : "NO..."));
			}
		}
		return currentBestGraph;
	}

	private Double getProbability(Integer newValue)
	{
		double result = 0;
		if (newValue < lastObjectiveFunctionValue)
			result = MAX_PROBABILITY;
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

	@Override
	public int getWorseNextSteps()
	{
		return worseNextSteps;
	}

	@Override
	public long getAlgorithmSteps()
	{
		return algorithmSteps;
	}
}

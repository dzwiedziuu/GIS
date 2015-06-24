package algorithm;

import java.util.LinkedList;
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
	private int algorithmSteps;
	public List<Integer> jumps = new LinkedList<Integer>();

	private int firstNTries;

	@Override
	public Graph colorGraph(Graph graph, Double initialTemp, Double minTemp, Double alfa, Long k, Double bolzmanFactor)
	{
		worseNextSteps = 0;
		logger.trace(graph.getCurrentColoring());
		nextNewColor = graph.size();
		lastObjectiveFunctionValue = getObjectiveFunction(graph);
		algorithmSteps = 0;
		for (currentTemp = initialTemp * 1.0; currentTemp > minTemp && algorithmSteps < firstNTries; currentTemp = currentTemp * alfa)
		{
			for (long epochLeft = k; epochLeft > 0 && algorithmSteps < firstNTries; epochLeft--)
			{
				algorithmSteps++;
				List<Vertex> listOfVertices = graph.getListOfIncorrectVertices();
				Set<Integer> colors = graph.getAllColors();
				int currentVertexIdx;
				if (listOfVertices.isEmpty())
				{
					currentVertexIdx = random.nextInt(graph.size());
					colors.add(nextNewColor++);
				} else
					currentVertexIdx = random.nextInt(listOfVertices.size());
				int oldColor = graph.getVertexColor(currentVertexIdx);
				colors.remove(oldColor);
				Integer[] colorArray = colors.toArray(new Integer[colors.size()]);
				Integer currentNewColor = colorArray[random.nextInt(colorArray.length)];
				logger.trace("Vertex ID: " + currentVertexIdx + ", old color: " + oldColor + ", new color: " + currentNewColor);
				graph.setVertexColor(currentVertexIdx, currentNewColor);
				logger.trace(graph.getCurrentColoring());
				Integer newObjectFunctionValue = getObjectiveFunction(graph);
				Double probability = getProbability(newObjectFunctionValue, bolzmanFactor);
				boolean switched = false;
				boolean doJump = probability > random.nextDouble();
				if (doJump)
				{
					if (graph.isLegal() && (currentBestGraph == null || currentBestGraph.getColorNumber() > graph.getColorNumber()))
						currentBestGraph = graph.copy();
					lastObjectiveFunctionValue = newObjectFunctionValue;
					if (probability != MAX_PROBABILITY)
						worseNextSteps++;
					switched = true;
				} else
					graph.setVertexColor(currentVertexIdx, oldColor);
				addJumpInfo(switched);
				logger.trace("Graph switched? " + (switched ? "YES!" : "NO..."));
			}
		}
		return currentBestGraph;
	}

	private Double getProbability(Integer newValue, Double bolzmanFactor)
	{
		double result = 0;
		if (newValue < lastObjectiveFunctionValue)
			result = MAX_PROBABILITY;
		else
			result = Math.pow(Math.E, -(newValue - lastObjectiveFunctionValue) / (currentTemp * bolzmanFactor));
		logger.trace("probability: " + result);
		return result;
	}

	protected Integer getObjectiveFunction(Graph graph)
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
	public int getAlgorithmSteps()
	{
		return algorithmSteps;
	}

	@Override
	public void setFirstNTries(int firstNTries)
	{
		this.firstNTries = firstNTries;
	}
	
	private void addJumpInfo(boolean jumpOccured)
	{
		if (jumpOccured) jumps.add(1); else jumps.add(0);
	}
	
	@Override
	public List<Integer> getJumpList() { return jumps; }
}

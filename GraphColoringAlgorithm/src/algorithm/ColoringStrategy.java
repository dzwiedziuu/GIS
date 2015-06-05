package algorithm;

import domain.Graph;

public interface ColoringStrategy
{
	public Graph colorGraph(Graph graph, Double initialTemp, Double minTemp, Double alfa, Long k);

	public int getWorseNextSteps();

	public int getAlgorithmSteps();
}

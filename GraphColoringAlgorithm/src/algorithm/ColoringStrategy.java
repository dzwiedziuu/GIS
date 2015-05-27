package algorithm;

import domain.Graph;

public interface ColoringStrategy
{
	public Graph colorGraph(Graph graph, Long initialTemp, Long minTemp, Double alfa);

	public int getWorseNextSteps();
}

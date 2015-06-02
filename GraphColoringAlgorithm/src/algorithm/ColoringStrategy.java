package algorithm;

import domain.Graph;

public interface ColoringStrategy
{
	public Graph colorGraph(Graph graph, Double initialTemp, Double minTemp, Double alfa);

	public int getWorseNextSteps();
}

package algorithm;

import java.io.BufferedWriter;

public interface PrintingStrategy
{
	public void printGraph(AlgorithmResult algorithmResult, BufferedWriter writer);
}

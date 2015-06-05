package algorithm;

import java.io.BufferedWriter;
import java.io.IOException;

public class DefaultPrintingStrategy implements PrintingStrategy
{

	@Override
	public void printGraph(AlgorithmResult algorithmResult, BufferedWriter writer)
	{
		try
		{
			writer.write("" + algorithmResult.colorNumber + " " + algorithmResult.worseSteps + " " + algorithmResult.totalSteps);
			writer.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

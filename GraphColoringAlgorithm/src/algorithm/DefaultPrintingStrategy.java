package algorithm;

import java.io.BufferedWriter;
import java.io.IOException;

import domain.Graph;

public class DefaultPrintingStrategy implements PrintingStrategy
{

	@Override
	public void printGraph(Graph g, int worseStepsNext, long algorithmSteps, BufferedWriter writer)
	{
		try
		{
			writer.write("" + g.getColorNumber() + " " + worseStepsNext + " " + algorithmSteps);
			writer.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package algorithm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ListIterator;

public class DefaultPrintingStrategy implements PrintingStrategy
{

	@Override
	public void printGraph(AlgorithmResult algorithmResult, BufferedWriter writer)
	{
		try
		{
			writer.write("" + algorithmResult.colorNumber + " " + algorithmResult.worseSteps + " " + algorithmResult.totalSteps + "\n");

			ListIterator<Integer> it = algorithmResult.jumpList.listIterator();
			while (it.hasNext())
			{
				if (it.nextIndex() != 0)  writer.write(" ");
				writer.write(it.next().toString());
			}
			writer.flush();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

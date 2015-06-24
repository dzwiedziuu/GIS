package algorithm;

import java.util.List;
import java.util.ListIterator;

public class AlgorithmResult
{
	public int colorNumber;
	public int worseSteps;
	public int totalSteps;
	public List<Integer> jumpList;
	

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Colors=" + colorNumber + ", worseSteps=" + worseSteps + "/" + totalSteps + "(" + getPctWorseSteps() + "), betterSteps=" + getBetterSteps() + "\n");
		sb.append("jumps=");
		ListIterator<Integer> it = jumpList.listIterator();
		while (it.hasNext())
		{
			if (it.nextIndex() != 1)  sb.append(";");
			sb.append(it.next());
		}
		return sb.toString();
	}

	public double getPctWorseSteps()
	{
		return ((double) worseSteps / totalSteps) * 100;
	}

	public int getBetterSteps()
	{
		return totalSteps - worseSteps;
	}
}

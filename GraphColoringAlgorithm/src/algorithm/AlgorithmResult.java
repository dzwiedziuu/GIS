package algorithm;

public class AlgorithmResult
{
	public int colorNumber;
	public int worseSteps;
	public int totalSteps;

	@Override
	public String toString()
	{
		return "Colors=" + colorNumber + ", worseSteps=" + worseSteps + "/" + totalSteps + "(" + getPctWorseSteps() + "), betterSteps=" + getBetterSteps();
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

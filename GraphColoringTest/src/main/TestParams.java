package main;

import test.RunTest.GroupResult;
import domain.Params;

public class TestParams extends Params
{
	public TestParams()
	{
		this.alpha = 0.95;
		this.initialTemperature = 1.0;
		this.minimalTemperature = 0.01;
		this.k = 20l;
		this.vertexNumber = 30;
		this.probability = 0.2;
		this.graphNumber = 10;
		this.tries = 10;
		this.satisfyLimit = 5;
		this.bolzmanFactor = 0.1;
	}

	public static double factor = 2;

	@Override
	public boolean doNextTest()
	{
		return this.initialTemperature >= 0.0001 && secondCondition();
	}

	public boolean secondCondition()
	{
		return this.initialTemperature <= 1000;
	}

	public static boolean switched = false;

	@Override
	public Params nextTestParams()
	{
		Params newParams = this.copy();
		if (!switched)
			newParams.initialTemperature = this.initialTemperature * factor;
		// factor = factor * 1.4;
		if (!secondCondition())
		{
			// switched = true;
			newParams.initialTemperature = 1.0;
		}
		if (switched)
			newParams.initialTemperature = this.initialTemperature / factor;
		return newParams;
	}

	@Override
	public boolean iSatisfiedIfNotChange(GroupResult currentGroupResult, GroupResult firstGroupResult)
	{
		if (firstGroupResult == null)
			return true;
		double difference = currentGroupResult.betterSteps - firstGroupResult.betterSteps;
		double factor = difference > 0 ? 1 : -1;
		double ratio = difference / firstGroupResult.betterSteps;
		ratio = Math.abs(ratio);
		if (ratio > 0.01 && satisfyLimit > notSatisfiedTimes)
		{
			this.minimalTemperature = this.minimalTemperature / Math.pow(this.alpha, difference / this.k);
			notSatisfiedTimes++;
			System.out.println("not satisfied, min temp=" + this.minimalTemperature);
			return false;
		}
		System.out.println("satisfied");
		notSatisfiedTimes = 0;
		return true;
	}
}

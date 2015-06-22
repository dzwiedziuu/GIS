package main;

import test.RunTest.GroupResult;
import domain.Params;

public class TestParams extends Params
{

	public TestParams()
	{
		this.alpha = 0.95;
		this.initialTemperature = 5000.0;
		this.minimalTemperature = 0.01;
		this.k = 20l;
		this.vertexNumber = 30;
		this.probability = 0.99;
		this.graphNumber = 5;
		this.paramTesttries = 100;
		this.satisfyLimit = 5;
		this.bolzmanFactor = 1;
		this.initialColorNumber = vertexNumber;
		this.algorithmTries = 20;
	}

	public static double factor = 2;

	@Override
	public boolean doNextTest()
	{
		return this.initialTemperature >= 1.0;
	}

	public static boolean switched = false;

	@Override
	public Params nextTestParams()
	{
		Params newParams = this.copy();
		newParams.initialTemperature = this.initialTemperature / factor;
		return newParams;
	}

	@Override
	public boolean iSatisfiedIfNotChange(GroupResult currentGroupResult, GroupResult firstGroupResult, GroupResult lastGroupResult)
	{
		// if (firstGroupResult == null)
		// return true;
		// double difference = currentGroupResult.betterSteps -
		// firstGroupResult.betterSteps;
		// double factor = difference > 0 ? 1 : -1;
		// double ratio = difference / firstGroupResult.betterSteps;
		// ratio = Math.abs(ratio);
		// if (ratio > 0.01 && satisfyLimit > notSatisfiedTimes)
		// {
		// this.minimalTemperature = this.minimalTemperature /
		// Math.pow(this.alpha, difference / this.k);
		// notSatisfiedTimes++;
		// System.out.println("not satisfied, min temp=" +
		// this.minimalTemperature);
		// return false;
		// }
		// System.out.println("satisfied");
		// notSatisfiedTimes = 0;
		// return true;
		return true;
	}
}

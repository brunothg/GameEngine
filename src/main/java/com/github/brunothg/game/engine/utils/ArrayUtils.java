package com.github.brunothg.game.engine.utils;

public class ArrayUtils
{

	public static double[] toDouble(String[] params)
	{

		double[] ar = new double[params.length];
		for (int i = 0; i < params.length; i++)
		{
			ar[i] = Double.valueOf(params[i]);
		}

		return ar;
	}

	public static int[] toInteger(String[] params)
	{

		int[] ar = new int[params.length];
		for (int i = 0; i < params.length; i++)
		{
			ar[i] = Integer.valueOf(params[i]);
		}

		return ar;
	}

}

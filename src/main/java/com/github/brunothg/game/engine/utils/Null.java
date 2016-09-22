package com.github.brunothg.game.engine.utils;

/**
 * 
 * Utility class for null value handling
 */
public class Null
{

	/**
	 * Check if there is a null value. If there's a null value replace it.
	 * 
	 * @param test Checked value
	 * @param replace Replace value
	 */
	public static <T> T nvl(T test, T replace)
	{

		if (test == null)
		{

			return replace;
		}

		return test;
	}

	/**
	 * Check if there is any null value in a set of objects
	 * 
	 * @return true if at least one null value could be found
	 */
	public static boolean isAnyNull(Object... objs)
	{

		for (int i = 0; i < objs.length; i++)
		{

			if (objs[i] == null)
			{

				return true;
			}
		}

		return false;
	}

	/**
	 * Check if there is any null or empty string value in a set of strings
	 * 
	 * @return true if at least one null or empty string value could be found
	 */
	public static boolean isAnyEmpty(String... strings)
	{

		for (int i = 0; i < strings.length; i++)
		{

			if (strings[i] == null || strings[i].isEmpty())
			{

				return true;
			}
		}

		return false;
	}

	/**
	 * Check if all values are null values
	 * 
	 * @return true if all values are null values
	 */
	public static boolean isNull(Object... objs)
	{

		for (int i = 0; i < objs.length; i++)
		{

			if (objs[i] != null)
			{

				return false;
			}
		}

		return true;
	}

	/**
	 * Check if no value is a null values
	 * 
	 * @return true if no value is a null value
	 */
	public static boolean isNotNull(Object... objs)
	{

		for (int i = 0; i < objs.length; i++)
		{

			if (objs[i] == null)
			{

				return false;
			}
		}

		return true;
	}
}

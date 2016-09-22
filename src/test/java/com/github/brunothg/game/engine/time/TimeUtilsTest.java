package com.github.brunothg.game.engine.time;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeUtilsTest
{

	@Test
	public void nanoToSecondTest() throws Exception
	{

		long nano = 456882345236L;
		double seconds = TimeUtils.Seconds(nano);

		assertEquals(456, (long) seconds);
	}

	@Test
	public void secondInMilli() throws Exception
	{

		int second = 57568;
		double milliseconds = TimeUtils.Milliseconds(TimeUtils.NanosecondsOfSeconds(second));

		assertEquals(57568000, (int) milliseconds);
	}

}

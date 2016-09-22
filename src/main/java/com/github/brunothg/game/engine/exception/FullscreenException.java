package com.github.brunothg.game.engine.exception;

public class FullscreenException extends RuntimeException
{

	private static final long serialVersionUID = 1L;

	public FullscreenException()
	{
		super();
	}

	public FullscreenException(String arg0, Throwable arg1, boolean arg2, boolean arg3)
	{
		super(arg0, arg1, arg2, arg3);
	}

	public FullscreenException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

	public FullscreenException(String arg0)
	{
		super(arg0);
	}

	public FullscreenException(Throwable arg0)
	{
		super(arg0);
	}

}

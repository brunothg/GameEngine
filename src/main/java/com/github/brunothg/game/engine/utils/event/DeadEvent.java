package com.github.brunothg.game.engine.utils.event;

/**
 * Event that is used if a fired event had no handler registered.
 * 
 * @author Marvin Bruns
 *
 */
public final class DeadEvent extends Event<Event<?>>
{

	public DeadEvent(Event<?> unhandledEvent)
	{
		super();
		this.setUnhandledEvent(unhandledEvent);
	}

	/**
	 * Get the unhandled event
	 * 
	 * @return The event
	 */
	public Event<?> getUnhandledEvent()
	{
		return getMessage();
	}

	public void setUnhandledEvent(Event<?> unhandledEvent)
	{
		setMessage(unhandledEvent);
	}

	@Override
	public String toString()
	{
		return "DeadEvent [unhandledEvent=" + getUnhandledEvent() + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((getUnhandledEvent() == null) ? 0 : getUnhandledEvent().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeadEvent other = (DeadEvent) obj;
		if (getUnhandledEvent() == null)
		{
			if (other.getUnhandledEvent() != null)
				return false;
		}
		else if (!getUnhandledEvent().equals(other.getUnhandledEvent()))
			return false;
		return true;
	}
}

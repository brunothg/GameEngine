package game.engine.utils.event;

import java.util.LinkedList;
import java.util.List;

/**
 * Base class for each Event
 * 
 * @author Marvin Bruns
 *
 */
public abstract class Event<T> {

	private Object source;
	private T message;
	private boolean consumed;
	private List<Object> consumer;

	public Event() {
		this(null);
	}

	public Event(Object source) {
		this(source, null);
	}

	public Event(Object source, T message) {
		this.source = source;
		this.setMessage(message);
		this.setConsumed(false);
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	public T getMessage() {
		return message;
	}

	public void setMessage(T message) {
		this.message = message;
	}

	/**
	 * Get the message string. If it isn't a string, the toString() method will
	 * be used.
	 * 
	 * @return The message or null, if not set
	 */
	public String getMessageString() {
		Object msg = getMessage();
		return (msg == null) ? null : msg.toString();
	}

	/**
	 * Test, if this event got handled before. You may ignore this event in this
	 * case.
	 * 
	 * @return true, if it was handled before
	 */
	public boolean isConsumed() {
		return consumed;
	}

	protected void setConsumed(boolean consumed) {
		this.consumed = consumed;
	}

	/**
	 * Call this method, if you handled this event and another handler is not
	 * required.
	 */
	public void consume() {
		consume(null);
	}

	/**
	 * Call this method, if you handled this event and another handler is not
	 * required.
	 * 
	 * @see #consume()
	 * @param consumer
	 *            Set the consuming object, so that other handler can react on
	 *            this
	 */
	public void consume(Object consumer) {
		setConsumed(true);

		if (consumer != null) {
			getConsumer().add(consumer);
		}
	}

	public List<Object> getConsumer() {
		if (consumer == null) {
			consumer = new LinkedList<Object>();
		}
		return consumer;
	}

	@Override
	public String toString() {
		return "Event [source=" + source + ", message=" + message + ", consumed=" + consumed + "]";
	}

}

package game.engine.utils.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An {@link EventBus} for passing {@link Event}s. Objects can register and fire
 * events. Handlermethods annotated with {@link EventListener} will be used.
 * 
 * @author Marvin Bruns
 *
 */
public class EventBus {
	private static final Logger LOG = LoggerFactory.getLogger(EventBus.class);

	private String name;
	private Map<Class<?>, List<Listener>> listeners = new HashMap<Class<?>, List<Listener>>();

	public EventBus(Class<?> cls) {
		this(cls.getCanonicalName());
	}

	public EventBus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void register(final Object listener) {
		if (listener == null) {
			throw new NullPointerException("Null value not allowed");
		}

		Method[] methods = listener.getClass().getDeclaredMethods();
		for (Method value : methods) {
			EventListener evListener = value.getAnnotation(EventListener.class);
			if (evListener == null) {
				continue;
			}

			Class<?>[] params = value.getParameterTypes();
			if (params.length != 1) {
				LOG.warn("Illegal EventListener registered {}#{}: only exact one param allowed",
						listener.getClass().getCanonicalName(), value.getName());
				continue;
			}

			Class<?> eventType = params[0];
			if (!Event.class.isAssignableFrom(eventType)) {
				LOG.warn("Illegal  EventListener registered {}#{}: must listen for Event subtypes, but has {}",
						listener.getClass().getCanonicalName(), value.getName(), eventType.getCanonicalName());
				continue;
			}

			addListener(listener, value, eventType);
		}

	}

	protected void addListener(Object listener, Method handler, Class<?> eventType) {
		Listener evListener = new Listener(listener, handler);

		List<Listener> evList = listeners.get(eventType);
		if (evList == null) {
			evList = new LinkedList<Listener>();
			listeners.put(eventType, evList);
		}

		evList.add(evListener);
	}

	public void unregister(Object listener) {
		if (listener == null) {
			throw new NullPointerException("Null value not allowed");
		}

		Set<Entry<Class<?>, List<Listener>>> entrySet = listeners.entrySet();
		for (Entry<Class<?>, List<Listener>> entry : entrySet) {
			List<Listener> value = entry.getValue();
			Iterator<Listener> iterator = value.iterator();
			while (iterator.hasNext()) {
				Listener next = iterator.next();
				Object obj = next.obj;
				if (obj == listener || obj.equals(listener)) {
					iterator.remove();
				}
			}
		}
	}

	/**
	 * Fire an event. All registered handler methods will called as long as the
	 * event has not be consumed or there are handler left that don't care about
	 * it.
	 * 
	 * @param event
	 *            Fired event
	 */
	public void fire(Event<?> event) {
		List<Listener> evListeners = listeners.get(event.getClass());
		if (evListeners == null) {
			return;
		}

		boolean foundAny = false;
		for (Listener listener : evListeners) {
			Object object = listener.getObject();
			Method handler = listener.getHandler();
			EventListener configuration = listener.getConfiguration();

			if (event.isConsumed() && !configuration.ignoreConsumption()) {
				continue;
			}

			try {
				handler.setAccessible(true);
			} catch (SecurityException e1) {
				LOG.warn("Could not make handler accessible: {}", listener);
			}
			try {
				handler.invoke(object, event);
				foundAny = true;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				LOG.warn("Execute event handler failed -> {}", listener);
			}
		}

		if (!foundAny && !(event instanceof DeadEvent)) {
			fire(new DeadEvent(event));
		}
	}

	protected static class Listener {
		private Object obj;
		private Method handler;

		public Listener(Object obj, Method handler) {
			super();
			this.obj = obj;
			this.handler = handler;
		}

		public Object getObject() {
			return obj;
		}

		public Method getHandler() {
			return handler;
		}

		public Class<?> getEventType() {
			return getHandler().getParameterTypes()[0];
		}

		public EventListener getConfiguration() {
			return getHandler().getAnnotation(EventListener.class);
		}

		@Override
		public String toString() {
			return "Listener [obj=" + obj + ", handler=" + handler + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((handler == null) ? 0 : handler.hashCode());
			result = prime * result + ((obj == null) ? 0 : obj.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Listener other = (Listener) obj;
			if (handler == null) {
				if (other.handler != null)
					return false;
			} else if (!handler.equals(other.handler))
				return false;
			if (this.obj == null) {
				if (other.obj != null)
					return false;
			} else if (!this.obj.equals(other.obj))
				return false;
			return true;
		}
	}

	@Override
	public String toString() {
		return "EventBus [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventBus other = (EventBus) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}

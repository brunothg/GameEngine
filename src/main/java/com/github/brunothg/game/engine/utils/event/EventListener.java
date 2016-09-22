package com.github.brunothg.game.engine.utils.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to identify handler methods. The {@link EventBus} will use this methods. The method should
 * have only one parameter thats of type {@link Event}. Die
 * 
 * @author Marvin Bruns
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface EventListener {
	public boolean ignoreConsumption() default false;
}

package br.com.nils.selenium.safo.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ FIELD })
@Inherited
public @interface SafoComponent {

	public String id() default "";

	public String xpath() default "";

	public String className() default "";

	public int resultPosition() default 0;

	public boolean ignore() default false;

	public int order() default 999;

	public boolean forceLostFocus() default true;

	public boolean clearBefore() default true;

	public boolean ajaxWait() default true;

	public boolean forceBlur() default true;

}

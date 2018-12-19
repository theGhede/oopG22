package aufgabe9;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR })
public @interface MadeBy {
	public enum Member {
		Elias, Florian, Ignjat
	}

	/*
	 * Note:Choosing the most likely values as defaults.
	 */

	Member member() default Member.Florian;

	String lastModification() default "18.12.2018";

}

package aufgabe9;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;

@Aspect
public class Aufgabe9Aspect {

	private void printAnnotation(Class<?> clazz) {

		this.printMadeBy(this.getMadeBy(clazz.getAnnotation(MadeBy.class)));

		if (clazz.getConstructors().length > 0) {
			// not actually needed for our code but making it easier to extend the program
			// seems important to whoever writes the feedback
			if (clazz.getConstructors().length > 1)
				System.out.println("This class has multiple constructors, the primary one is:");
			// Note: interesting that constructors don't have getSimpleName()
			if (clazz.getConstructors()[0].getAnnotation(MadeBy.class) != null) {
				System.out.println("Constructor " + clazz.getConstructors()[0].getName() + ":");
				this.printMadeBy(this.getMadeBy(clazz.getConstructors()[0].getAnnotation(MadeBy.class)));
			}

			Method[] methods = clazz.getDeclaredMethods();
			// could use foreach for methods but that's prohibited, it's not streamable and
			// so we sadly must resort to fori instead
			for (int i = 0; i < methods.length; i++) {
				if (methods[i] != null) {
					if (methods[i].getAnnotation(MadeBy.class) != null) {
						System.out.println("Method " + methods[i].getName() + ":");
						this.printMadeBy(this.getMadeBy(methods[i].getAnnotation(MadeBy.class)));
					}
				}
			}
		}
		System.out.println("\n");
	}

	private MadeBy getMadeBy(Annotation annotation) {
		return (MadeBy) annotation;
	}

	private void printMadeBy(MadeBy madeBy) {
		System.out.println("	Made by: " + madeBy.member().toString());
		System.out.println("	Last modified: " + madeBy.lastModification());
	}

	@Before("execution (* Test.start(..))")
	public void preStart(JoinPoint joinpoint) {
		System.out.println("		Who-made-what:");
		// Note: since nothing in Aufgabe9Aspect is core it has no MadeBy annotation
		if (Test.class.isAnnotationPresent(MadeBy.class)) {
			System.out.println("Class " + Test.class.getSimpleName() + ":");
			this.printAnnotation(Test.class);
		}
		if (Population.class.isAnnotationPresent(MadeBy.class)) {
			System.out.println("Class " + Population.class.getSimpleName() + ":");
			this.printAnnotation(Population.class);
		}
		if (Person.class.isAnnotationPresent(MadeBy.class)) {
			System.out.println("Class " + Person.class.getSimpleName() + ":");
			this.printAnnotation(Person.class);
		}
		if (WishMap.class.isAnnotationPresent(MadeBy.class)) {
			System.out.println("Class " + WishMap.class.getSimpleName() + ":");
			this.printAnnotation(WishMap.class);
		}
		if (WishList.class.isAnnotationPresent(MadeBy.class)) {
			System.out.println("Class " + WishList.class.getSimpleName() + ":");
			this.printAnnotation(WishList.class);
		}
		if (Organization.class.isAnnotationPresent(MadeBy.class)) {
			System.out.println("Class " + Organization.class.getSimpleName() + ":");
			this.printAnnotation(Organization.class);
		}

		System.out.println("\n" + "		Preface:");
		System.out.println(
				"The stats for all the wishes are showing rather small changes from year to year because each wish starts out with \n single digit values in year 0 in order not to randomly have a huge variance after organizations advertising.");
		System.out.println(
				"This is especially true for industry representation averages since the organizations actively try to keep the value \n high while people wish for wishes that have been effectively advertised for.");
		System.out.println(
				"Amount of wishes per person is also relatively stable by design and in order to ensure the organizations products \n are ending up on peoples christmas lists peoples WishMaps have to be refilled periodically since 5 wishes per year \n are being granted (potentially even negative ones, everyone has received unwanted gifts sometimes)");
		System.out.println(
				"Newly created people also come with the same amount of wishes as the people created in year 0 \n and each year at the end of the year a portion of the population dies (on avg 15%) and new people are created (on avg 17%)  \n which leads to a small and not entirely unrealistic population growth each year.");
	}

	@Before("execution (* Population.yearEnd(..))")
	public void outputCensus(JoinPoint joinpoint) {
		this.printCensus();
	}

	@Before("execution (* WishList.yearEnd(..))")
	public void outputWishes(JoinPoint joinpoint) {
		this.printWishStats();
	}

	@After("execution (* WishList.yearEnd(..))")
	public void outputOrganization(JoinPoint joinpoint) {
		this.printRep();
	}

	/*
	 * Note:
	 * 
	 * Aspect is built so the Advice calls a print...() method which then looks at
	 * the objects in Test and calls necessary functions on these objects which can
	 * be found in the relevant classes. So output is generated here and the data
	 * which is being printed is calculated there (within non-static (!) functions
	 * and not here to preserve class cohesion).
	 */
	private void printCensus() {
		System.out
				.println("Census of population in Year " + Test.currentYear + " is " + Test.population.census() + ".");
	}

	private void printWishStats() {
		System.out.println("On average every person made " + Test.population.avgWishes() + " wishes this year.");
		System.out.println("These wishes have an average strengthof " + Test.population.avgDesires() + ".");
		System.out.println("On average " + Test.population.avgNegatives() + " negative wishes have been made.");
		System.out.println(
				"Excluding negative values the average strength is: " + Test.population.avgPositives() + ". \n");
	}

	private void printRep() {
		System.out.println("The industries products are represented by the populations wishes: ");
		Stream<Double> r = Test.industry.stream().map(Organization::representedWishes);
		r.forEach(System.out::println);
		System.out
				.println("(avg representation for each organizations products on each persons wishes, from org1 to org"
						+ Test.industry.size());
		System.out.println(" & highly dependent on the organizations portfolio size)" + "\n");
	}
}

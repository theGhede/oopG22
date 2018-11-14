package Aufgabe4;

public class Test {
	/* Two new Interfaces:
	 * 	- HerdMammal:   new addition for Bird-FlightlessBird-HerdAnimal-PackAnimal-Mammal cluster
	 * 					because some HerdAnimals are FliglessBirds (which are Birds) and some HerdAnimals are Mammals
	 * 					and all PackAnimals are HerdAnimals and Mammals - HerdMammal helps distinguish HerdAnimals (Bird/Mammal)
	 * 	- ShoalingFish: without this either no or all Fish would have to be swarming
	 * */
	
	/*
	 * potentiell nicht bestehende Untertypenbeziehungen:
	 * 
	 * 		- Überall wo Vererbungen vorkommen haben wir Java subtypes, welche auf Ersetzbarkeit entsprechend LSP
	 * 		  überprüft werden. Genauer betrachten wir hier HerdAnimal und HerdAnimal.getAlpha() (& all dessen subtypes)
	 * 		  und Animal (in Betracht auf Zusicherungen von air, water, ground und subtypes von Animal).
	 * 		  Im ersten Fall konnten wir eine bestehende Untertypenbeziehung sicherstellen (new HerdMammal & HerdAnimal
	 * 		  als Interface für FlightlessBird & HerdMammal).
	 * 		  Im zweiten Fall ist Ersetzbarkeit auch erfüllt, da air(), water(), ground() Zusicherungen, aber keinerlei
	 * 		  Vorbedingungen besitzen (Preconditions sind also equally strict für super- & subtypes; nämlich nicht gegeben)
	 * 											
	 * 		- alle Typen welche nicht auf dem selben Vererbungs-Pfad liegen (z.B. MigratoryLocust & PackAnimal);
	 * 		  da Untertypen Vererbung vorraussetzen
	 */
	
	// to see if an object implements one of the interfaces
	public static void imp (Class inter, Object object) {
		if (inter != null) {
			System.out.println("Interface " + inter.getSimpleName() + " is implemented by " + object.getClass().getSimpleName() + " :  " + inter.isInstance(object) + "\n");
		} else {
			System.out.println("null");
		}
	}
	
	// to check inheritance between interfaces
	public static void superface (Class sup, Class sub) {
		if (sup != null && sub != null) {
			System.out.println("Interface " + sup.getSimpleName() + " is a superinterface of " + sub.getSimpleName() + " :  " + sup.isAssignableFrom(sub) + "\n");
		} else {
			System.out.println("One of the parameters is null.");
		}
	}
	
	// list all superinterfaces of an interface
	public static void listInterfaces(Class inter) {
		System.out.println("Superinterfaces of interface " + inter.getSimpleName() + ":");
		System.out.println(listface(inter) + "\n");
	}
	
	public static String listface (Class inter) {
		String s = "";
		for (int i = 0; i < inter.getInterfaces().length; i++) {
			s +=  inter.getInterfaces()[i].getSimpleName();
			s += ", ";
		}
		for (int i = 0; i < inter.getInterfaces().length; i++) {
			if (inter.getInterfaces()[i].getInterfaces().length != 0) {
				s += listface(inter.getInterfaces()[i]);
			}
		}
		return s;
	}

	public static void main(String[] args) {
		// Wolf, Whale, Penguin
		Wolf wolf = new Wolf();	Whale whale = new Whale(); Penguin pengu = new Penguin();
		
		System.out.println("Taking a look at PackAnimals & HerdAnimals with a wolf object: \n");
		listInterfaces(PackAnimal.class);
		superface(HerdAnimal.class, PackAnimal.class);
		imp(PackAnimal.class, wolf);
		imp(HerdAnimal.class, wolf);
		System.out.println(wolf.getAlpha().getClass().getSimpleName());
		imp(HerdAnimal.class, wolf.getAlpha());
		
		
		System.out.println("Whale as an example of SchoolAnimal: \n");
		listface(SchoolAnimal.class);
		imp(SchoolAnimal.class, whale);
		superface(Mammal.class, SchoolAnimal.class);
		superface(SocialAnimal.class, SchoolAnimal.class);
		imp(HerdAnimal.class, whale);
		superface(Animal.class, SchoolAnimal.class);
		
		System.out.println("Penguins are FlighlessBirds, which are Birds and HerdAnimals but no Mammals \n");
		listInterfaces(FlightlessBird.class);
		imp(FlightlessBird.class, pengu);
		superface(Bird.class, FlightlessBird.class);
		superface(HerdAnimal.class, FlightlessBird.class);
		imp(Mammal.class, pengu);
		System.out.println("Obviously there are Birds that are not FlightlessBirds as well:");
		superface(FlightlessBird.class, Bird.class);
		System.out.println("But whereever we require a Bird object a FlightlessBird would still do because a lack of preconditions. \n");
		
		System.out.println("PackAnimals are Mammals and HerdAnimals but no Birds \n");
		superface(Mammal.class, PackAnimal.class);
		superface(HerdAnimal.class, PackAnimal.class);
		superface(Bird.class, PackAnimal.class);
		
		System.out.println("Mammals are Animals and HerdMammals are SocialAnimals but not all Mammals are social \n");
		superface(Animal.class, Mammal.class);
		superface(SocialAnimal.class, HerdMammal.class);
		superface(SocialAnimal.class, Mammal.class);
		
	}
}

/* TODO:  Beschreibung wer an welchem Teil gearbeitet hat, entsprechend der Angabe
 * Elias Nachbaur (01634010): UML & structuring
 * Florian Fusstetter (00709759): UML & structuring, structural adjustments to adhere to Liskov Substitution Principle,
 * 								  test methods/objects/cases, conditions (except Animal)
 * Ignjat Karanovic (01529940): UML & structuring, conditions for Animal
 */
package Aufgabe4;

public class Test {
	/* Two new Interfaces:
	 * 	- HerdMammal:   new addition for Bird-FlightlessBird-HerdAnimal-PackAnimal-Mammal cluster
	 * 					because some HerdAnimals are FliglessBirds (which are Birds) and some HerdAnimals are Mammals
	 * 					and all PackAnimals are HerdAnimals and Mammals - HerdMammal helps distinguish HerdAnimals (Bird/Mammal)
	 * 	- ShoalingFish: without this either no or all Fish would have to be swarming
	 * */
	
	/*
	 * TODO: CHECK:
	 * nicht bestehende Untertypenbeziehungen:
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
		/* TODO: make test classes & test them
		 * 		 decide which ones to keep and use */
		// Wolf, Whale, Locust, Penguin, TestBird, Sardine
		Wolf wolf = new Wolf();	Whale whale = new Whale(); Locust locust = new Locust();
		Penguin pengu = new Penguin(); TestBird bird = new TestBird(); Sardine sardine = new Sardine();
		TestInsect insect = new TestInsect(); TestMammal mammal = new TestMammal();
		
		System.err.println("Taking a look at PackAnimals & HerdAnimals with a wolf object: \n");
		listInterfaces(PackAnimal.class);
		superface(HerdAnimal.class, PackAnimal.class);
		imp(PackAnimal.class, wolf);
		imp(HerdAnimal.class, wolf);
		System.out.println(wolf.getAlpha().getClass().getSimpleName());
		imp(HerdAnimal.class, wolf.getAlpha());
		
		
		System.err.println("Whale as an example of SchoolAnimal: \n");
		imp(SocialAnimal.class, whale);
		imp(Mammal.class, whale);
		imp(SchoolAnimal.class, whale);
		imp(PackAnimal.class, whale);
		superface(Animal.class, SchoolAnimal.class);
		
		System.err.println("Penguins are FlighlessBirds, which are Birds and HerdAnimals but no Mammals \n");
		listInterfaces(FlightlessBird.class);
		imp(FlightlessBird.class, pengu);
		imp(Bird.class, pengu);
		imp(HerdAnimal.class, pengu);
		imp(Mammal.class, pengu);
		System.out.println("Obviously there are Birds that are not FlightlessBirds as well");
		superface(FlightlessBird.class, Bird.class);
		
		System.err.println("PackAnimals are Mammals and HerdAnimals but no Birds\n");
		superface(Mammal.class, PackAnimal.class);
		superface(HerdAnimal.class, PackAnimal.class);
		superface(Bird.class, PackAnimal.class);
		
		System.err.println("Mammals are Animals and HerdMammals are SocialAnimals but not all Mammals are social \n");
		superface(Animal.class, Mammal.class);
		superface(SocialAnimal.class, HerdMammal.class);
		superface(SocialAnimal.class, Mammal.class);
		
	}
}

/* TODO:  Beschreibung wer an welchem Teil gearbeitet hat, entsprechend der Angabe
 * Elias Nachbaur (01634010): 
 * Florian Fusstetter (00709759): 
 * Ignjat Karanovic (01529940):
 */
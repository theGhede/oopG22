package Aufgabe4;

public class Test {
	/* Two new Interfaces:
	 * 	- HerdMammal:   new addition for Bird-FlightlessBird-HerdAnimal-PackAnimal-Mammal cluster
	 * 					because some HerdAnimals are FliglessBirds (which all are Birds) and some HerdAnimals are Mammals
	 * 					and all PackAnimals are HerdAnimals and Mammals - HerdMammal helps distinguish HerdAnimals (Bird/Mammal)
	 * 	- ShoalingFish: without this either no or all Fish would have to be swarming
	 * */
	
	/*
	 * TODO:
	 * nicht bestehende Untertypenbeziehungen:
	 * 		- HerdAnimal & HerdMammal / FlightlessBird: Vererbung vorhanden, aber keine Untertypen, da getAlpha() einen return type
	 * 													des ensprechenden Tiers hat (Ersetzbarkeit also nur möglich falls
	 * 													HerdAnimal = HerdMammal [bzw. FlightlessBird] gilt)
	 * 		- SocialAnimal & HerdAnimal:				"HerdAnimal getAlpha()" stört Ersetzbarkeit;
	 * 													
	 * 
	 * 		- alle Typen welche nicht auf dem selben Vererbungs-Pfad liegen (z.B. MigratoryLocust & PackAnimal);
	 * 		  da Untertypen Vererbung vorraussetzen
	 */

	public static void main(String[] args) {
		// TODO: make test classes & test them
		// TODO: decide which ones and how abstract they should be
	}
}

/* TODO:  Beschreibung wer an welchem Teil gearbeitet hat, entsprechend der Angabe
 * Elias Nachbaur (01634010): 
 * Florian Fusstetter (00709759): 
 * Ignjat Karanovic (01529940):
 */
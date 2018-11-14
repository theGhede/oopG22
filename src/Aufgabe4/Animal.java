package Aufgabe4;

public interface Animal {

	// Postconditions for each method
	int air();
	/* assertion { stundenAnzahl >= 0 } */


	int water();
	/* assertion { stundenAnzahl >= 0 } */


	int ground();
	/* assertion { stundenAnzahl >= 0 } */
	
	// Nachbedingung - min. einer der drei Erwartungswerte ist größer als 0
}

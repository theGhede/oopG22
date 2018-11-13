package Aufgabe4;

public interface Animal {

	/* assertion { stundenAnzahl >= 0 } */
	int air();

	/* assertion { stundenAnzahl >= 0 } */
	int water();

	/* assertion { stundenAnzahl >= 0 } */
	int ground();
	
	// Nachbedingung - min. einer der drei Erwartungswerte ist größer als 0
}

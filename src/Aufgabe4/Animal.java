package Aufgabe4;

public interface Animal {

	// Server-controlled History Constraints for each method
	/*
	 * Note: Diese Zusicherung gilt auch für alle Untertypen von Animal, ist bei
	 * 		 vielen Untertypen gleich aber bei anchen der Untertypen strenger (z.B. Fish).
	 * 		 Dies ist für Ersetzbarkeit kein Problem, da es sich hier nicht um
	 * 		 Vorbedingungen handelt (-> equal or less strict Precondition & guarantees
	 * 		 same or more)
	 */
	int air();
	/* assertion { expected time spent >= 0 } */

	int water();
	/* assertion { expected time spent >= 0 } */

	int ground();
	/* assertion { expected time spent >= 0 } */

	// Nachbedingung - min. einer der drei Erwartungswerte ist größer als 0
}
